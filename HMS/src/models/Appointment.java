package models;

import java.util.List;

public class Appointment {
    private String appointmentId;
    private String doctorId;
    private String patientId;
    private String date;
    private String time;
    private String status;
    private String outcomeNotes;
    private List<String> prescriptions;

    // Modify the constructor to accept six parameters if needed
    public Appointment(String appointmentId, String doctorId, String patientId, String date, String time, String status) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    // Existing constructor with four parameters
    public Appointment(String appointmentId, String doctorId, String date, String time) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = "Available";  // Default status if not provided
    }

    // Getters
    public String getAppointmentId() {
        return appointmentId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public String getOutcomeNotes() {
        return outcomeNotes;
    }

    public List<String> getPrescriptions() {
        return prescriptions;
    }

    // Setters
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Define the missing setOutcomeNotes method
    public void setOutcomeNotes(String notes) {
        this.outcomeNotes = notes;
    }

    // Define the missing setPrescriptions method
    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
    }
    public void updateStatus(String status) {
        this.status = status;
    }
}
