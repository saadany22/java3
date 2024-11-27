
/**
 * Write a description of class AttendeeUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AttendeeUI extends JFrame {
    private AttendanceManager attendanceManager;
    private FeedbackManager feedbackManager;
    private SessionManager sessionManager;
    private ScheduleManager scheduleManager;
    private CertificateDatabase certificateDatabase; // Add reference to CertificateDatabase
    private JTextField emailField, passwordField, nameField;
    private JTextArea userDetailsArea;
    private String loggedInUserName = null; // Tracks the currently logged-in user

    public AttendeeUI(AttendanceManager attendanceManager, FeedbackManager feedbackManager,
                      SessionManager sessionManager, ScheduleManager scheduleManager,
                      CertificateDatabase certificateDatabase) { // Pass CertificateDatabase to constructor
        this.attendanceManager = attendanceManager;
        this.feedbackManager = feedbackManager;
        this.sessionManager = sessionManager;
        this.scheduleManager = scheduleManager;
        this.certificateDatabase = certificateDatabase; // Initialize CertificateDatabase
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Conference Management System");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to the Conference Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Name (Sign Up):"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> handleSignUp());
        buttonPanel.add(signUpButton);

        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(e -> handleLogIn());
        buttonPanel.add(loginButton);

        JButton viewDetailsButton = new JButton("View All Users");
        viewDetailsButton.addActionListener(e -> displayAllUsers());
        buttonPanel.add(viewDetailsButton);

        JButton feedbackButton = new JButton("Submit Feedback");
        feedbackButton.addActionListener(e -> submitFeedback());
        feedbackButton.setEnabled(false); // Enabled only after login
        buttonPanel.add(feedbackButton);

        JButton viewSessionsButton = new JButton("View Sessions");
        viewSessionsButton.addActionListener(e -> displayAllSessions());
        buttonPanel.add(viewSessionsButton);

        JButton addToScheduleButton = new JButton("Add to Schedule");
        addToScheduleButton.addActionListener(e -> addToSchedule());
        addToScheduleButton.setEnabled(false); // Initially disabled
        buttonPanel.add(addToScheduleButton);

        JButton viewScheduleButton = new JButton("View Schedule");
        viewScheduleButton.addActionListener(e -> viewSchedule());
        buttonPanel.add(viewScheduleButton);

        // New Button to View Certificates
        JButton viewCertificatesButton = new JButton("View Certificates");
        viewCertificatesButton.addActionListener(e -> viewCertificates());
        buttonPanel.add(viewCertificatesButton);

        add(buttonPanel, BorderLayout.SOUTH);

        userDetailsArea = new JTextArea();
        userDetailsArea.setEditable(false);
        userDetailsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(userDetailsArea), BorderLayout.EAST);

        setVisible(true);

        loginButton.addActionListener(e -> feedbackButton.setEnabled(loggedInUserName != null));
        viewSessionsButton.addActionListener(e -> addToScheduleButton.setEnabled(loggedInUserName != null));
    }

    private void handleSignUp() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required for sign-up.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = attendanceManager.signup(name, email, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Sign-up successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "An account with this email already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogIn() {
        String email = emailField.getText();
        String password = passwordField.getText();

        loggedInUserName = attendanceManager.login(email, password);
        if (loggedInUserName != null) {
            JOptionPane.showMessageDialog(this, "Welcome back, " + loggedInUserName + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAllUsers() {
        String allUsers = attendanceManager.getAllUsers();
        userDetailsArea.setText(allUsers);
    }

    private void submitFeedback() {
        if (loggedInUserName == null) {
            JOptionPane.showMessageDialog(this, "Please log in to submit feedback.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String feedback = JOptionPane.showInputDialog(this, "Enter your feedback:", "Submit Feedback", JOptionPane.PLAIN_MESSAGE);

        if (feedback != null && !feedback.isEmpty()) {
            boolean success = feedbackManager.submitFeedback(loggedInUserName, feedback);
            if (success) {
                JOptionPane.showMessageDialog(this, "Thank you for your feedback!", "Feedback Submitted", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error submitting feedback. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Feedback cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAllSessions() {
        String allSessions = sessionManager.getAllSessions();
        userDetailsArea.setText(allSessions);
    }

    private void addToSchedule() {
        if (loggedInUserName == null) {
            JOptionPane.showMessageDialog(this, "Please log in to add sessions to your schedule.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sessionId = JOptionPane.showInputDialog(this, "Enter Session ID to add to your schedule:");
        if (sessionId != null && !sessionId.isEmpty()) {
            boolean success = scheduleManager.addUserToSession(loggedInUserName, sessionId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Session added to your schedule!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add session. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewSchedule() {
        if (loggedInUserName == null) {
            JOptionPane.showMessageDialog(this, "Please log in to view your schedule.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String schedule = scheduleManager.getUserSchedule(loggedInUserName);
        userDetailsArea.setText(schedule);
    }

    // New Method to View Certificates
    private void viewCertificates() {
        if (loggedInUserName == null) {
            JOptionPane.showMessageDialog(this, "Please log in to view your certificates.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> certificates = certificateDatabase.getCertificatesForUser(loggedInUserName);
        if (certificates.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No certificates found for the user.", "No Certificates", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder certList = new StringBuilder("Here are your certificates:\n\n");
            for (String cert : certificates) {
                certList.append(cert).append("\n");
            }
            userDetailsArea.setText(certList.toString());
        }
    }

    public static void main(String[] args) {
        // Initialize the required databases
        ConferenceDatabase conferenceDatabase = new ConferenceDatabase("users.csv");
        FeedbackDatabase feedbackDatabase = new FeedbackDatabase("feedback.csv");
        SessionDatabase sessionDatabase = new SessionDatabase("session.csv");
        ScheduleDatabase scheduleDatabase = new ScheduleDatabase();
        CertificateDatabase certificateDatabase = new CertificateDatabase("certificates.csv");

        // Initialize Managers
        AttendanceManager attendanceManager = new AttendanceManager(conferenceDatabase);
        FeedbackManager feedbackManager = new FeedbackManager(feedbackDatabase);
        SessionManager sessionManager = new SessionManager(sessionDatabase);
        ScheduleManager scheduleManager = new ScheduleManager(scheduleDatabase);

        // Launch the Attendee UI
        new AttendeeUI(attendanceManager, feedbackManager, sessionManager, scheduleManager, certificateDatabase);
    }
}

















