
/**
 * Write a description of class main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static Manager manager = new Manager(); // Handles user data
    private static String loggedInUserID = null; // Tracks the logged-in user

    public static void main(String[] args) {
        // Create the main JFrame
        JFrame frame = new JFrame("Conference Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Main panel for buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 10, 10));

        // Create buttons for each action
        JButton signupButton = new JButton("Sign Up");
        JButton loginButton = new JButton("Log In");
        JButton retrieveUsersButton = new JButton("Retrieve All Users");
        JButton exitButton = new JButton("Exit");

        // Add buttons to the panel
        mainPanel.add(signupButton);
        mainPanel.add(loginButton);
        mainPanel.add(retrieveUsersButton);
        mainPanel.add(exitButton);

        // Add action listeners to the buttons
        signupButton.addActionListener(e -> showSignupDialog(frame));
        loginButton.addActionListener(e -> showLoginDialog(frame));
        retrieveUsersButton.addActionListener(e -> showAllUsersDialog(frame));
        exitButton.addActionListener(e -> System.exit(0));

        // Add panel to frame and make it visible
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Show the Sign-Up dialog
    private static void showSignupDialog(JFrame parent) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Sign Up", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            boolean success = manager.signup(name, email, password);
            if (success) {
                JOptionPane.showMessageDialog(parent, "Sign-up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent, "Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Show the Log-In dialog
    private static void showLoginDialog(JFrame parent) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Log In", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            String userID = manager.login(email, password);
            if (userID != null) {
                loggedInUserID = userID;
                JOptionPane.showMessageDialog(parent, "Login successful! Your ID: " + userID, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Show all users in the database
    private static void showAllUsersDialog(JFrame parent) {
        StringBuilder allUsers = new StringBuilder("=== Registered Users ===\n");
        java.util.List<User> users = manager.getAllUsers();

        if (users.isEmpty()) {
            allUsers.append("No users are currently registered.");
        } else {
            for (User user : users) {
                allUsers.append("Name: ").append(user.getName())
                        .append(", Email: ").append(user.getEmail())
                        .append(", ID: ").append(user.getAttendeeID())
                        .append("\n");
            }
        }

        // Show the users in a message dialog
        JOptionPane.showMessageDialog(parent, allUsers.toString(), "All Users", JOptionPane.INFORMATION_MESSAGE);
    }
}


