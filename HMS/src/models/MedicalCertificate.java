package models;

import java.time.LocalDate;

public class MedicalCertificate {
    private String patientId;
    private String patientName;
    private String reason;
    private LocalDate issueDate;
    private int duration;  // Duration in days
    private String status; // Pending, Approved, Rejected

    // Constructor
    public MedicalCertificate(String patientId, String patientName, String reason, int duration) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.reason = reason;
        this.issueDate = LocalDate.now();  // Default to current date
        this.duration = duration;
        this.status = "Pending";  // Default status
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

    // Convert to CSV format
    public String[] toCSVRow() {
        return new String[] {
            patientId, patientName, reason, issueDate.toString(), String.valueOf(duration), status
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
        System.out.println("-----------------------------");
    }
}