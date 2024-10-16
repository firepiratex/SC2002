package management;

import java.util.*;
import models.Appointment;

public class AppointmentManagement {
    private List<Appointment> appointments;

    public AppointmentManagement() {
        this.appointments = new ArrayList<>();
        // Load appointments from CSV or another data source here
    }

     // Set doctor's availability for a particular time slot
     public void setDoctorAvailability(String doctorId, String date, String time) {
        String appointmentId = UUID.randomUUID().toString();
        Appointment newAvailability = new Appointment(appointmentId, doctorId, date, time);
        newAvailability.setStatus("Available");
        appointments.add(newAvailability);
        System.out.println("Doctor's availability set for " + date + " at " + time);
    }

    // View appointments for a specific doctor
    public void viewAppointments(String doctorId) {
        System.out.println("Appointments for Doctor ID: " + doctorId);
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorId().equals(doctorId) && appointment.getStatus().equals("Confirmed")) {
                System.out.println("Appointment ID: " + appointment.getAppointmentId() + 
                                   ", Date: " + appointment.getDate() + 
                                   ", Time: " + appointment.getTime() + 
                                   ", Status: " + appointment.getStatus());
            }
        }
    }

    public Appointment findAvailableSlot(String doctorId, String date, String time) {
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorId().equals(doctorId) && 
                appointment.getDate().equals(date) && 
                appointment.getTime().equals(time) && 
                appointment.getStatus().equals("Available")) {
                return appointment;
            }
        }
        return null;  // Return null if no available slot is found
    }

    public Appointment findAppointmentById(String appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return appointment;
            }
        }
        return null;  // Return null if no appointment is found
    }

    public void scheduleAppointment(String patientId, String doctorId, String date, String time) {
        Appointment appointment = findAvailableSlot(doctorId, date, time);
        if (appointment != null) {
            appointment.setPatientId(patientId);
            appointment.setStatus("Confirmed");
            System.out.println("Appointment successfully scheduled.");
        } else {
            System.out.println("Selected time slot is unavailable.");
        }
    }

    public void cancelAppointment(String appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setStatus("Cancelled");
            System.out.println("Appointment successfully cancelled.");
        } else {
            System.out.println("Appointment not found.");
        }
    }
     // View all appointments in the system
     public void viewAllAppointments() {
        System.out.println("All Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println("Appointment ID: " + appointment.getAppointmentId() +
                               ", Doctor ID: " + appointment.getDoctorId() +
                               ", Date: " + appointment.getDate() +
                               ", Time: " + appointment.getTime() +
                               ", Status: " + appointment.getStatus());
        }
    }
}
