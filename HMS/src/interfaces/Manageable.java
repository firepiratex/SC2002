package interfaces;

import models.User;
/**
 * Interface to ensure that a class implementing it has the methods for managing staff.
 * This interface defines the method and parameter for adding, removing, and viewing staff members.
 */
public interface Manageable {
	/**
     * Adds a new staff member.
     *
     * @param newStaff object representing the new staff member to be added
     */
    void addStaff(User newStaff);
    /**
     * Removes a staff member based on their unique identifier.
     *
     * @param staffId the ID of the staff member to be removed
     */
    void removeStaff(String staffId);
    /**
     * Displays all staff members.
     */
    void viewAllStaff();
}
