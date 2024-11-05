package management;

import handlers.CSVHandler;
import handlers.PatientHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import models.Patient;
import models.User;

public class MedicalRecordManagement {

    private static final String appointmentOutcomeFile = "./src/data/Appointment_Outcome_Record.csv";

    public static void viewPatientMedicalRecord(User user) {
    	boolean recordFound = false;
        List<String[]> recordList = CSVHandler.readCSV(appointmentOutcomeFile);        
        List<String[]> tempList = new ArrayList<>();
        
        if (user.getRole().equals("Doctor")) {
            for (int i = 0; i < recordList.size(); i++) {
            	String[] getRecord = recordList.get(i);
                String doctorID = getRecord[0];
                if (doctorID.equals(user.getId())) {
                	tempList.add(getRecord);
                    recordFound = true;
                }
            }
        } else if (user.getRole().equals("Patient")) {
            for (int i = 0; i < recordList.size(); i++) {
            	String[] getRecord = recordList.get(i);
                String patientID = getRecord[1];
                if (patientID.equals(user.getId())) {
                	tempList.add(getRecord);
                    recordFound = true;
                }
            }
        } else if (user.getRole().equals("Pharmacist")) {
            if (recordList.size() != 0) {
            	recordFound = true;
            }
        }
        if (recordFound == false) {
        	System.out.println("No record available.");
        } else {
        	System.out.println("----Patient Medical Records----");
            System.out.println("Doctor\tPatient\t\tDate\t\tType of Service\t\tPrescription Status\tConsultation Notes");
        	for(int i = 0; i < tempList.size(); i++) {
        		String[] getRecord = tempList.get(i);
                String doctorID = getRecord[0];
                String patientID = getRecord[1];
                String date = getRecord[2];
                String service = getRecord[3];
                String status = getRecord[4];
                String notes = getRecord[5];
                System.out.println(doctorID + "\t" + patientID + "\t\t" + date + "\t" + service + "\t\t\t" + status + "\t\t\t" + notes);
        	}
        }
        System.out.println("");
    }

    public static void updatePatientMedicalRecord(User doctor, Scanner scanner) {
        List<String[]> recordList = CSVHandler.readCSV(appointmentOutcomeFile);
        List<String[]> doctorPatientRecord = new ArrayList<>();
        String[] parts;
        int choice;

        for (int i = 0; i < recordList.size(); i++) {
            String doctorID = recordList.get(i)[0];
            if (doctorID.equals(doctor.getId())) {
                doctorPatientRecord.add(recordList.get(i));
            }
        }

        System.out.println("----Patient Medical Records----");
        for (int i = 0; i < doctorPatientRecord.size(); i++) {
            System.out.println((i + 1) + ". " + Arrays.toString(doctorPatientRecord.get(i)));
        }

        while (true) {
            System.out.print("\nChoose a record to update (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                } else if (choice >= 1 && choice <= doctorPatientRecord.size()) {
                    String doctorPatientRecordString = Arrays.toString(doctorPatientRecord.get(choice - 1));
                    for (int i = 0; i < recordList.size(); i++) {
                        String recordListString = Arrays.toString(recordList.get(i));
                        if (recordListString.equals(doctorPatientRecordString)) {
                        	parts = recordList.get(i);
                        	System.out.print("New Blood Type (blank if no changes): ");
                            scanner.nextLine();
                            String bloodType = scanner.nextLine();
                            if (!(bloodType.equals(""))) {
                            	Patient patient = PatientHandler.getInstance().findPatientById(parts[1]);
                            	patient.setBloodType(bloodType);
                            }
                            System.out.print("Type of service provided: ");
                            String service = scanner.nextLine();

                            System.out.print("Consultation Notes: ");
                            String notes = scanner.nextLine();
                            
                            recordList.set(i, new String[]{parts[0], parts[1], parts[2], service, parts[4], notes});
                            System.out.println("Update successfully.");
                            recordList.add(0, new String[]{"Doctor ID,Patient ID,Date,Type of Service, Prescription Status, Consultation Notes"});
                            CSVHandler.writeCSV(appointmentOutcomeFile, recordList);
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next(); // Consume the invalid input
            }
        }
    }
    private static boolean isValidMedication(String medication) {
        return medication.equalsIgnoreCase("Paracetamol") ||
               medication.equalsIgnoreCase("Ibuprofen") ||
               medication.equalsIgnoreCase("Amoxicillin");
    }

}
