package models;

import handlers.StaffHandler;
import interfaces.Manageable;
import management.AppointmentManagement;
import management.InventoryManagement;

public class Administrator extends User implements Manageable {

    private StaffHandler staffHandler;
    private final InventoryManagement inventoryManagement;
    private int age;

    public Administrator(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Administrator", gender);
        this.age = age;
        new AppointmentManagement();
        this.inventoryManagement = new InventoryManagement();
    }

    // Lazy initialization for staffManagement
    public StaffHandler getStaffManagement() {
        if (this.staffHandler == null) {
            this.staffHandler = StaffHandler.getInstance();  // Initialize when needed
        }
        return this.staffHandler;
    }

    public int getAge() {
        return age;
    }

    @Override
    public void displayMenu() {
        System.out.println("Administrator Menu:");
        System.out.println("1. Manage Hospital Staff");
        System.out.println("2. View Appointments");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }

    // Implement the methods from Manageable interface
    @Override
    public void addStaff(User newStaff) {
        getStaffManagement().addStaff(newStaff);  // Use lazy initialization
    }

    @Override
    public void removeStaff(String staffId) {
        getStaffManagement().removeStaff(staffId);  // Use lazy initialization
    }

    @Override
    public void viewAllStaff() {
        getStaffManagement().displayStaff();  // Use lazy initialization
    }

    // New Functions
    // View all appointments
    public void viewAppointments() {
        //appointmentManagement.viewAllAppointments();
    }

    // Manage medication inventory
    public void manageInventory() {
        inventoryManagement.viewInventory();
    }

}
