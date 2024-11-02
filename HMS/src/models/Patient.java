package models;

import java.util.regex.Pattern;
import handlers.MedicalCertificateHandler;
import handlers.PatientHandler;
import management.AppointmentManagement;  // Ensure this import is present

public class Patient extends User {

    private String contactInfo;
    private String contactNo;

    public Patient(String id, String name, String password, String dateOfBirth, String gender, String bloodType, String contactInfo, String contactNo) {
        super(id, name, password, "Patient", gender);
        this.contactInfo = contactInfo;
        this.contactNo = contactNo;
    }

    public String getContactInfo() {
        return contactInfo;
    }
    
    public String getContactNumber() {
        return contactNo;
    }

    public void updatePersonalInfo(String email) {
        if (isValid(email)) {
            this.contactInfo = email;
            System.out.println("\nContact information updated successfully.\n");
            PatientHandler.getInstance().saveAccount();
        } else {
            System.out.println("\nInvalid email address.\n");
        }
    }
    
    public void updateContactNo(String number) {
        if (number.matches("[0-9]{8}")) {
            this.contactNo = number;
            System.out.println("\nContact Number updated successfully.\n");
            PatientHandler.getInstance().saveAccount();
        } else {
            System.out.println("\nInvalid number.\n");
        }
    }

    public void viewMedicalRecord() {
        System.out.println("Medical Record for Patient ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Contact Info: " + getContactInfo());
        System.out.println("Contact Number: " + getContactNumber());
        System.out.println("");
    }

    public void requestMedicalCertificate(String reason, int duration) {
        // Check if the patient has had any past appointments before requesting an MC
        if (AppointmentManagement.hasPastAppointment(this)) {
            MedicalCertificate certificate = new MedicalCertificate(getId(), getName(), reason, duration);
            MedicalCertificateHandler.addCertificate(certificate);
            System.out.println("Medical certificate requested successfully.");
        } else {
            System.out.println("Error: You cannot request a medical certificate without any past appointments.");
        }
    }

    public void viewMedicalCertificates() {
        MedicalCertificateHandler.viewCertificatesForPatient(this);
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
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Request Medical Certificate");
        System.out.println("10. View Medical Certificates");
        System.out.println("11. View Billing Records");
        System.out.println("12. Logout");
    }
    
    public void displayPersonalInfoMenu() {
        System.out.println("1. Update Email Address");
        System.out.println("2. Update Phone Number");
        System.out.println("0. Exit");
    }
    
    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$"; 

        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) {
            return false; 
        }
        return pat.matcher(email).matches(); 
    }
}
