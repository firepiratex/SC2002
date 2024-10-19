package handlers;

import java.util.Scanner;
import models.Patient;
import models.User;

public class LoginHandler {
    private PatientHandler patientHandler = new PatientHandler();
    private StaffManagement staffManagement = new StaffManagement();

    public User login() {
        Scanner sc = new Scanner(System.in);
        User user = null;
        while(true) {
	        System.out.print("Enter User ID: ");
	        String id = sc.nextLine();
	        System.out.print("Enter Password: ");
	        String password = sc.nextLine();
	
	        user = authenticate(id, password);
	        if (user != null && user.getPassword().equals("password")) {
	        	initialLogin(user, sc);
	        } else if (user != null) {
	            System.out.println("Login successful! Welcome, " + user.getName() + " (" + user.getRole() + ")");
	            break;
	        } else {
	            System.out.println("Login failed. Invalid credentials.");
	        }
        }
        return user;
    }

    private User authenticate(String id, String password) {
        // First, check if the user is a patient
        Patient patient = patientHandler.findPatientById(id);
        if (patient != null && patient.validatePassword(password)) {
            return patient;
        }

        // Then check if the user is staff (doctor, pharmacist, or administrator)
        User staff = staffManagement.findStaffById(id);
        if (staff != null && staff.validatePassword(password)) {
            return staff;
        }

        // If no match found, return null
        return null;
    }
    
    private void initialLogin(User user, Scanner scanner) {
    	System.out.println("This is your first time logging in so you need "
				+ "to change your password");
		while (true) {
			System.out.println("----Change Password----");
			System.out.print("Enter your new password: ");
			String password = scanner.next();
			System.out.print("Enter your new password again: ");
			String confirmPassword = scanner.next();
			if (password.equals(confirmPassword)) {
				if (confirmPassword.equals("password")) {
					System.out.println("Cannot be the default password.");
					continue;
				}
				System.out.println("You have changed your password successfully!");
				user.setPassword(confirmPassword);
				break;
			} else {
				System.out.println("The password does not match. Try again");
			}
		}
		if (user.getRole().equals("Patient")) {
			patientHandler.savePatients();
		} else {
			staffManagement.saveStaffs();
		}
		scanner.nextLine();
    }
}

