package models;

import handlers.StaffManagement;

public class Administrator extends User {
    private StaffManagement staffManagement;

    public Administrator(String id, String name, String password) {
        super(id, name, password, "Administrator");
        this.staffManagement = new StaffManagement();  // Initialize staff management
    }

    // Implement displayMenu() for the Administrator
    @Override
    public void displayMenu() {
        System.out.println("Administrator Menu:");
        System.out.println("1. Manage Hospital Staff");
        System.out.println("2. View Appointments");
        System.out.println("3. Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }

    public void addStaffMember(User newStaff) {
        staffManagement.addStaff(newStaff);  // Use the StaffManagement class to add staff
        System.out.println("New staff member added successfully.");
    }

    public void removeStaffMember(String staffId) {
        User staff = staffManagement.findStaffById(staffId);  // Use the StaffManagement class to find staff
        if (staff != null) {
            staffManagement.removeStaff(staffId);
            System.out.println("Staff member removed successfully.");
        } else {
            System.out.println("Staff member not found.");
        }
    }

    public void displayStaff() {
        staffManagement.displayStaff();  // Display all staff members using StaffManagement
    }
}
