package models;

import java.util.List;
import management.AppointmentManagement;

public class Doctor extends User {
    private AppointmentManagement appointmentManagement;
    private int age;

    public Doctor(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Doctor", gender);
        this.age = age;
        this.appointmentManagement = new AppointmentManagement();  // Initialize appointment management
    }

    public void setAvailability(String date, String time) {
        appointmentManagement.setDoctorAvailability(this.getId(), date, time);  // Call setDoctorAvailability method
    }

    public void viewUpcomingAppointments() {
        appointmentManagement.viewAppointments(this.getId());  // Call viewAppointments method
    }
    
    public int getAge() {
    	return age;
    }

    // Recording the outcome of the appointment
    public void recordAppointmentOutcome(Appointment appointment, String notes, List<String> prescriptions) {
        appointment.setOutcomeNotes(notes);  // Set outcome notes for the appointment
        appointment.setPrescriptions(prescriptions);  // Set prescriptions for the appointment
        appointment.setStatus("Completed");  // Mark appointment as completed
        System.out.println("Appointment outcome recorded successfully.");
    }

    @Override
    public void displayMenu() {
        System.out.println("Doctor Menu:");
        System.out.println("1. Set Availability");
        System.out.println("2. View Upcoming Appointments");
        System.out.println("3. Record Appointment Outcome");
        System.out.println("4. Logout");
    }
}
