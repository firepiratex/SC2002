package models;

public class Patient extends User {

    private String contactInfo;

    public Patient(String id, String name, String password, String dateOfBirth, String gender, String bloodType, String contactInfo) {
        super(id, name, password, "Patient", gender);
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void updatePersonalInfo(String newContact) {
        this.contactInfo = newContact;
    }

    public void viewMedicalRecord() {
        System.out.println("Medical Record for Patient ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Contact Info: " + getContactInfo());
    }

    @Override
    public void displayMenu() {
        System.out.println("Patient Menu:");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule Appointment");
        System.out.println("5. Reschedule Appointment");
        System.out.println("6. Cancel Appointment");
        System.out.println("7. View Past Appointment Outcome Records");
        System.out.println("8. Logout");
    }
}
