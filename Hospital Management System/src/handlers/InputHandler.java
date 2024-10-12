package handlers;

import java.util.List;
import java.util.Scanner;

import managements.AppointmentManagement;
import managements.InformationManagement;
import managements.InventoryManagement;
import managements.MedicalRecordManagement;
import managements.StaffManagement;
import medicine.Medicine;
import users.User;

public class InputHandler {
	public static void handlePatientMenu(User patient, int option, Scanner scanner, List<User> staffList, List<User> patientList) {
		switch (option) {
			case 1:
				InformationManagement.viewPatientInfo(patient);
				break;
			case 2:
				InformationManagement.updatePersonalInformation(patient, scanner, patientList);
				break;
			case 3:
				AppointmentManagement.viewAvailableAppointment(scanner, staffList);
				break;
			case 4:
				AppointmentManagement.scheduleAppointment(patient, scanner, staffList);
				break;
			case 5:
				AppointmentManagement.reScheduleAppointment(patient, scanner, staffList);
				break;
			case 6:
				AppointmentManagement.cancelAppointment(patient, scanner, staffList);
				break;
			case 7:
				AppointmentManagement.viewScheduledAppointment(patient, staffList);
				break;
			case 8:
				MedicalRecordManagement.viewPatientMedicalRecord(patient);
				break;
		}
	}

	public static void handleDoctorMenu(User doctor, int option, Scanner scanner) {
		switch (option) {
			case 1:
				MedicalRecordManagement.viewPatientMedicalRecord(doctor);
				break;
			case 2:
				MedicalRecordManagement.managePatientMedicalRecord(doctor, scanner);
				break;
			case 3:
				AppointmentManagement.viewDoctorSchedule(doctor);
				break;
			case 4:
				AppointmentManagement.setAvailableAppointment(doctor, scanner);
				break;
			case 5:
				AppointmentManagement.manageAppointment(doctor, scanner);
				break;
			case 6:
				AppointmentManagement.viewUpcomingAppointment(doctor);
				break;
			case 7:
				AppointmentManagement.recordAppointmentOutcome(doctor, scanner);
				break;
		}
	}
	
	public static void handlePharmacistMenu(User pharmacist, int option, Scanner scanner, List<Medicine> medicineList) {
		switch (option) {
			case 1:
				MedicalRecordManagement.viewPatientMedicalRecord(pharmacist);
				break;
			case 2:
				MedicalRecordManagement.managePrescription(scanner);
				break;
			case 3:
				InventoryManagement.viewInventory(medicineList);
				break;
			case 4:
				InventoryManagement.submitReplenishmentRequest(pharmacist, scanner, medicineList);
				break;
		}
	}
	
	public static void handleAdministratorMenu(User admin, int option, Scanner scanner, List<User> staffList, List<Medicine> medicineList) {
		int choice;
		switch (option) {
		case 1:
			while (true) {
				MenuHandler.viewAndManageStaff();
				System.out.print("Enter your choice: ");
				if (scanner.hasNextInt()) {
					choice = scanner.nextInt();
					if (choice == 1) {
						StaffManagement.viewStaff(scanner, staffList);
						break;
					} else if (choice == 2) {
						StaffManagement.manageStaff(scanner, staffList);
						break;
					} else {
						System.out.println("Invalid choice. Try again");
					}
				} else {
					System.out.println("Invalid input. Try again.");
	                scanner.next();
				}
			}
			break;
		case 2:
			while (true) {
				MenuHandler.viewAppointmentDetail();
				System.out.print("Enter your choice: ");
				if (scanner.hasNextInt()) {
					choice = scanner.nextInt();
					if (choice == 1) {
						AppointmentManagement.viewAppointmentLog();
						break;
					} else if (choice == 2) {
						AppointmentManagement.viewAllAppointment();
						break;
					} else {
						System.out.println("Invalid choice. Try again");
					}
				} else {
					System.out.println("Invalid input. Try again.");
	                scanner.next();
				}
			}
			break;
		case 3:
			while (true) {
				MenuHandler.viewAndManageMedicine();
				System.out.print("Enter your choice: ");
				if (scanner.hasNextInt()) {
					choice = scanner.nextInt();
					if (choice == 1) {
						InventoryManagement.viewInventory(medicineList);
						break;
					} else if (choice == 2) {
						InventoryManagement.manageInventory(scanner, medicineList);
						break;
					} else if (choice == 3) {
						InventoryManagement.manageStockLevelAlert(scanner, medicineList);
						break;
					} else {
						System.out.println("Invalid choice. Try again");
					}
				} else {
					System.out.println("Invalid input. Try again.");
	                scanner.next();
				}
			}
			break;
		case 4:
			InventoryManagement.manageReplenishmentRequest(scanner, medicineList);
			break;
		}
	}
}
