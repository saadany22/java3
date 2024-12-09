
/**
 * Write a description of class SpeakerManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.List;

public class SpeakerManager extends SpeakerDatabase {

    public SpeakerManager(String databaseFilePath) {
        super(databaseFilePath); // Calls the constructor of SpeakerDatabase
    }

    @Override
    public List<Speaker> getAllSpeakers() {
        // Override the method to add custom logic, if needed
        return super.getAllSpeakers(); // Call the parent method from SpeakerDatabase
    }

    public void addSpeaker(String name, String bio) {
        // Create a new Speaker object and add it to the database
        Speaker newSpeaker = new Speaker(name, bio);
        super.addSpeaker(newSpeaker); // Call the method from SpeakerDatabase
    }

    public List<String> getSpeakerNames() {
        return super.getSpeakerNames(); // Return the list of speaker names
    }
}







