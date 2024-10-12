package managements;

import java.util.List;
import java.util.Scanner;

import handlers.CSVHandler;
import users.Patient;
import users.User;

public class InformationManagement {
	public static void viewPatientInfo(User patient) {
		Patient patient2 = (Patient) patient;
		System.out.println(patient2.patientInformation());
	}
	
	public static void updatePersonalInformation(User patient, Scanner scanner, List<User> patientList) {
		Patient patient2 = (Patient) patient;
		System.out.println("----Change Personal Information----");
		System.out.println("Current Email Address: " + patient2.getEmail());
		System.out.print("Enter new Email Address: ");
		scanner.nextLine();
		String email = scanner.nextLine();
		patient2.setEmail(email);
		System.out.println("\nYou have changed your email successfully.");
		CSVHandler.savePatientList(patientList);
	}
}
