/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.User;
import Model.UserDAO;
import View.LogInFrame;
import javax.swing.JOptionPane;
import View.NewJFrame;



/**
 *
 * @author ALIENWARE
 */
public class LogInController {

    private LogInFrame loginFrame;
    private UserDAO userDAO;

    // Pass the existing UserDAO from Main
    public LogInController(LogInFrame loginFrame, UserDAO userDAO) {
        this.loginFrame = loginFrame;
        this.userDAO = userDAO; // use the same instance
        initController();
    }

    private void initController() {
        loginFrame.getLoginButton().addActionListener(e -> handleLogin());
    }

    public void handleLogin() {
        
        String username = loginFrame.getUsernameField().getText().trim();
        String password = new String(loginFrame.getPasswordField().getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginFrame, "Please enter username and password.", "Login Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        

        User user = userDAO.validateLogin(username, password); //DAO hashes password

        if (user == null) {
            JOptionPane.showMessageDialog(loginFrame, "Invalid login credentials or account not approved.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!"admin".equalsIgnoreCase(user.getRole())) {
            JOptionPane.showMessageDialog(loginFrame, "Only admin can login here.", "Access Denied", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(loginFrame, "Login successful! Opening dashboard...", "Welcome", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        NewJFrame dashboard = new NewJFrame();
        dashboard.setVisible(true);

        openDashboard(user);
    }
    
    public void openDashboard(User user){        
        //create dashboard view
        NewJFrame dashboardView = new NewJFrame();
        dashboardView.setLocationRelativeTo(null);
        dashboardView.setVisible(true);
        
        //create dashboard controller
        DashboardController dashboardController = new DashboardController(
        dashboardView, userDAO, user);
        
        //close login frame
        loginFrame.dispose();
            }
}
