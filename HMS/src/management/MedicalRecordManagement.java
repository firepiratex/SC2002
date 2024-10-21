package management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import handlers.CSVHandler;
import models.User;

public class MedicalRecordManagement {
    private static final String appointmentOutcomeFile = "src/data/Appointment_Outcome_Record.csv";
    
    public static void viewPatientMedicalRecord(User user) {
		List<String[]> recordList = CSVHandler.readCSV(appointmentOutcomeFile);
		System.out.println("----Patient Medical Records----");
		if (user.getRole().equals("Doctor")) {
			for(int i = 0; i < recordList.size(); i++) {
				String doctorID = recordList.get(i)[0];
				if (doctorID.equals(user.getId())) {
					System.out.println(Arrays.toString(recordList.get(i)));
				}
			}
		} else if (user.getRole().equals("Patient")){
			for(int i = 0; i < recordList.size(); i++) {
				String patientID = recordList.get(i)[1];
				if (patientID.equals(user.getId())) {
					System.out.println(Arrays.toString(recordList.get(i)));
				}
			}
		} else if (user.getRole().equals("Pharmacist")) {
			for(int i = 0; i < recordList.size(); i++) {
				System.out.println(Arrays.toString(recordList.get(i)));
			}
		}
	}
    
	public static void updatePatientMedicalRecord(User doctor, Scanner scanner) {
		List<String[]> recordList = CSVHandler.readCSV(appointmentOutcomeFile);
		List<String[]> doctorPatientRecord = new ArrayList<>();
		String[] parts;
		int choice;
		for(int i = 0; i < recordList.size(); i++) {
			String doctorID = recordList.get(i)[0];
			if (doctorID.equals(doctor.getId())) {
				doctorPatientRecord.add(recordList.get(i));
			}
		}
		System.out.println("----Patient Medical Records----");
		for (int i = 0; i < doctorPatientRecord.size(); i++) {
			System.out.println((i+1) + ". " + Arrays.toString(doctorPatientRecord.get(i)));
		}
		while(true) {
			System.out.print("\nChoose a record to update (0 to exit): ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return;
				} else if (choice >= 1 && choice <= doctorPatientRecord.size()) {
					String doctorPatientRecordString = Arrays.toString(doctorPatientRecord.get(choice-1));
					for(int i = 0; i < recordList.size(); i++) {
						String recordListString = Arrays.toString(recordList.get(i));
						if (recordListString.equals(doctorPatientRecordString)) {
							parts = recordList.get(i);
							System.out.print("Type of service provided: ");
							scanner.nextLine();
							String service = scanner.nextLine();
							System.out.print("Prescribed Medications (default is pending): ");
							String medication = scanner.nextLine();
							System.out.print("Consultation Notes: ");
							String notes = scanner.nextLine();
							recordList.set(i, new String[] {parts[0], parts[1], parts[2], service, medication, notes});
							System.out.println("Update successfully.");
							recordList.add(0, new String[] {"Doctor ID,Patient ID,Date,Type of Service, Prescribed Medications, Consultation Notes"});
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
                scanner.next();
			}
		}
	}
}