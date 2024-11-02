package main;

import handlers.LoginHandler;
import handlers.MedicalCertificateHandler;
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
import models.User;

public class HospitalApp {

    public static void main(String[] args) {
        while (true) {
            LoginHandler loginHandler = new LoginHandler();
            User user = loginHandler.login();

            if (user != null) {
                handleUserInput(user);
            } else {
                System.out.println("Login failed! Exiting system.");
            }
        }
    }

    public static void handleUserInput(User user) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            user.displayMenu();
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
                handleAdminActions((Administrator) user, choice, sc);
            }

            if (choice == 10) {
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
                System.out.print("Enter new contact number: ");
                String contactNo = sc.nextLine();
                patient.updatePersonalInfo(contactNo);
                System.out.println("Contact information updated successfully.");
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
            case 8:  // Request medical certificate
                System.out.print("Enter reason for medical certificate: ");
                String reason = sc.nextLine();
                System.out.print("Enter duration (in days): ");
                int duration = sc.nextInt();
                sc.nextLine();  // Consume the newline character
                patient.requestMedicalCertificate(reason, duration);
                break;
            case 9:  // View medical certificates
                patient.viewMedicalCertificates();
                break;
            case 10:  // Logout
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
            case 4:
                doctor.setAvailability(sc);
                break;
            case 5:
                AppointmentManagement.manageAppointmentRequest(sc, doctor);
                break;
            case 6:
                doctor.viewUpcomingAppointments();
                break;
            case 7:
                AppointmentManagement.recordAppointmentOutcome(sc, doctor);
                break;
            case 8:  // View all medical certificates
                MedicalCertificateHandler.viewAllCertificates();
                break;
            case 9:  // Approve or reject a specific medical certificate
                System.out.println("Enter the Patient ID whose certificate you want to approve/reject: ");
                String patientIdToUpdate = sc.nextLine();
                System.out.print("Enter new status (Approved/Rejected): ");
                String newStatus = sc.nextLine();
                doctor.approveOrRejectCertificate(patientIdToUpdate, newStatus);
                break;
            case 10:
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
    }

    public static void handleAdminActions(Administrator admin, int choice, Scanner sc) {
        StaffManager staffManager = new StaffManager(admin);
        InventoryManagement inventoryManagement = new InventoryManagement();
        switch (choice) {
            case 1:
                staffManager.manageStaff(sc);
                break;
            case 2:
                admin.viewAppointments();
                break;
            case 3:
                inventoryManagement.inventoryMenu(sc);
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
