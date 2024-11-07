package models;

import handlers.PasswordHash;
/**
 * This class serves as the base for different types of users, such as patient, doctor, pharmacist and administrators.
 */
public abstract class User {
    protected String id;
    protected String name;
    protected String password;
    protected String role;
    protected String gender;
    /**
     * Constructs a User object with the specified parameter.
     *
     * @param id       the unique identifier of the user
     * @param name     the name of the user
     * @param password the password for the user's account
     * @param role     the role assigned to the user
     * @param gender   the gender of the user
     */
    public User(String id, String name, String password, String role, String gender) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.gender = gender;
    }
    /**
     * Retrieves the ID of the user.
     *
     * @return the user's ID
     */
    public String getId() {
        return id;
    }
    /**
     * Retrieves the name of the user.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets a new password for the user.
     *
     * @param newPassword is the new password to be set
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    /**
     * Retrieves the role of the user.
     *
     * @return the user's role
     */
    public String getRole() {
        return role;
    }
    /**
     * Validates the input password against the stored password by hashing the input.
     *
     * @param inputPassword the password provided for validation
     * @return true if the input password matches the stored password, else false
     */
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(PasswordHash.hash(inputPassword));
    }
    /**
     * Retrieves the password of the user.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Returns a string representation of the user, including their ID, name, role, and gender.
     *
     * @return a string representation of the user
     */
    public String toString() {
    	return getId() + " " + getName() + " " + getRole() + " " + getGender();
    }
    /**
     * Retrieves the gender of the user.
     *
     * @return the user's gender
     */
    public String getGender() {
        return gender;
    }
    /**
     * Abstract method for displaying the menu, which should be implemented by subclasses.
     */
    public abstract void displayMenu();
}
