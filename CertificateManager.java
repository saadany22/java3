
/**
 * Write a description of class CertificateManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CertificateManager {
    private CertificateDatabase certificateDatabase;

    public CertificateManager(CertificateDatabase certificateDatabase) {
        this.certificateDatabase = certificateDatabase;
    }

    // This method generates a certificate for the user and sends it
    public void generateAndSendCertificate(String userName, String sessionId) {
        // Generate certificate
        String certificateContent = "Certificate of Attendance\n" +
                                    "This is to certify that " + userName + "\n" +
                                    "has attended the session " + sessionId + ".\n" +
                                    "Date: " + java.time.LocalDate.now();

        // Send certificate (For now, we just print it to console as sending logic isn't specified)
        sendCertificate(userName, certificateContent);

        // Save certificate record in the database
        certificateDatabase.saveCertificate(userName, sessionId);
    }

    // This method simulates sending the certificate (printing in this case)
    private void sendCertificate(String userName, String certificateContent) {
        System.out.println("Sending certificate to: " + userName);
        System.out.println(certificateContent);
        // You can add email or file sending logic here
    }
}






