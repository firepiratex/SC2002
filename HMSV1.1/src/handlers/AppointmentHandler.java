package handlers;

import java.util.*;
import models.Appointment;

public class AppointmentHandler {
    private List<Appointment> appointments;
    private final String appointmentFile = "data/Appointment_Detail.csv";

    public AppointmentHandler() {
        this.appointments = new ArrayList<>();
        loadAppointments();
    }

    public void loadAppointments() {
        List<String[]> data = CSVHandler.readCSV(appointmentFile);
        for (String[] row : data) {
            String appointmentId = row[0];
            String patientId = row[1];
            String doctorId = row[2];
            String date = row[3];
            String time = row[4];
            String status = row[5];
            appointments.add(new Appointment(appointmentId, patientId, doctorId, date, time, status));
        }
    }

    public void saveAppointments() {
        List<String[]> data = new ArrayList<>();
        for (Appointment appointment : appointments) {
            String[] row = {appointment.getAppointmentId(), appointment.getPatientId(), appointment.getDoctorId(), appointment.getDate(), appointment.getTime(), appointment.getStatus()};
            data.add(row);
        }
        CSVHandler.writeCSV(appointmentFile, data);
    }

    public List<Appointment> getAppointmentsForUser(String userId) {
        List<Appointment> userAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientId().equals(userId) || appointment.getDoctorId().equals(userId)) {
                userAppointments.add(appointment);
            }
        }
        return userAppointments;
    }

    public void cancelAppointment(String appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                appointment.updateStatus("Cancelled");
            }
        }
    }
}
