/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.*;
import javax.swing.border.AbstractBorder;
import Model.User;
import Model.PendingVolunteersDAO;
import Model.UserDAO;



/**
 *
 * @author ALIENWARE
 */
public class LogInFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LogInFrame.class.getName());
    private RoundedFillButton customLoginButton;
    private RoundedFillButton customSignUpButton;
    private UserDAO userDAO;
    private PendingVolunteersDAO pendingDAO;
    
    public LogInFrame() {
        userDAO = new UserDAO();
        pendingDAO = new PendingVolunteersDAO();
        initComponents();
        setupCustomButtons();
    }
    
    private void setupCustomButtons(){
        Color baseColor = new Color(91, 158, 165);

        //create custom buttons
        customLoginButton  = new RoundedFillButton("Log In", baseColor);
        customSignUpButton  = new RoundedFillButton("Sign Up", baseColor);

        //remove the original buttons from the panel
        LogInDetailsPanel.remove(LogInButton);
        LogInDetailsPanel.remove(SignUpButton);

        
        //add custom buttons at the same position
        LogInDetailsPanel.add(customLoginButton,  new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, -1, -1));
        LogInDetailsPanel.add(customSignUpButton,  new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 400, -1, -1));

        //Action listener
        customLoginButton.addActionListener(evt -> handleLogin());
        customSignUpButton.addActionListener(evt -> handleSignUp());
        
        //refresh the panel
        LogInDetailsPanel.revalidate();
        LogInDetailsPanel.repaint();
    } 
    
    //handle login method
    private void handleLogin(){
        String username = UsernameTextFieldLI.getText().trim();
        String password = new String(PasswordFieldLI.getPassword());
        
        if(username.isEmpty() || password.isEmpty()){
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Please enter both username and passoword",
                    "LogIn Errror",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        //Step 1: Validate credentials (username + password)
        User user = userDAO.validateLogin(username, password);
        
        if (user == null){
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Invalid username or password",
                    "Login Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Step 2: Check user Status
        String status = pendingDAO.getUserStatus(username);

        if (status == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "User status not found. Please contact support.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Step 3: Handle based on status
        switch (status) {
            case "pending_user":
                // User is waiting for admin approval
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Your registration is pending.\nPlease wait for the admin's decision.",
                        "Pending Approval",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                break;

            case "approved_user":
                // User is approved - allow login
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Login successful! Welcome " + username,
                        "Success",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);

                // Close login frame
                this.dispose();
        
                
                // Open appropriate dashboard based on role
                if ("admin".equalsIgnoreCase(user.getRole())) {
                    // Open admin dashboard
                    // new AdminDashboard().setVisible(true);
                    System.out.println("Admin logged in: " + username);
                } else {
                    // Open user dashboard
                    // new UserDashboard(user).setVisible(true);
                    System.out.println("User logged in: " + username);
                }
                break;
                case "declined_user":
                // User has been declined by admin
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Your registration has been declined by the admin.\nPlease contact support for more information.",
                        "Registration Declined",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                break;

            default:
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Unknown user status. Please contact support.",
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
    
    //handle signup = opens sign up form
    private void handleSignUp(){
        //create a new JFrame to  hold the signUpForm panel
        javax.swing.JFrame signUpFrame = new javax.swing.JFrame("SIGN UP");
        signUpFrame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        
            //CREATE SIGNUOP PANEL AND ADD TO THE FRAME
            SignUpForm signUpPanel = new SignUpForm();
            signUpFrame.add(signUpPanel);
            
            // Set frame properties
        signUpFrame.setSize(1800, 1000); // Adjust size as needed
        signUpFrame.setLocationRelativeTo(null); // Center on screen
        signUpFrame.setVisible(true);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LogInPanel = new javax.swing.JPanel();
        LogInDetailsPanel = new javax.swing.JPanel();
        UsernameLabelLI = new javax.swing.JLabel();
        UsernameTextFieldLI = new javax.swing.JTextField();
        PasswordLabelLI = new javax.swing.JLabel();
        PasswordFieldLI = new javax.swing.JPasswordField();
        LogInButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        SignUpButton = new javax.swing.JButton();
        LogInBGLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        LogInPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogInDetailsPanel.setBackground(new java.awt.Color(91, 158, 165));
        LogInDetailsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        UsernameLabelLI.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        UsernameLabelLI.setForeground(new java.awt.Color(255, 255, 255));
        UsernameLabelLI.setText("Username: ");
        LogInDetailsPanel.add(UsernameLabelLI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));
        LogInDetailsPanel.add(UsernameTextFieldLI, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 200, 30));

        PasswordLabelLI.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        PasswordLabelLI.setForeground(new java.awt.Color(255, 255, 255));
        PasswordLabelLI.setText("Password: ");
        LogInDetailsPanel.add(PasswordLabelLI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));
        LogInDetailsPanel.add(PasswordFieldLI, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 200, 30));

        LogInButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        LogInButton.setText("Log In");
        LogInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogInButtonActionPerformed(evt);
            }
        });
        LogInDetailsPanel.add(LogInButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, -1, -1));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Don't have an account? ");
        LogInDetailsPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, -1, -1));

        SignUpButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        SignUpButton.setText("Sign Up");
        LogInDetailsPanel.add(SignUpButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, -1, -1));

        LogInPanel.add(LogInDetailsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 410, 520, 600));

        LogInBGLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/LogInPage.jpg"))); // NOI18N
        LogInPanel.add(LogInBGLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LogInPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LogInPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogInButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LogInButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> new LogInFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LogInBGLabel;
    private javax.swing.JButton LogInButton;
    private javax.swing.JPanel LogInDetailsPanel;
    private javax.swing.JPanel LogInPanel;
    private javax.swing.JPasswordField PasswordFieldLI;
    private javax.swing.JLabel PasswordLabelLI;
    private javax.swing.JButton SignUpButton;
    private javax.swing.JLabel UsernameLabelLI;
    private javax.swing.JTextField UsernameTextFieldLI;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    
    public javax.swing.JButton getLoginButton() {
        return customLoginButton;
    }

    public javax.swing.JButton getSignUpButton() {
        return customSignUpButton;
    }

    public javax.swing.JTextField getUsernameField() {
        return UsernameTextFieldLI;
    }

    public javax.swing.JPasswordField getPasswordField() {
        return PasswordFieldLI;
    }
    
    
    class RoundedBorder extends AbstractBorder {

        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g,
                int x, int y, int width, int height) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(c.getForeground());
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(6, 16, 6, 16);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(6, 16, 6, 16);
            return insets;
        }
    }  
}

class RoundedFillButton extends JButton { //class level

    private Color normalColor;
    private Color hoverColor;
    private Color pressedColor;
    private int radius = 20;

    public RoundedFillButton(String text, Color baseColor) {
        super(text);

        normalColor = baseColor;
        hoverColor = baseColor.darker();
        pressedColor = hoverColor.darker();

        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.BOLD, 16));

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setMargin(new Insets(8, 22, 8, 22));
        setBackground(normalColor);

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(normalColor);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                setBackground(hoverColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
        g2.dispose();
    }
}
