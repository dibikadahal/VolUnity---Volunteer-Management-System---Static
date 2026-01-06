/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.ValidationUtil;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    
    // Error labels
    private JLabel eventNameError;
    private JLabel descriptionError;
    private JLabel startDateError;
    private JLabel endDateError;
    private JLabel locationError;
    private JLabel eventStatusError;
    private JLabel organizerNameError;
    private JLabel organizerContactError;

    public EventAddDialog(JFrame parent) {
        super(parent, "Add New Event", true); // Modal dialog

        initComponents();
        initValidation();
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
        
        //add all input fields with error labels
        Object[] eventNameComponents = addInputFieldWithError(formPanel, "Event Name", false);
        eventNameField = (JTextField) eventNameComponents[0];
        eventNameError = (JLabel) eventNameComponents[1];
        
        Object[] descriptionComponents = addTextAreaFieldWithError(formPanel, "Description:");
        descriptionArea = (JTextArea) descriptionComponents[0];
        descriptionError = (JLabel) descriptionComponents[1];
        
        Object[] startDateComponents = addInputFieldWithError(formPanel, "Start Date (yyyy-MM-dd):", false);
        startDateField = (JTextField) startDateComponents[0];
        startDateError = (JLabel) startDateComponents[1];

        Object[] endDateComponents = addInputFieldWithError(formPanel, "End Date (yyyy-MM-dd):", false);
        endDateField = (JTextField) endDateComponents[0];
        endDateError = (JLabel) endDateComponents[1];
        
        Object[] locationComponents = addInputFieldWithError(formPanel, "Location:", false);
        locationField = (JTextField) locationComponents[0];
        locationError = (JLabel) locationComponents[1];

        eventTypeCombo = addComboBoxField(formPanel, "Event Type:",
                new String[]{"Workshop", "Training", "Community Service", "Fundraiser", "Seminar", "Competition", "Other"});

        Object[] eventStatusComponents = addInputFieldWithError(formPanel, "Event Status:", false);
        eventStatusField = (JTextField) eventStatusComponents[0];
        eventStatusError = (JLabel) eventStatusComponents[1];

        Object[] organizerNameComponents = addInputFieldWithError(formPanel, "Organizer's Name:", false);
        organizerNameField = (JTextField) organizerNameComponents[0];
        organizerNameError = (JLabel) organizerNameComponents[1];

        Object[] organizerContactComponents = addInputFieldWithError(formPanel, "Organizer's Contact:", false);
        organizerContactField = (JTextField) organizerContactComponents[0];
        organizerContactError = (JLabel) organizerContactComponents[1];

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
        saveButton.addActionListener(e -> handleSave());

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
    }
    
    //Initialize real time validation
    private void initValidation(){
        //Event name validation
        addDocumentListener(eventNameField, () -> {
            String error = ValidationUtil.validateEventName(eventNameField.getText());
            if(error != null){
                showError(eventNameError, error);
            }else{
                hideError(eventNameError);
            }
        });
        
        // Description validation
        addDocumentListener(descriptionArea, () -> {
            String error = ValidationUtil.validateDescription(descriptionArea.getText());
            if (error != null) {
                showError(descriptionError, error);
            } else {
                hideError(descriptionError);
            }
        });
        
        // Start Date validation
        addDocumentListener(startDateField, () -> {
            String error = ValidationUtil.validateEventDate(startDateField.getText());
            if (error != null) {
                showError(startDateError, error);
            } else {
                hideError(startDateError);
                // Revalidate end date when start date changes
                validateEndDate();
            }
        });
        
        // End Date validation
        addDocumentListener(endDateField, () -> validateEndDate());
        
        // Location validation
        addDocumentListener(locationField, () -> {
            String error = ValidationUtil.validateLocation(locationField.getText());
            if (error != null) {
                showError(locationError, error);
            } else {
                hideError(locationError);
            }
        });
        
        // Event Status validation
        addDocumentListener(eventStatusField, () -> {
            String error = ValidationUtil.validateEventStatus(eventStatusField.getText());
            if (error != null) {
                showError(eventStatusError, error);
            } else {
                hideError(eventStatusError);
            }
        });
        
        // Organizer Name validation
        addDocumentListener(organizerNameField, () -> {
            String error = ValidationUtil.validateOrganizerName(organizerNameField.getText());
            if (error != null) {
                showError(organizerNameError, error);
            } else {
                hideError(organizerNameError);
            }
        });
        
        // Organizer Contact validation
        addDocumentListener(organizerContactField, () -> {
            String error = ValidationUtil.validateOrganizerContact(organizerContactField.getText());
            if (error != null) {
                showError(organizerContactError, error);
            } else {
                hideError(organizerContactError);
            }
        });
    }
    
    private void validateEndDate(){
        String endValue = endDateField.getText();
        String startValue = startDateField.getText();
        
        // First check if end date itself is valid
        String endError = ValidationUtil.validateEventDate(endValue);
        if (endError != null) {
            showError(endDateError, endError);
            return;
        }
        
        // Then check if date range is valid
        String rangeError = ValidationUtil.validateDateRange(startValue, endValue);
        if (rangeError != null) {
            showError(endDateError, rangeError);
        } else {
            hideError(endDateError);
        }
    }
    
    
    private void handleSave(){
        //validate all fields
        boolean isValid = true;
        
        if (!ValidationUtil.isNotEmpty(eventNameField.getText())
                || !ValidationUtil.hasMinLength(eventNameField.getText(), 3)) {
            showError(eventNameError, eventNameField.getText().isEmpty()
                    ? "Event name is required" : "Event name must be at least 3 characters");
            isValid = false;
        }
        
        if (!ValidationUtil.isNotEmpty(descriptionArea.getText())
                || !ValidationUtil.hasMinLength(descriptionArea.getText(), 10)) {
            showError(descriptionError, descriptionArea.getText().isEmpty()
                    ? "Description is required" : "Description must be at least 10 characters");
            isValid = false;
        }
        
        if (!ValidationUtil.isValidDate(startDateField.getText())) {
            showError(startDateError, startDateField.getText().isEmpty()
                    ? "Start date is required" : "Invalid date format");
            isValid = false;
        }
        
        if (!ValidationUtil.isValidDate(endDateField.getText())) {
            showError(endDateError, endDateField.getText().isEmpty()
                    ? "End date is required" : "Invalid date format");
            isValid = false;
        } else if (!ValidationUtil.isEndDateAfterStartDate(startDateField.getText(), endDateField.getText())) {
            showError(endDateError, "End date must be after or equal to start date");
            isValid = false;
        }
        
        if (!ValidationUtil.isNotEmpty(locationField.getText())) {
            showError(locationError, "Location is required");
            isValid = false;
        }
        
        if (!ValidationUtil.isNotEmpty(eventStatusField.getText())) {
            showError(eventStatusError, "Event status is required");
            isValid = false;
        }

        if (!ValidationUtil.isAlphaWithSpaces(organizerNameField.getText())) {
            showError(organizerNameError, organizerNameField.getText().isEmpty()
                    ? "Organizer name is required" : "Name should contain only letters");
            isValid = false;
        }

        if (!ValidationUtil.isValidPhone(organizerContactField.getText())) {
            showError(organizerContactError, organizerContactField.getText().isEmpty()
                    ? "Organizer contact is required" : "Invalid phone number format");
            isValid = false;
        }

        if (isValid) {
            // TODO: Add save logic here (call controller)
            JOptionPane.showMessageDialog(this, "Event saved successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please fix all validation errors",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // Helper method to add document listener
    private void addDocumentListener(JTextArea textComponent, Runnable validation) {
        textComponent.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                validation.run();
            }

            public void removeUpdate(DocumentEvent e) {
                validation.run();
            }

            public void insertUpdate(DocumentEvent e) {
                validation.run();
            }
        });
    }
    
    private void addDocumentListener(JTextField textField, Runnable validation) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                validation.run();
            }

            public void removeUpdate(DocumentEvent e) {
                validation.run();
            }

            public void insertUpdate(DocumentEvent e) {
                validation.run();
            }
        });
    }
    
    // Show error message
    private void showError(JLabel errorLabel, String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        }
    }

    // Hide error message
    private void hideError(JLabel errorLabel) {
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
    }
    
    // Add input field with error label
    private Object[] addInputFieldWithError(JPanel panel, String label, boolean isMultiline) {
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

        // Error label
        JLabel errorLabel = new JLabel();
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        rowPanel.add(labelComp, BorderLayout.NORTH);
        rowPanel.add(textField, BorderLayout.CENTER);
        rowPanel.add(errorLabel, BorderLayout.SOUTH);

        panel.add(rowPanel);

        // Add separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(220, 220, 220));
        panel.add(separator);

        return new Object[]{textField, errorLabel};
    }
    
    // Add text area field with error label
    private Object[] addTextAreaFieldWithError(JPanel panel, String label) {
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

        // Error label
        JLabel errorLabel = new JLabel();
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        rowPanel.add(labelComp, BorderLayout.NORTH);
        rowPanel.add(scrollPane, BorderLayout.CENTER);
        rowPanel.add(errorLabel, BorderLayout.SOUTH);

        panel.add(rowPanel);

        // Add separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(220, 220, 220));
        panel.add(separator);

        return new Object[]{textArea, errorLabel};
    }
    
    
    // Add combo box field
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
    
    
    // Show dialog with dimmed background
    public static void showDialog(JFrame parent) {
        final JPanel glassPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 120));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        glassPane.setOpaque(false);
        parent.setGlassPane(glassPane);
        glassPane.setVisible(true);

        EventAddDialog dialog = new EventAddDialog(parent);
        dialog.setVisible(true);

        glassPane.setVisible(false);
    }

    
    // Getters for field values (for Controller to use)
    public String getEventName() {
        return eventNameField.getText().trim();
    }

    public String getEventDescription() {
        return descriptionArea.getText().trim();
    }

    public String getEventStartDate() {
        return startDateField.getText().trim();
    }

    public String getEventEndDate() {
        return endDateField.getText().trim();
    }

    public String geEventtLocation() {
        return locationField.getText().trim();
    }

    public String getEventType() {
        return (String) eventTypeCombo.getSelectedItem();
    }

    public String getEventStatus() {
        return eventStatusField.getText().trim();
    }

    public String getEventOrganizerName() {
        return organizerNameField.getText().trim();
    }

    public String getEventOrganizerContact() {
        return organizerContactField.getText().trim();
    }
}