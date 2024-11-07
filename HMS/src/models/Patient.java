package models;

import handlers.MedicalCertificateHandler;
import handlers.PatientHandler;
import java.util.regex.Pattern;
import management.AppointmentManagement;  // Ensure this import is present
/**
 * Represents a patient in the hospital management system.
 * This class provides methods for viewing and updating patient details, requesting medical certificates,
 * and viewing medical records. This class also extends to the User class
 */
public class Patient extends User {
	
	private String dateofBirth;
	private String bloodType;
    private String contactInfo;
    private String contactNo;
    /**
     * Constructs a Patient object with the given details.
     *
     * @param id          the ID of the patient
     * @param name        the name of the patient
     * @param password    the password of the patient
     * @param dateOfBirth the date of birth of the patient
     * @param gender      the gender of the patient
     * @param bloodType   the blood type of the patient
     * @param contactInfo the contact email of the patient
     * @param contactNo   the contact number of the patient
     */
    public Patient(String id, String name, String password, String dateOfBirth, String gender, String bloodType, String contactInfo, String contactNo) {
        super(id, name, password, "Patient", gender);
        this.dateofBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.contactNo = contactNo;
    }
    /**
     * Returns the contact email of the patient.
     *
     * @return the contact email
     */
    public String getContactInfo() {
        return contactInfo;
    }
    /**
     * Returns the contact number of the patient.
     *
     * @return the contact number
     */
    public String getContactNumber() {
        return contactNo;
    }
    /**
     * Returns the date of birth of the patient.
     *
     * @return the date of birth
     */
    public String getDateofBirth() {
		return dateofBirth;
	}
    /**
     * Returns the blood type of the patient.
     *
     * @return the blood type
     */
	public String getBloodType() {
		return bloodType;
	}
	/**
     * Sets the blood type of the patient.
     *
     * @param bloodType the new blood type to set
     */
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	/**
     * Updates the contact email of the patient after validation.
     *
     * @param email the new contact email to set
     */
	public void updatePersonalInfo(String email) {
        if (isValid(email)) {
            this.contactInfo = email;
            System.out.println("\nContact information updated successfully.\n");
            PatientHandler.getInstance().saveAccount();
        } else {
            System.out.println("\nInvalid email address.\n");
        }
    }
	/**
     * Updates the contact number of the patient after validation.
     *
     * @param number the new contact number to set
     */
    public void updateContactNo(String number) {
        if (number.matches("[0-9]{8}")) {
            this.contactNo = number;
            System.out.println("\nContact Number updated successfully.\n");
            PatientHandler.getInstance().saveAccount();
        } else {
            System.out.println("\nInvalid number.\n");
        }
    }
    /**
     * Displays the patient's medical record.
     */
    public void viewMedicalRecord() {
        System.out.println("Medical Record for Patient ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Date of Birth: " + getDateofBirth());
        System.out.println("Gender: " + super.getGender());
        System.out.println("Blood Type: " + getBloodType());
        System.out.println("Contact Info: " + getContactInfo());
        System.out.println("Contact Number: " + getContactNumber());
        System.out.println("");
    }
    /**
     * Requests a medical certificate for the patient if they have past appointments.
     *
     * @param reason   the reason for the medical certificate
     * @param duration the duration (in days) for which the certificate is valid
     */
    public void requestMedicalCertificate(String reason, int duration) {
        // Check if the patient has had any past appointments before requesting an MC
        if (AppointmentManagement.hasPastAppointment(this)) {
            MedicalCertificate certificate = new MedicalCertificate(getId(), getName(), reason, duration);
            MedicalCertificateHandler.getInstance().addCertificate(certificate);
            System.out.println("Medical certificate requested successfully.");
        } else {
            System.out.println("Error: You cannot request a medical certificate without any past appointments.");
        }
    }
    /**
     * Displays all medical certificates for the patient.
     */
    public void viewMedicalCertificates() {
        MedicalCertificateHandler.viewCertificatesForPatient(this);
    }
    /**
     * Displays the menu options for the patient.
     */
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
    /**
     * Displays the menu options for updating personal information.
     */
    public void displayPersonalInfoMenu() {
        System.out.println("1. Update Email Address");
        System.out.println("2. Update Phone Number");
        System.out.println("0. Exit");
    }
    /**
     * Validates the format of an email address.
     *
     * @param email the email address to validate
     * @return true if the email format is valid, false otherwise
     */
    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }
}
