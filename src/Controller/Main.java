/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import View.LogInFrame;
import Model.DataManager;
import javax.swing.UIManager;

/**
 *This class is the entry point for VolUnity application
 * It initializes the data and launches login screen
 */
public class Main {
    
    public static void main(String[] args) {
        // Set Look and Feel 
                setLookAndFeel();  //change the behaviour and appearance of the UI
        
           //INITIALIZE IN MEMORY DATA
           DataManager.initialize();
           
           //launch login screen
           java.awt.EventQueue.invokeLater(() -> {
                LogInFrame loginFrame = new LogInFrame();
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setVisible(true);
    });
    }
    
    
    private static void setLookAndFeel(){
        try{
            for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
        }
    }catch(Exception e){
        e.printStackTrace();
        }
    }
 }