package models;

import java.util.Scanner;

public class HospitalManagementSystemTest {
    
    public static void main(String[] args) {
        // Simulating a simple login system
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Hospital Management System");
        
        System.out.print("Enter User ID: ");
        String userID = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        // Simulated users for testing
        Patient patient = new Patient("P1001", "Alice Brown", "alice.brown@example.com", "A+");
        Doctor doctor = new Doctor("D001", "John Smith", "john.smith@example.com", "Cardiologist");
        Pharmacist pharmacist = new Pharmacist("P001", "Mark Lee", "mark.lee@example.com");
        Administrator admin = new Administrator("A001", "Sarah Lee", "sarah.lee@example.com");
        
        // Login logic
        if (userID.equals(patient.userID) && password.equals(patient.password)) {
            System.out.println("Welcome, " + patient.name + "!");
            patient.displayMenu();
            handlePatientActions(patient, scanner);
        } else if (userID.equals(doctor.userID) && password.equals(doctor.password)) {
            System.out.println("Welcome, Dr. " + doctor.name + "!");
            doctor.displayMenu();
            handleDoctorActions(doctor, scanner);
        } else if (userID.equals(pharmacist.userID) && password.equals(pharmacist.password)) {
            System.out.println("Welcome, " + pharmacist.name + "!");
            pharmacist.displayMenu();
            handlePharmacistActions(pharmacist, scanner);
        } else if (userID.equals(admin.userID) && password.equals(admin.password)) {
            System.out.println("Welcome, " + admin.name + "!");
            admin.displayMenu();
            handleAdminActions(admin, scanner);
        } else {
            System.out.println("Invalid login credentials.");
        }
        
        scanner.close();
    }

    // Method to handle Patient actions
    private static void handlePatientActions(Patient patient, Scanner scanner) {
        System.out.println("\nPatient Menu:");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Contact Info");
        System.out.println("3. Schedule an Appointment");
        System.out.println("4. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                patient.viewMedicalRecord();
                break;
            case 2:
                System.out.print("Enter new contact info: ");
                String newContactInfo = scanner.nextLine();
                patient.updateContactInfo(newContactInfo);
                System.out.println("Contact info updated.");
                break;
            case 3:
                System.out.println("Scheduling appointment (simulated)...");
                // Simulate scheduling
                break;
            case 4:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method to handle Doctor actions
    private static void handleDoctorActions(Doctor doctor, Scanner scanner) {
        System.out.println("\nDoctor Menu:");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Record");
        System.out.println("3. View Upcoming Appointments");
        System.out.println("4. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Viewing patient medical records (simulated)...");
                // Simulate viewing records
                break;
            case 2:
                System.out.println("Updating patient record (simulated)...");
                // Simulate updating records
                break;
            case 3:
                System.out.println("Viewing upcoming appointments (simulated)...");
                // Simulate viewing appointments
                break;
            case 4:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method to handle Pharmacist actions
    private static void handlePharmacistActions(Pharmacist pharmacist, Scanner scanner) {
        System.out.println("\nPharmacist Menu:");
        System.out.println("1. View Prescription");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Viewing prescriptions (simulated)...");
                // Simulate viewing prescriptions
                break;
            case 2:
                System.out.println("Updating prescription status (simulated)...");
                // Simulate updating prescription status
                break;
            case 3:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method to handle Administrator actions
    private static void handleAdminActions(Administrator admin, Scanner scanner) {
        System.out.println("\nAdministrator Menu:");
        System.out.println("1. Manage Staff");
        System.out.println("2. Manage Inventory");
        System.out.println("3. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Managing staff (simulated)...");
                // Simulate managing staff
                break;
            case 2:
                System.out.println("Managing inventory (simulated)...");
                // Simulate managing inventory
                break;
            case 3:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}