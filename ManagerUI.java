
/**
 * Write a description of class ManagerUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagerUI extends JFrame {
    private SessionManager sessionManager;
    private ScheduleManager scheduleManager;
    private ConferenceDatabase conferenceDatabase; // For accessing usernames
    private CertificateManager certificateManager;  // Certificate manager
    private JTextField sessionIdField, sessionNameField, speakerField, dateField, timeField, roomField;
    private JComboBox<String> userDropdown; // Dropdown for selecting users
    private JTextArea sessionDetailsArea, userScheduleArea;

    public ManagerUI(SessionManager sessionManager, ScheduleManager scheduleManager, ConferenceDatabase conferenceDatabase, CertificateManager certificateManager) {
        this.sessionManager = sessionManager;
        this.scheduleManager = scheduleManager;
        this.conferenceDatabase = conferenceDatabase;
        this.certificateManager = certificateManager;  // Initialize CertificateManager
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Conference Manager - Administrative Tools");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Conference Manager Administrative Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Session ID:"));
        sessionIdField = new JTextField();
        formPanel.add(sessionIdField);

        formPanel.add(new JLabel("Session Name:"));
        sessionNameField = new JTextField();
        formPanel.add(sessionNameField);

        formPanel.add(new JLabel("Speaker:"));
        speakerField = new JTextField();
        formPanel.add(speakerField);

        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        formPanel.add(dateField);

        formPanel.add(new JLabel("Time (HH:MM):"));
        timeField = new JTextField();
        formPanel.add(timeField);

        formPanel.add(new JLabel("Room:"));
        roomField = new JTextField();
        formPanel.add(roomField);

        formPanel.add(new JLabel("Select User:"));
        userDropdown = new JComboBox<>();
        populateUserDropdown(); // Populate the dropdown with usernames
        formPanel.add(userDropdown);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton createSessionButton = new JButton("Create Session");
        createSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateSession();
            }
        });
        buttonPanel.add(createSessionButton);

        JButton viewSessionsButton = new JButton("View All Sessions");
        viewSessionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllSessions();
            }
        });
        buttonPanel.add(viewSessionsButton);

        JButton addUserToSessionButton = new JButton("Add User to Session");
        addUserToSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddUserToSession();
            }
        });
        buttonPanel.add(addUserToSessionButton);

        // New button to reward certificate
        JButton rewardCertificateButton = new JButton("Reward Certificate");
        rewardCertificateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRewardCertificate(); // Reward certificate to selected user
            }
        });
        buttonPanel.add(rewardCertificateButton);

        add(buttonPanel, BorderLayout.SOUTH);

        JPanel displayPanel = new JPanel(new GridLayout(1, 2));
        sessionDetailsArea = new JTextArea();
        sessionDetailsArea.setEditable(false);
        sessionDetailsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.add(new JScrollPane(sessionDetailsArea));

        userScheduleArea = new JTextArea();
        userScheduleArea.setEditable(false);
        userScheduleArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.add(new JScrollPane(userScheduleArea));

        add(displayPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private void populateUserDropdown() {
        List<String> usernames = conferenceDatabase.getAllUsernames();
        for (String username : usernames) {
            userDropdown.addItem(username);
        }
    }

    private void handleCreateSession() {
        String sessionId = sessionIdField.getText();
        String sessionName = sessionNameField.getText();
        String speaker = speakerField.getText();
        String date = dateField.getText();
        String time = timeField.getText();
        String room = roomField.getText();

        if (sessionId.isEmpty() || sessionName.isEmpty() || speaker.isEmpty() || date.isEmpty() || time.isEmpty() || room.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required to create a session.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sessionManager.createSession(sessionId, sessionName, speaker, date, time, room);
        JOptionPane.showMessageDialog(this, "Session created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        clearFields();
    }

    private void displayAllSessions() {
        String allSessions = sessionManager.getAllSessions();
        sessionDetailsArea.setText(allSessions);
    }

    private void handleAddUserToSession() {
        String sessionId = sessionIdField.getText();
        String selectedUser = (String) userDropdown.getSelectedItem();

        if (sessionId.isEmpty() || selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Both Session ID and a user must be selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = scheduleManager.addUserToSession(selectedUser, sessionId);
        if (success) {
            JOptionPane.showMessageDialog(this, "User added to session successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            displayUserSchedule(selectedUser);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add user to session. The session may already exist in the user's schedule.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRewardCertificate() {
        String selectedUser = (String) userDropdown.getSelectedItem();

        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "You must select a user.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sessionId = sessionIdField.getText();  // Reward certificate for the selected session
        certificateManager.generateAndSendCertificate(selectedUser, sessionId);
        JOptionPane.showMessageDialog(this, "Certificate has been awarded to " + selectedUser, "Certificate Awarded", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayUserSchedule(String userName) {
        try {
            // Get the schedule as a single formatted string
            String schedule = scheduleManager.getUserSchedule(userName);
    
            // Display the result in the text area
            if (schedule != null && !schedule.isEmpty()) {
                userScheduleArea.setText(schedule);
            } else {
                userScheduleArea.setText("No schedule found for " + userName + ".");
            }
        } catch (Exception e) {
            // Handle any unexpected issues
            userScheduleArea.setText("An error occurred while fetching the schedule for " + userName + ".");
        }
    }

    private void clearFields() {
        sessionIdField.setText("");
        sessionNameField.setText("");
        speakerField.setText("");
        dateField.setText("");
        timeField.setText("");
        roomField.setText("");
        userDropdown.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SessionDatabase sessionDatabase = new SessionDatabase("session.csv");
        SessionManager sessionManager = new SessionManager(sessionDatabase);
        ScheduleDatabase scheduleDatabase = new ScheduleDatabase();
        ScheduleManager scheduleManager = new ScheduleManager(scheduleDatabase);
        ConferenceDatabase conferenceDatabase = new ConferenceDatabase("users.csv");

        // Initialize the CertificateManager
        CertificateDatabase certificateDatabase = new CertificateDatabase("certificates.csv");
        CertificateManager certificateManager = new CertificateManager(certificateDatabase);

        new ManagerUI(sessionManager, scheduleManager, conferenceDatabase, certificateManager);
    }
}



