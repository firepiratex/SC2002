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
            System.out.print("Enter User ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();
            

            // Authenticate the user with the provided credentials
            user = authenticate(id, password);

            // If the user is found
            if (user != null) {
                // Check if the password is the default one
                if (password.equals("password")) {
                    initialLogin(user, sc); // Prompt for password change
                } else {
                    // Check against hashed password for subsequent logins
                    if (validatePassword(user.getPassword(), password)) {
                        System.out.println("Login successful! Welcome, " + user.getName() + " (" + user.getRole() + ")");
                        break; // Successful login
                    } else {
                        System.out.println("Login failed. Invalid credentials.");
                    }
                }
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        }
        return user;
    }

    private User authenticate(String id, String password) {
        // First, check if the user is a patient
        Patient patient = patientHandler.findPatientById(id);
        if (patient != null) {
            return patient; // Return the patient if found
        }

        // Then check if the user is staff (doctor, pharmacist, or administrator)
        User staff = staffManagement.findStaffById(id);
        if (staff != null) {
            return staff; // Return the staff member if found
        }

        // If no match found, return null
        return null;
    }

    // Method for validating the password using hashing for comparison
    private boolean validatePassword(String storedHashedPassword, String inputPassword) {
        return storedHashedPassword.equals(PasswordHash.hash(inputPassword)); // Compare hashed password
    }

    private void initialLogin(User user, Scanner scanner) {
        System.out.println("This is your first time logging in, so you need to change your password.");
        while (true) {
            System.out.println("----Change Password----");
            System.out.print("Enter your new password: ");
            String password = scanner.next();
            System.out.print("Enter your new password again: ");
            String confirmPassword = scanner.next();
            if (password.equals(confirmPassword)) {
                if (confirmPassword.equals("password")) {
                    System.out.println("Cannot be the default password.");
                    continue; // Reject default password
                }
                System.out.println("You have changed your password successfully!");
                user.setPassword(PasswordHash.hash(confirmPassword)); // Store the hashed password
                break; // Exit the loop after successful change
            } else {
                System.out.println("The passwords do not match. Try again.");
            }
        }

        // Save the updated user information
        if (user.getRole().equals("Patient")) {
            patientHandler.saveAccount();
        } else {
            staffManagement.saveAccount();
        }
        scanner.nextLine(); // Clear the scanner buffer
    }
}
