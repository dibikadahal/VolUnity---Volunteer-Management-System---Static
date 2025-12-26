/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Volunteer;
import Model.DataManager;
import Model.PasswordUtil;
import Model.ValidationUtil;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.List;

/**
 *CRUD Controller - for handling create, read, update and delete operations
 */
public class VolunteerCRUDController {
    
    private JFrame view;
    
    public VolunteerCRUDController(JFrame view){
        this.view = view;
    }
    
    
    
    //============CREATE - Add new volunteer=====================
    public boolean createVolunteer (String fullName, String dob, String gender, String contact, String email, String education,
            String skills, String experience, String username, String password){
        
        //Validate all fields
        String error = validateVolunteerData(fullName, dob, gender, contact, email, education, skills, experience, username, password);
        
        if (error != null){
            JOptionPane.showMessageDialog(view, error, "Validation Error", JOptionPane.ERROR_MESSAGE);
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
        if(DataManager.addVolunteerDirect(volunteer)){
            JOptionPane.showMessageDialog(view, "Volunteer added successfully!\n\n" +
                    "Name: " + fullName + "\n" +
                    "Username: " + username,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }else{
            JOptionPane.showMessageDialog (view, "Failed to add volunteer.\nUsername may already exist.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } 
    }
    
    
    
    //================READ - Get all volunteers==================
    public List<Volunteer> getAllVolunteers(){
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
            String email, String education, String skills, String experience){
        
        //get existing volunteers
        Volunteer existing = DataManager.getVolunteerByUsername(originalUsername);
        if (existing == null) {
            JOptionPane.showMessageDialog(view, "Volunteer not found", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        //validate updated volunteer (skip username / password validation)
          String error = validateVolunteerDataForUpdate(fullName, dob, gender, contact, email, education, skills, experience);
          
          if(error != null){
              JOptionPane.showMessageDialog(view, error, "Validation Error", JOptionPane.ERROR_MESSAGE);
              return false;
          }
          
          //create updated volunteer (keep same username and password)
          Volunteer updated = new Volunteer(
          fullName.trim(), dob.trim(), gender, contact.trim(), email.trim(), education.trim(),
                  skills.trim(), experience.trim(),
                  existing.getUsername(), existing.getPassword(), "user"
          );
          
          //Update in DataManager
          if (DataManager.updateVolunteers(originalUsername, updated)){
              JOptionPane.showMessageDialog(view, "Volunteer updated successfullt!\n\n" + 
                      "Name: " + fullName, 
                      "Success", JOptionPane.INFORMATION_MESSAGE);
              return true;
          }else{
              JOptionPane.showMessageDialog(view, "Failed to update volunteer",
                      "Error", JOptionPane.ERROR_MESSAGE);
              return false;
          }
    }
    
    
    
    //=============DELETE - Remove volunteer===================
    public boolean deleteVolunteer (String username, String fullName){
        int confirm = JOptionPane.showConfirmDialog(view, 
                "Are you sure you want to DELETE this volunteer?\n\n" + 
                        "Name: " + fullName + "\n" +
                        "Username: " + username + "\n\n" +
                        "This action cannot be undone!",
                "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        //IF YES
        if(confirm == JOptionPane.YES_OPTION){
            if (DataManager.deleteVolunteer(username)){
                JOptionPane.showMessageDialog(view,
                        "Volunteer deleted successfully!\n\n" +
                                "Name: " + fullName,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }else{
                JOptionPane.showMessageDialog(view, "Failed to delete volunteer.",
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
        
        if ((error = ValidationUtil.validateFullName(fullName)) != null) return error;
        if ((error = ValidationUtil.validateDOB(dob)) != null) return error;
        if ("Gender".equals(gender)) return "Please select a gender";
        if ((error = ValidationUtil.validateContact(contact)) != null) return error;
        if ((error = ValidationUtil.validateEmail(email)) != null) return error;
        if ((error = ValidationUtil.validateEducation(education)) != null) return error;
        if ((error = ValidationUtil.validateSkills(skills)) != null) return error;
        if ((error = ValidationUtil.validateExperience(experience)) != null) return error;
        if ((error = ValidationUtil.validateUsername(username)) != null) return error;
        if ((error = ValidationUtil.validatePassword(password)) != null) return error;
        
        return null;
    }
    
    // Helper: Validate volunteer data (for UPDATE - no username/password check)
    private String validateVolunteerDataForUpdate(String fullName, String dob, String gender,
                                                 String contact, String email, String education,
                                                 String skills, String experience) {
        String error;
        
        if ((error = ValidationUtil.validateFullName(fullName)) != null) return error;
        if ((error = ValidationUtil.validateDOB(dob)) != null) return error;
        if ("Gender".equals(gender)) return "Please select a gender";
        if ((error = ValidationUtil.validateContact(contact)) != null) return error;
        if ((error = ValidationUtil.validateEmail(email)) != null) return error;
        if ((error = ValidationUtil.validateEducation(education)) != null) return error;
        if ((error = ValidationUtil.validateSkills(skills)) != null) return error;
        if ((error = ValidationUtil.validateExperience(experience)) != null) return error;
        
        return null;
}
}
