/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Volunteer;
import Model.DataManager;
import Model.PasswordUtil;
import Model.ValidationUtil;
import View.AdminDashboard;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.util.List;


/**
 * CRUD Controller - for handling create, read, update and delete operations
 */
public class VolunteerCRUDController {
    private AdminDashboard dashboard;
    private InsertionSort sortController;
    private LinearSearch searchController;
    
    public VolunteerCRUDController(AdminDashboard dashboard) {
    this.dashboard = dashboard;
    this.sortController = new InsertionSort();
    this.searchController = new LinearSearch();
    }
   
    
    /**
     * Get all approved volunteers
     */
    public LinkedList<Volunteer> getAllApprovedVolunteers() {
        return DataManager.getApprovedVolunteers();
    }

    /**
     * Get volunteer by ID
     */
    public Volunteer getVolunteerById(String volunteerId) {
        return DataManager.getApprovedVolunteerById(volunteerId);
    }

    /**
     * View volunteer details - Reuses VolunteerDetailsDialog
     */
    public void viewVolunteer(String volunteerId) {
        System.out.println("DEBUG - Attempting to view volunteer ID: " + volunteerId);

        Volunteer volunteer = DataManager.getApprovedVolunteerById(volunteerId);
        
        if (volunteer == null) {
            System.out.println("DEBUG - Volunteer is NULL");
            // Delegate to view to show the dialog
            LinkedList<Volunteer> all = DataManager.getApprovedVolunteers();
            System.out.println("DEBUG - Total approved volunteers: " + all.size());
            for (Volunteer v : all) {
                System.out.println("  - ID: '" + v.getVolunteerId() + "', Name: " + v.getFullName());
            }
        } else {
            System.out.println("DEBUG - Volunteer FOUND: " + volunteer.getFullName());
        }
        
        if (volunteer != null) {
            dashboard.showVolunteerDetails(volunteer);
        } else {
            dashboard.showError("Volunteer not found!", "Error");
        }
    }

    /**
     * Delete approved volunteer
     */
   public boolean deleteVolunteer(String volunteerId) {
   System.out.println("DEBUG - Attempting to delete volunteer ID: " + volunteerId);

    Volunteer volunteer = DataManager.getApprovedVolunteerById(volunteerId);

    if (volunteer == null) {
        System.out.println("DEBUG - Cannot delete, volunteer not found!");
        dashboard.showError("Volunteer not found!", "Error");
        return false;
    }

    // Ask for confirmation
    boolean confirmed = dashboard.confirmDelete(
            "Are you sure you want to delete volunteer: " + volunteer.getFullName() + "?"
    );

    if (confirmed) {
        boolean success = DataManager.deleteApprovedVolunteer(volunteerId);

        if (success) {
            // ✅ ADD THIS LINE - Log the action
            dashboard.getActionStackController().logVolunteerDeleted(volunteer);
            
            dashboard.showSuccess("Volunteer deleted successfully!", "Success");
            dashboard.refreshApprovedVolunteerTable();
            dashboard.refreshDashboardStats();
            return true;
        } else {
            dashboard.showError("Failed to delete volunteer!", "Error");
            return false;
        }
    }

    return false;
}

