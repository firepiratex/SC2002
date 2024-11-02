package handlers;

import java.io.*;
import java.time.LocalDate;
import models.MedicalCertificate;
import models.Patient;

public class MedicalCertificateHandler {

    private static final String FILE_PATH = "./src/data/Medical_Certificate.csv";  // Ensure the path matches your project structure

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

    public static void viewAllCertificates() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean isFirstLine = true;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;  // Skip header
                    System.out.println("---- All Pending Medical Certificate Requests ----");
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 6) {
                    MedicalCertificate certificate = new MedicalCertificate(
                            data[0], data[1], data[2], Integer.parseInt(data[4])
                    );
                    certificate.setStatus(data[5]);
                    certificate.setIssueDate(LocalDate.parse(data[3]));

                    // Only display certificates with "Pending" status
                    if ("Pending".equalsIgnoreCase(certificate.getStatus())) {
                        System.out.println("Patient ID: " + data[0] + ", Name: " + data[1] + ", Reason: " + data[2] + ", Duration: " + data[4] + " days, Status: " + data[5]);
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("No pending medical certificates found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing medical certificate data: " + e.getMessage());
        }
    }

    public static void updateCertificateStatus(String patientId, String newStatus, String doctorId) {
        File inputFile = new File(FILE_PATH);
        File tempFile = new File("HMS/src/data/temp_Medical_Certificate.csv");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
    
            String line;
            boolean found = false;
    
            // Copy header
            writer.write(reader.readLine() + ",Approved/Rejected By");
            writer.newLine();
    
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6 && data[0].equals(patientId)) {
                    data[5] = newStatus;  // Update the status field
                    line += "," + doctorId;  // Add the doctor ID who approved/rejected
                    found = true;
                }
                writer.write(line);
                writer.newLine();
            }
    
            if (!found) {
                System.out.println("No medical certificate found for the given patient ID.");
            } else {
                System.out.println("Medical certificate status updated successfully.");
            }
    
            // Replace the original file with the updated one
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("Error updating the file.");
            }
    
        } catch (IOException e) {
            System.out.println("Error updating certificate status: " + e.getMessage());
        }
    }
    
}
