package models;

public class Pharmacist extends User {
    public Pharmacist(String userID, String name, String contactInfo) {
        super(userID, name, contactInfo);
    }

    public void viewPrescriptions() {
        // Logic to view prescriptions
    }

    @Override
    public void displayMenu() {
        // Display pharmacist-specific menu options
    }
}
