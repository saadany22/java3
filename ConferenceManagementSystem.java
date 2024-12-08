
/**
 * Write a description of class main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConferenceManagementSystem {

    private JFrame frame;
    private JTable attendeeTable, eventTable;
    private DefaultTableModel attendeeModel, eventModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConferenceManagementSystem().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // Main frame setup
        frame = new JFrame("Conference Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Attendee Management", createAttendeePanel());
        tabbedPane.addTab("Event Management", createEventPanel());
        tabbedPane.addTab("Overview", createOverviewPanel());

        // Add tabbed pane to frame
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel createAttendeePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Form for attendee registration
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Register Attendee"));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JButton addButton = new JButton("Add Attendee");

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(new JLabel()); // Spacer
        formPanel.add(addButton);

        // Table for displaying attendees
        attendeeModel = new DefaultTableModel(new String[]{"Name", "Email"}, 0);
        attendeeTable = new JTable(attendeeModel);
        JScrollPane tableScrollPane = new JScrollPane(attendeeTable);

        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            if (!name.isEmpty() && !email.isEmpty()) {
                attendeeModel.addRow(new Object[]{name, email});
                nameField.setText("");
                emailField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEventPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Form for event creation
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Create Event"));

        JLabel eventNameLabel = new JLabel("Event Name:");
        JTextField eventNameField = new JTextField();

        JLabel eventDateLabel = new JLabel("Event Date:");
        JTextField eventDateField = new JTextField("YYYY-MM-DD");

        JButton createEventButton = new JButton("Add Event");

        formPanel.add(eventNameLabel);
        formPanel.add(eventNameField);
        formPanel.add(eventDateLabel);
        formPanel.add(eventDateField);
        formPanel.add(new JLabel()); // Spacer
        formPanel.add(createEventButton);

        // Table for displaying events
        eventModel = new DefaultTableModel(new String[]{"Event Name", "Date"}, 0);
        eventTable = new JTable(eventModel);
        JScrollPane tableScrollPane = new JScrollPane(eventTable);

        createEventButton.addActionListener(e -> {
            String eventName = eventNameField.getText().trim();
            String eventDate = eventDateField.getText().trim();
            if (!eventName.isEmpty() && !eventDate.isEmpty()) {
                eventModel.addRow(new Object[]{eventName, eventDate});
                eventNameField.setText("");
                eventDateField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createOverviewPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        // Overview of attendees
        JPanel attendeeOverview = new JPanel(new BorderLayout());
        attendeeOverview.setBorder(BorderFactory.createTitledBorder("Registered Attendees"));
        attendeeOverview.add(new JScrollPane(attendeeTable), BorderLayout.CENTER);

        // Overview of events
        JPanel eventOverview = new JPanel(new BorderLayout());
        eventOverview.setBorder(BorderFactory.createTitledBorder("Scheduled Events"));
        eventOverview.add(new JScrollPane(eventTable), BorderLayout.CENTER);

        panel.add(attendeeOverview);
        panel.add(eventOverview);

        return panel;
    }
}




