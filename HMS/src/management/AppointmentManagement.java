package management;

import handlers.AppointmentHandler;
import handlers.StaffHandler;
import java.util.List;
import java.util.Scanner;
import models.Appointment;
import models.Patient;
import models.User;
/**
 * Provides various methods for managing appointments and handles between the interaction between the user and system
 * This class interacts with AppointmentHandler and StaffHandler for functionality.
 */
public class AppointmentManagement {
	/**
	 * Views available appointment slots for a selected doctor.
	 *
	 * @param scanner a Scanner object for user input
	 */
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
    /**
     * Views scheduled appointments for a given patient.
     *
     * @param patient the patient whose scheduled appointments are to be viewed
     */
    public static void viewScheduledAppointment(Patient patient) {
    	AppointmentHandler.getInstance().viewScheduledAppointment(patient);
    }
    /**
     * Schedules a new appointment for the given patient with a selected doctor.
     *
     * @param scanner a Scanner object for user input
     * @param patient the patient for whom the appointment is being scheduled
     */
    public static void scheduleAppointment(Scanner scanner, Patient patient) {
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
            scanner.nextLine();
        }
        AppointmentHandler.getInstance().setAppointment(doctor, patient, scanner);
        System.out.println("Scheduled appointment successful.\n");
    }
    /**
     * Reschedules an existing appointment for a given patient.
     *
     * @param scanner a Scanner object for user input
     * @param patient the patient whose appointment is to be rescheduled
     */
    public static void rescheduleAppointment(Scanner scanner, Patient patient) {
        AppointmentHandler.getInstance().rescheduleAppointment(patient, scanner);
    }
    /**
     * Cancels an existing appointment for a given patient.
     *
     * @param scanner a Scanner object for user input
     * @param patient the patient whose appointment is to be canceled
     */
    public static void cancelAppointment(Scanner scanner, Patient patient) {
        AppointmentHandler.getInstance().cancelAppointment(patient, scanner);
    }
    /**
     * Manages appointment requests for a doctor, allowing them to accept or decline.
     *
     * @param scanner a Scanner object for user input
     * @param doctor  the doctor managing their appointment requests
     */
    public static void manageAppointmentRequest(Scanner scanner, User doctor) {
        AppointmentHandler.getInstance().manageAppointment(scanner, doctor);
    }
    /**
     * Records the outcome of a completed appointment for a given doctor.
     *
     * @param scanner a Scanner object for user input
     * @param doctor  the doctor recording the outcome of the appointment
     */
    public static void recordAppointmentOutcome(Scanner scanner, User doctor) {
        AppointmentHandler.getInstance().recordAppointmentOutcome(scanner, doctor);
    }
    /**
     * Checks if a given patient has any past appointments with outcomes recorded.
     *
     * @param patient the patient whose appointment history is being checked
     * @return true if the patient has past appointments with recorded outcomes, false otherwise
     */
    public static boolean hasPastAppointment(Patient patient) {
        List<Appointment> pastAppointments = AppointmentHandler.getInstance().getAppointmentsForPatient(patient.getId());
    
        for (Appointment appointment : pastAppointments) {
            if (appointment.getOutcome().equals("Refer to Record")) {
                return true;
            }
        }
        return false;
    }
}
