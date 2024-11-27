
/**
 * Write a description of class Manager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;

public class Manager {
    private HashMap<String, User> usersByEmail = new HashMap<>();
    private final String FILE_NAME = "users.csv"; // File to store user data

    public Manager() {
        loadUsersFromCSV(); // Load users from the CSV file on startup
    }

    // Load users from the CSV file
    private void loadUsersFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 4) { // Ensure correct format
                    String attendeeID = userData[0];
                    String name = userData[1];
                    String email = userData[2];
                    String password = userData[3];
                    User user = new User(attendeeID, name, email, password);
                    usersByEmail.put(email, user);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found. Starting with an empty database.");
        } catch (IOException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        }
    }

    // Save users to the CSV file
    private void saveUsersToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : usersByEmail.values()) {
                writer.write(user.getAttendeeID() + "," + user.getName() + "," + user.getEmail() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to CSV file: " + e.getMessage());
        }
    }

    // Sign up method - Adds a new user and saves to the CSV file
    public boolean signup(String name, String email, String password) {
        if (usersByEmail.containsKey(email)) {
            return false; // User already exists
        }
        String attendeeID = UUID.randomUUID().toString(); // Generate a unique ID
        User newUser = new User(attendeeID, name, email, password);
        usersByEmail.put(email, newUser);
        saveUsersToCSV(); // Save updated users to file
        return true;
    }

    // Log in method - Returns the attendee ID if credentials are valid
    public String login(String email, String password) {
        User user = usersByEmail.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user.getAttendeeID(); // Return the ID of the logged-in user
        }
        return null; // Invalid credentials
    }

    // Method to retrieve the name of a logged-in user by ID
    public String getAttendeeName(String attendeeID) {
        for (User user : usersByEmail.values()) {
            if (user.getAttendeeID().equals(attendeeID)) {
                return user.getName();
            }
        }
        return null;
    }

    // Method to retrieve all users
    public List<User> getAllUsers() {
        return new ArrayList<>(usersByEmail.values());
    }
}



