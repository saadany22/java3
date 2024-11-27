
/**
 * Write a description of class SpeakerManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.HashMap;
import java.util.Map;

public class SpeakerManager {
    private Map<String, Speaker> speakerDatabase;

    // Constructor
    public SpeakerManager() {
        this.speakerDatabase = new HashMap<>();
    }

    // Method to add a new speaker to the system
    public void addSpeaker(String speakerId, String speakerName, String speakerBio) {
        // Create a new speaker object
        Speaker newSpeaker = new Speaker(speakerId, speakerName, speakerBio);
        
        // Store the speaker in the database
        speakerDatabase.put(speakerId, newSpeaker);
        System.out.println("New speaker added: " + speakerName);
    }

    // Method to get a speaker's bio by speaker ID
    public String getSpeakerBio(String speakerId) {
        // Retrieve the speaker from the database
        Speaker speaker = speakerDatabase.get(speakerId);
        
        if (speaker != null) {
            return speaker.getBio();
        } else {
            return "Speaker not found.";
        }
    }

    // Inner class to represent a speaker
    public static class Speaker {
        private String speakerId;
        private String speakerName;
        private String bio;

        // Constructor
        public Speaker(String speakerId, String speakerName, String bio) {
            this.speakerId = speakerId;
            this.speakerName = speakerName;
            this.bio = bio;
        }

        // Getters
        public String getSpeakerId() {
            return speakerId;
        }

        public String getSpeakerName() {
            return speakerName;
        }

        public String getBio() {
            return bio;
        }
    }
}

