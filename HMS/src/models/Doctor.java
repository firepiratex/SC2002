package models;

import java.time.LocalTime;
import java.util.Scanner;

import handlers.AppointmentHandler;
import interfaces.DateAndTime;

public class Doctor extends User implements DateAndTime{
    private int age;

    public Doctor(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Doctor", gender);
        this.age = age;
    }

    public void setAvailability(Scanner scanner) {
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
        String[] row = {super.getId(), date, startTime, endTime};
        if (AppointmentHandler.getInstance().saveDoctorAvailability(row)) {
            System.out.println("Doctor's availability set for " + date + " from " + startTime + " to " + endTime);
        }
    }

    public void viewUpcomingAppointments() {
        AppointmentHandler.getInstance().viewUpcomingAppointment(this);
    }
    
    public int getAge() {
        return age;
    }

    @Override
    public void displayMenu() {
        System.out.println("Doctor Menu:");
        System.out.println("\n1. View Patient Medical Records\n"
                + "2. Update Patient Medical Records\n"
                + "3. View Personal Schedule\n"
                + "4. Set Availability for Appointments\n"
                + "5. Accept or Decline Appointment Requests\n"
                + "6. View Upcoming Appointments\n"
                + "7. Record Appointment Outcome\n"
                + "8. Logout\n");
    }
}
