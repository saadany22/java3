
/**
 * Write a description of class ConferenceDatabase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class User {
    private String attendeeID;
    private String name;
    private String email;
    private String password;

    // Constructor
    public User(String attendeeID, String name, String email, String password) {
        this.attendeeID = attendeeID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getAttendeeID() {
        return attendeeID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // For debugging or display purposes
    @Override
    public String toString() {
        return "User{" +
                "attendeeID='" + attendeeID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

