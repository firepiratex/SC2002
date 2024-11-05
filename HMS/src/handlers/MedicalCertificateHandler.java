package handlers;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import models.Doctor;
import models.MedicalCertificate;
import models.Patient;

public class MedicalCertificateHandler {
	
	private static MedicalCertificateHandler instance; 
    private static final String FILE_PATH = "./src/data/Medical_Certificate.csv";  // Ensure the path matches your project structure
    private List<MedicalCertificate> mcList;
    
    public void addCertificate(MedicalCertificate certificate) {
        List<String[]> mcCSV = CSVHandler.readCSV(FILE_PATH);
        mcCSV.add(certificate.toCSVRow());
        mcCSV.add(0, new String[]{"Patient ID,Patient Name,Reason,Date,Days,Status,Approved/Rejected By"});
        mcList.add(certificate);
        saveMC();
    }
    
    private MedicalCertificateHandler() {
        this.mcList = new ArrayList<>();
        loadMC();  // Load staff data when initializing
    }

    public static MedicalCertificateHandler getInstance() {
        if (instance == null) {
            instance = new MedicalCertificateHandler();
        }
        return instance;
    }
    
    private void loadMC() {
    	List<String[]> mcCSV = CSVHandler.readCSV(FILE_PATH);
    	for(int i = 0; i < mcCSV.size(); i++) {
    		String patientID = mcCSV.get(i)[0];
    		String patientName = mcCSV.get(i)[1];
    		String reason = mcCSV.get(i)[2];
    		String date = mcCSV.get(i)[3];
    		String day = mcCSV.get(i)[4];
    		String status = mcCSV.get(i)[5];
    		String outcome = mcCSV.get(i)[6];
    		
    		MedicalCertificate mc = null;
    		mc = new MedicalCertificate(patientID, patientName, reason, date, Integer.valueOf(day), status, outcome);
    		mcList.add(mc);
    	}
    }
    
    private void saveMC() {
    	List<String[]> data = new ArrayList<>();
    	for(MedicalCertificate mc : mcList) {
    		String[] row = {mc.getPatientId(), mc.getPatientName(), mc.getReason(), mc.getIssueDate().toString(), String.valueOf(mc.getDuration()), mc.getStatus(), mc.getApprovedBy()};
    		data.add(row);
    	}
    	data.add(0, new String[]{"Patient ID,Patient Name,Reason,Date,Days,Status,Approved/Rejected By"});
    	CSVHandler.writeCSV(FILE_PATH, data);
    }
    
    public static void viewCertificatesForPatient(Patient patient) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean found = false;
    
            // Skip the header if present
            reader.readLine();
    
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7 && data[0].equals(patient.getId())) {
                    MedicalCertificate certificate = new MedicalCertificate(
                            data[0], data[1], data[2], Integer.parseInt(data[4])
                    );
                    certificate.setStatus(data[5]);
                    certificate.setIssueDate(LocalDate.parse(data[3]));
                    if (data.length > 6) {
                        certificate.setApprovedBy(data[6]);
                    }
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
                    System.out.println("---- All Medical Certificate Requests ----");
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 7) {
                    MedicalCertificate certificate = new MedicalCertificate(
                            data[0], data[1], data[2], Integer.parseInt(data[4])
                    );
                    certificate.setStatus(data[5]);
                    certificate.setIssueDate(LocalDate.parse(data[3]));

                    System.out.println("Patient ID: " + data[0] + ", Name: " + data[1] + ", Reason: " + data[2] + 
                            ", Duration: " + data[4] + " days, Status: " + data[5] + 
                            ", Approved/Rejected By: " + (data.length > 6 ? data[6] : "N/A"));
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No medical certificates found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing medical certificate data: " + e.getMessage());
        }
    }

    public void updateCertificateStatus(Doctor doctor, Scanner scanner) {
    	int choice;
    	for(int i = 0; i < mcList.size(); i++) {
    		System.out.println((i+1) + ". " + mcList.get(i));
    	}
    	System.out.print("Enter your choice (0 to exit): ");
    	if (scanner.hasNextInt()) {
    		choice = scanner.nextInt();
    		if (choice == 0) {
    			return;
    		} else if (choice >= 1 && choice <= mcList.size()) {
    			MedicalCertificate currentMC = mcList.get(choice-1);
                System.out.print("Enter new status (Approved/Rejected): ");
                scanner.nextLine();
                String newStatus = scanner.nextLine();
    			currentMC.setStatus(newStatus);
    			currentMC.setApprovedBy(doctor.getId());
    			currentMC.setIssueDate(LocalDate.now());
    			saveMC();
    		} else {
    			System.out.println("Invalid choice.");
    		}
    	} else {
    		System.out.println("Invalid input.");
    	}
    }
    
    public static boolean viewPendingCertificates() {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;  // Skip header
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 7 && "Pending".equalsIgnoreCase(data[5])) {
                    System.out.println("Patient ID: " + data[0] + ", Name: " + data[1] + ", Reason: " + data[2] +
                            ", Duration: " + data[4] + " days, Status: " + data[5]);
                    found = true;
                }
            }

            /*if (!found) {
                System.out.println("No pending medical certificates found.");
            }*/
        } catch (IOException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        }
        return found;  // Return true if found, otherwise false
    }
}
