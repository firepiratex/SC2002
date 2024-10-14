package models;

public class Patient extends User {
    private String patientID;
    private String bloodType;

    public Patient(String userID, String name, String contactInfo, String bloodType) {
        super(userID, name, contactInfo);
        this.patientID = userID;
        this.bloodType = bloodType;
    }

    public void viewMedicalRecord() {
        // Logic to view medical record
    }

    public void updateContactInfo(String newContactInfo) {
        super.contactInfo = newContactInfo;
    }

    @Override
    public void displayMenu() {
        // Display patient-specific menu options
    }
}
