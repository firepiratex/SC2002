package management;

import handlers.AppointmentHandler;
import handlers.StaffHandler;
import java.time.LocalDate;  // Ensure this class exists and has a method to get the appointment date
import java.util.List;
import java.util.Scanner;
import models.Appointment;
import models.Patient;
import models.User;

public class AppointmentManagement {

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

    public static void manageRescheduleAppointment(Scanner scanner, Patient patient) {
        AppointmentHandler.getInstance().rescheduleAppointment(patient, scanner);
    }

    public static void manageAppointment(Scanner scanner, Patient patient) {
        AppointmentHandler.getInstance().cancelAppointment(patient, scanner);
    }

    public static void manageAppointmentRequest(Scanner scanner, User doctor) {
        AppointmentHandler.getInstance().manageAppointment(scanner, doctor);
    }

    public static void recordAppointmentOutcome(Scanner scanner, User doctor) {
        AppointmentHandler.getInstance().recordAppointmentOutcome(scanner, doctor);
    }

    // New method to check if a patient has any past appointments
    public static boolean hasPastAppointment(Patient patient) {
        List<Appointment> pastAppointments = AppointmentHandler.getInstance().getAppointmentsForPatient(patient.getId());
    
        for (Appointment appointment : pastAppointments) {
            if (appointment.getOutcome().equals("Refer to Record")) {
                return true;  // Found a past appointment
            }
        }
        return false;  // No past appointments found
    }
}
