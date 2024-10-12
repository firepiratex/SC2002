package handlers;

import java.util.List;
import java.util.Scanner;

import users.Role;
import users.User;

public class LoginHandler {
	public static User login(List<User> listOfStaff, List<User> listOfPatient) {
		Scanner scanner = new Scanner(System.in);
		User authenticatedUser = null;
		List<User> staffList = listOfStaff;
		List<User> patientList = listOfPatient;
		while (true) {
	        System.out.println(
	                "██╗  ██╗███╗   ███╗███████╗\r\n" +
	                "██║  ██║████╗ ████║██╔════╝\r\n" +
	                "███████║██╔████╔██║███████╗\r\n" +
	                "██╔══██║██║╚██╔╝██║╚════██║\r\n" +
	                "██║  ██║██║ ╚═╝ ██║███████║\r\n" +
	                "╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝"
	            );
			System.out.println("----Hospital Management System----");
			System.out.print("User ID: ");
			String userID = scanner.next();
			System.out.print("Password: ");
			String password = scanner.next();
			if (userID.toLowerCase().charAt(0) == 'p' && userID.charAt(1) == '1') {
				for (User patient : patientList) {
					if (userID.equals(patient.getUniqueID()) && password.equals(patient.getPassword())) {
						authenticatedUser = patient;
					}
				}
			} else {
				for(User staff : staffList) {
					if (userID.equals(staff.getUniqueID()) && password.equals(staff.getPassword())) {
						authenticatedUser = staff;
					}
				}
			}
			if (authenticatedUser != null && authenticatedUser.getRole() != Role.Patient) {
				if (authenticatedUser.getInitialLogin()) {
					LoginHandler.changePassword(authenticatedUser, scanner);
					AccountHandler.saveStaffAccount(staffList);
					System.out.println("Login again.");
					authenticatedUser = null;
					continue;
				}
				System.out.println("Login successful.");
				return authenticatedUser;
			} else if (authenticatedUser != null && authenticatedUser.getRole() == Role.Patient) {
				if (authenticatedUser.getInitialLogin()) {
					LoginHandler.changePassword(authenticatedUser, scanner);
					AccountHandler.savePatientAccount(patientList);
					System.out.println("Login again.");
					authenticatedUser = null;
					continue;
				}
				System.out.println("Login successful.");
				return authenticatedUser;
			}
			System.out.println("Wrong user ID or password.");
		}
	}
	
	public static void changePassword(User user, Scanner scanner) {
		System.out.println("This is your first time logging in so you need "
				+ "to change your password");
		while (true) {
			System.out.println("----Change Password----");
			System.out.print("Enter your new password: ");
			String password = scanner.next();
			System.out.print("Enter your new password again: ");
			String confirmPassword = scanner.next();
			if (password.equals(confirmPassword)) {
				System.out.println("You have changed your password successfully!");
				user.setPassword(confirmPassword);
				user.setInitialLogin(false);
				break;
			} else {
				System.out.println("The password does not match. Try again");
			}
		}
	}
}
