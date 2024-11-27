
/**
 * Write a description of class SpeakerDatabase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.List;

public class SpeakerDatabase {
    private List<Speaker> speakers;

    public SpeakerDatabase() {
        speakers = new ArrayList<>();
    }

    public void addSpeaker(Speaker speaker) {
        speakers.add(speaker);
    }

    public List<String> getSpeakerNames() {
        List<String> names = new ArrayList<>();
        for (Speaker speaker : speakers) {
            names.add(speaker.getName());
        }
        return names;
    }
}

class Speaker {
    private String name;
    private String bio;

    public Speaker(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }
}


