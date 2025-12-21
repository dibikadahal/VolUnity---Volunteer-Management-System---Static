/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VolUnityMain;

import View.LogInFrame;
import Controller.LogInController;
import Model.User;
import Model.UserDAO;
import java.util.logging.Logger;

/**
 *
 * @author ALIENWARE
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Set Look and Feel (optional)
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.severe("Failed to set LookAndFeel: " + ex.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> {
            try{
                   // Initialize DAO
                 UserDAO userDAO = new UserDAO();

            // Create admin if not exists
                if (!userDAO.usernameExists("Dibika")) {
                    User admin = new User(
                            "Dibika",
                            userDAO.hashPassword("Admin1"),
                            "admin"
                    );
                    userDAO.addUser(admin);
                    System.out.println("✓ Admin user created: Dibika/Admin1");
                }else{
                    System.out.println("Admin user already exists");
                }
                
                
                //print allusers 
                System.out.println("\n=====Allusers in the system=======");
                for (User u : userDAO.getAllUsers()){
                    System.out.println(" " + u.getUsername() + " : " +u.getRole());
                }
                System.out.println("===========================\n");

                    //create and show login frame
                    LogInFrame loginFrame = new LogInFrame();
                    loginFrame.setLocationRelativeTo(null); //create on center
                    loginFrame.setVisible(true);
                    
                    //to connect controller with UserDAO
                    new LogInController(loginFrame, userDAO);
                    
                    System.out.println("Login screen successfully loaded");
            } catch (Exception e){
                logger.severe("Error initializing application: " + e.getMessage());
                e.printStackTrace();
                
                javax.swing.JOptionPane.showMessageDialog(
                        null,
                        "Failed to initialize application: " + e.getMessage(),
                        "Startup Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                                );
            }
        });
    }
}