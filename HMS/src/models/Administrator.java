package models;

public class Administrator extends User {
    public Administrator(String userID, String name, String contactInfo) {
        super(userID, name, contactInfo);
    }

    public void manageStaff() {
        // Logic to manage staff
    }

    public void manageInventory() {
        // Logic to manage inventory
    }

    @Override
    public void displayMenu() {
        // Display administrator-specific menu options
    }
}
