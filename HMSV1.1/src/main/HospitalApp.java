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
                handleAdminActions((Administrator) user, choice, sc);  // Pass Scanner as well
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
                boolean running = false;
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

    public static void handleAdminActions(Administrator admin, int choice, Scanner sc) {
        switch (choice) {
            case 1:
                //manageStaff(admin, sc);  // Option 1: Manage Hospital Staff
                break;
            case 2:
                admin.viewAppointments();  // Option 2: View Appointments
                break;
            case 3:
                admin.manageInventory();  // Option 3: Manage Medication Inventory
                break;
            case 4:
                System.out.print("Enter medicine name for replenishment approval: ");
                String medicineName = sc.nextLine();
                admin.approveReplenishmentRequest(medicineName);  // Option 4: Approve Replenishment Requests
                break;
            case 5:
                System.out.println("Returning to login...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }


    /*private static void manageStaff(Administrator admin, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Manage Hospital Staff:");
            System.out.println("1. Add Staff");
            System.out.println("2. Remove Staff");
            System.out.println("3. View All Staff");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline
    
            switch (choice) {
                case 1:
                    System.out.print("Enter staff ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter staff name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter staff password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter staff role (Doctor, Pharmacist, Administrator): ");
                    String role = scanner.nextLine();
    
                    User newStaff = null;  // Declare a User variable to hold the new staff member
                    switch (role) {
                        case "Doctor":
                            newStaff = new Doctor(id, name, password);
                            break;
                        case "Pharmacist":
                            newStaff = new Pharmacist(id, name, password);
                            break;
                        case "Administrator":
                            newStaff = new Administrator(id, name, password);
                            break;
                        default:
                            System.out.println("Invalid role! Please enter Doctor, Pharmacist, or Administrator.");
                            continue;
                    }
    
                    if (newStaff != null) {
                        admin.addStaff(newStaff);  // Add the new staff member
                    }
                    break;
                case 2:
                    System.out.print("Enter staff ID to remove: ");
                    String staffId = scanner.nextLine();
                    admin.removeStaff(staffId);  // Remove a staff member
                    break;
                case 3:
                    admin.viewAllStaff();  // View all staff members
                    break;
                case 4:
                    exit = true;  // Back to the main menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }*/
}
