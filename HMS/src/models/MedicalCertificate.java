package models;

import java.time.LocalDate;
/**
 * Represents a medical certificate issued to a patient, containing details such as the reason,
 * duration, issue date, and approval status.
 */
public class MedicalCertificate {
    private String patientId;
    private String patientName;
    private String reason;
    private LocalDate issueDate;
    private int duration;  // Duration in days
    private String status; // Pending, Approved, Rejected
    private String approvedBy; // Doctor who approved/rejected
    /**
     * Constructs a MedicalCertificate object with the specified patient details, reason, and duration.
     * The issue date is set to the current date, the status is set to "Pending," and the approver is set to "N/A."
     *
     * @param patientId   the ID of the patient
     * @param patientName the name of the patient
     * @param reason      the reason for the medical certificate
     * @param duration    the duration in days for which the certificate is valid
     */
    public MedicalCertificate(String patientId, String patientName, String reason, int duration) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.reason = reason;
        this.issueDate = LocalDate.now();  // Default to current date
        this.duration = duration;
        this.status = "Pending";  // Default status
        this.approvedBy = "N/A";  // Default value if not set
    }
    /**
     * Another Constructor that constructs a MedicalCertificate with detailed parameters for existing records.
     *
     * @param patientId   the ID of the patient
     * @param patientName the name of the patient
     * @param reason      the reason for the medical certificate
     * @param date        the issue date of the certificate as a string
     * @param day         the duration in days for which the certificate is valid
     * @param status      the status of the certificate (e.g., Pending, Approved, Rejected)
     * @param outcome     the name of the doctor who approved or rejected the certificate
     */
    public MedicalCertificate(String patientId, String patientName, String reason, String date, int day, String status, String outcome) {
    	this.patientId = patientId;
        this.patientName = patientName;
        this.reason = reason;
        this.issueDate = LocalDate.parse(date);
        this.duration = day;
        this.status = status;
        this.approvedBy = outcome;
    }
    /**
     * Returns the issue date of the medical certificate.
     *
     * @return the issue date
     */
    public LocalDate getIssueDate() {
        return issueDate;
    }
    /**
     * Sets the issue date of the medical certificate.
     *
     * @param issueDate the issue date to set
     */
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    /**
     * Returns the patient ID associated with the certificate.
     *
     * @return the patient ID
     */
    public String getPatientId() {
        return patientId;
    }
    /**
     * Returns the patient name associated with the certificate.
     *
     * @return the patient name
     */
    public String getPatientName() {
        return patientName;
    }
    /**
     * Returns the reason for the issue of the certificate.
     *
     * @return the reason for issuing the certificate
     */
    public String getReason() {
        return reason;
    }
    /**
     * Returns the duration in days for which the certificate is valid.
     *
     * @return the duration in days
     */
    public int getDuration() {
        return duration;
    }
    /**
     * Returns the status of the certificate.
     *
     * @return the status (e.g., Pending, Approved, Rejected)
     */
    public String getStatus() {
        return status;
    }
    /**
     * Sets the status of the certificate.
     *
     * @param status the status to set (e.g., Pending, Approved, Rejected)
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Returns the name of the doctor who approved or rejected the certificate.
     *
     * @return the name of the approving/rejecting doctor
     */
    public String getApprovedBy() {
        return approvedBy;
    }
    /**
     * Sets the name of the doctor who approved or rejected the certificate.
     *
     * @param approvedBy the name of the doctor
     */
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    /**
     * Returns a string representation of the medical certificate.
     *
     * @return a string representation containing the patient ID, patient name, reason, and duration
     */
    @Override
    public String toString() {
    	return getPatientId() + " " + getPatientName() + " " + getReason() + " " + getDuration();
    }
    /**
     * Converts the certificate details to a format suitable for CSV storage.
     *
     * @return an array of strings representing the certificate's details
     */
    public String[] toCSVRow() {
        return new String[] {
            patientId, patientName, reason, issueDate.toString(), String.valueOf(duration), status, approvedBy
        };
    }
    /**
     * Displays the details of the medical certificate in a formatted manner.
     */
    public void displayCertificate() {
        System.out.println("---- Medical Certificate ----");
        System.out.println("Patient ID: " + patientId);
        System.out.println("Patient Name: " + patientName);
        System.out.println("Reason: " + reason);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("Duration: " + duration + " days");
        System.out.println("Status: " + status);
        System.out.println("Approved/Rejected By: " + approvedBy);
        System.out.println("-----------------------------");
    }
}
