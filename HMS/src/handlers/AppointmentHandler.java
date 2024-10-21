package handlers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import interfaces.DateAndTime;
import models.Appointment;
import models.Doctor;
import models.User;

public class AppointmentHandler implements DateAndTime{
	private static AppointmentHandler instance;
    private List<String> appointments;
    private List<String> timeList;
    private final String appointmentFile = "src/data/Appointment_Detail.csv";
    private final String doctorFile = "src/data/Doctor_Availability.csv";

    private AppointmentHandler() {
        this.appointments = new ArrayList<>();
        this.timeList = new ArrayList<>();
    }
    
    public static AppointmentHandler getInstance() {
    	if (instance == null) {
            instance = new AppointmentHandler();  // Initialize only when needed
        }
        return instance;
    }
    
    public void viewAvailableAppointment(User doctor, Scanner scanner) {
    	String date;
    	Boolean newTimeList = false;
    	List<String[]> data = CSVHandler.readCSV(doctorFile);
    	while (true) {
			System.out.print("Enter the date (DD/MM/YYYY): ");
			date = scanner.next();
			if (DateAndTime.dateChecker(date)) {
				break;
			}
			System.out.println("Incorrect date. Try again.");
		}
    	LocalTime beginning = LocalTime.parse("09:00");
		LocalTime ending = LocalTime.parse("17:00");
		timeList.add(beginning.toString());
		while (true) {
			beginning = beginning.plusHours(1);
			if (beginning.isAfter(ending)) {
				break;
			}
			timeList.add(beginning.toString());
		}
    	if (data.size() != 0) {
    		for(String[] line : data) {
    			if (doctor.getId().equals(line[0]) && date.equals(line[1])) {
    				timeList.clear();
    				beginning = LocalTime.parse(line[2]);
    				ending = LocalTime.parse(line[3]);
    				newTimeList = true;
    				break;
    			}
    		}
    	}
    	if (newTimeList) {
    		timeList.add(beginning.toString());
    		while (true) {
    			beginning = beginning.plusHours(1);
    			if (beginning.isAfter(ending)) {
    				break;
    			}
    			timeList.add(beginning.toString());
    		}
    	}
    	int j = 0;
		while(j < timeList.size()) {
			System.out.print((j+1) + ". " + timeList.get(j) + "\t");
			j++;
			if (j >= timeList.size()) {
				break;
			}
			System.out.println((j+1) + ". " + timeList.get(j));
			j++;
		}
		System.out.println("\n");
    }
    
    public boolean saveDoctorAvailability(String[] line) {
    	Boolean duplicateTime = false;
    	List<String[]> data = CSVHandler.readCSV(doctorFile);
    	if (data.size() != 0) {
	    	for (String[] eachRow : data) {
				if (line[0].equals(eachRow[0]) && line[1].equals(eachRow[1])) {
					LocalTime newStartTime = LocalTime.parse(line[2]);
			        LocalTime existingStartTime = LocalTime.parse(eachRow[2]);
			        LocalTime existingEndTime = LocalTime.parse(eachRow[3]);
			        if (newStartTime.compareTo(existingStartTime) >= 0 && newStartTime.compareTo(existingEndTime) < 0) {
			        	duplicateTime = true;
			        }
				}
			}
    	}
    	if (duplicateTime) {
			System.out.println("Time clashes with existing available time.\n");
			return false;
		} else {
			data.add(0, new String[] {"Doctor ID,Date,Start,End"});
			data.add(line);
			CSVHandler.writeCSV(doctorFile, data);
			return true;
		}
    }
    
    public void loadAppointments() {
        List<String[]> data = CSVHandler.readCSV(appointmentFile);
        for (String[] row : data) {
            appointments.add(row[0]);
        }
    }

    /*public void saveAppointments() {
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
    }*/
    
    
}
