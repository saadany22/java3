
/**
 * Write a description of class ScheduleManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ScheduleManager {
    private ScheduleDatabase scheduleDatabase;

    public ScheduleManager(ScheduleDatabase scheduleDatabase) {
        this.scheduleDatabase = scheduleDatabase;
    }

    // Add a session to a user's schedule
    public boolean addUserToSession(String userName, String sessionId) {
        return scheduleDatabase.addSessionToUserSchedule(userName, sessionId);
    }

    // Retrieve the schedule of a user
    public String getUserSchedule(String userName) {
        return scheduleDatabase.getUserSchedule(userName);
    }
}








    
 





