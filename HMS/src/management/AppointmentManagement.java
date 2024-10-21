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
        System.out.println("Scheduled appointment successful.\n");
    }

    public static void manageRescheduleAppointment(Scanner scanner, Patient patient) {
        AppointmentHandler.getInstance().rescheduleAppointment(patient, scanner);
    }
    
    public static void manageAppointment(Scanner scanner, Patient patient) {
    	AppointmentHandler.getInstance().cancelAppointment(patient, scanner);
    }
    
    public static void manageAppointmentRequest(Scanner scanner, User doctor) {
    	AppointmentHandler.getInstance().manageAppointment(scanner, doctor);
    }
    
    public static void viewUpcomingAppointment(User doctor) {
    	AppointmentHandler.getInstance().viewUpcomingAppointment(doctor);
    }
    
    public static void recordAppointmentOutcome(Scanner scanner, User doctor) {
    	AppointmentHandler.getInstance().recordAppointmentOutcome(scanner, doctor);
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
}
