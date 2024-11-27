
/**
 * Write a description of class ScheduleDatabase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.List;

public class ScheduleDatabase {
    private List<String[]> userSchedules; // Each entry is a user schedule: [userName, sessionId1, sessionId2, ...]

    public ScheduleDatabase() {
        this.userSchedules = new ArrayList<>();
    }

    // Add a session to a user's schedule
    public boolean addSessionToUserSchedule(String userName, String sessionId) {
        for (int i = 0; i < userSchedules.size(); i++) {
            String[] userSchedule = userSchedules.get(i);

            if (userSchedule[0].equals(userName)) {
                // Check if the session is already in the schedule
                for (int j = 1; j < userSchedule.length; j++) {
                    if (userSchedule[j].equals(sessionId)) {
                        return false; // Session already in schedule
                    }
                }

                // Add session to existing schedule
                String[] updatedSchedule = new String[userSchedule.length + 1];
                System.arraycopy(userSchedule, 0, updatedSchedule, 0, userSchedule.length);
                updatedSchedule[userSchedule.length] = sessionId;
                userSchedules.set(i, updatedSchedule); // Update the schedule in the list
                return true;
            }
        }

        // If no user found, add a new schedule for the user
        userSchedules.add(new String[] { userName, sessionId });
        return true;
    }

    // Get a user's schedule as a formatted string
    public String getUserSchedule(String userName) {
        for (String[] userSchedule : userSchedules) {
            if (userSchedule[0].equals(userName)) {
                StringBuilder schedule = new StringBuilder("Schedule for " + userName + ":\n");
                for (int i = 1; i < userSchedule.length; i++) {
                    schedule.append("Session ID: ").append(userSchedule[i]).append("\n");
                }
                return schedule.toString();
            }
        }
        return "No schedule found for user: " + userName;
    }
}








