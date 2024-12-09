
/**
 * Write a description of class SessionManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private SessionDatabase sessionDatabase;

    public SessionManager(SessionDatabase sessionDatabase) {
        this.sessionDatabase = sessionDatabase;
    }

    // Adds a session using individual fields instead of a Session object
    public void createSession(String sessionId, String sessionName, String speaker, String date, String time, String room) {
        String[] sessionData = {sessionId, sessionName, speaker, date, time, room};
        sessionDatabase.addSession(sessionData);
    }

    // Updates a session by modifying its fields in the database
    public void updateSession(String sessionId, String updatedName, String updatedSpeaker, String updatedDate, String updatedTime, String updatedRoom) {
        List<String[]> allSessions = sessionDatabase.getAllSessions();
        for (String[] session : allSessions) {
            if (session[0].equals(sessionId)) {
                session[1] = updatedName;
                session[2] = updatedSpeaker;
                session[3] = updatedDate;
                session[4] = updatedTime;
                session[5] = updatedRoom;
                sessionDatabase.updateSessions(allSessions);
                return;
            }
        }
    }

    // Retrieves details for a single session based on its ID
    public String getSessionDetails(String sessionId) {
        List<String[]> allSessions = sessionDatabase.getAllSessions();
        for (String[] session : allSessions) {
            if (session[0].equals(sessionId)) {
                return String.format(
                    "ID: %s, Name: %s, Speaker: %s, Date: %s, Time: %s, Room: %s",
                    session[0], session[1], session[2], session[3], session[4], session[5]
                );
            }
        }
        return "Session not found.";
    }

    // Links a speaker to a specific session
    public void linkSpeakerToSession(String sessionId, String speaker) {
        List<String[]> allSessions = sessionDatabase.getAllSessions();
        for (String[] session : allSessions) {
            if (session[0].equals(sessionId)) {
                session[2] = speaker;
                sessionDatabase.updateSessions(allSessions);
                return;
            }
        }
    }

    // Retrieves a formatted string of all sessions
    public String getAllSessions() {
        List<String[]> allSessions = sessionDatabase.getAllSessions();
        if (allSessions.isEmpty()) {
            return "No sessions are currently available.";
        }

        StringBuilder sessionList = new StringBuilder("Available Sessions:\n");
        for (String[] session : allSessions) {
            sessionList.append(String.format(
                "ID: %s, Name: %s, Speaker: %s, Date: %s, Time: %s, Room: %s\n",
                session[0], session[1], session[2], session[3], session[4], session[5]
            ));
        }
        return sessionList.toString();
    }
    
    
    // Checks if a session exists based on its ID
    public boolean sessionExists(String sessionId) {
        List<String[]> allSessions = sessionDatabase.getAllSessions();
        for (String[] session : allSessions) {
            if (session[0].equals(sessionId)) {
                return true;  // Session found
            }
        }
        return false;  // Session not found
    }// Other methods remain unchanged...
}




