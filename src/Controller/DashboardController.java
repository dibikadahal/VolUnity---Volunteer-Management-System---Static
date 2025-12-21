/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.User;
import Model.UserDAO;
import View.NewJFrame;
import javax.swing.JOptionPane;

/**
DashboardController handles all dashboard-related logic
* Responsibilities:
* - initialize dashboard with user information
* - manage dashboard display and updates
* - handle dashboard-specific user interactions
 */
public class DashboardController {
    private NewJFrame dashboardView;
    private UserDAO userDAO;
    private User currentUser;
    
    public DashboardController(NewJFrame dashboardView, UserDAO userDAO, User currentUser){
        this.dashboardView = dashboardView;
        this.userDAO = userDAO;
        this.currentUser = currentUser;
        
        initializeDashboard();
    }
    
private void initializeDashboard(){    
    //show the appropriate dashboard based on role
    dashboardView.showDashboard(currentUser.getRole(), currentUser.getUsername());
    
    //setup any additional listeners 
    setupEventListeners();
    }

//set up event listweners for dashboard components
private void setupEventListeners(){
    // add dashboard specific listeners - refresh buttons, data updates etc
        }

//get the current login user
public User getCurrentUser(){
    return currentUser;
}

//clean uo resources when dashboard is cleaned
public void cleanup(){
    dashboardView.stopDateTimeDisplay();
}
}
