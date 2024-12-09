
/**
 * Write a description of class SpeakerDatabase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;

public class SpeakerDatabase {
    private List<Speaker> speakers;
    private String filePath;

    public SpeakerDatabase(String filePath) {
        this.filePath = filePath;
        this.speakers = new ArrayList<>();
        loadSpeakersFromFile();
    }

    // Load speakers from the CSV file (if exists)
    private void loadSpeakersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    // Create a speaker object and add it to the list
                    Speaker speaker = new Speaker(data[0], data[1]);
                    speakers.add(speaker);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save speakers to the CSV file
    private void saveSpeakersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Speaker speaker : speakers) {
                writer.write(speaker.getName() + "," + speaker.getBio());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a speaker to the list and save to file
    public void addSpeaker(Speaker speaker) {
        speakers.add(speaker);
        saveSpeakersToFile();
    }

    // Get all speaker names
    public List<String> getSpeakerNames() {
        List<String> names = new ArrayList<>();
        for (Speaker speaker : speakers) {
            names.add(speaker.getName());
        }
        return names;
    }

    // Get all speaker details (List of Speaker objects)
    public List<Speaker> getAllSpeakers() {
        return speakers;
    }
}





