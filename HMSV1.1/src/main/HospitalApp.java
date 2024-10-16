package main;

import handlers.LoginHandler;
import java.util.Scanner;
import models.Administrator;
import models.Doctor;
import models.Patient;
import models.Pharmacist;
import models.User;

public class HospitalApp {
    public static void main(String[] args) {
        LoginHandler loginHandler = new LoginHandler();
        User user = loginHandler.login();

        if (user != null) {
            handleUserInput(user);  // Removed the extra call to displayMenu() here
        } else {
            System.out.println("Login failed! Exiting system.");
        }
    }

    public static void handleUserInput(User user) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        // Continue displaying the menu and asking for input until the user logs out
        while (running) {
            user.displayMenu();  // Display the user-specific menu (this is the only place it should be called)
            System.out.print("Enter option: ");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume the newline character

            if (user instanceof Patient) {
                handlePatientActions((Patient) user, choice);
            } else if (user instanceof Doctor) {
                handleDoctorActions((Doctor) user, choice);
            } else if (user instanceof Pharmacist) {
                handlePharmacistActions((Pharmacist) user, choice);
            } else if (user instanceof Administrator) {
                handleAdminActions((Administrator) user, choice);
            }

            // If the user selects option 8, log them out and exit the loop
            if (choice == 8) {
                running = false;
                System.out.println("Logging out...");
            }
        }
    }

    // Implement patient-specific actions
    public static void handlePatientActions(Patient patient, int choice) {
        Scanner sc = new Scanner(System.in);
        switch (choice) {
            case 1:
                patient.viewMedicalRecord();
                break;
            case 2:
                String contactNo = sc.nextLine();
                patient.updatePersonalInfo(contactNo);
                break;
            case 3:
                // Code to view available appointments
                break;
            case 4:
                // Code to schedule an appointment
                break;
            case 5:
                // Code to reschedule an appointment
                break;
            case 6:
                // Code to cancel an appointment
                break;
            case 7:
                // Code to view past appointment outcomes
                break;
            case 8:
                System.out.println("Returning to login...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    // Implement doctor-specific actions
    public static void handleDoctorActions(Doctor doctor, int choice) {
        // Similar logic for doctor actions
    }

    // Implement pharmacist-specific actions
    public static void handlePharmacistActions(Pharmacist pharmacist, int choice) {
        // Similar logic for pharmacist actions
    }

    // Implement administrator-specific actions
    public static void handleAdminActions(Administrator admin, int choice) {
        // Similar logic for administrator actions
    }
}
