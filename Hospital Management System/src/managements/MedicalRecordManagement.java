package managements;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import handlers.CSVHandler;
import handlers.MedicineHandler;
import medicine.Medicine;
import users.Role;
import users.User;

public class MedicalRecordManagement {
	public static void viewPatientMedicalRecord(User user) {
		List<String> recordList = CSVHandler.loadFile("Appointment_Outcome_Record.csv");
		String[] parts;
		System.out.println("----Patient Medical Records----");
		if (user.getRole() == Role.Doctor) {
			for (String eachRecord : recordList) {
				parts = eachRecord.split(",");
				if (parts[0].equals(user.getUniqueID())) {
					System.out.println(eachRecord);
				}
			}
		} else if (user.getRole() == Role.Patient){
			for (String eachRecord : recordList) {
				parts = eachRecord.split(",");
				if (parts[1].equals(user.getUniqueID())) {
					System.out.println(eachRecord);
				}
			}
		} else if (user.getRole() == Role.Pharmacist) {
			for (String eachRecord : recordList) {
				System.out.println(eachRecord);
			}
		}
	}
	
	public static void managePatientMedicalRecord(User doctor, Scanner scanner) {
		List<String> recordList = CSVHandler.loadFile("Appointment_Outcome_Record.csv");
		List<String> doctorPatientRecord = new ArrayList<>();
		String[] parts;
		String row;
		int choice;
		for (String eachRecord : recordList) {
			parts = eachRecord.split(",");
			if (parts[0].equals(doctor.getUniqueID())) {
				doctorPatientRecord.add(eachRecord);
			}
		}
		System.out.println("----Patient Medical Records----");
		for (int i = 0; i < doctorPatientRecord.size(); i++) {
			System.out.println((i+1) + ". " + doctorPatientRecord.get(i));
		}
		while(true) {
			System.out.print("\nChoose a record to update (0 to exit): ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return;
				} else if (choice >= 1 && choice <= doctorPatientRecord.size()) {
					for(int i = 0; i < recordList.size(); i++) {
						if (recordList.get(i).equals(doctorPatientRecord.get(choice-1))) {
							parts = recordList.get(i).split(",");
							System.out.print("Type of service provided: ");
							scanner.nextLine();
							String service = scanner.nextLine();
							System.out.print("Prescribed Medications (default is pending): ");
							String medication = scanner.nextLine();
							System.out.print("Consultation Notes: ");
							String notes = scanner.nextLine();
							row = parts[0] + "," + parts[1] + "," + parts[2] + "," + service + "," + medication + "," + notes;
							recordList.set(i, row);
							System.out.println("Update successfully.");
							CSVHandler.saveAppointmentOutcomeRecord(recordList);
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
	
	public static void managePrescription(Scanner scanner) {
		List<String> recordList = CSVHandler.loadFile("Appointment_Outcome_Record.csv");
		List<Medicine> medicineList = MedicineHandler.loadMedicine("Medicine_List.csv");
		int choice, medication,amount;
		String[] parts;
		String row;
		System.out.println("----Patient Medical Records----");
		for (int i = 0; i < recordList.size(); i++) {
			System.out.println((i+1) + ". " + recordList.get(i));
		}
		while(true) {
			System.out.print("\nChoose a record to update (0 to exit): ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return;
				} else if (choice >= 1 && choice <= recordList.size()) {
					for(int i = 0; i < recordList.size(); i++) {
						if (recordList.get(i).equals(recordList.get(choice-1))) {
							parts = recordList.get(i).split(",");
							System.out.println("----Medications----");
							for(int j = 0; j < medicineList.size(); j++) {
								System.out.println((j+1) + ". " + medicineList.get(j).getMedicineName());
							}
							System.out.print("\nChoose a medication to prescribe: ");
							if (scanner.hasNextInt()) {
								medication = scanner.nextInt();
								System.out.print("Amount to give: ");
								if (scanner.hasNextInt()) {
									amount = scanner.nextInt();
									if (amount < 1 || amount > medicineList.get(medication-1).getStock()) {
										System.out.println("Invalid amount.");
										return;
									}
									medicineList.get(medication-1).minusStock(amount);
								} else {
									System.out.println("Invalid amount.");
									return;
								}
							} else {
								System.out.println("Invalid medication.");
								return;
							}
							row = parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + ",dispensed," + parts[5];
							recordList.set(i, row);
							System.out.println("Update successfully.");
							MedicineHandler.saveMedicine(medicineList);
							CSVHandler.saveAppointmentOutcomeRecord(recordList);
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
