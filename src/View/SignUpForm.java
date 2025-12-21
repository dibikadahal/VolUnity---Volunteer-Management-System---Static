/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import javax.swing.*;
import Model.SignUpDAO;
import Model.SignUpUser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;




/**
 *
 * @author ALIENWARE
 */
public class SignUpForm extends javax.swing.JPanel {
    //TextFields
    
    private JTextField fullNameField;
    private JTextField dobField;
    private JComboBox<String> genderCombo;
    private JTextField contactField;
    private JTextField emailField;
    private JTextField educationField;
    private JTextArea skillsArea;
    private JTextArea experienceArea;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    //Labels for validation message
    private JLabel  fullNameError;
    private JLabel dobError;
    private JLabel contactError;
    private JLabel emailError;
    private JLabel educationError;
    private JLabel skillsError;
    private JLabel experienceError;
    private JLabel usernameError;
    private JLabel passwordError;
    
    private JButton submitButton;
    private SignUpDAO signUpDAO;
    
    public SignUpForm(){
        signUpDAO = new SignUpDAO();
        initComponents();
        setupValidations();
        }
    
    private void setupValidations(){
       //  mapNetbeans components to validation fields
                fullNameField = fullNameTextField;
                dobField = dobTextField;
                genderCombo = genderComboBox;
                contactField = contactNumberTextField;
                emailField = emailTextField;
                educationField = educationTextField;
                skillsArea = skillsTextArea;
                experienceArea = pastExperienceTextArea;
                usernameField = usernameTextField;
                passwordField = signUpPasswordField;
                
                //create error labels
                createErrorLabels();
                        
        
        //full name validations
        addDocumentListener(fullNameField, ()-> validateFullName());
        
        //DOB validation
        addDocumentListener(dobField, () -> validateDOB());
        
        //Contact validation
        addDocumentListener(contactField, () -> validateContact());
        
        //Email validation
        addDocumentListener(emailField, () -> validateEmail());
        
        //Education validation
        addDocumentListener(educationField, () -> validateEducation());
        
        //username validations
        addDocumentListener(usernameField, () -> validateUsername());
        
        //Password validation
        addDocumentListener(passwordField, () -> validatePassword());
        
        //skills validation
        skillsArea.getDocument().addDocumentListener(new DocumentListener(){
        public void insertUpdate(DocumentEvent e) {validateSkills(); }
        public void removeUpdate(DocumentEvent e){validateSkills();}
        public void changedUpdate (DocumentEvent e){validateSkills();}
         });
    
         //experience validations
         experienceArea.getDocument().addDocumentListener(new DocumentListener(){
        public void insertUpdate(DocumentEvent e) {validateExperience(); }
        public void removeUpdate(DocumentEvent e){validateExperience();}
        public void changedUpdate (DocumentEvent e){validateExperience();}
         });
    

         //placeholder functionality for DOB
         dobField.addFocusListener(new java.awt.event.FocusAdapter(){
         @Override
         public void focusGained(java.awt.event.FocusEvent e){
             if (dobField.getText().equals("yyyy/mm/dd")){
                 dobField.setText("");
                 dobField.setForeground(java.awt.Color.BLACK);
             }
         }
         @Override
         public void focusLost(java.awt.event.FocusEvent e){
             if (dobField.getText().isEmpty()){
                 dobField.setText("yyyy/mm/dd");
                 dobField.setForeground(java.awt.Color.GRAY);
             }
         }
});
}
    
    
    private void createErrorLabels(){
        // Full Name Error
        fullNameError = new JLabel("");
        fullNameError.setForeground(java.awt.Color.RED);
        fullNameError.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        SignUpInnerPanel.add(fullNameError, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 85, 420, 20));

