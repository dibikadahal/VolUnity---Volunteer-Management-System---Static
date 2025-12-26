/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Volunteer;
import Model.DataManager;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import View.AdminDashboard;


public class AdminController {
 private JFrame view;
 
 public AdminController(JFrame view){
     this.view = view;
 }
 
 
 //======APPROVE VOLUNTEERS==========
public boolean approveVolunteer(Volunteer volunteer) {
        Volunteer first = DataManager.front();

        if (first == null) {
            JOptionPane.showMessageDialog(view,
                    "No pending volunteers in queue.",
                    "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        // enforce FIFO
        if (!first.getUsername().equals(volunteer.getUsername())) {
            JOptionPane.showMessageDialog(view,
                    "Please process volunteers in order!\n\n"
                    + "First in queue: " + first.getFullName(),
                    "Queue Order Required", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Accept volunteer: " + first.getFullName() + "?\n\n"
                + "Registration Date: " + first.getRegistrationDateTime(),
                "Confirm Accept", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean approved = DataManager.approveVolunteer(first.getUsername());
            if (approved) {
                JOptionPane.showMessageDialog(view,
                        "✓ " + first.getFullName() + " APPROVED!",
                        "Volunteer Approved", JOptionPane.INFORMATION_MESSAGE);
                
                //add to the approved volunteer table
                ((AdminDashboard) view).addApprovedVolunteerToTable(volunteer);
                return true;
            }
            return approved;
        }

        return false;
    }



 //================DECLINE VOLUNTEERS================
// ====== DECLINE VOLUNTEER ==========
public boolean declineVolunteer(Volunteer volunteer) {

        Volunteer first = DataManager.front();

        if (first == null) {
            JOptionPane.showMessageDialog(view,
                    "No pending volunteers in queue.",
                    "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        // Enforce FIFO
        if (!first.getUsername().equals(volunteer.getUsername())) {
            JOptionPane.showMessageDialog(view,
                    "Please process volunteers in order!\n\n"
                    + "First in queue: " + first.getFullName(),
                    "Queue Order Required", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String reason = JOptionPane.showInputDialog(view,
                "Decline volunteer: " + first.getFullName() + "\n\n"
                + "Optional: Enter reason (or leave blank):",
                "Confirm Decline", JOptionPane.WARNING_MESSAGE);

        if (reason == null) {
            return false;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to DECLINE?\n\n"
                + "Volunteer: " + first.getFullName(),
                "Final Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (DataManager.declineVolunteer(first.getUsername())) {
                JOptionPane.showMessageDialog(view,
                        "✗ " + first.getFullName() + " DECLINED.",
                        "Volunteer Declined", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        return false;
    }
}

