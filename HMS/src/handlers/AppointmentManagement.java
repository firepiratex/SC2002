package handlers;

import java.util.*;
import models.Appointment;

public class AppointmentManagement {
    private List<Appointment> appointments;

    public AppointmentManagement() {
        this.appointments = new ArrayList<>();
        loadAppointments();
    }

    public void loadAppointments() {
        // Load appointments from a data source (this is a simulation)
        // You might load this from a database or file
    }

    public void viewAllAppointments() {
        System.out.println("Hospital Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println("Appointment ID: " + appointment.getAppointmentId() + ", Doctor ID: " + appointment.getDoctorId()
                    + ", Date: " + appointment.getDate() + ", Time: " + appointment.getTime()
                    + ", Status: " + appointment.getStatus());
        }
    }
}
