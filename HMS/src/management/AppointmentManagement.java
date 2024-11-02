package management;

import handlers.AppointmentHandler;
import handlers.StaffHandler;
import java.util.*;

import models.Patient;
import models.User;

public class AppointmentManagement{

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
}
