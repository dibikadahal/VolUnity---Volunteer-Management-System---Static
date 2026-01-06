package View;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import javax.swing.border.*;
import java.time.format.TextStyle;
import java.util.Locale;

public class CalendarPanel extends JPanel {

    //==========COLOUR PALETTE=============
    private final Color PRIMARY = new Color(91, 158, 165);
    private final Color BACKGROUND = new Color(245, 248, 249);
    private final Color CELL_BG = Color.WHITE;
    private final Color SELECTED_BG = new Color(210, 233, 235);
    private final Color TEXT_PRIMARY = new Color(45, 55, 72);

    private YearMonth currentMonth;
    private JLabel monthLabel;
    private JPanel calendarGrid;

    public CalendarPanel() {
        currentMonth = YearMonth.now();
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);

        add(createHeader(), BorderLayout.NORTH);

        // Add scroll pane to ensure everything is visible
        JScrollPane scrollPane = new JScrollPane(createCalendarBody());
        scrollPane.setBorder(null);
        scrollPane.setBackground(BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        refreshCalendar();
    }

    //========HEADER===========
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 0));
        header.setBackground(BACKGROUND);
        header.setBorder(new EmptyBorder(10, 15, 10, 15));

        JButton prevBtn = new JButton("◀");
        JButton nextBtn = new JButton("▶");

        styleNavButton(prevBtn);
        styleNavButton(nextBtn);

        prevBtn.addActionListener(e -> {
            currentMonth = currentMonth.minusMonths(1);
            refreshCalendar();
        });

        nextBtn.addActionListener(e -> {
            currentMonth = currentMonth.plusMonths(1);
            refreshCalendar();
        });

        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        monthLabel.setForeground(TEXT_PRIMARY);

        header.add(prevBtn, BorderLayout.WEST);
        header.add(monthLabel, BorderLayout.CENTER);
        header.add(nextBtn, BorderLayout.EAST);

        return header;
    }

    //===============BODY=====================
    private JPanel createCalendarBody() {
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BACKGROUND);

        body.add(createDayNames(), BorderLayout.NORTH);

        calendarGrid = new JPanel();
        calendarGrid.setBackground(BACKGROUND);
        calendarGrid.setBorder(new EmptyBorder(5, 10, 10, 10));

        body.add(calendarGrid, BorderLayout.CENTER);
        return body;
    }

    //=======DAY NAMES==================
    private JPanel createDayNames() {
        JPanel days = new JPanel(new GridLayout(1, 7, 3, 0));
        days.setBackground(BACKGROUND);
        days.setBorder(new EmptyBorder(3, 10, 3, 10));

        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (String day : dayNames) {
            JLabel lbl = new JLabel(day, SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
            lbl.setForeground(PRIMARY);
            days.add(lbl);
        }
        return days;
    }

    //============REFRESH CALENDAR==================
    private void refreshCalendar() {
        calendarGrid.removeAll();

        monthLabel.setText(
                currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                + " " + currentMonth.getYear()
        );

        LocalDate firstDay = currentMonth.atDay(1);
        int startDayOfWeek = firstDay.getDayOfWeek().getValue();
        int startDay = (startDayOfWeek == 7) ? 0 : startDayOfWeek;
        int daysInMonth = currentMonth.lengthOfMonth();

        LocalDate today = LocalDate.now();

        // Calculate weeks needed
        int totalCells = startDay + daysInMonth;
        int weeksNeeded = (int) Math.ceil(totalCells / 7.0);

        // Set grid layout
        calendarGrid.setLayout(new GridLayout(weeksNeeded, 7, 3, 3));

        // Add empty cells before first day
        for (int i = 0; i < startDay; i++) {
            JPanel emptyCell = createDateCell();
            emptyCell.setBackground(new Color(248, 248, 248));
            emptyCell.setBorder(new LineBorder(new Color(230, 230, 230)));
            calendarGrid.add(emptyCell);
        }

        // Add cells for each day
        for (int dayNum = 1; dayNum <= daysInMonth; dayNum++) {
            JPanel cell = createDateCell();

            JLabel dateLabel = new JLabel(String.valueOf(dayNum), SwingConstants.CENTER);
            dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            dateLabel.setForeground(TEXT_PRIMARY);
            dateLabel.setBorder(new EmptyBorder(3, 0, 0, 0));
            cell.add(dateLabel, BorderLayout.NORTH);

            // Highlight today
            if (today.getYear() == currentMonth.getYear()
                    && today.getMonth() == currentMonth.getMonth()
                    && today.getDayOfMonth() == dayNum) {
                cell.setBackground(SELECTED_BG);
                dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
            }

            calendarGrid.add(cell);
        }

        // Fill remaining cells
        int cellsAdded = startDay + daysInMonth;
        int totalCellsNeeded = weeksNeeded * 7;
        while (cellsAdded < totalCellsNeeded) {
            JPanel emptyCell = createDateCell();
            emptyCell.setBackground(new Color(248, 248, 248));
            emptyCell.setBorder(new LineBorder(new Color(230, 230, 230)));
            calendarGrid.add(emptyCell);
            cellsAdded++;
        }

        revalidate();
        repaint();
    }

    // ===== DATE CELL =====
    private JPanel createDateCell() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CELL_BG);
        panel.setBorder(new LineBorder(new Color(220, 220, 220)));
        panel.setPreferredSize(new Dimension(75, 55));
        panel.setMinimumSize(new Dimension(75, 55));
        return panel;
    }

    private void styleNavButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setBorder(new EmptyBorder(4, 10, 4, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btn.setPreferredSize(new Dimension(40, 28));
    }
}
