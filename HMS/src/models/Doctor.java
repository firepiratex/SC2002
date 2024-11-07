package models;

import handlers.AppointmentHandler;
import handlers.MedicalCertificateHandler;
import interfaces.DateAndTime;
import java.time.LocalTime;
import java.util.Scanner;
/**
 * Represents a doctor in the system who can manage their schedule and view appointments,
 * and handle patient medical records and certificates. This class extends to User
 * and implements DateAndTime for date and time-related functionalities.
 */
public class Doctor extends User implements DateAndTime {

    private int age;
    /**
     * Constructs a Doctor object with specified attributes.
     *
     * @param id       the unique identifier of the doctor
     * @param name     the name of the doctor
     * @param password the password for the doctor's account
     * @param gender   the gender of the doctor
     * @param age      the age of the doctor
     */
    public Doctor(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Doctor", gender);
        this.age = age;
    }
    /**
     * Returns a string representation of the doctor, including basic user information and age.
     *
     * @return a string representation of the doctor
     */
    @Override
    public String toString() {
    	return super.toString() + " " + getAge();
    }
    /**
     * Allows the doctor to set their availability for appointments on a specified date.
     *
     * @param scanner a Scanner object for user input
     */
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
    /**
     * Views the upcoming appointments for the doctor.
     */
    public void viewUpcomingAppointments() {
        AppointmentHandler.getInstance().viewUpcomingAppointment(this);
    }
    /**
     * Views the doctor's personal schedule of confirmed appointments.
     */
    public void viewPersonalSchedule() {
    	AppointmentHandler.getInstance().viewPersonalSchedule(this);
    }
    /**
     * Retrieves the age of the doctor.
     *
     * @return the age of the doctor
     */
    public int getAge() {
        return age;
    }
    /**
     * Views the medical certificates of a specified patient.
     *
     * @param patient the patient whose medical certificates are to be viewed
     */
    public void viewPatientCertificates(Patient patient) {
        MedicalCertificateHandler.viewCertificatesForPatient(patient);
    }    
    /**
     * Displays the menu options available to the doctor.
     */
    @Override
    public void displayMenu() {
        System.out.println("Doctor Menu:");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. View Patient Medical Certificates");
        System.out.println("9. Approve/Reject Medical Certificates");
        System.out.println("10. Logout");
    }
}
