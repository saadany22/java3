
/**
 * Write a description of class ScheduleDatabase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;

public class ScheduleDatabase {
    private String fileName;
    private List<String[]> userSchedules; // List of records [userName, sessionId]

    public ScheduleDatabase(String fileName) {
        this.fileName = fileName;
        this.userSchedules = new ArrayList<>();
        loadSchedulesFromFile();
    }

    // Adds a session to a user's schedule
    public boolean addSessionToUserSchedule(String userName, String sessionId) {
        // Check if the session for the user already exists
        for (String[] record : userSchedules) {
            if (record[0].equals(userName) && record[1].equals(sessionId)) {
                return false; // Session already in schedule
            }
        }

        // Add the new session for the user
        userSchedules.add(new String[]{userName, sessionId});
        saveSchedulesToFile();
        return true;
    }

    // Retrieves a user's schedule as a formatted string
    public String getUserSchedule(String userName) {
        StringBuilder result = new StringBuilder("Schedule for " + userName + ":\n");
        boolean found = false;

        for (String[] record : userSchedules) {
            if (record[0].equals(userName)) {
                result.append("Session ID: ").append(record[1]).append("\n");
                found = true;
            }
        }

        if (!found) {
            return "No schedule found for user: " + userName;
        }
        return result.toString();
    }

    // Exports all schedules to a CSV file
    public void exportScheduleToCSV(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write header
            writer.write("User Name, Session ID\n");

            // Write each record to the file
            for (String[] record : userSchedules) {
                writer.write(String.join(",", record));
                writer.newLine();
            }

            System.out.println("Schedule data exported successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving schedule data to CSV: " + e.getMessage());
        }
    }

    // Loads schedules from the CSV file into the list
    private void loadSchedulesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split line by comma and add to the list
                String[] record = line.split(",");
                if (record.length == 2) { // Ensure valid format
                    userSchedules.add(record);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading schedules from file: " + e.getMessage());
        }
    }

    // Saves the current list of schedules to the CSV file
    private void saveSchedulesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] record : userSchedules) {
                writer.write(String.join(",", record));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving schedules to file: " + e.getMessage());
        }
    }
}











