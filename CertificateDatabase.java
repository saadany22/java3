
/**
 * Write a description of class CertificateDatabase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class CertificateDatabase {
    private String filePath;

    public CertificateDatabase(String filePath) {
        this.filePath = filePath;
    }

    // This method saves a certificate to the CSV file (records user and session)
    public void saveCertificate(String userName, String sessionId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(userName + "," + sessionId + ",True");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method checks if a user has received a certificate for a given session
    public boolean hasReceivedCertificate(String userName, String sessionId) {
        Set<String> issuedCertificates = loadIssuedCertificates();
        return issuedCertificates.contains(userName + "," + sessionId);
    }

    // This method loads all issued certificates from the CSV file
    private Set<String> loadIssuedCertificates() {
        Set<String> issuedCertificates = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3 && data[2].equals("True")) {
                    issuedCertificates.add(data[0] + "," + data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return issuedCertificates;
    }

    // This method retrieves certificates for a user
    public List<String> getCertificatesForUser(String userName) {
        List<String> certificates = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3 && data[0].equals(userName) && data[2].equals("True")) {
                    certificates.add("Here is your certificate" + data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return certificates;
    }
}






