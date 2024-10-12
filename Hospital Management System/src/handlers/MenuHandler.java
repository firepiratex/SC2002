package handlers;

import java.util.List;
import java.util.Scanner;

import medicine.Medicine;
import users.Role;
import users.User;

public class MenuHandler {
	public static void viewPatientMenu() {
		System.out.println("\n1. View Medical Record\n"
				+ "2. Update Personal Information\n"
				+ "3. View Available Appointment Slots\n"
				+ "4. Schedule an Appointment\n"
				+ "5. Reschedule an Appointment\n"
				+ "6. Cancel an Appointment\n"
				+ "7. View Scheduled Appointments\n"
				+ "8. View Past Appointment Outcome Records\n"
				+ "9. Logout\n");
	}
	
	public static void viewDoctorMenu() {
		System.out.println("\n1. View Patient Medical Records\n"
				+ "2. Update Patient Medical Records\n"
				+ "3. View Personal Schedule\n"
				+ "4. Set Availability for Appointments\n"
				+ "5. Accept or Decline Appointment Requests\n"
				+ "6. View Upcoming Appointments\n"
				+ "7. Record Appointment Outcome\n"
				+ "8. Logout\n");
	}
	
	public static void viewPharmacistMenu() {
		System.out.println("\n1. View Appointment Outcome Record\n"
				+ "2. Update Prescription Status\n"
				+ "3. View Medication Inventory\n"
				+ "4. Submit Replenishment Request\n"
				+ "5. Logout\n");
	}
	
	public static void viewAdministratorMenu() {
		System.out.println("\n1. View and Manage Hospital Staff\n"
				+ "2. View Appointments details\n"
				+ "3. View and Manage Medication Inventory\n"
				+ "4. Approve Replenishment Requests\n"
				+ "5. Logout\n");
	}
	
	public static void viewAndManageStaff() {
		System.out.println("\n1. View Staff\n"
				+ "2. Manage Staff\n");
	}
	
	public static void viewAppointmentDetail() {
		System.out.println("\n1. View Real-time Update of Appointment\n"
				+ "2. View Appointment Details\n");
	}
	
	public static void filterBy() {
		System.out.println("\n1. Filter by role\n"
				+ "2. Filter by gender\n");
	}
	
	public static void filterByRole() {
		System.out.println("\n1. Doctor\n"
				+ "2. Pharmacist\n"
				+ "3. Administrator\n"
				+ "0. Exit\n");
	}
	
	public static void filterByGender() {
		System.out.println("\n1. Male\n"
				+ "2. Female\n"
				+ "0. Exit\n");
	}
	
	public static void manageStaff() {
		System.out.println("\n1. Add Staff\n"
				+ "2. Update Staff\n"
				+ "3. Remove Staff\n"
				+ "0. Exit\n");
	}
	
	public static void updateStaff() {
		System.out.println("\n1. Name\n"
				+ "2. Role\n"
				+ "3. Gender\n"
				+ "4. Age\n"
				+ "0. Exit\n");
	}
	
	public static void viewAndManageMedicine() {
		System.out.println("\n1. View Inventory of Medicine\n"
				+ "2. Manage Inventory of Medicine\n"
				+ "3. Update Medicine Stock Level Alert\n");
	}
	
	public static void manageMedicine() {
		System.out.println("\n1. Add Medicine\n"
				+ "2. Take Medicine\n"
				+ "0. Exit\n");
	}
	
	public static void handleUser(User user, List<User> staffList, List<User> patientList, List<Medicine> medicineList, Scanner scanner) {
		int choice;
		if (user.getRole() == Role.Patient) {
			while (true) {
				viewPatientMenu();
				System.out.print("Enter your choice: ");
				if (scanner.hasNextInt()) {
					choice = scanner.nextInt();
					if (choice >= 1 && choice <= 8) {
						InputHandler.handlePatientMenu(user, choice, scanner, staffList, patientList);
					} else if (choice == 9) {
						return;
					} else {
						System.out.println("Invalid choice. Try again.\n");
					}
				} else {
					System.out.println("Invalid input. Try again.\n");
					scanner.next();
				}
			}
		} else if (user.getRole() == Role.Doctor) {
			while (true) {
				viewDoctorMenu();
				System.out.print("Enter your choice: ");
				try {
					choice = scanner.nextInt();
					if (choice >= 1 && choice <= 7) {
						InputHandler.handleDoctorMenu(user, choice, scanner);
					} else if (choice == 8) {
						return;
					} else {
						System.out.println("Invalid choice. Try again.\n");
					}
				} catch (Exception e) {
					System.out.println("Invalid input. Try again.\n");
					scanner.next();
				}
			}
		} else if (user.getRole() == Role.Pharmacist) {
			while (true) {
				viewPharmacistMenu();
				System.out.print("Enter your choice: ");
				try {
					choice = scanner.nextInt();
					if (choice >= 1 && choice <= 4) {
						InputHandler.handlePharmacistMenu(user, choice, scanner, medicineList);
					} else if (choice == 5) {
						return;
					} else {
						System.out.println("Invalid choice. Try again.\n");
					}
				} catch (Exception e) {
					System.out.println("Invalid input. Try again.\n");
					scanner.next();
				}
			}
		} else if (user.getRole() == Role.Administrator) {
			while (true) {
				viewAdministratorMenu();
				System.out.print("Enter your choice: ");
				if (scanner.hasNextInt()) {
					choice = scanner.nextInt();
					if (choice >= 1 && choice <= 4) {
						InputHandler.handleAdministratorMenu(user, choice, scanner, staffList, medicineList);
					} else if (choice == 5) {
						return;
					} else {
						System.out.println("Invalid choice. Try again.\n");
					}
				} else {
					System.out.println("Invalid input. Try again.\n");
					scanner.next();
				}
			}
		} else {
			System.out.println("User does not have a role");
		}
	}
}
