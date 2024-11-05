package handlers;

import java.util.Scanner;
import models.Patient;
import models.User;

public class LoginHandler {

    public User login() {
        PatientHandler.getInstance().displayPatient();
        StaffHandler.getInstance().displayStaff();
        Scanner sc = new Scanner(System.in);
        User user = null;
        while (true) {
        	System.out.println(
                    "-----------------------------\n"+
                    " ██╗  ██╗███╗   ███╗███████╗\r\n" +
                    " ██║  ██║████╗ ████║██╔════╝\r\n" +
                    " ███████║██╔████╔██║███████╗ \r\n" +
                    " ██╔══██║██║╚██╔╝██║╚════██║ \r\n" +
                    " ██║  ██║██║ ╚═╝ ██║███████║\r\n" +
                    " ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝\r" +
                    "-----------------------------");
            System.out.println("Hospital Management System");
            System.out.print("Enter User ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            user = authenticate(id, password);
            if (user != null && user.getPassword().equals(PasswordHash.hash("password"))) {
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
        Patient patient = PatientHandler.getInstance().findPatientById(id);
        if (patient != null && patient.validatePassword(password)) {
            return patient;
        }

        User staff = StaffHandler.getInstance().findStaffById(id);
        if (staff != null && staff.validatePassword(password)) {
            return staff;
        }

        return null;
    }

    private void initialLogin(User user, Scanner scanner) {
    	boolean length = false, upperCharacter = false, specialCharacter = false, number = false;
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
                if (confirmPassword.length() >= 8) {
                	length = true;
                }
                for(int i = 0; i < confirmPassword.length(); i++) {
                	if (confirmPassword.charAt(i) >= 65 && confirmPassword.charAt(i) <= 90) {
                		upperCharacter = true;
                	}
                	if (confirmPassword.charAt(i) >= 48 && confirmPassword.charAt(i) <= 57) {
                		number = true;
                	}
                	if ((confirmPassword.charAt(i) >= 33 && confirmPassword.charAt(i) <= 42) || confirmPassword.charAt(i) == 64) {
                		specialCharacter = true;
                	}
                }
                if (length == false || upperCharacter == false || specialCharacter == false || number == false) {
                	System.out.println("The password does not meet the requirement.");
                	if (length == false) {
                		System.out.println("[x] More than 8 characters");
                	} else {
                		System.out.println("[/] More than 8 characters");
                	}
                	if (upperCharacter == false) {
                		System.out.println("[x] At least one upper character");
                	} else {
                		System.out.println("[/] At least one upper character");
                	}
                	if (specialCharacter == false) {
                		System.out.println("[x] At least one special character (!, \", #, $, %, &, ', (, ), *, @)");
                	} else {
                		System.out.println("[/] At least one special character (!, \", #, $, %, &, ', (, ), *, @)");
                	}
                	if (number == false) {
                		System.out.println("[x] At least one number");
                	} else {
                		System.out.println("[/] At least one number");
                	}
                	continue;
                }
                System.out.println("You have changed your password successfully!");
                user.setPassword(PasswordHash.hash(confirmPassword)); // Store the hashed password
                break;
            } else {
                System.out.println("The password does not match. Try again");
            }
        }
        if (user.getRole().equals("Patient")) {
            PatientHandler.getInstance().saveAccount();
        } else {
            StaffHandler.getInstance().saveAccount();
        }
        scanner.nextLine();
    }
}
