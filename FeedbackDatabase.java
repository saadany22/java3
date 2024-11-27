
/**
 * Write a description of class FeedbackDatabase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDatabase {
    private String feedbackFile;

    // Constructor
    public FeedbackDatabase(String feedbackFile) {
        this.feedbackFile = feedbackFile;
        ensureFeedbackFile();
    }

    // Ensure feedback.csv exists
    private void ensureFeedbackFile() {
        File file = new File(feedbackFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Adds feedback to feedback.csv
    public boolean addFeedback(String userName, String feedbackText) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(feedbackFile, true))) {
            writer.write(userName + "," + feedbackText);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieves all feedback entries from feedback.csv
    public List<String[]> getAllFeedback() {
        List<String[]> feedbackEntries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(feedbackFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2); // Split into userName and feedbackText
                if (parts.length == 2) {
                    feedbackEntries.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feedbackEntries;
    }
}

