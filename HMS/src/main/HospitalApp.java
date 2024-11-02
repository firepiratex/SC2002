package main;

import handlers.LoginHandler;
import handlers.MedicineHandler;
import java.util.Scanner;
import management.AppointmentManagement;
import management.InventoryManagement;
import management.MedicalRecordManagement;
import management.StaffManager;
import models.Administrator;
import models.Doctor;
import models.Patient;
import models.Pharmacist;
import models.User;  // Import the new StaffManager class

public class HospitalApp {

    public static void main(String[] args) {
        while (true) {
            LoginHandler loginHandler = new LoginHandler();
            User user = loginHandler.login();

            if (user != null) {
                handleUserInput(user);  // Removed the extra call to displayMenu() here
            } else {
                System.out.println("Login failed! Exiting system.");
            }
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
                handleAdminActions((Administrator) user, choice, sc);  // Pass Scanner as well
            }

            // If the user selects option 8, log them out and exit the loop
            if (choice == 8) {
                running = false;
                System.out.println("Logging out...");
            } else if ((user instanceof Pharmacist)) {
                if (choice == 5) {
                    running = false;
                    System.out.println("Logging out...");
                }
            } else if (user instanceof Administrator) {
                if (choice == 5) {
                    running = false;
                    System.out.println("Logging out...");
                }
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
                AppointmentManagement.viewAvailableAppointment(sc);
                break;
            case 4:
                AppointmentManagement.scheduleAppointment(sc, patient);
                break;
            case 5:
                AppointmentManagement.manageRescheduleAppointment(sc, patient);
                break;
            case 6:
                AppointmentManagement.manageAppointment(sc, patient);
                break;
            case 7:
                MedicalRecordManagement.viewPatientMedicalRecord(patient);
                break;
            case 8:
                System.out.println("Returning to login...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    public static void handleDoctorActions(Doctor doctor, int choice) {
        Scanner sc = new Scanner(System.in);
        switch (choice) {
            case 1:
                MedicalRecordManagement.viewPatientMedicalRecord(doctor);
                break;
            case 2:
                MedicalRecordManagement.updatePatientMedicalRecord(doctor, sc);
                break;
            case 3:
                break;
            case 4:
                AppointmentManagement.setDoctorAvailability(sc, doctor);
                break;
            case 5:
                AppointmentManagement.manageAppointmentRequest(sc, doctor);
                break;
            case 6:
                AppointmentManagement.viewUpcomingAppointment(doctor);
                break;
            case 7:
                AppointmentManagement.recordAppointmentOutcome(sc, doctor);
                break;
            case 8:
                System.out.println("Returning to login...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    // Implement pharmacist-specific actions
    public static void handlePharmacistActions(Pharmacist pharmacist, int choice) {
        Scanner sc = new Scanner(System.in);
        MedicineHandler medicineHandler = new MedicineHandler();
        InventoryManagement inventoryManagement = new InventoryManagement();
        switch (choice) {
            case 1:
                pharmacist.viewAppointmentOutcomeRecord(sc);
                break;
            case 2:
                pharmacist.managePrescription(sc, medicineHandler);
                break;
            case 3:
                inventoryManagement.viewInventory();
                break;
            case 4:
                inventoryManagement.submitReplenishmentRequest(pharmacist, sc);
                break;
            case 5:
                System.out.println("Returning to login...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");

        }
        // Similar logic for pharmacist actions
    }

    public static void handleAdminActions(Administrator admin, int choice, Scanner sc) {
        StaffManager staffManager = new StaffManager(admin);
        InventoryManagement inventoryManagement = new InventoryManagement();
        switch (choice) {
            case 1:
                staffManager.manageStaff(sc);  // Option 1: Manage Hospital Staff
                break;
            case 2:
                admin.viewAppointments();  // Option 2: View Appointments
                break;
            case 3:
                inventoryManagement.inventoryMenu(sc);  // Option 3: Manage Medication Inventory
                break;
            case 4:
                inventoryManagement.manageReplenishmentRequest(sc);
                break;
            case 5:
                System.out.println("Returning to login...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}
