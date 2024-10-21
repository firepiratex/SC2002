package management;

import handlers.AppointmentHandler;
import handlers.StaffHandler;
import interfaces.DateAndTime;
import java.time.LocalTime;
import java.util.*;
import models.Patient;
import models.User;

public class AppointmentManagement implements DateAndTime {

    public static void viewAvailableAppointment(Scanner scanner) {
        int choice, size;
        User doctor;
        size = StaffHandler.getInstance().displayDoctor();
        while (true) {
            System.out.print("Enter the doctor (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                } else if (choice >= 1 && choice <= size) {
                    doctor = StaffHandler.getInstance().getStaff(choice - 1);
                    break;
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
        AppointmentHandler.getInstance().viewAvailableAppointment(doctor, scanner);
    }

    public static void scheduleAppointment(Scanner scanner, Patient patient) {
        int choice, size;
        String[] line;
        User doctor;
        size = StaffHandler.getInstance().displayDoctor();
        while (true) {
            System.out.print("Enter the doctor (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                } else if (choice >= 1 && choice <= size) {
                    doctor = StaffHandler.getInstance().getStaff(choice - 1);
                    break;
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
        line = AppointmentHandler.getInstance().setAppointment(doctor, patient, scanner);
        if (line == null) {
            return;
        }
        line[0] = patient.getId();
        AppointmentHandler.getInstance().saveScheduledAppointment(line);
        System.out.println("Scheduled appointment successful.");
    }

    public static void manageRescheduleAppointment(Scanner scanner, Patient patient) {
        AppointmentHandler.getInstance().rescheduleAppointment(patient, scanner);
    }

    public static void setDoctorAvailability(Scanner scanner, User doctor) {
        String date, startTime, endTime;
        while (true) {
            System.out.print("Enter the date (DD/MM/YYYY): ");
            date = scanner.next();
            if (DateAndTime.dateChecker(date)) {
                break;
            }
            System.out.println("Incorrect date. Try again.");
        }
        while (true) {
            System.out.print("Enter the start time (00:00 - 23:59): ");
            startTime = scanner.next();
            if (DateAndTime.timeOfDay(startTime)) {
                if (DateAndTime.timeChecker(startTime)) {
                    break;
                }
            }
            System.out.println("Incorrect time. Try again.");
        }
        while (true) {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime newStartTime = start.plusMinutes(1);
            System.out.print("Enter the end time (" + newStartTime + " - 23:59): ");
            endTime = scanner.next();
            if (DateAndTime.timeOfDay(endTime)) {
                if (DateAndTime.timeChecker(startTime, endTime)) {
                    break;
                }
            }
            System.out.println("Incorrect time. Try again.");
        }
        String[] row = {doctor.getId(), date, startTime, endTime};
        if (AppointmentHandler.getInstance().saveDoctorAvailability(row)) {
            System.out.println("Doctor's availability set for " + date + " from " + startTime + " to " + endTime);
        }
    }

    /*
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
	}*/
}
