/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;

public class EventAddDialog extends JDialog{
    
    //input fields
    private JTextField eventNameField;
    private JTextArea descriptionArea;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField locationField;
    private JComboBox<String> eventTypeCombo;
    private JTextField eventStatusField;
    private JTextField organizerNameField;
    private JTextField organizerContactField;
    
    private boolean validateForm() {
        //required field checks
        if (eventNameField.getText().trim().isEmpty()) {
            showError("Event name is required.", eventNameField);
            return false;
        }
        return true;
    }

    public EventAddDialog(JFrame parent) {
        super(parent, "Add New Event", true); // Modal dialog

        initComponents();
        setLocationRelativeTo(parent); // Center on parent
    }

    private void initComponents() {
        setSize(600, 750);
        setResizable(false);
        setUndecorated(true); 

        // Main panel with white background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(91, 158, 165), 3),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(91, 158, 165));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("ADD NEW EVENT");
        titleLabel.setFont(new Font("Sitka Text", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Form Panel (Scrollable)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Add all input fields
        eventNameField = addInputField(formPanel, "Event Name:", false);
        descriptionArea = addTextAreaField(formPanel, "Description:");
        startDateField = addInputField(formPanel, "Start Date:", false);
        endDateField = addInputField(formPanel, "End Date:", false);
        locationField = addInputField(formPanel, "Location:", false);
        eventTypeCombo = addComboBoxField(formPanel, "Event Type:",
                new String[]{"Workshop", "Training", "Community Service", "Fundraiser", "Seminar", "Competition", "Other"});
        eventStatusField = addInputField(formPanel, "Event Status:", false);
        organizerNameField = addInputField(formPanel, "Organizer's Name:", false);
        organizerContactField = addInputField(formPanel, "Organizer's Contact:", false);

        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));

        // Save Button
        JButton saveButton = new JButton("SAVE");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        saveButton.setBackground(new Color(91, 158, 165));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.addActionListener(e -> {
            // TODO: Add save logic here
            dispose();
        });

        // Cancel Button
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        cancelButton.setBackground(new Color(200, 200, 200));
        cancelButton.setForeground(new Color(60, 60, 60));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        
        saveButton.addActionListener(e -> {
            if (validateForm()) {
                dispose();
            }
        });
    }

    // Add a single line input field
    private JTextField addInputField(JPanel panel, String label, boolean isMultiline) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout(10, 5));
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Sitka Text", Font.BOLD, 16));
        labelComp.setForeground(new Color(91, 158, 165));

        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setForeground(new Color(60, 60, 60));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        rowPanel.add(labelComp, BorderLayout.NORTH);
        rowPanel.add(textField, BorderLayout.CENTER);

        panel.add(rowPanel);

        // Add separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(220, 220, 220));
        panel.add(separator);

        return textField;
    }

    // Add a multiline text area field
    private JTextArea addTextAreaField(JPanel panel, String label) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout(10, 5));
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Sitka Text", Font.BOLD, 16));
        labelComp.setForeground(new Color(91, 158, 165));

        JTextArea textArea = new JTextArea(4, 20);
        textArea.setFont(new Font("Arial", Font.PLAIN, 15));
        textArea.setForeground(new Color(60, 60, 60));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        rowPanel.add(labelComp, BorderLayout.NORTH);
        rowPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(rowPanel);

        // Add separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(220, 220, 220));
        panel.add(separator);

        return textArea;
    }

    // Add a combo box field
    private JComboBox<String> addComboBoxField(JPanel panel, String label, String[] options) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout(10, 5));
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Sitka Text", Font.BOLD, 16));
        labelComp.setForeground(new Color(91, 158, 165));

        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        comboBox.setForeground(new Color(60, 60, 60));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        rowPanel.add(labelComp, BorderLayout.NORTH);
        rowPanel.add(comboBox, BorderLayout.CENTER);

        panel.add(rowPanel);

        // Add separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(220, 220, 220));
        panel.add(separator);

        return comboBox;
    }

    // Show the dialog with a blurred/dimmed background effect
    public static void showDialog(JFrame parent) {
        // Create glass pane for blur/dim effect
        final JPanel glassPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 120)); // Semi-transparent black
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        glassPane.setOpaque(false);
        parent.setGlassPane(glassPane);
        glassPane.setVisible(true);

        // Show dialog
        EventAddDialog dialog = new EventAddDialog(parent);
        dialog.setVisible(true);

        // Hide glass pane after dialog closes
        glassPane.setVisible(false);
    }
    
    
    private void showError(String message, JComponent field){
        JOptionPane.showMessageDialog(
        this, message, "Validation error", JOptionPane.ERROR_MESSAGE);
            field.requestFocus();
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }
    
    private void resetBorder(JComponent field) {
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
    }
 
}
