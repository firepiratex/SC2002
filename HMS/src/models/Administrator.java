package models;

import handlers.AppointmentHandler;
import handlers.StaffHandler;
import interfaces.Manageable;
import management.AppointmentManagement;
import management.InventoryManagement;

/**
 * The class represents a administrator member involved in managing staff and inventory. This class extends to the User class
 * and implements Manageable interface for handling managing staff.
 * The class includes methods for getting staff list, adding/removing staff, managing inventory and viewing all the appointments.
 */
public class Administrator extends User implements Manageable {

    private StaffHandler staffHandler;
    private final InventoryManagement inventoryManagement;
    private int age;
    /**
     * Constructs an {@code Administrator} object with specified attributes.
     *
     * @param id        the unique identifier of the administrator
     * @param name      the name of the administrator
     * @param password  the password for the administrator's account
     * @param gender    the gender of the administrator
     * @param age       the age of the administrator
     */
    public Administrator(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Administrator", gender);
        this.age = age;
        new AppointmentManagement();
        this.inventoryManagement = new InventoryManagement();
    }
    /**
     * Retrieves the StaffHandler instance for managing staff. If not already initialized, it creates an instance.
     *
     * @return the instance for managing staff
     */
    public StaffHandler getStaffManagement() {
        if (this.staffHandler == null) {
            this.staffHandler = StaffHandler.getInstance();
        }
        return this.staffHandler;
    }
    /**
     * Retrieves the age of the administrator.
     *
     * @return the age of the administrator
     */
    public int getAge() {
        return age;
    }
    /**
     * Displays the administrator's menu with available options.
     */
    @Override
    public void displayMenu() {
        System.out.println("Administrator Menu:");
        System.out.println("1. Manage Hospital Staff");
        System.out.println("2. View Appointments");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }
    /**
     * Adds a new staff member to the staff management system.
     *
     * @param newStaff object representing the new staff member to be added
     */
    @Override
    public void addStaff(User newStaff) {
        getStaffManagement().addStaff(newStaff);
    }
    /**
     * Removes a staff member from the staff management system by their ID.
     *
     * @param staffId the ID of the staff member to be removed
     */
    @Override
    public void removeStaff(String staffId) {
        getStaffManagement().removeStaff(staffId);
    }
    /**
     * Displays all staff members.
     */
    @Override
    public void viewAllStaff() {
        getStaffManagement().displayStaff();
    }
    /**
     * Views all appointments scheduled within the system.
     */
    public void viewAppointments() {
        AppointmentHandler.getInstance().viewAllAppointment();
    }
    /**
     * Manages the medication inventory.
     */
    public void manageInventory() {
        inventoryManagement.viewInventory();
    }
    /**
     * Returns a string representation of the administrator, including their basic information and age.
     *
     * @return a string representation of the administrator
     */
    public String toString() {
    	return super.toString() + " " + getAge();
    }

}
