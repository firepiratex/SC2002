package models;

public class Doctor extends User {
    private String specialization;

    public Doctor(String userID, String name, String contactInfo, String specialization) {
        super(userID, name, contactInfo);
        this.specialization = specialization;
    }

    public void viewPatientMedicalRecords() {
        // Logic to view patient medical records
    }

    @Override
    public void displayMenu() {
        // Display doctor-specific menu options
    }
}