        // DOB Error
        dobError = new JLabel("");
        dobError.setForeground(java.awt.Color.RED);
        dobError.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        SignUpInnerPanel.add(dobError, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 195, 420, 20));

        // Contact Error
        contactError = new JLabel("");
        contactError.setForeground(java.awt.Color.RED);
        contactError.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        SignUpInnerPanel.add(contactError, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 415, 420, 20));

        // Email Error
        emailError = new JLabel("");
        emailError.setForeground(java.awt.Color.RED);
        emailError.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        SignUpInnerPanel.add(emailError, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 525, 420, 20));

        // Education Error
        educationError = new JLabel("");
        educationError.setForeground(java.awt.Color.RED);
        educationError.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        SignUpInnerPanel.add(educationError, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 635, 420, 20));

        // Skills Error
        skillsError = new JLabel("");
        skillsError.setForeground(java.awt.Color.RED);
        skillsError.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        SignUpInnerPanel.add(skillsError, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 205, 470, 20));

        // Experience Error
        experienceError = new JLabel("");
        experienceError.setForeground(java.awt.Color.RED);
        experienceError.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        SignUpInnerPanel.add(experienceError, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 435, 470, 20));

        // Username Error
        usernameError = new JLabel("");
        usernameError.setForeground(java.awt.Color.RED);
        usernameError.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        SignUpInnerPanel.add(usernameError, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 535, 320, 20));

        // Password Error
        passwordError = new JLabel("");
        passwordError.setForeground(java.awt.Color.RED);
        passwordError.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        SignUpInnerPanel.add(passwordError, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 595, 320, 20));      
    }
    
    
    private void addDocumentListener(JTextField field, Runnable validator){
        field.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e) {validator.run();}
            public void removeUpdate(DocumentEvent e) {validator.run();}
            public void changedUpdate(DocumentEvent e) {validator.run();}
        });
    }
    
    
    // VALIDATE FULL NAME
    private boolean validateFullName(){
        String name = fullNameField.getText().trim();
        if(name.isEmpty()){
            fullNameError.setText ("Full name is required");
            fullNameError.setForeground(java.awt.Color.RED);
            return false;
        }
        if(!Pattern.matches("^[a-zA-Z\\s]+$", name)){
            fullNameError.setText("Only letters and spaces allowed");
            fullNameError.setForeground(java.awt.Color.RED);
            return false;
        }
        fullNameError.setText("✓");
        fullNameError.setForeground(java.awt.Color.GREEN);
        return true;
    }
    
    
    //VALIDATE DATE OF BIRTH
    private boolean validateDOB(){
        String dob = dobField.getText().trim();
        if(dob.isEmpty() || dob.equals("yyyy/mm/dd")){
        dobError.setText("Date of birth is required");
            dobError.setForeground(java.awt.Color.RED);
            return false;
    }   
    //parse string into date object
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    sdf.setLenient (false); //only valid cal;endar dates like (2025.12/18)
     try {
            Date birthDate = sdf.parse(dob); //stored in the form of birth date
            Date currentDate = new Date();
            
            if (birthDate.after(currentDate)) {
                dobError.setText("Date cannot be in the future");
                dobError.setForeground(java.awt.Color.RED);
                return false;
            }
            
            long ageInMillis = currentDate.getTime() - birthDate.getTime(); //RETURN EACH  DATE TIME IN MILLISECOND
            long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365); //CONVERT INTO YEARS
            
            if (ageInYears < 13) {
                dobError.setText("Must be at least 13 years old");
                dobError.setForeground(java.awt.Color.RED);
                return false;
            }
            if (ageInYears > 120) {
                dobError.setText("Please enter a valid date");
                dobError.setForeground(java.awt.Color.RED);
                return false;
            }
            
            dobError.setText("✓");
            dobError.setForeground(java.awt.Color.GREEN);
            return true;
 } catch (ParseException e) {
            dobError.setText("Invalid date format (use yyyy/mm/dd)");
            dobError.setForeground(java.awt.Color.RED);
            return false;
        }
}

