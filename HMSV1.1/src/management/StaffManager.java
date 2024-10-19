package management;

import java.util.Scanner;
import models.Administrator;
import models.Doctor;
import models.Pharmacist;
import models.User;

public class StaffManager {
    
    private Administrator admin;  // Administrator who manages staff

    // Constructor for StaffManager
    public StaffManager(Administrator admin) {
        this.admin = admin;
    }

    // Manage staff menu
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
                    addStaffHandler(scanner);  // Handles adding a staff member
                    break;
                case 2:
                    removeStaffHandler(scanner);  // Handles removing a staff member
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
    }

    // Handles adding a staff member
    private void addStaffHandler(Scanner scanner) {
        String id = promptForInput("Enter staff ID: ", scanner);
        String name = promptForInput("Enter staff name: ", scanner);
        String password = promptForInput("Enter staff password: ", scanner);
        String gender = promptForInput("Enter staff gender: ", scanner);
        int age = promptForAge("Enter staff age: ", scanner);
        String role = promptForInput("Enter staff role (Doctor, Pharmacist, Administrator): ", scanner);

        User newStaff = createStaffByRole(id, name, password, gender, age, role);

        if (newStaff != null) {
            admin.addStaff(newStaff);  // Add the new staff member
            System.out.println("New staff added: " + newStaff.getName() + " (" + newStaff.getRole() + ")");
        } else {
            System.out.println("Invalid role! Please enter Doctor, Pharmacist, or Administrator.");
        }
    }

    // Handles removing a staff member
    private void removeStaffHandler(Scanner scanner) {
        String staffId = promptForInput("Enter staff ID to remove: ", scanner);
        admin.removeStaff(staffId);
    }

    // Helper method to prompt for string input
    private String promptForInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Helper method to prompt for integer input (age)
    private int promptForAge(String prompt, Scanner scanner) {
        System.out.print(prompt);
        int age = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        return age;
    }

    // Helper method to create staff based on role
    private User createStaffByRole(String id, String name, String password, String gender, int age, String role) {
        switch (role) {
            case "Doctor":
                return new Doctor(id, name, password, gender, age);
            case "Pharmacist":
                return new Pharmacist(id, name, password, gender, age);
            case "Administrator":
                return new Administrator(id, name, password, gender, age);
            default:
                return null;  // Invalid role
        }
    }
}
