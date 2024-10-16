package handlers;

import java.util.Scanner;
import models.Patient;
import models.User;

public class LoginHandler {
    private PatientHandler patientHandler = new PatientHandler();
    private StaffManagement staffManagement = new StaffManagement();

    public User login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        User user = authenticate(id, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName() + " (" + user.getRole() + ")");
        } else {
            System.out.println("Login failed. Invalid credentials.");
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
}

