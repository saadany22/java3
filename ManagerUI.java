
/**
 * Write a description of class ManagerUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagerUI extends JFrame {
    private SessionManager sessionManager;
    private ScheduleManager scheduleManager;
    private ConferenceDatabase conferenceDatabase;
    private CertificateManager certificateManager;
    private SpeakerDatabase speakerDatabase; // Database for managing speakers
    private JTextField sessionIdField, sessionNameField, dateField, timeField, roomField;
    private JComboBox<String> userDropdown, speakerDropdown;
    private JTextArea sessionDetailsArea, userScheduleArea;

    public ManagerUI(SessionManager sessionManager, ScheduleManager scheduleManager,
                     ConferenceDatabase conferenceDatabase, CertificateManager certificateManager) {
        this.sessionManager = sessionManager;
        this.scheduleManager = scheduleManager;
        this.conferenceDatabase = conferenceDatabase;
        this.certificateManager = certificateManager;
        this.speakerDatabase = new SpeakerDatabase("speaker.csv"); // Initialize with file path
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Conference Manager - Administrative Tools");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title at the top
        JLabel titleLabel = new JLabel("Conference Manager Administrative Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // Left side - form panel
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Session ID:"));
        sessionIdField = new JTextField();
        formPanel.add(sessionIdField);

        formPanel.add(new JLabel("Session Name:"));
        sessionNameField = new JTextField();
        formPanel.add(sessionNameField);

        formPanel.add(new JLabel("Speaker:"));
        speakerDropdown = new JComboBox<>();
        populateSpeakerDropdown();
        formPanel.add(speakerDropdown);

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
        populateUserDropdown();
        formPanel.add(userDropdown);

        add(formPanel, BorderLayout.CENTER);

        // Bottom - button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton createSessionButton = new JButton("Create Session");
        createSessionButton.addActionListener(e -> handleCreateSession());
        buttonPanel.add(createSessionButton);

        JButton viewSessionsButton = new JButton("View All Sessions");
        viewSessionsButton.addActionListener(e -> displayAllSessions());
        buttonPanel.add(viewSessionsButton);

        JButton addUserToSessionButton = new JButton("Add User to Session");
        addUserToSessionButton.addActionListener(e -> handleAddUserToSession());
        buttonPanel.add(addUserToSessionButton);

        JButton addSpeakerButton = new JButton("Add Speaker");
        addSpeakerButton.addActionListener(e -> handleAddSpeaker());
        buttonPanel.add(addSpeakerButton);

        JButton rewardCertificateButton = new JButton("Reward Certificate");
        rewardCertificateButton.addActionListener(e -> handleRewardCertificate());
        buttonPanel.add(rewardCertificateButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Right side - details section
        JPanel detailsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        sessionDetailsArea = new JTextArea();
        sessionDetailsArea.setEditable(false);
        sessionDetailsArea.setBorder(BorderFactory.createTitledBorder("Session Details"));
        detailsPanel.add(new JScrollPane(sessionDetailsArea));

        userScheduleArea = new JTextArea();
        userScheduleArea.setEditable(false);
        userScheduleArea.setBorder(BorderFactory.createTitledBorder("User Schedule"));
        detailsPanel.add(new JScrollPane(userScheduleArea));

        add(detailsPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private void populateUserDropdown() {
        List<String> usernames = conferenceDatabase.getAllUsernames();
        for (String username : usernames) {
            userDropdown.addItem(username);
        }
    }

    private void populateSpeakerDropdown() {
        List<String> speakerNames = speakerDatabase.getSpeakerNames();
        for (String name : speakerNames) {
            speakerDropdown.addItem(name);
        }
    }

    private void handleCreateSession() {
        String sessionId = sessionIdField.getText();
        String sessionName = sessionNameField.getText();
        String speakerName = (String) speakerDropdown.getSelectedItem();
        String date = dateField.getText();
        String time = timeField.getText();
        String room = roomField.getText();

        if (sessionId.isEmpty() || sessionName.isEmpty() || speakerName == null || date.isEmpty() || time.isEmpty() || room.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required to create a session.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sessionManager.createSession(sessionId, sessionName, speakerName, date, time, room);
        JOptionPane.showMessageDialog(this, "Session created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void handleAddSpeaker() {
        JTextField speakerNameField = new JTextField(20);
        JTextField speakerBioField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Speaker Name:"));
        panel.add(speakerNameField);
        panel.add(new JLabel("Speaker Bio:"));
        panel.add(speakerBioField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Speaker", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String name = speakerNameField.getText();
            String bio = speakerBioField.getText();

            if (name.isEmpty() || bio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Both fields are required to add a speaker.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Speaker newSpeaker = new Speaker(name, bio);
            speakerDatabase.addSpeaker(newSpeaker);
            populateSpeakerDropdown();
            JOptionPane.showMessageDialog(this, "Speaker added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
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
            JOptionPane.showMessageDialog(this, "Failed to add user to session.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRewardCertificate() {
        String selectedUser = (String) userDropdown.getSelectedItem();
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "You must select a user.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String sessionId = sessionIdField.getText();
        certificateManager.generateAndSendCertificate(selectedUser, sessionId);
        JOptionPane.showMessageDialog(this, "Certificate has been awarded to " + selectedUser, "Certificate Awarded", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayUserSchedule(String userName) {
        try {
            String schedule = scheduleManager.getUserSchedule(userName);
            userScheduleArea.setText(schedule);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error displaying user schedule: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        sessionIdField.setText("");
        sessionNameField.setText("");
        dateField.setText("");
        timeField.setText("");
        roomField.setText("");
        speakerDropdown.setSelectedIndex(0);
        userDropdown.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SessionDatabase sessionDatabase = new SessionDatabase("session.csv");
        ScheduleDatabase scheduleDatabase = new ScheduleDatabase("schedule.csv");
        ConferenceDatabase conferenceDatabase = new ConferenceDatabase("users.csv");
        CertificateDatabase certificateDatabase = new CertificateDatabase("certificates.csv");

        SessionManager sessionManager = new SessionManager(sessionDatabase);
        ScheduleManager scheduleManager = new ScheduleManager(scheduleDatabase);
        CertificateManager certificateManager = new CertificateManager(certificateDatabase);

        new ManagerUI(sessionManager, scheduleManager, conferenceDatabase, certificateManager);
    }
}







