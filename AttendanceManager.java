
/**
 * Write a description of class AttendanceManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.List;

public class AttendanceManager {
    private ConferenceDatabase database;

    public AttendanceManager(ConferenceDatabase database) {
        this.database = database;
    }

    public boolean signup(String name, String email, String password) {
        String attendeeID = java.util.UUID.randomUUID().toString();
        return database.addUser(attendeeID, name, email, password);
    }

    public String login(String email, String password) {
        String[] user = database.getUserByEmail(email);
        if (user != null && user[3].equals(password)) {
            return user[1]; // Return user name
        }
        return null; // Login failed
    }

    public String getAllUsers() {
        List<String[]> users = database.getAllUsers();
        StringBuilder sb = new StringBuilder();
        for (String[] user : users) {
            sb.append("ID: ").append(user[0])
              .append(", Name: ").append(user[1])
              .append(", Email: ").append(user[2])
              .append("\n");
        }
        return sb.toString();
    }
    

}