    //============CREATE - Add new volunteer=====================
    public boolean createVolunteer(String fullName, String dob, String gender, String contact, String email, String education,
            String skills, String experience, String username, String password) {

        //Validate all fields
        String error = validateVolunteerData(fullName, dob, gender, contact, email, education, skills, experience, username, password);

        if (error != null) {
            JOptionPane.showMessageDialog(dashboard, error, "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //Hash password
        String hashedPassword = PasswordUtil.hash(password);

        //Create volunteer object
        Volunteer volunteer = new Volunteer(
                fullName.trim(), dob.trim(), gender,
                contact.trim(), email.trim(), education.trim(),
                skills.trim(), experience.trim(),
                username.trim(), hashedPassword, "user"
        );

        //Add directly (bypass queue for admin created volunteers)
        if (DataManager.addVolunteerDirect(volunteer)) {
            JOptionPane.showMessageDialog(dashboard, "Volunteer added successfully!\n\n"
                    + "Name: " + fullName + "\n"
                    + "Username: " + username,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(dashboard, "Failed to add volunteer.\nUsername may already exist.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //================READ - Get all volunteers==================
    public List<Volunteer> getAllVolunteers() {
        return DataManager.getAllVolunteers();
    }

    /*
    //READ=Search volunteer
    public List<Volunteer> searchVolunteers(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllVolunteers();
        }
        return DataManager.searchVolunteers(searchTerm);
    }
     */
    //===========UPDATE - Modify existing volunteer details===============
    public boolean updateVolunteer(String originalUsername, String fullName, String dob, String gender, String contact,
            String email, String education, String skills, String experience) {

        //get existing volunteers
        Volunteer existing = DataManager.getVolunteerByUsername(originalUsername);
        if (existing == null) {
            JOptionPane.showMessageDialog(dashboard, "Volunteer not found", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //validate updated volunteer (skip username / password validation)
        String error = validateVolunteerDataForUpdate(fullName, dob, gender, contact, email, education, skills, experience);

        if (error != null) {
            JOptionPane.showMessageDialog(dashboard, error, "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //create updated volunteer (keep same username and password)
        Volunteer updated = new Volunteer(
                fullName.trim(), dob.trim(), gender, contact.trim(), email.trim(), education.trim(),
                skills.trim(), experience.trim(),
                existing.getUsername(), existing.getPassword(), "user"
        );

        //Update in DataManager
        if (DataManager.updateVolunteers(originalUsername, updated)) {
            JOptionPane.showMessageDialog(dashboard, "Volunteer updated successfully!\n\n"
                    + "Name: " + fullName,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(dashboard, "Failed to update volunteer",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //=============DELETE - Remove volunteer===================
    public boolean deleteVolunteerbyUsername(String username, String fullName) {
        int confirm = JOptionPane.showConfirmDialog(dashboard,
                "Are you sure you want to DELETE this volunteer?\n\n"
                + "Name: " + fullName + "\n"
                + "Username: " + username + "\n\n"
                + "This action cannot be undone!",
                "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        //IF YES
        if (confirm == JOptionPane.YES_OPTION) {
            if (DataManager.deleteVolunteer(username)) {
                JOptionPane.showMessageDialog(dashboard,
                        "Volunteer deleted successfully!\n\n"
                        + "Name: " + fullName,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(dashboard, "Failed to delete volunteer.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    //Helper: Validate all volunteer data (for CREATE)
    private String validateVolunteerData(String fullName, String dob, String gender,
            String contact, String email, String education,
            String skills, String experience,
            String username, String password) {
        String error;

        if ((error = ValidationUtil.validateFullName(fullName)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateDOB(dob)) != null) {
            return error;
        }
        if ("Gender".equals(gender)) {
            return "Please select a gender";
        }
        if ((error = ValidationUtil.validateContact(contact)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateEmail(email)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateEducation(education)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateSkills(skills)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateExperience(experience)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateUsername(username)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validatePassword(password)) != null) {
            return error;
        }

        return null;
    }

    // Helper: Validate volunteer data (for UPDATE - no username/password check)
    private String validateVolunteerDataForUpdate(String fullName, String dob, String gender,
            String contact, String email, String education,
            String skills, String experience) {
        String error;

        if ((error = ValidationUtil.validateFullName(fullName)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateDOB(dob)) != null) {
            return error;
        }
        if ("Gender".equals(gender)) {
            return "Please select a gender";
        }
        if ((error = ValidationUtil.validateContact(contact)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateEmail(email)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateEducation(education)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateSkills(skills)) != null) {
            return error;
        }
        if ((error = ValidationUtil.validateExperience(experience)) != null) {
            return error;
        }

        return null;
    }
    
    public void refreshApprovedVolunteerTable(){
        DefaultTableModel model = (DefaultTableModel) dashboard.getVolunteerTable().getModel();
        model.setRowCount(0);
        
        for (Volunteer v : DataManager.getApprovedVolunteers()) {
            model.addRow(new Object[]{
                v.getVolunteerId(),
                v.getFullName(),
                v.getContactNumber(),
                v.getEmail(),
             "Approved",
                v.getUsername()
            });
        }
  }
    
    //================SORTING==========================
    //=--------------get volunteers sorted by name in ascending order----------------]
    public LinkedList<Volunteer> getVolunteersSortedByNameAscending(){
        LinkedList<Volunteer> volunteers = DataManager.getApprovedVolunteers();
        return sortController.sortByNameAscending(volunteers);
    }
    
    //=--------------get volunteers sorted by name in descending order----------------]
    public LinkedList<Volunteer> getVolunteersSortedByNameDescending() {
        LinkedList<Volunteer> volunteers = DataManager.getApprovedVolunteers();
        return sortController.sortByNameDescending(volunteers);
    }
    
    /**
     * Demonstrate sorting process (optional - for testing)
     */
   public void demonstrateSorting(boolean ascending) {
        LinkedList<Volunteer> volunteers = DataManager.getApprovedVolunteers();
        sortController.demonstrateSorting(volunteers, ascending);
    }
   
   
   //===================SEARCHING===========================
   //Search volunteers by name (partial match)
   public LinkedList<Volunteer> searchVolunteersByName(String searchTerm){
       LinkedList<Volunteer> volunteers = DataManager.getApprovedVolunteers();
       return searchController.searchByPartialName(volunteers, searchTerm);
   }
   
   //search volunteers by multiple criteria  (name, email, contact)
   public LinkedList<Volunteer> searchVolunteers(String searchTerm) {
        LinkedList<Volunteer> volunteers = DataManager.getApprovedVolunteers();
        return searchController.searchByMultipleCriteria(volunteers, searchTerm);
    }
   
   //search for exact name match
    public Volunteer searchExactName(String name) {
        LinkedList<Volunteer> volunteers = DataManager.getApprovedVolunteers();
        return searchController.searchByExactName(volunteers, name);
    }
    
    //search by id
    public Volunteer searchById(int volunteerId) {
        LinkedList<Volunteer> volunteers = DataManager.getApprovedVolunteers();
        return searchController.searchById(volunteers, volunteerId);
    }
    
    //demonstrate linear search process
    public void demonstrateSearch(String searchName) {
        LinkedList<Volunteer> volunteers = DataManager.getApprovedVolunteers();
        searchController.demonstrateLinearSearch(volunteers, searchName);
    }  
}
