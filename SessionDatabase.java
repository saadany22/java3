
/**
 * Write a description of class SessionDatabase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;

public class SessionDatabase {
    private String fileName;
    private List<String[]> sessions;

    public SessionDatabase(String fileName) {
        this.fileName = fileName;
        this.sessions = new ArrayList<>();
        loadSessionsFromFile();
    }

    // Adds a session to the database
    public void addSession(String[] sessionData) {
        sessions.add(sessionData);
        saveSessionsToFile();
    }

    // Retrieves all sessions from the database
    public List<String[]> getAllSessions() {
        return new ArrayList<>(sessions); // Return a copy to avoid modification outside
    }

    // Retrieves a session by its ID
    public String[] getSessionById(String sessionId) {
        for (String[] session : sessions) {
            if (session[0].equals(sessionId)) {
                return session;
            }
        }
        return null; // If session not found
    }

    // Updates the sessions after modifying one or more
    public void updateSessions(List<String[]> updatedSessions) {
        this.sessions = updatedSessions;
        saveSessionsToFile();
    }

    // Loads sessions from the CSV file into the list
    private void loadSessionsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] sessionData = line.split(",");
                sessions.add(sessionData);
            }
        } catch (IOException e) {
            System.err.println("Error loading sessions from file: " + e.getMessage());
        }
    }

    // Saves the current list of sessions to the CSV file
    private void saveSessionsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] session : sessions) {
                writer.write(String.join(",", session));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving sessions to file: " + e.getMessage());
        }
    }
}



