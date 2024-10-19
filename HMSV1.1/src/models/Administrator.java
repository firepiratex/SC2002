package models;

import handlers.AppointmentManagement;
import handlers.InventoryManagement;
import handlers.StaffManagement;
import interfaces.Manageable;

public class Administrator extends User implements Manageable {
    private StaffManagement staffManagement;
    private AppointmentManagement appointmentManagement;
    private InventoryManagement inventoryManagement;
    private int age;

    public Administrator(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Administrator", gender);
        this.age = age;
        this.appointmentManagement = new AppointmentManagement();
        this.inventoryManagement = new InventoryManagement();
        // Do not initialize staffManagement here to avoid recursion
    }

    // Lazy initialization for staffManagement
    public StaffManagement getStaffManagement() {
        if (this.staffManagement == null) {
            this.staffManagement = new StaffManagement();  // Initialize when needed
        }
        return this.staffManagement;
    }
    
    public int getAge() {
    	return age;
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
        appointmentManagement.viewAllAppointments();
    }

    // Manage medication inventory
    public void manageInventory() {
        inventoryManagement.displayInventory();
    }

    // Approve replenishment requests for specific medications
    public void approveReplenishmentRequest(String medicineName) {
        inventoryManagement.approveReplenishmentRequest(medicineName);
    }
}
