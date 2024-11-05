package models;

import java.time.LocalDate;

public class MedicalCertificate {
    private String patientId;
    private String patientName;
    private String reason;
    private LocalDate issueDate;
    private int duration;  // Duration in days
    private String status; // Pending, Approved, Rejected
    private String approvedBy; // Doctor who approved/rejected

    // Constructor
    public MedicalCertificate(String patientId, String patientName, String reason, int duration) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.reason = reason;
        this.issueDate = LocalDate.now();  // Default to current date
        this.duration = duration;
        this.status = "Pending";  // Default status
        this.approvedBy = "N/A";  // Default value if not set
    }
    
    public MedicalCertificate(String patientId, String patientName, String reason, String date, int day, String status, String outcome) {
    	this.patientId = patientId;
        this.patientName = patientName;
        this.reason = reason;
        this.issueDate = LocalDate.parse(date);
        this.duration = day;
        this.status = status;
        this.approvedBy = outcome;
    }

    // Getters and setters
    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getReason() {
        return reason;
    }

    public int getDuration() {
        return duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    public String toString() {
    	return getPatientId() + " " + getPatientName() + " " + getReason() + " " + getDuration();
    }
    
    // Convert to CSV format
    public String[] toCSVRow() {
        return new String[] {
            patientId, patientName, reason, issueDate.toString(), String.valueOf(duration), status, approvedBy
        };
    }

    // Display certificate details
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
