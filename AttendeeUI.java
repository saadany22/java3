
/**
 * Write a description of class AttendeeUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class AttendeeUI extends JFrame {
    private AttendanceManager attendanceManager;
    private FeedbackManager feedbackManager;
    private SessionManager sessionManager;
    private ScheduleManager scheduleManager;
    private CertificateDatabase certificateDatabase;
    private JTextField emailField, passwordField, nameField;
    private JTextArea userDetailsArea;
    private String loggedInUserName = null;

    public AttendeeUI(AttendanceManager attendanceManager, FeedbackManager feedbackManager,
                      SessionManager sessionManager, ScheduleManager scheduleManager,
                      CertificateDatabase certificateDatabase) {
        this.attendanceManager = attendanceManager;
        this.feedbackManager = feedbackManager;
        this.sessionManager = sessionManager;
        this.scheduleManager = scheduleManager;
        this.certificateDatabase = certificateDatabase;
        initializeUI();
    }

    private void initializeUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }
    
        setTitle("Conference Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    
        JLabel titleLabel = new JLabel("Welcome to the Conference Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);
    
        // Main Content Panel (split left and right)
        JPanel mainPanel = new JPanel(new BorderLayout(20, 10)); // Added horizontal gap
        add(mainPanel, BorderLayout.CENTER);
    
        // Form Panel (User Registration & Login)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("User Registration & Login"));
        formPanel.setBackground(Color.WHITE);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JLabel nameLabel = new JLabel("Name (Sign Up):");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(nameLabel, gbc);
    
        nameField = new JTextField(20); // Increased width
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        formPanel.add(nameField, gbc);
    
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);
    
        emailField = new JTextField(20); // Increased width
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(emailField, gbc);
    
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);
    
        passwordField = new JPasswordField(20); // Increased width
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(passwordField, gbc);
    
        // Make form panel wider
        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.add(formPanel, BorderLayout.CENTER);
        formContainer.setBorder(new EmptyBorder(10, 20, 10, 20)); // Add padding for cleaner look
    
        mainPanel.add(formContainer, BorderLayout.CENTER);
    
        // User Details Section (always visible)
        userDetailsArea = new JTextArea(10, 20);
        userDetailsArea.setEditable(false);
        userDetailsArea.setBorder(BorderFactory.createTitledBorder("Details"));
        JScrollPane scrollPane = new JScrollPane(userDetailsArea);
        mainPanel.add(scrollPane, BorderLayout.EAST);
    
        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10)); // Fit all buttons in 2 rows
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
    
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
        feedbackButton.setEnabled(false);
        buttonPanel.add(feedbackButton);
    
        JButton viewSessionsButton = new JButton("View Sessions");
        viewSessionsButton.addActionListener(e -> displayAllSessions());
        buttonPanel.add(viewSessionsButton);
    
        JButton addToScheduleButton = new JButton("Add to Schedule");
        addToScheduleButton.addActionListener(e -> addToSchedule());
        addToScheduleButton.setEnabled(false);
        buttonPanel.add(addToScheduleButton);
    
        JButton viewScheduleButton = new JButton("View Schedule");
        viewScheduleButton.addActionListener(e -> viewSchedule());
        buttonPanel.add(viewScheduleButton);
    
        JButton viewCertificatesButton = new JButton("View Certificates");
        viewCertificatesButton.addActionListener(e -> viewCertificates());
        buttonPanel.add(viewCertificatesButton);
    
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        loginButton.addActionListener(e -> feedbackButton.setEnabled(loggedInUserName != null));
        viewSessionsButton.addActionListener(e -> addToScheduleButton.setEnabled(loggedInUserName != null));
    
        setVisible(true);
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
        ScheduleDatabase scheduleDatabase = new ScheduleDatabase("schedule.csv");
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



















