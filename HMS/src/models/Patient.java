package models;

import java.util.regex.Pattern;

import handlers.MedicalCertificateHandler;

public class Patient extends User {

    private String contactInfo;

    public Patient(String id, String name, String password, String dateOfBirth, String gender, String bloodType, String contactInfo) {
        super(id, name, password, "Patient", gender);
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void updatePersonalInfo(String email) {
        if (isValid(email)) {
        	this.contactInfo = email;
        	System.out.println("\nContact information updated successfully.\n");
        } else {
        	System.out.println("\nInvalid email address.\n");
        }
    }

    public void viewMedicalRecord() {
        System.out.println("Medical Record for Patient ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Contact Info: " + getContactInfo());
    }

    public void requestMedicalCertificate(String reason, int duration) {
        MedicalCertificate certificate = new MedicalCertificate(getId(), getName(), reason, duration);
        MedicalCertificateHandler.addCertificate(certificate);
        System.out.println("Medical certificate requested successfully.");
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
        System.out.println("7. View Past Appointment Outcome Records");
        System.out.println("8. Request Medical Certificate");
        System.out.println("9. View Medical Certificates");
        System.out.println("10. View Billing Records");
        System.out.println("11. Logout");
    }
    
    public static boolean isValid(String email) {
    	String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
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
