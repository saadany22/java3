
/**
 * Write a description of class ScheduleDatabase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleDatabase {
    private Map<String, List<String>> userSchedules; // Maps userName to a list of sessionIds

    public ScheduleDatabase() {
        this.userSchedules = new HashMap<>();
    }

    // Add a session to a user's schedule
    public boolean addSessionToUserSchedule(String userName, String sessionId) {
        // Get or create the user's schedule
        userSchedules.putIfAbsent(userName, new ArrayList<>());

        // Check if the session is already in the schedule
        List<String> schedule = userSchedules.get(userName);
        if (schedule.contains(sessionId)) {
            return false; // Session already in schedule
        }

        // Add the session to the schedule
        schedule.add(sessionId);
        return true;
    }

    // Get a user's schedule as a formatted string
    public String getUserSchedule(String userName) {
        List<String> schedule = userSchedules.get(userName);

        if (schedule == null || schedule.isEmpty()) {
            return "No schedule found for user: " + userName;
        }

        StringBuilder result = new StringBuilder("Schedule for " + userName + ":\n");
        for (String sessionId : schedule) {
            result.append("Session ID: ").append(sessionId).append("\n");
        }
        return result.toString();
    }
}