//VALIDATE CONTACTS
    private boolean validateContact(){
        String contact = contactField.getText().trim();
        if(contact.isEmpty()){
            contactError.setText("Contact number is required");
            contactError.setForeground(java.awt.Color.RED);
            return false;
        }
        if (!Pattern.matches("^[0-9]{10,15}$", contact)) {
            contactError.setText("Enter valid phone number (10 digits)");
            contactError.setForeground(java.awt.Color.RED);
            return false;
        }
        contactError.setText("✓");
        contactError.setForeground(java.awt.Color.GREEN);
        return true;
    }
    
    
    //VALIDATE EMAIL
    private boolean validateEmail() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            emailError.setText("Email is required");
            emailError.setForeground(java.awt.Color.RED);
            return false;
        }
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", email)) {
            emailError.setText("Enter valid email address");
            emailError.setForeground(java.awt.Color.RED);
            return false;
        }
        emailError.setText("✓");
        emailError.setForeground(java.awt.Color.GREEN);
        return true;
    }
    
    //VALIDATE EDUCATION
    private boolean validateEducation() {
        String education = educationField.getText().trim();
        if (education.isEmpty()) {
            educationError.setText("Education is required");
            educationError.setForeground(java.awt.Color.RED);
            return false;
        }
        educationError.setText("✓");
        educationError.setForeground(java.awt.Color.GREEN);
        return true;
    }
    
    //VALIDATE SKILLS
    private boolean validateSkills() {
        String skills = skillsArea.getText().trim();
        if (skills.isEmpty()) {
            skillsError.setText("Skills are required");
            skillsError.setForeground(java.awt.Color.RED);
            return false;
        }
        skillsError.setText("✓");
        skillsError.setForeground(java.awt.Color.GREEN);
        return true;
    }
    
    //VALIDATE EXPERIENCE
    private boolean validateExperience() {
        String experience = experienceArea.getText().trim();
        if (experience.isEmpty()) {
            experienceError.setText("Past experience is required");
            experienceError.setForeground(java.awt.Color.RED);
            return false;
        }
        experienceError.setText("✓");
        experienceError.setForeground(java.awt.Color.GREEN);
        return true;
    }
    
    
    //VALIDATE USERNAME\
    private boolean validateUsername() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            usernameError.setText("Username is required");
            usernameError.setForeground(java.awt.Color.RED);
            return false;
        }

        if (signUpDAO.usernameExists(username)) {
            usernameError.setText("Username already exists");
            usernameError.setForeground(java.awt.Color.RED);
            return false;
        }

        usernameError.setText("✓");
        usernameError.setForeground(java.awt.Color.GREEN);
        return true;
    }
    
    
    //VALIDATE PASSWORD
    private boolean validatePassword() {
        String password = new String(passwordField.getPassword());
        if (password.isEmpty()) {
            passwordError.setText("Password is required");
            passwordError.setForeground(java.awt.Color.RED);
            return false;
        }
        if (password.length() < 6) {
            passwordError.setText("Password must be at least 6 characters");
            passwordError.setForeground(java.awt.Color.RED);
            return false;
        }
        passwordError.setText("✓");
        passwordError.setForeground(java.awt.Color.GREEN);
        return true;
    }
    
    private void clearForm() {
        fullNameField.setText("");
        dobField.setText("yyyy/mm/dd");
        genderCombo.setSelectedIndex(0);
        contactField.setText("");
        emailField.setText("");
        educationField.setText("");
        skillsArea.setText("");
        experienceArea.setText("");
        usernameField.setText("");
        passwordField.setText("");

        // Clear error messages
        fullNameError.setText("");
        dobError.setText("");
        contactError.setText("");
        emailError.setText("");
        educationError.setText("");
        skillsError.setText("");
        experienceError.setText("");
        usernameError.setText("");
        passwordError.setText("");
 }

    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SignUpPanel = new javax.swing.JPanel();
        SignUpInnerPanel = new javax.swing.JPanel();
        FullNameLabel = new javax.swing.JLabel();
        fullNameTextField = new javax.swing.JTextField();
        DOBLabel = new javax.swing.JLabel();
        dobTextField = new javax.swing.JTextField();
        GenderLabel = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox<>();
        contactNumberLabel = new javax.swing.JLabel();
        contactNumberTextField = new javax.swing.JTextField();
        EmailLabel = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        EducationLabel = new javax.swing.JLabel();
        educationTextField = new javax.swing.JTextField();
        SkillsLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        skillsTextArea = new javax.swing.JTextArea();
        PastExperienceLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pastExperienceTextArea = new javax.swing.JTextArea();
        SubmitButton = new javax.swing.JButton();
        UsernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        signUpPasswordField = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        SignUpPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SignUpInnerPanel.setBackground(new java.awt.Color(187, 215, 217));
        SignUpInnerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FullNameLabel.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        FullNameLabel.setText("Full Name: ");
        SignUpInnerPanel.add(FullNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 170, 50));

        fullNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullNameTextFieldActionPerformed(evt);
            }
        });
        SignUpInnerPanel.add(fullNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 420, 40));

        DOBLabel.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        DOBLabel.setText("Date of Birth: ");
        SignUpInnerPanel.add(DOBLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        dobTextField.setText("yyyy/mm/dd");
        dobTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dobTextFieldActionPerformed(evt);
            }
        });
        SignUpInnerPanel.add(dobTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, 420, 40));

        GenderLabel.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        GenderLabel.setText("Gender: ");
        SignUpInnerPanel.add(GenderLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gender", "Female", "Male", "Others" }));
        genderComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genderComboBoxActionPerformed(evt);
            }
        });
        SignUpInnerPanel.add(genderComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, 170, 30));

        contactNumberLabel.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        contactNumberLabel.setText("Contact Number: ");
        SignUpInnerPanel.add(contactNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, -1, -1));

        contactNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactNumberTextFieldActionPerformed(evt);
            }
        });
        SignUpInnerPanel.add(contactNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, 420, 40));

        EmailLabel.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        EmailLabel.setText("Email:");
        SignUpInnerPanel.add(EmailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 490, -1, -1));

        emailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextFieldActionPerformed(evt);
            }
        });
        SignUpInnerPanel.add(emailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 480, 420, 40));

        EducationLabel.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        EducationLabel.setText("Education: ");
        SignUpInnerPanel.add(EducationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 600, -1, -1));

        educationTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                educationTextFieldActionPerformed(evt);
            }
        });
        SignUpInnerPanel.add(educationTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 590, 420, 40));

        SkillsLabel.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        SkillsLabel.setText("Skills: ");
        SignUpInnerPanel.add(SkillsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 40, -1, -1));

        skillsTextArea.setColumns(20);
        skillsTextArea.setRows(5);
        jScrollPane1.setViewportView(skillsTextArea);

        SignUpInnerPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 80, 470, 120));

        PastExperienceLabel.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        PastExperienceLabel.setText("Past Experience:");
        SignUpInnerPanel.add(PastExperienceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 250, -1, -1));

        pastExperienceTextArea.setColumns(20);
        pastExperienceTextArea.setRows(5);
        jScrollPane2.setViewportView(pastExperienceTextArea);

        SignUpInnerPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 290, 470, 140));

        SubmitButton.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        SubmitButton.setText("SUBMIT");
        SubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButtonActionPerformed(evt);
            }
        });
        SignUpInnerPanel.add(SubmitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 640, 120, 40));

        UsernameLabel.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        UsernameLabel.setText("Username:");
        SignUpInnerPanel.add(UsernameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 500, -1, -1));
        SignUpInnerPanel.add(usernameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 490, 320, 40));

        jLabel3.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        jLabel3.setText("Password:");
        SignUpInnerPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 560, -1, -1));
        SignUpInnerPanel.add(signUpPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 550, 320, 40));

        SignUpPanel.add(SignUpInnerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 1460, 690));

        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\Dibika_new\\College\\2nd Year\\Java\\Java Coutsework\\Pictures\\SIGN UP_new2.jpg")); // NOI18N
        SignUpPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, 1980, 1160));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2059, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(SignUpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 79, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(SignUpPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fullNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullNameTextFieldActionPerformed

    private void dobTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dobTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dobTextFieldActionPerformed

    private void genderComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_genderComboBoxActionPerformed

    private void contactNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactNumberTextFieldActionPerformed

    private void emailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextFieldActionPerformed

    private void educationTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_educationTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_educationTextFieldActionPerformed

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButtonActionPerformed
                //validate all fields here
                boolean isValid = true;
                
                isValid &= validateFullName();
                isValid &= validateDOB();
                isValid &= validateContact();
                isValid &= validateEmail();
                isValid &= validateEducation();
                isValid &= validateSkills();
                isValid &= validateExperience();
                isValid &= validateUsername();
                isValid &= validatePassword();
                
                //check gender selection
                if(genderCombo.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(this, "Please select a gender", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    isValid = false;
                }

                if (!isValid) {
                    JOptionPane.showMessageDialog(this, "Please fix all errors before submitting", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                    
                        // Create User object
            SignUpUser user = new SignUpUser();
            user.setFullName(fullNameField.getText().trim());
            user.setDateOfBirth(dobField.getText().trim());
            user.setGender((String) genderCombo.getSelectedItem());
            user.setContactNumber(contactField.getText().trim());
            user.setEmail(emailField.getText().trim());
            user.setEducation(educationField.getText().trim());
            user.setSkills(skillsArea.getText().trim());
            user.setPastExperience(experienceArea.getText().trim());
            user.setUsername(usernameField.getText().trim());
            user.setPassword(new String(passwordField.getPassword()));
            
            user.setRole("user");

            // Save user
            if (signUpDAO.saveUser(user)) {
                JOptionPane.showMessageDialog(this, "Registration successful! Wait for the admin's decision.", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear all fields after successful submission
                clearForm();

                // If you need to navigate back to login, you'll need to handle that in your parent frame
                // For example: if this panel is inside a JFrame with CardLayout, switch back to login panel
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

                
                
                
    }//GEN-LAST:event_SubmitButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DOBLabel;
    private javax.swing.JLabel EducationLabel;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JLabel FullNameLabel;
    private javax.swing.JLabel GenderLabel;
    private javax.swing.JLabel PastExperienceLabel;
    private javax.swing.JPanel SignUpInnerPanel;
    private javax.swing.JPanel SignUpPanel;
    private javax.swing.JLabel SkillsLabel;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JLabel contactNumberLabel;
    private javax.swing.JTextField contactNumberTextField;
    private javax.swing.JTextField dobTextField;
    private javax.swing.JTextField educationTextField;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JTextField fullNameTextField;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea pastExperienceTextArea;
    private javax.swing.JPasswordField signUpPasswordField;
    private javax.swing.JTextArea skillsTextArea;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
