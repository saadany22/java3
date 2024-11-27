
/**
 * Write a description of class SpeakerManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.List;

public class SpeakerManager {
    private SpeakerDatabase speakerDatabase;

    public SpeakerManager(SpeakerDatabase speakerDatabase) {
        this.speakerDatabase = speakerDatabase;
    }

    public void addSpeaker(String name, String bio) {
        speakerDatabase.addSpeaker(new Speaker(name, bio));
    }

    public List<String> getAllSpeakers() {
        return speakerDatabase.getSpeakerNames();
    }
}




