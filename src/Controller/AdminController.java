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
        Volunteer firstInQueue = DataManager.front();

        if (firstInQueue == null) {
            JOptionPane.showMessageDialog(view,
                    "No pending volunteers in queue.",
                    "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        // enforce FIFO
        if (!firstInQueue.getUsername().equals(volunteer.getUsername())) {
            JOptionPane.showMessageDialog(view,
                    "Please process volunteers in order!\n\n"
                    + "First in queue: " + firstInQueue.getFullName(),
                    "Queue Order Required", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Accept volunteer: " + firstInQueue.getFullName() + "?\n\n"
                + "Registration Date: " + firstInQueue.getRegistrationDateTime(),
                "Confirm Accept", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean approved = DataManager.approveVolunteer(firstInQueue.getUsername());
            if (approved) {
                JOptionPane.showMessageDialog(view,
                        "✓ " + firstInQueue.getFullName() + " APPROVED!",
                        "Volunteer Approved", JOptionPane.INFORMATION_MESSAGE);
                
                //add to the approved volunteer table
                if (view instanceof AdminDashboard){
                    ((AdminDashboard) view).addApprovedVolunteerToTable(firstInQueue);
                }
                return true;
            }
        }
        return false;
    }



 //================DECLINE VOLUNTEERS================
// ====== DECLINE VOLUNTEER ==========
public boolean declineVolunteer(Volunteer volunteer) {
        Volunteer firstInQueue = DataManager.front();

        if (firstInQueue == null) {
            JOptionPane.showMessageDialog(view,
                    "No pending volunteers in queue.",
                    "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        // Enforce FIFO
        if (!firstInQueue.getUsername().equals(volunteer.getUsername())) {
            JOptionPane.showMessageDialog(view,
                    "Please process volunteers in order!\n\n"
                    + "First in queue: " + firstInQueue.getFullName(),
                    "Queue Order Required", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String reason = JOptionPane.showInputDialog(view,
                "Decline volunteer: " + firstInQueue.getFullName() + "\n\n"
                + "Optional: Enter reason (or leave blank):",
                "Confirm Decline", JOptionPane.WARNING_MESSAGE);

        if (reason == null) {
            return false;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to DECLINE?\n\n"
                + "Volunteer: " + firstInQueue.getFullName(),
                "Final Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (DataManager.declineVolunteer(firstInQueue.getUsername())) {
                JOptionPane.showMessageDialog(view,
                        "✗ " + firstInQueue.getFullName() + " DECLINED.",
                        "Volunteer Declined", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        return false;
    }
}

