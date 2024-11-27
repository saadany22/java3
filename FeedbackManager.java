
/**
 * Write a description of class FeedbackManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.List;

public class FeedbackManager {
    private FeedbackDatabase feedbackDatabase;

    // Constructor
    public FeedbackManager(FeedbackDatabase feedbackDatabase) {
        this.feedbackDatabase = feedbackDatabase;
    }

    // Allows a user to submit feedback
    public boolean submitFeedback(String userName, String feedbackText) {
        return feedbackDatabase.addFeedback(userName, feedbackText);
    }

    // Retrieves all feedback from the database
    public String getFeedback() {
        List<String[]> feedbackEntries = feedbackDatabase.getAllFeedback();
        StringBuilder sb = new StringBuilder("Feedback Data:\n");

        for (String[] entry : feedbackEntries) {
            sb.append("User: ").append(entry[0])
              .append(", Feedback: ").append(entry[1])
              .append("\n");
        }
        return sb.toString();
    }
}

