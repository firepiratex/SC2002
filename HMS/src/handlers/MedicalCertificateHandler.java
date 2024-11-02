package handlers;

import java.io.*;
import java.time.LocalDate;
import models.MedicalCertificate;
import models.Patient;

public class MedicalCertificateHandler {
    private static final String FILE_PATH = "HMS/src/data/Medical_Certificate.csv";  // Update with your actual path

    public static void addCertificate(MedicalCertificate certificate) {
        File file = new File(FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Add header if the file is new or empty
            if (file.length() == 0) {
                writer.write("Patient ID,Name,Reason,Issue Date,Duration,Status");
                writer.newLine();
            }
            String[] data = certificate.toCSVRow();
            writer.write(String.join(",", data));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public static void viewCertificatesForPatient(Patient patient) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean found = false;

            // Skip the header if present
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6 && data[0].equals(patient.getId())) {
                    MedicalCertificate certificate = new MedicalCertificate(
                        data[0], data[1], data[2], Integer.parseInt(data[4])
                    );
                    certificate.setStatus(data[5]);
                    certificate.setIssueDate(LocalDate.parse(data[3]));
                    certificate.displayCertificate();
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No medical certificates found for this patient.");
            }
        } catch (IOException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing medical certificate data: " + e.getMessage());
        }
    }
}
