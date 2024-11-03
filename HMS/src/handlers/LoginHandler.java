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
