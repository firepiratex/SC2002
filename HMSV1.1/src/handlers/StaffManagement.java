package handlers;

import java.util.*;
import models.User;

public class StaffManagement {
    private List<User> staff;
    private final String staffFile = "data/Staff_List.csv";

    public StaffManagement() {
        this.staff = new ArrayList<>();
        loadStaff();  // Load staff data when initializing
    }

    // Load staff from CSV
    public void loadStaff() {
        List<String[]> data = CSVHandler.readCSV(staffFile);
        for (String[] row : data) {
            String id = row[0];                // Staff ID
            String name = row[1];              // Staff Name
            String role = row[3];              // Role (Doctor, Pharmacist, Administrator)
            String defaultPassword = "password";  // Default password
            User staffMember = null;

            switch (role) {
                case "Doctor":
                    staffMember = new models.Doctor(id, name, defaultPassword);
                    break;
                case "Pharmacist":
                    staffMember = new models.Pharmacist(id, name, defaultPassword);
                    break;
                case "Administrator":
                    staffMember = new models.Administrator(id, name, defaultPassword);
                    break;
            }

            if (staffMember != null) {
                staff.add(staffMember);
            }
        }
    }

    // Add new staff member
    public void addStaff(User newStaff) {
        staff.add(newStaff);
        System.out.println("Staff member added successfully.");
    }

    // Remove staff member by ID
    public void removeStaff(String staffId) {
        Iterator<User> iterator = staff.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId().equals(staffId)) {
                iterator.remove();  // Use iterator.remove() to properly remove from a list during iteration
                System.out.println("Staff member removed successfully.");
                return;
            }
        }
        System.out.println("Staff member not found.");
    }

    // Find a staff member by ID
    public User findStaffById(String id) {
        for (User user : staff) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    // Display all staff members
    public void displayStaff() {
        System.out.println("Hospital Staff:");
        for (User user : staff) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Role: " + user.getRole());
        }
    }
}
