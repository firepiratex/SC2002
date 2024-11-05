package management;

import handlers.CSVHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import models.User;

public class MedicalRecordManagement {

    private static final String appointmentOutcomeFile = "./src/data/Appointment_Outcome_Record.csv";

    public static void viewPatientMedicalRecord(User user) {
    	boolean recordFound = false;
        List<String[]> recordList = CSVHandler.readCSV(appointmentOutcomeFile);
        System.out.println("----Patient Medical Records----");
        System.out.println("Doctor\tPatient\t\tDate\t\tType of Service\t\tPrescription Status\tConsultation Notes");
        

        if (user.getRole().equals("Doctor")) {
            for (int i = 0; i < recordList.size(); i++) {
            	String[] getRecord = recordList.get(i);
                String doctorID = getRecord[0];
                String patientID = getRecord[1];
                String date = getRecord[2];
                String service = getRecord[3];
                String status = getRecord[4];
                String notes = getRecord[5];
                if (doctorID.equals(user.getId())) {
                	System.out.println(doctorID + "\t" + patientID + "\t\t" + date + "\t" + service + "\t\t\t" + status + "\t\t\t" + notes);
                    recordFound = true;
                }
            }
        } else if (user.getRole().equals("Patient")) {
            for (int i = 0; i < recordList.size(); i++) {
            	String[] getRecord = recordList.get(i);
                String doctorID = getRecord[0];
                String patientID = getRecord[1];
                String date = getRecord[2];
                String service = getRecord[3];
                String status = getRecord[4];
                String notes = getRecord[5];
                if (patientID.equals(user.getId())) {
                	System.out.println(doctorID + "\t" + patientID + "\t\t" + date + "\t" + service + "\t\t\t" + status + "\t\t\t" + notes);
                    recordFound = true;
                }
            }
        } else if (user.getRole().equals("Pharmacist")) {	
            for (int i = 0; i < recordList.size(); i++) {
            	String[] getRecord = recordList.get(i);
                String doctorID = getRecord[0];
                String patientID = getRecord[1];
                String date = getRecord[2];
                String service = getRecord[3];
                String status = getRecord[4];
                String notes = getRecord[5];
                System.out.println(doctorID + "\t" + patientID + "\t\t" + date + "\t" + service + "\t\t\t" + status + "\t\t\t" + notes);
                if (recordList.size() != 0) {
                	recordFound = true;
                }
            }
        }
        if (recordFound == false) {
        	System.out.println("No record available.");
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
                            System.out.print("Type of service provided: ");
                            scanner.nextLine(); // Consume newline
                            String service = scanner.nextLine();

                            System.out.print("Consultation Notes: ");
                            String notes = scanner.nextLine();
							
							String paymentStatus = "Unpaid";


                            List<String> medicationsWithQuantities = new ArrayList<>();
                            String addMore;

                            do {
                                String medication;
                                do {
                                    System.out.print("Prescribed Medication (Medication List: Paracetamol, Ibuprofen, Amoxicillin): ");
                                    medication = scanner.nextLine().trim();
                                    if (!isValidMedication(medication)) {
                                        System.out.println("Invalid medication. Please enter Paracetamol, Ibuprofen, or Amoxicillin.");
                                    }
                                } while (!isValidMedication(medication));

                                System.out.print("Quantity of Medication: ");
                                String quantity = scanner.nextLine();

                                medicationsWithQuantities.add(medication + " (" + quantity + ")");

                                System.out.print("Add another medication? (yes/no): ");
                                addMore = scanner.nextLine().trim().toLowerCase();
                            } while (addMore.equals("yes"));

                            String medicationSummary = String.join(", ", medicationsWithQuantities);

                            recordList.set(i, new String[]{parts[0], parts[1], parts[2], service, notes, paymentStatus, medicationSummary});
                            System.out.println("Update successfully.");
                            recordList.add(0, new String[]{"Doctor ID,Patient ID,Date,Type of Service,,Consultation Notes, Payment Status, Prescribed Medications "});
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
