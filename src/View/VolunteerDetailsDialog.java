/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import Model.SignUpUser;
import java.awt.*;

/**
 *
 * @author ALIENWARE
 */
public class VolunteerDetailsDialog extends JDialog{
    
    private SignUpUser volunteer;
    
    public VolunteerDetailsDialog(JFrame parent, SignUpUser volunteer){
        super(parent, "Volunteer Details", true); //Modal dialog
        this.volunteer = volunteer;
        
        initComponents();
        setLocationRelativeTo(parent); //center on parent
    }
    
    private void initComponents(){
        setSize(600, 700);
        setResizable(false);
        setUndecorated(true); //remove windoe deoration for modern look
        
        //Main panel with white background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(91, 158, 165), 3),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        //Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(91, 158, 165));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Volunteer Details");
        titleLabel.setFont(new Font("Sitka Text", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        //Details Panel (Scrollable)
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        //add all the details
        addDetailRow(detailsPanel, "Full Name:", volunteer.getFullName());
        addDetailRow(detailsPanel, "Username: ", volunteer.getUsername());
        addDetailRow(detailsPanel, "Date of Birth:", volunteer.getDateOfBirth());
        addDetailRow(detailsPanel, "Gender:", volunteer.getGender());
        addDetailRow(detailsPanel, "Contact Number:", volunteer.getContactNumber());
        addDetailRow(detailsPanel, "Email:", volunteer.getEmail());
        addDetailRow(detailsPanel, "Education:", volunteer.getEducation());
        addDetailRow(detailsPanel, "Registration Date:", volunteer.getRegistrationDateTime());
        
        //Skills(multiline)
        addDetailRowMultiline(detailsPanel, "Skills:", volunteer.getSkills());
        
        //PastExperience(Multiline)
        addDetailRowMultiline(detailsPanel, "Past Experience: ", volunteer.getPastExperience());
        
        //wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        
        //Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JButton closeButton = new JButton("CLOSE");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        closeButton.setBackground(new Color(91, 158, 165));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setPreferredSize(new Dimension(120, 40));
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(closeButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);      
    }
    
    
    //add a single line detail row
    private void addDetailRow(JPanel panel, String label, String value){
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout(10, 0));
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Sitka Text", Font.BOLD, 16));
        labelComp.setForeground(new Color(91, 158, 165));
        labelComp.setPreferredSize(new Dimension(180, 25));
        
        JLabel valueComp = new JLabel(value != null ? value : "N/A");
        valueComp.setFont(new Font("Arial", Font.PLAIN, 15));
        valueComp.setForeground(new Color(60, 60, 60));
        
        rowPanel.add(labelComp, BorderLayout.WEST);
        rowPanel.add(valueComp, BorderLayout.CENTER);
        
        panel.add(rowPanel);
        
        //Add separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(220, 220, 220));
        panel.add(separator);
    }
    
    //Add a multiline detail row ( for skills and experience)
    private void addDetailRowMultiline(JPanel panel, String label, String value){
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout(10, 10));
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Sitka Text", Font.BOLD, 16));
        labelComp.setForeground(new Color(91, 158, 165));
        
        JTextArea valueComp = new JTextArea(value != null ? value : "N/A");
        valueComp.setFont(new Font("Arial", Font.PLAIN, 15));
        valueComp.setForeground(new Color(60, 60, 60));
        valueComp.setLineWrap(true);
        valueComp.setWrapStyleWord(true);
        valueComp.setEditable(false);
        valueComp.setBackground(Color.WHITE);
        valueComp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        valueComp.setRows(3);
        
        rowPanel.add(labelComp, BorderLayout.NORTH);
        rowPanel.add(valueComp, BorderLayout.CENTER);
        
        panel.add(rowPanel);
        
        //Add separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(220, 220, 220));
        panel.add(separator);
    }
    
    
    //Show the dialog with a blurred / dimmed backgrounf effect
    public static void showDialog(JFrame parent, SignUpUser volunteer){
        //create glass pane for blur / dim effect
        final JPanel glassPane = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                g.setColor(new Color(0, 0, 0, 120)); //semi transparent black
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        glassPane.setOpaque(false);
        parent.setGlassPane(glassPane);
        glassPane.setVisible(true);
        
        //Show dialog
        VolunteerDetailsDialog dialog = new VolunteerDetailsDialog(parent, volunteer);
        dialog.setVisible(true);
        
        //hide glass pane after dialog class
        glassPane.setVisible(false);
    }   
}
 