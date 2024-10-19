package models;

import handlers.AppointmentManagement;
import handlers.InventoryManagement;
import handlers.StaffManagement;
import interfaces.Manageable;
import java.util.Scanner;

public class Administrator extends User implements Manageable {
    private StaffManagement staffManagement;
    private AppointmentManagement appointmentManagement;
    private InventoryManagement inventoryManagement;
    private int age;

    public Administrator(String id, String name, String password, String gender, int age, boolean avoidInit) {
        super(id, name, password, "Administrator", gender);
        this.age = age;
        // Lazy initialization of other handlers to avoid recursion
        if (!avoidInit) {
            this.appointmentManagement = new AppointmentManagement();
            this.inventoryManagement = new InventoryManagement();
        }
    }

    // Lazy initialization for staffManagement
    public StaffManagement getStaffManagement() {
        if (this.staffManagement == null) {
            this.staffManagement = new StaffManagement();  // Initialize when needed
        }
        return this.staffManagement;
    }

    @Override
    public void displayMenu() {
        System.out.println("Administrator Menu:");
        System.out.println("1. Manage Hospital Staff");
        System.out.println("2. View Appointments");
        System.out.println("3. Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }

    // Implement the methods from Manageable interface
    @Override
    public void addStaff(User newStaff) {
        getStaffManagement().addStaff(newStaff);  // Use lazy initialization
        getStaffManagement().saveStaffs();  // Save to CSV after adding new staff
    }

    @Override
    public void removeStaff(String staffId) {
        getStaffManagement().removeStaff(staffId);  // Use lazy initialization
        getStaffManagement().saveStaffs();  // Save to CSV after removing staff
    }

    @Override
    public void viewAllStaff() {
        getStaffManagement().displayStaff();  // Use lazy initialization
    }

    // New Functions
    public void viewAppointments() {
        appointmentManagement.viewAllAppointments();
    }

    public void manageInventory() {
        inventoryManagement.displayInventory();
    }

    public void approveReplenishmentRequest(String medicineName) {
        inventoryManagement.approveReplenishmentRequest(medicineName);
    }

    // Manage hospital staff
    public void manageStaff(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Manage Hospital Staff:");
            System.out.println("1. Add Staff");
            System.out.println("2. Remove Staff");
            System.out.println("3. View All Staff");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Add new staff
                    addNewStaff(scanner);
                    break;
                case 2:
                    // Remove existing staff
                    System.out.print("Enter staff ID to remove: ");
                    String staffId = scanner.nextLine();
                    removeStaff(staffId);
                    break;
                case 3:
                    // View all staff members
                    viewAllStaff();
                    break;
                case 4:
                    exit = true;  // Back to main menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Function to add new staff under the Administrator role
    public void addNewStaff(Scanner scanner) {
        System.out.print("Enter staff ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter staff name: ");
        String name = scanner.nextLine();
        System.out.print("Enter staff password: ");
        String password = scanner.nextLine();
        System.out.print("Enter staff gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter staff age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter staff role (Doctor, Pharmacist, Administrator): ");
        String role = scanner.nextLine();

        User newStaff = null;
        switch (role) {
            case "Doctor":
                newStaff = new Doctor(id, name, password, gender, age);
                break;
            case "Pharmacist":
                newStaff = new Pharmacist(id, name, password, gender, age);
                break;
            case "Administrator":
                newStaff = new Administrator(id, name, password, gender, age, true);
                break;
            default:
                System.out.println("Invalid role! Please enter Doctor, Pharmacist, or Administrator.");
                return;
        }

        if (newStaff != null) {
            addStaff(newStaff);
            System.out.println("New staff added: " + newStaff.getName() + " (" + newStaff.getRole() + ")");
        }
    }
}
