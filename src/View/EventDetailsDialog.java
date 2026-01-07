/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Event;
import javax.swing.*;
import java.awt.*;

public class EventDetailsDialog extends JDialog {

    public EventDetailsDialog(JFrame parent, Event event) {
        super(parent, "Event Details", true);
        initComponents(event);
        setLocationRelativeTo(parent);
    }

    private void initComponents(Event event) {
        setSize(600, 700);
        setResizable(false);
        setUndecorated(true);

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
        JLabel titleLabel = new JLabel("EVENT DETAILS");
        titleLabel.setFont(new Font("Sitka Text", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Details Panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addDetailRow(detailsPanel, "Event ID:", event.getEventId());
        addDetailRow(detailsPanel, "Event Name:", event.getEventName());
        addDetailRow(detailsPanel, "Description:", event.getDescription());
        addDetailRow(detailsPanel, "Start Date:", event.getStartDate());
        addDetailRow(detailsPanel, "End Date:", event.getEndDate());
        addDetailRow(detailsPanel, "Location:", event.getLocation());
        addDetailRow(detailsPanel, "Event Type:", event.getEventType());
        addDetailRow(detailsPanel, "Event Status:", event.getEventStatus());
        addDetailRow(detailsPanel, "Organizer Name:", event.getOrganizerName());
        addDetailRow(detailsPanel, "Organizer Contact:", event.getOrganizerContact());

        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Close Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton closeButton = new JButton("CLOSE");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        closeButton.setBackground(new Color(200, 200, 200));
        closeButton.setForeground(new Color(60, 60, 60));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setPreferredSize(new Dimension(120, 40));
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addDetailRow(JPanel panel, String label, String value) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 5));
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Sitka Text", Font.BOLD, 16));
        labelComp.setForeground(new Color(91, 158, 165));

        JTextArea valueComp = new JTextArea(value);
        valueComp.setFont(new Font("Arial", Font.PLAIN, 15));
        valueComp.setForeground(new Color(60, 60, 60));
        valueComp.setEditable(false);
        valueComp.setLineWrap(true);
        valueComp.setWrapStyleWord(true);
        valueComp.setBackground(Color.WHITE);
        valueComp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        rowPanel.add(labelComp, BorderLayout.NORTH);
        rowPanel.add(valueComp, BorderLayout.CENTER);

        panel.add(rowPanel);

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(220, 220, 220));
        panel.add(separator);
    }

    public static void showDialog(JFrame parent, Event event) {
        // Create glass pane for dimming effect
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

        EventDetailsDialog dialog = new EventDetailsDialog(parent, event);
        dialog.setVisible(true);

        glassPane.setVisible(false);
    }
}
