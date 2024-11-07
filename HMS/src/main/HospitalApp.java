package main;

import handlers.LoginHandler;
import handlers.MedicalCertificateHandler;
import handlers.MedicineHandler;
import java.util.Scanner;
import management.AppointmentManagement;
import management.BillingRecordManagement;
import management.InventoryManagement;
import management.MedicalRecordManagement;
import management.StaffManager;
import models.Administrator;
import models.Doctor;
import models.Patient;
import models.Pharmacist;
import models.User;
/**
 * Main entry point for the hospital management application.
 * This class handles user login and provides user-specific functionality based on their roles.
 */
public class HospitalApp {
	/**
     * Main method to start the hospital application. Continuously prompts for user login and
     * redirects to user-specific functionality if login is successful.
     *
     * @param args the command-line arguments
     */
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
    /**
     * Handles user input and actions based on the user's role.
     *
     * @param user the logged-in user
     */
    public static void handleUserInput(User user) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            user.displayMenu();
            System.out.print("Enter option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            if (user instanceof Patient) {
                handlePatientActions((Patient) user, choice);
            } else if (user instanceof Doctor) {
                handleDoctorActions((Doctor) user, choice);
            } else if (user instanceof Pharmacist) {
                handlePharmacistActions((Pharmacist) user, choice);
            } else if (user instanceof Administrator) {
                handleAdminActions((Administrator) user, choice, sc);
            }

            if (user instanceof Doctor) {
                if (choice == 10) {
                    running = false;
                    System.out.println("Logging out...");
                }
            } else if (user instanceof Patient) {
                if (choice == 12) {
                    running = false;
                    System.out.println("Logging out...");
                }
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
    /**
     * Handles actions specific to patients.
     *
     * @param patient the logged-in patient
     * @param choice  the option selected by the patient
     */
    public static void handlePatientActions(Patient patient, int choice) {
        Scanner sc = new Scanner(System.in);
        switch (choice) {
            case 1:
                patient.viewMedicalRecord();
                break;
            case 2:
                patient.displayPersonalInfoMenu();
                while (true) {
                    System.out.print("Enter the choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice == 0) {
                        break;
                    } else if (choice == 1) {
                        System.out.print("Enter new contact email address: ");
                        String email = sc.nextLine();
                        patient.updatePersonalInfo(email);
                        break;
                    } else if (choice == 2) {
                        System.out.print("Enter new contact number: ");
                        String number = sc.nextLine();
                        patient.updateContactNo(number);
                        break;
                    } else {
                        System.out.println("Invalid choice.");
                    }
                }
                break;
            case 3:
                AppointmentManagement.viewAvailableAppointment(sc);
                break;
            case 4:
                AppointmentManagement.scheduleAppointment(sc, patient);
                break;
            case 5:
                AppointmentManagement.rescheduleAppointment(sc, patient);
                break;
            case 6:
                AppointmentManagement.cancelAppointment(sc, patient);
                break;
            case 7:
                AppointmentManagement.viewScheduledAppointment(patient);
                break;
            case 8:
                MedicalRecordManagement.viewPatientMedicalRecord(patient);
                break;
            case 9: // Request medical certificate
                System.out.print("Enter reason for medical certificate: ");
                String reason = sc.nextLine();
                System.out.print("Enter duration (in days): ");
                int duration;
                if (sc.hasNextInt()) {
                	duration = sc.nextInt();
                } else {
                	System.out.println("Invalid duration.\n");
                	return;
                }
                sc.nextLine(); // Consume the newline character
                patient.requestMedicalCertificate(reason, duration);
                break;
            case 10: // View medical certificates
                patient.viewMedicalCertificates();
                break;
            case 11:
                BillingRecordManagement.displayPastOutcomes(sc, patient);
                break;
            case 12: // Logout
                System.out.println("Returning to login...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    /**
     * Handles actions specific to doctors.
     *
     * @param doctor the logged-in doctor
     * @param choice the option selected by the doctor
     */
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
                doctor.viewPersonalSchedule();
                break;
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
            case 8: // View all medical certificates
                MedicalCertificateHandler.viewAllCertificates();
                break;
            case 9: // Approve or reject a specific medical certificate
                System.out.println("---- Pending Medical Certificate Requests ----");
                MedicalCertificateHandler.getInstance().updateCertificateStatus(doctor, sc);
                break;
            case 10:
                System.out.println("Returning to login...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    /**
     * Handles actions specific to pharmacists.
     *
     * @param pharmacist the logged-in pharmacist
     * @param choice     the option selected by the pharmacist
     */
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
    /**
     * Handles actions specific to administrators.
     *
     * @param admin the logged-in administrator
     * @param choice the option selected by the administrator
     * @param sc     a {@code Scanner} object for user input
     */
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
