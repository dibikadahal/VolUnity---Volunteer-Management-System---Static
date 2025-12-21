/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultCellEditor;  // ← FIXED: No ".table" here!
import java.awt.Font;
import javax.swing.Timer;
import View.SignUpForm;
import View.VolunteerDetailsDialog;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import Model.PendingVolunteersDAO;
import Model.SignUpUser;


        

/**
 *
 * @author ALIENWARE
 */
public class NewJFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NewJFrame.class.getName());
    private Timer dateTimeTimer;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private SignUpForm signUpForm;
    private PendingVolunteersDAO pendingDAO;
    private DefaultTableModel tableModel;
    private List<SignUpUser> cachedPendingVolunteers;


    
    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
        setupPendingVolunteerTable();
        
        
        //make JFrame full screen / maximized
        Parent.setPreferredSize(null);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        //make the JFrame fill the JFrame
       getContentPane().setLayout(new java.awt.BorderLayout());
       getContentPane().add(Parent, java.awt.BorderLayout.CENTER);       
        this.setVisible(true);
        
        
        // ===== ADD THIS SECTION: Round panels with shadow =====
        makeRoundedPanelWithShadow(totalVolunteersPanel, 20, new Color(255, 255, 255));
        makeRoundedPanelWithShadow(totalEventsPanel, 20, new Color(255, 255, 255));
        makeRoundedPanelWithShadow(upcomingEventsPanel, 20, new Color(255, 255, 255));
        makeRoundedPanelWithShadow(newVolunteerRegistrationPanel, 20, new Color(255, 255, 255));
        makeRoundedPanelWithShadow(graphPanel, 20, new Color(255, 255, 255));

        // Add padding inside panels for content
        totalVolunteersPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        totalEventsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        upcomingEventsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        newVolunteerRegistrationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        graphPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // ===== END OF ROUNDED PANELS SECTION =====
        
        
        //customize the table row height
        volunteerTable.setRowHeight(30);
        
        //center align and style the table header
        JTableHeader header = volunteerTable.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(new Font("Times New Roman", Font.BOLD, 16));
        for (int i = 0; i<volunteerTable.getColumnModel().getColumnCount(); i++){
            volunteerTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        volunteerTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD,18));
            
    }
    
    private void setupPendingVolunteerTable(){
        pendingDAO = new PendingVolunteersDAO();
        
        //configuring the existing table
        pendingVolunteerTable.setRowHeight(40);
        pendingVolunteerTable.setFont(new Font("Arial", Font.PLAIN, 14));
        pendingVolunteerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        pendingVolunteerTable.getTableHeader().setBackground(new Color(91, 158, 165));
        pendingVolunteerTable.getTableHeader().setForeground(Color.WHITE);
        
        //get the table model
        tableModel = (DefaultTableModel) pendingVolunteerTable.getModel();
        
        //Clear any existinmg data
        tableModel.setRowCount(0);
        
        //add button renderers and editors for button columns
        pendingVolunteerTable.getColumn("Accept").setCellRenderer(new ButtonRenderer());
        pendingVolunteerTable.getColumn("Accept").setCellEditor(new ButtonEditor(new JCheckBox(), "Accept"));
        
        pendingVolunteerTable.getColumn("Decline").setCellRenderer(new ButtonRenderer());
        pendingVolunteerTable.getColumn("Decline").setCellEditor(new ButtonEditor(new JCheckBox(), "Decline"));

        pendingVolunteerTable.getColumn("View").setCellRenderer(new ButtonRenderer());
        pendingVolunteerTable.getColumn("View").setCellEditor(new ButtonEditor(new JCheckBox(), "View"));
    
        //set column widths
        pendingVolunteerTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // S.No
        pendingVolunteerTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Name
        pendingVolunteerTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Registration Date
        pendingVolunteerTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Accept
        pendingVolunteerTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Decline
        pendingVolunteerTable.getColumnModel().getColumn(5).setPreferredWidth(100); // View
    
        //setup refresh button action
        refreshButton.addActionListener(e -> loadPendingVolunteers());
        
        //load initial data
        loadPendingVolunteers();
    }
    
    
    //============LOAD PENDING VOLUNTEERS FROM THE DATABASE==================
    private void loadPendingVolunteers(){
        //clear existing rows
        tableModel.setRowCount(0);
        
        //get pending volunteers (sorted by registration date - queue order)
        cachedPendingVolunteers = pendingDAO.getPendingVolunteers();
        List<SignUpUser> pendingVolunteers = cachedPendingVolunteers;

        
        //check if the queue is empty
        if (pendingVolunteers.isEmpty()){
            JOptionPane.showMessageDialog(this, "No pending volunteers at the moment",
                    "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
                    return;
        }
        
        
        // Display queue info in console (for debugging)
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       VOLUNTEER QUEUE STATUS         ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ Total Pending: " + String.format("%-22d", pendingVolunteers.size()) + "║");
        System.out.println("║ Front (First): " + String.format("%-22s", pendingDAO.front().getFullName()) + "║");
        System.out.println("║ Rear (Last):   " + String.format("%-22s", pendingDAO.rear().getFullName()) + "║");
        System.out.println("║ Queue Full:    " + String.format("%-22s", pendingDAO.isFull() ? "YES" : "NO") + "║");
        System.out.println("║ Queue Size:    " + String.format("%-22s", pendingDAO.getQueueSize() + "/100") + "║");
        System.out.println("╚══════════════════════════════════════╝\n");
        
        //Populate tab;e
        int serialNo = 1;
        for (SignUpUser volunteer : pendingVolunteers){
            Object[] row = {
                serialNo++,
                volunteer.getFullName(),
                volunteer.getRegistrationDateTime(),
                "Accept", // button
                "Decline", //button
                "View" //button
            }; 
            tableModel.addRow(row);
        }
    }
    
      
    //=========BUTTON RENDERER - Makes button appear in the table cells
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer{
        public ButtonRenderer(){
            setOpaque (true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column){
            setText((value == null) ? "" : value.toString());
            
            //Style butons based on types
            if ("Accept".equals(value)){
                setBackground(new Color(76, 175, 80)); //Green
                setForeground(Color.WHITE);
            }else if ("Decline".equals(value)) {
                setBackground(new Color(244, 67, 54)); // Red
                setForeground(Color.WHITE);
            } else if ("View".equals(value)) {
                setBackground(new Color(33, 150, 243)); // Blue
                setForeground(Color.WHITE);
            }
            
            setFont(new Font("Arial", Font.BOLD, 12));
            setFocusPainted(false);
            setBorderPainted(false);
            
            //only first row (front of queue) should have accept / decline enabled
            if (row>0 && (column == 3 || column == 4)){
                setEnabled(false);
                setBackground(Color.LIGHT_GRAY);
            }else{
                setEnabled(true);
            }
            return this;
        }
        
    }
    
    
    //==========BUTTON EDITOR (handles button clicks)===============
    class ButtonEditor extends DefaultCellEditor{
        private JButton button;
        private String label;
        private boolean clicked;
        private int currentRow;
        
        public ButtonEditor(JCheckBox checkBox, String buttonLabel){
            super(checkBox);
            this.label = buttonLabel;
            button  = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            button.setText(label);

            // Style buttons
            if ("Accept".equals(label)) {
                button.setBackground(new Color(76, 175, 80));
                button.setForeground(Color.WHITE);
            } else if ("Decline".equals(label)) {
                button.setBackground(new Color(244, 67, 54));
                button.setForeground(Color.WHITE);
            } else if ("View".equals(label)) {
                button.setBackground(new Color(33, 150, 243));
                button.setForeground(Color.WHITE);
            }
            
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setFocusPainted(false);
            button.setBorderPainted(false);

            clicked = true;
            return button;
    }
        
        @Override
        public Object getCellEditorValue(){
            if (clicked){
                //get the volunteer from he row
                String volunteerName = (String) tableModel.getValueAt(currentRow, 1);
                
                //get the actual volunteer object
                SignUpUser selectedVolunteer = cachedPendingVolunteers.get(currentRow);

                
                if (selectedVolunteer == null) {
                    JOptionPane.showMessageDialog(button, "Volunteer not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    clicked = false;
                    return label;
                }
                
                // Handle button actions
                if ("Accept".equals(label)) {
                    handleAccept(selectedVolunteer, currentRow);
                } else if ("Decline".equals(label)) {
                    handleDecline(selectedVolunteer, currentRow);
                } else if ("View".equals(label)) {
                    handleView(selectedVolunteer);
                }
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
    
    
    //=====HANDLE ACCEPT BUTTON======
    private void handleAccept (SignUpUser volunteer, int row){
        // check if this is thge first volunteer in the queue (FIFO)
        if (row != 0) {
            JOptionPane.showMessageDialog(this,
                    "⚠ Please process volunteers in order!\n\n"
                    + "Queue Rule: First volunteer must be processed first\n\n"
                    + "➤ First in queue: " + pendingDAO.front().getFullName() + "\n"
                    + "  (Registered: " + pendingDAO.front().getRegistrationDateTime() + ")",
                    "Queue Order Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Accept volunteer: " + volunteer.getFullName() + "?\n\n"
                + "Registration Date: " + volunteer.getRegistrationDateTime() + "\n"
                + "Email: " + volunteer.getEmail() + "\n\n"
                + "This will grant them access to the system.",
                "Confirm Accept",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Update status to "approved_user"
            if (pendingDAO.updateVolunteerStatus(volunteer.getUsername(), "approved_user")) {
                JOptionPane.showMessageDialog(this,
                        "✓ " + volunteer.getFullName() + " has been APPROVED!\n\n"
                        + "They can now login to the system with their credentials.\n\n"
                        + "Username: " + volunteer.getUsername(),
                        "Volunteer Approved",
                        JOptionPane.INFORMATION_MESSAGE);
                // Reload table (volunteer will be removed from queue)
                loadPendingVolunteers();
            } else {
                JOptionPane.showMessageDialog(this,
                        " Failed to update volunteer status.\n\n"
                        + "Please try again or contact support.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);        
             }
        }
    }
    
    
    //=========HANDLE DECLINE BUTTON=========
    private void handleDecline(SignUpUser volunteer, int row){
        //Check if this is the first volunteer in the queue
        if (row != 0) {
            JOptionPane.showMessageDialog(this,
                    "⚠ Please process volunteers in order!\n\n"
                    + "Queue Rule: First volunteer must be processed first\n\n"
                    + "➤ First in queue: " + pendingDAO.front().getFullName() + "\n"
                    + "  (Registered: " + pendingDAO.front().getRegistrationDateTime() + ")",
                    "Queue Order Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String reason = JOptionPane.showInputDialog(this,
                "Decline volunteer: " + volunteer.getFullName() + "\n\n"
                + "Optional: Enter reason for decline (or leave blank):",
                "Confirm Decline",
                JOptionPane.WARNING_MESSAGE);
        
        if (reason == null) {
            // User clicked cancel
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to DECLINE?\n\n"
                + "Volunteer: " + volunteer.getFullName() + "\n"
                + "Registration Date: " + volunteer.getRegistrationDateTime() + "\n\n"
                + "They will NOT be able to access the system.",
                "Final Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Update status to "declined_user"
            if (pendingDAO.updateVolunteerStatus(volunteer.getUsername(), "declined_user")) {
                JOptionPane.showMessageDialog(this,
                        " " + volunteer.getFullName() + " has been DECLINED.\n\n"
                        + "They will see a decline message when trying to login.",
                        "Volunteer Declined",
                        JOptionPane.INFORMATION_MESSAGE);

                // Reload table (volunteer will be removed from queue)
                loadPendingVolunteers();
            } else {
                JOptionPane.showMessageDialog(this,
                        " Failed to update volunteer status.\n\n"
                        + "Please try again or contact support.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    //==========HANDLE VIEW BUTTON===============
    private void handleView(SignUpUser volunteer){
        //show modal dialog with volunteer details and bluer effect
        View.VolunteerDetailsDialog.showDialog(this, volunteer);
    }
    
    
    //==========TO MAKE THE PANEL ROUNDED WITH PANEL==========
    private void makeRoundedPanelWithShadow(JPanel panel, int radius, Color bgColor) {
        panel.setOpaque(false);
        panel.setBackground(bgColor);

        // Override paintComponent to draw rounded shape with shadow
        panel.setUI(new javax.swing.plaf.PanelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = c.getWidth();
                int height = c.getHeight();

                // Draw shadow (multiple layers for blur effect)
                int shadowGap = 5;
                int shadowOffset = 3;

                for (int i = shadowGap; i > 0; i--) {
                    int alpha = 20 - (i * 3); // Gradually decrease opacity
                    g2.setColor(new Color(0, 0, 0, alpha));
                    g2.fillRoundRect(
                            shadowOffset,
                            shadowOffset,
                            width - shadowGap - 1,
                            height - shadowGap - 1,
                            radius,
                            radius
                    );
                }

                // Draw the main rounded rectangle (panel background)
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, width - shadowGap, height - shadowGap, radius, radius);

                // Optional: Draw border
                g2.setColor(new Color(200, 200, 200, 100)); // Light border
                g2.drawRoundRect(0, 0, width - shadowGap - 1, height - shadowGap - 1, radius, radius);

                g2.dispose();
            }
        });
    }
    
 
   
  

    
   


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Parent = new javax.swing.JPanel();
        Dashboard = new javax.swing.JPanel();
        navigator = new javax.swing.JPanel();
        adminDashboardButton = new javax.swing.JButton();
        volunteerButton = new javax.swing.JButton();
        calendarButton = new javax.swing.JButton();
        eventButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        Parent1 = new javax.swing.JPanel();
        adminDashboardPanel = new javax.swing.JPanel();
        totalVolunteersPanel = new javax.swing.JPanel();
        newVolunteerRegistrationPanel = new javax.swing.JPanel();
        PendingVolunteersLabel = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        pendingVolunteerTable = new javax.swing.JTable();
        graphPanel = new javax.swing.JPanel();
        TopPanel = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        dateTimeLabel = new javax.swing.JLabel();
        totalEventsPanel = new javax.swing.JPanel();
        upcomingEventsPanel = new javax.swing.JPanel();
        volunteerPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        volunteerTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        sortByComboBox = new javax.swing.JComboBox<>();
        EventPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        addEventsButton = new javax.swing.JButton();
        sortEventsJCombo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        eventsSearchBox = new javax.swing.JTextField();
        CalendarPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        Parent.setBackground(new java.awt.Color(255, 204, 204));
        Parent.setLayout(new java.awt.CardLayout());

        Dashboard.setBackground(new java.awt.Color(143, 202, 225));

        navigator.setBackground(new java.awt.Color(91, 158, 165));
        navigator.setLayout(null);

        adminDashboardButton.setBackground(new java.awt.Color(63, 164, 203));
        adminDashboardButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        adminDashboardButton.setForeground(new java.awt.Color(255, 255, 255));
        adminDashboardButton.setText("🖥️ Admin Dashboard");
        adminDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminDashboardButtonActionPerformed(evt);
            }
        });
        navigator.add(adminDashboardButton);
        adminDashboardButton.setBounds(20, 230, 220, 50);

        volunteerButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        volunteerButton.setText("🧑‍🤝‍🧑 Volunteer");
        volunteerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volunteerButtonActionPerformed(evt);
            }
        });
        navigator.add(volunteerButton);
        volunteerButton.setBounds(20, 360, 210, 50);

        calendarButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        calendarButton.setText("🗓️ Calendar");
        navigator.add(calendarButton);
        calendarButton.setBounds(20, 640, 210, 50);

        eventButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        eventButton.setText("📋 Event");
        navigator.add(eventButton);
        eventButton.setBounds(20, 510, 210, 49);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/VolUnity_logo_new.png"))); // NOI18N
        jLabel9.setText("jLabel9");
        navigator.add(jLabel9);
        jLabel9.setBounds(0, 0, 250, 170);

        Parent1.setLayout(new java.awt.CardLayout());

        adminDashboardPanel.setBackground(new java.awt.Color(214, 228, 231));
        adminDashboardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout totalVolunteersPanelLayout = new javax.swing.GroupLayout(totalVolunteersPanel);
        totalVolunteersPanel.setLayout(totalVolunteersPanelLayout);
        totalVolunteersPanelLayout.setHorizontalGroup(
            totalVolunteersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        totalVolunteersPanelLayout.setVerticalGroup(
            totalVolunteersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        adminDashboardPanel.add(totalVolunteersPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 350, 300));

        newVolunteerRegistrationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PendingVolunteersLabel.setFont(new java.awt.Font("Palatino Linotype", 0, 18)); // NOI18N
        PendingVolunteersLabel.setText("Pending Volunteers");
        newVolunteerRegistrationPanel.add(PendingVolunteersLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        refreshButton.setText("Refresh");
        newVolunteerRegistrationPanel.add(refreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, -1, -1));

        pendingVolunteerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "S. No", "Name", "Registration Date", "Accept", "Decline", "View"
            }
        ));
        jScrollPane3.setViewportView(pendingVolunteerTable);

        newVolunteerRegistrationPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 77, 660, 350));

        adminDashboardPanel.add(newVolunteerRegistrationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 530, 680, 430));

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );

        adminDashboardPanel.add(graphPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 530, 500, 430));

        TopPanel.setBackground(new java.awt.Color(255, 255, 255));
        TopPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        welcomeLabel.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        welcomeLabel.setText("Welcome Back, (name)");
        TopPanel.add(welcomeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        dateTimeLabel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        dateTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateTimeLabel.setText("Date and Time");
        TopPanel.add(dateTimeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 40, 240, -1));

        adminDashboardPanel.add(TopPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 140));

        javax.swing.GroupLayout totalEventsPanelLayout = new javax.swing.GroupLayout(totalEventsPanel);
        totalEventsPanel.setLayout(totalEventsPanelLayout);
        totalEventsPanelLayout.setHorizontalGroup(
            totalEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        totalEventsPanelLayout.setVerticalGroup(
            totalEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        adminDashboardPanel.add(totalEventsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 190, 350, 300));

        javax.swing.GroupLayout upcomingEventsPanelLayout = new javax.swing.GroupLayout(upcomingEventsPanel);
        upcomingEventsPanel.setLayout(upcomingEventsPanelLayout);
        upcomingEventsPanelLayout.setHorizontalGroup(
            upcomingEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        upcomingEventsPanelLayout.setVerticalGroup(
            upcomingEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        adminDashboardPanel.add(upcomingEventsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 190, 420, 300));

        Parent1.add(adminDashboardPanel, "card2");

        volunteerPanel.setBackground(new java.awt.Color(214, 228, 231));
        volunteerPanel.setFont(new java.awt.Font("Segoe UI Historic", 0, 36)); // NOI18N
        volunteerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Serif", 0, 70)); // NOI18N
        jLabel5.setText("Volunteer Record");
        volunteerPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));

        volunteerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Volunteer ID", "Name", "Contact", "Email", "Status", "Options"
            }
        ));
        jScrollPane1.setViewportView(volunteerTable);

        volunteerPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 1270, 690));

        jLabel6.setFont(new java.awt.Font("Perpetua", 0, 24)); // NOI18N
        jLabel6.setText("Search: ");
        volunteerPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 150, -1, -1));

        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });
        volunteerPanel.add(searchTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 150, 200, 30));

        sortByComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort By", "Name", "Status" }));
        sortByComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortByComboBoxActionPerformed(evt);
            }
        });
        volunteerPanel.add(sortByComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 150, 80, 30));

        Parent1.add(volunteerPanel, "card3");

        EventPanel.setBackground(new java.awt.Color(214, 228, 231));
        EventPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Serif", 0, 70)); // NOI18N
        jLabel1.setText("Events Record");
        EventPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Event ID", "Name", "Start Date", "Duration", "Location", "Organizer's name"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        EventPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 1400, 720));

        addEventsButton.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        addEventsButton.setText("+ Add Events");
        addEventsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEventsButtonActionPerformed(evt);
            }
        });
        EventPanel.add(addEventsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        sortEventsJCombo.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        sortEventsJCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort By", "Name", "Date" }));
        EventPanel.add(sortEventsJCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 160, -1, -1));

        jLabel3.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        jLabel3.setText("Search: ");
        EventPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 163, 80, 30));
        EventPanel.add(eventsSearchBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 160, 190, 30));

        Parent1.add(EventPanel, "card4");

        CalendarPanel.setBackground(new java.awt.Color(214, 228, 231));
        CalendarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Serif", 0, 70)); // NOI18N
        jLabel2.setText("Calendar");
        CalendarPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 40, -1, -1));

        Parent1.add(CalendarPanel, "card5");

        javax.swing.GroupLayout DashboardLayout = new javax.swing.GroupLayout(Dashboard);
        Dashboard.setLayout(DashboardLayout);
        DashboardLayout.setHorizontalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addComponent(navigator, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Parent1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DashboardLayout.setVerticalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Parent1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(navigator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Parent.add(Dashboard, "card3");

        getContentPane().add(Parent);
        Parent.setBounds(0, 0, 950, 540);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void adminDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminDashboardButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminDashboardButtonActionPerformed

    private void volunteerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volunteerButtonActionPerformed
        java.awt.CardLayout cl = (java.awt.CardLayout) Parent1.getLayout();
        cl.show(Parent1, "card3" );
    }//GEN-LAST:event_volunteerButtonActionPerformed

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void sortByComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortByComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sortByComboBoxActionPerformed

    private void addEventsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEventsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addEventsButtonActionPerformed

    

// ============== COMPLETE FIX FOR YOUR NewJFrame.java ==============
// 1. REPLACE your showDashboard method with this:
    public void showDashboard(String role, String username) {

        // Update welcome message first
        updateWelcomeMessage(username, role);

        // Start date/time display
        startDateTimeDisplay();

        if ("admin".equalsIgnoreCase(role)) {
            // Switch Parent to show Dashboard panel
            CardLayout cl = (CardLayout) Parent.getLayout();
            cl.show(Parent, "card3");

            // Switch Parent1 to show adminDashboardPanel
            CardLayout cl2 = (CardLayout) Parent1.getLayout();
            cl2.show(Parent1, "card2");

        } else if ("user".equalsIgnoreCase(role)) {
            // Switch Parent to show Dashboard panel
            CardLayout cl = (CardLayout) Parent.getLayout();
            cl.show(Parent, "card3");

            // Switch Parent1 to show volunteerPanel
            CardLayout cl2 = (CardLayout) Parent1.getLayout();
            cl2.show(Parent1, "card3");
        }

        // Force refresh
        Parent.revalidate();
        Parent.repaint();
        this.revalidate();
        this.repaint();
    }

// 2. REPLACE your updateWelcomeMessage method:
    private void updateWelcomeMessage(String username, String role) {

        if ("admin".equalsIgnoreCase(role)) {
            welcomeLabel.setText("<html><div style='font-family:Times New Roman; font-size:32px;'>"
                    + "<b>Welcome back, " + username + "!</b></div>"
                    + "<p style='color:gray; font-size:25px;'>Admin Dashboard</p></html>");
        } else {
            welcomeLabel.setText("<html><div style='font-family:Segoe UI Emoji; font-size:32px;'>"
                    + "<b>Welcome back, " + username + "! 👋</b></div>"
                    + "<p style='color:gray; font-size:25px;'>Volunteer Dashboard</p></html>");
        }

        welcomeLabel.revalidate();
        welcomeLabel.repaint();
    }

// 3. REPLACE your startDateTimeDisplay method:
    private void startDateTimeDisplay() {

        // Update immediately
        updateDateTime();

        // Stop any existing timer first
        if (dateTimeTimer != null && dateTimeTimer.isRunning()) {
            dateTimeTimer.stop();
        }

        // Create new timer
        dateTimeTimer = new javax.swing.Timer(1000, e -> updateDateTime());
        dateTimeTimer.start();

    }

// 4. REPLACE your updateDateTime method:
    private void updateDateTime() {
        try {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter formatter
                    = java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy - hh:mm:ss a");
            String dateTimeText = now.format(formatter);

            dateTimeLabel.setText("<html><div style='text-align:right; font-family:Times New Roman; font-size:14px;'>"
                    + "📅 " + dateTimeText + "</div></html>");

            dateTimeLabel.revalidate();
            dateTimeLabel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// 5. ADD this method to stop the timer when needed:
    public void stopDateTimeDisplay() {
        if (dateTimeTimer != null && dateTimeTimer.isRunning()) {
            dateTimeTimer.stop();
        }
    }

     

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CalendarPanel;
    private javax.swing.JPanel Dashboard;
    private javax.swing.JPanel EventPanel;
    private javax.swing.JPanel Parent;
    private javax.swing.JPanel Parent1;
    private javax.swing.JLabel PendingVolunteersLabel;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JButton addEventsButton;
    private javax.swing.JButton adminDashboardButton;
    private javax.swing.JPanel adminDashboardPanel;
    private javax.swing.JButton calendarButton;
    private javax.swing.JLabel dateTimeLabel;
    private javax.swing.JButton eventButton;
    private javax.swing.JTextField eventsSearchBox;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel navigator;
    private javax.swing.JPanel newVolunteerRegistrationPanel;
    private javax.swing.JTable pendingVolunteerTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JComboBox<String> sortByComboBox;
    private javax.swing.JComboBox<String> sortEventsJCombo;
    private javax.swing.JPanel totalEventsPanel;
    private javax.swing.JPanel totalVolunteersPanel;
    private javax.swing.JPanel upcomingEventsPanel;
    private javax.swing.JButton volunteerButton;
    private javax.swing.JPanel volunteerPanel;
    private javax.swing.JTable volunteerTable;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables

    
}
