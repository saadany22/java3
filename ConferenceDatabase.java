
/**
 * Write a description of class ConferenceDatabase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;

public class ConferenceDatabase {
    private String databaseFile;

    public ConferenceDatabase(String databaseFile) {
        this.databaseFile = databaseFile;
        ensureDatabaseFile();
    }

    private void ensureDatabaseFile() {
        File file = new File(databaseFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Adds a new user to the database
    public boolean addUser(String attendeeID, String name, String email, String password) {
        if (getUserByEmail(email) != null) {
            return false; // User already exists
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFile, true))) {
            writer.write(attendeeID + "," + name + "," + email + "," + password);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieves a user by email
    public String[] getUserByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(databaseFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[2].equals(email)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }

    // Retrieves all users in the database
    public List<String[]> getAllUsers() {
        List<String[]> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(databaseFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    users.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    // Retrieves all usernames (names) in the database
    public List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(databaseFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    usernames.add(parts[1]); // Assuming name is the second field
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usernames;
    }
}

