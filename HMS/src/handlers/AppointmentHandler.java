package handlers;

import interfaces.DateAndTime;
import java.time.LocalTime;
import java.util.*;
import models.Patient;
import models.User;

public class AppointmentHandler implements DateAndTime {

    private static AppointmentHandler instance;
    private List<String> timeList;
    private final String appointmentFile = "src/data/Appointment_Detail.csv";
    private final String appointmentLogFile = "src/data/Appointment_Log.csv";
    private final String appointmentOutcomeFile = "src/data/Appointment_Outcome_Record.csv";
    private final String doctorFile = "src/data/Doctor_Availability.csv";

    private AppointmentHandler() {
        this.timeList = new ArrayList<>();
    }

    public static AppointmentHandler getInstance() {
        if (instance == null) {
            instance = new AppointmentHandler();
        }
        return instance;
    }

    public void viewAvailableAppointment(User doctor, Scanner scanner) {
        timeList.clear();
        String date, startTime = "09:00", endTime = "17:00";
        List<String[]> doctorSchedule = CSVHandler.readCSV(doctorFile);
        List<String[]> appointmentSchedule = CSVHandler.readCSV(appointmentFile);
        List<String> appointmentList = new ArrayList<>();
        while (true) {
            System.out.print("Enter the date (DD/MM/YYYY): ");
            date = scanner.next();
            if (DateAndTime.dateChecker(date)) {
                break;
            }
            System.out.println("Incorrect date. Try again.");
        }
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            if (doctor.getId().equals(appointmentSchedule.get(i)[1]) && date.equals(appointmentSchedule.get(i)[3])) {
                appointmentList.add(appointmentSchedule.get(i)[4]);
            }
        }
        for (int i = 0; i < doctorSchedule.size(); i++) {
            if (doctor.getId().equals(doctorSchedule.get(i)[0]) && date.equals(doctorSchedule.get(i)[1])) {
                startTime = doctorSchedule.get(i)[2];
                endTime = doctorSchedule.get(i)[3];
                break;
            }
        }
        LocalTime beginning = LocalTime.parse(startTime);
        LocalTime ending = LocalTime.parse(endTime);
        timeList.add(beginning.toString());
        while (true) {
            beginning = beginning.plusHours(1);
            if (beginning.isAfter(ending)) {
                break;
            }
            timeList.add(beginning.toString());
        }
        timeList.removeAll(appointmentList);
        int j = 0;
        while (j < timeList.size()) {
            System.out.print((j + 1) + ". " + timeList.get(j) + "\t");
            j++;
            if (j >= timeList.size()) {
                break;
            }
            System.out.println((j + 1) + ". " + timeList.get(j));
            j++;
        }
        System.out.println("\n");
    }

    public void viewUpcomingAppointment(User doctor) {
        List<String[]> appointmentSchedule = CSVHandler.readCSV(appointmentFile);
        List<String[]> doctorSchedule = new ArrayList<>();
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            String doctorID = appointmentSchedule.get(i)[1];
            String status = appointmentSchedule.get(i)[2];
            if (doctor.getId().equals(doctorID) && status.equals("Confirmed")) {
                doctorSchedule.add(appointmentSchedule.get(i));
            }
        }
        if (doctorSchedule.size() == 0) {
            System.out.println("You have no appointments\n");
        } else {
            System.out.println("----" + doctor.getName() + "'s Schedule----");
            for (int i = 0; i < doctorSchedule.size(); i++) {
                System.out.println((i + 1) + ". " + Arrays.toString(doctorSchedule.get(i)));
            }
        }
    }

    public String[] setAppointment(User doctor, Patient patient, Scanner scanner) {
        timeList.clear();
        int choice;
        String time, date, startTime = "09:00", endTime = "17:00";
        List<String[]> doctorSchedule = CSVHandler.readCSV(doctorFile);
        List<String[]> appointmentSchedule = CSVHandler.readCSV(appointmentFile);
        List<String> appointmentList = new ArrayList<>();
        List<String> patientScheduleList = new ArrayList<>();
        while (true) {
            System.out.print("Enter the date (DD/MM/YYYY): ");
            date = scanner.next();
            if (DateAndTime.dateChecker(date)) {
                break;
            }
            System.out.println("Incorrect date. Try again.");
        }
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            if (doctor.getId().equals(appointmentSchedule.get(i)[1]) && date.equals(appointmentSchedule.get(i)[3])) {
                appointmentList.add(appointmentSchedule.get(i)[4]);
            }
        }
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            if (patient.getId().equals(appointmentSchedule.get(i)[0]) && date.equals(appointmentSchedule.get(i)[3])) {
                patientScheduleList.add(appointmentSchedule.get(i)[4]);
            }
        }
        for (int i = 0; i < doctorSchedule.size(); i++) {
            if (doctor.getId().equals(doctorSchedule.get(i)[0]) && date.equals(doctorSchedule.get(i)[1])) {
                startTime = doctorSchedule.get(i)[2];
                endTime = doctorSchedule.get(i)[3];
                break;
            }
        }
        LocalTime beginning = LocalTime.parse(startTime);
        LocalTime ending = LocalTime.parse(endTime);
        timeList.add(beginning.toString());
        while (true) {
            beginning = beginning.plusHours(1);
            if (beginning.isAfter(ending)) {
                break;
            }
            timeList.add(beginning.toString());
        }
        timeList.removeAll(appointmentList);
        timeList.removeAll(patientScheduleList);
        if (timeList.size() == 0) {
            System.out.println("No available timeslot.\n");
            return null;
        }
        int j = 0;
        while (j < timeList.size()) {
            System.out.print((j + 1) + ". " + timeList.get(j) + "\t");
            j++;
            if (j >= timeList.size()) {
                break;
            }
            System.out.println((j + 1) + ". " + timeList.get(j));
            j++;
        }
        System.out.println("\n");
        while (true) {
            System.out.print("Enter the slot (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return null;
                } else if (choice >= 1 && choice <= timeList.size()) {
                    time = timeList.get(choice - 1);
                    break;
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
        return new String[]{"Patient ID", doctor.getId(), "Pending", date, time, "-"};
    }

    public void setAppointment(User doctor, Patient patient, Scanner scanner, String[] existingAppointment) {
        timeList.clear();
        int choice;
        String time, date, startTime = "09:00", endTime = "17:00";
        String[] row;
        List<String[]> doctorSchedule = CSVHandler.readCSV(doctorFile);
        List<String[]> appointmentSchedule = CSVHandler.readCSV(appointmentFile);
        List<String[]> appointmentLogList = CSVHandler.readCSV(appointmentLogFile);
        List<String> appointmentList = new ArrayList<>();
        List<String> patientScheduleList = new ArrayList<>();
        while (true) {
            System.out.print("Enter the date (DD/MM/YYYY): ");
            date = scanner.next();
            if (DateAndTime.dateChecker(date)) {
                break;
            }
            System.out.println("Incorrect date. Try again.");
        }
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            if (doctor.getId().equals(appointmentSchedule.get(i)[1]) && date.equals(appointmentSchedule.get(i)[3])) {
                appointmentList.add(appointmentSchedule.get(i)[4]);
            }
        }
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            if (patient.getId().equals(appointmentSchedule.get(i)[0]) && date.equals(appointmentSchedule.get(i)[3])) {
                patientScheduleList.add(appointmentSchedule.get(i)[4]);
            }
        }
        for (int i = 0; i < doctorSchedule.size(); i++) {
            if (doctor.getId().equals(doctorSchedule.get(i)[0]) && date.equals(doctorSchedule.get(i)[1])) {
                startTime = doctorSchedule.get(i)[2];
                endTime = doctorSchedule.get(i)[3];
                break;
            }
        }
        LocalTime beginning = LocalTime.parse(startTime);
        LocalTime ending = LocalTime.parse(endTime);
        timeList.add(beginning.toString());
        while (true) {
            beginning = beginning.plusHours(1);
            if (beginning.isAfter(ending)) {
                break;
            }
            timeList.add(beginning.toString());
        }
        timeList.removeAll(appointmentList);
        timeList.removeAll(patientScheduleList);
        if (timeList.size() == 0) {
            System.out.println("No available timeslot.\n");
            return;
        }
        int j = 0;
        while (j < timeList.size()) {
            System.out.print((j + 1) + ". " + timeList.get(j) + "\t");
            j++;
            if (j >= timeList.size()) {
                break;
            }
            System.out.println((j + 1) + ". " + timeList.get(j));
            j++;
        }
        System.out.println("\n");
        while (true) {
            System.out.print("Enter the slot (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                } else if (choice >= 1 && choice <= timeList.size()) {
                    time = timeList.get(choice - 1);
                    row = new String[]{patient.getId(), doctor.getId(), "Pending", date, time, "-"};
                    for (int i = 0; i < appointmentSchedule.size(); i++) {
                        String appointment = Arrays.toString(appointmentSchedule.get(i));
                        String existing = Arrays.toString(existingAppointment);
                        if (appointment.equals(existing)) {
                            appointmentSchedule.remove(i);
                        }
                    }
                    break;
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
        appointmentLogList.add(row);
        appointmentLogList.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        CSVHandler.writeCSV(appointmentLogFile, appointmentLogList);
        appointmentSchedule.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        appointmentSchedule.add(row);
        CSVHandler.writeCSV(appointmentFile, appointmentSchedule);
    }

    public void rescheduleAppointment(Patient patient, Scanner scanner) {
        int choice, choice2, size;
        User doctor;
        String doctorID, status, date, time;
        List<String[]> appointmentSchedule = CSVHandler.readCSV(appointmentFile);
        List<String[]> patientExistingAppointment = new ArrayList<>();
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            if (appointmentSchedule.get(i)[0].equals(patient.getId()) && !appointmentSchedule.get(i)[2].equals("Canceled") && appointmentSchedule.get(i)[5].equals("-")) {
                patientExistingAppointment.add(appointmentSchedule.get(i));
            }
        }
        if (patientExistingAppointment.size() == 0) {
            System.out.println("You have no existing Appointment.");
            return;
        }
        System.out.println("----Existing Appointment----");
        for (int i = 0; i < patientExistingAppointment.size(); i++) {
            doctorID = patientExistingAppointment.get(i)[1];
            status = patientExistingAppointment.get(i)[2];
            date = patientExistingAppointment.get(i)[3];
            time = patientExistingAppointment.get(i)[4];
            System.out.println((i + 1) + ". " + StaffHandler.getInstance().findStaffById(doctorID).getName() + " " + date + " " + time + " (" + status + ")");
        }
        while (true) {
            System.out.print("\nEnter the appointment you want to reschedule (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                }
                if (choice >= 1 && choice <= patientExistingAppointment.size()) {
                    size = StaffHandler.getInstance().displayDoctor();
                    while (true) {
                        System.out.print("Enter the doctor (0 to exit): ");
                        if (scanner.hasNextInt()) {
                            choice2 = scanner.nextInt();
                            if (choice2 == 0) {
                                return;
                            } else if (choice2 >= 1 && choice2 <= size) {
                                doctor = StaffHandler.getInstance().getStaff(choice2 - 1);
                                break;
                            } else {
                                System.out.println("Invalid option. Try again.");
                            }
                        } else {
                            System.out.println("Invalid input. Try again.");
                        }
                    }
                    setAppointment(doctor, patient, scanner, patientExistingAppointment.get(choice - 1));
                    break;
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next();
            }
        }
    }

    public void cancelAppointment(Patient patient, Scanner scanner) {
        int choice;
        String doctorID, status, date, time;
        List<String[]> appointmentSchedule = CSVHandler.readCSV(appointmentFile);
        List<String[]> appointmentLogList = CSVHandler.readCSV(appointmentLogFile);
        List<String[]> patientExistingAppointment = new ArrayList<>();
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            if (appointmentSchedule.get(i)[0].equals(patient.getId()) && !appointmentSchedule.get(i)[2].equals("Canceled") && appointmentSchedule.get(i)[5].equals("-")) {
                patientExistingAppointment.add(appointmentSchedule.get(i));
            }
        }
        if (patientExistingAppointment.size() == 0) {
            System.out.println("You have no existing Appointment.");
            return;
        }
        System.out.println("----Existing Appointment----");
        for (int i = 0; i < patientExistingAppointment.size(); i++) {
            doctorID = patientExistingAppointment.get(i)[1];
            status = patientExistingAppointment.get(i)[2];
            date = patientExistingAppointment.get(i)[3];
            time = patientExistingAppointment.get(i)[4];
            System.out.println((i + 1) + ". " + StaffHandler.getInstance().findStaffById(doctorID).getName() + " " + date + " " + time + " (" + status + ")");
        }
        while (true) {
            System.out.print("\nEnter the appointment you want to cancel (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                }
                if (choice >= 1 && choice <= patientExistingAppointment.size()) {
                    String cancel = Arrays.toString(patientExistingAppointment.get(choice - 1));
                    String[] parts = patientExistingAppointment.get(choice - 1);
                    for (int i = 0; i < appointmentSchedule.size(); i++) {
                        String appointment = Arrays.toString(appointmentSchedule.get(i));
                        if (appointment.equals(cancel)) {
                            appointmentSchedule.remove(i);
                        }
                    }
                    appointmentLogList.add(new String[]{parts[0], parts[1], "Canceled(Patient)", parts[3], parts[4], parts[5]});
                    appointmentLogList.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
                    CSVHandler.writeCSV(appointmentLogFile, appointmentLogList);
                    System.out.println("You have cancelled the appointment.");
                    appointmentSchedule.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
                    CSVHandler.writeCSV(appointmentFile, appointmentSchedule);
                    break;
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next();
            }
        }
    }

    public void manageAppointment(Scanner scanner, User doctor) {
        List<String[]> appointmentSchedule = CSVHandler.readCSV(appointmentFile);
        List<String[]> appointmentLogList = CSVHandler.readCSV(appointmentLogFile);
        List<String[]> doctorSchedule = new ArrayList<>();
        int choice;
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            String doctorID = appointmentSchedule.get(i)[1];
            if (doctor.getId().equals(doctorID)) {
                doctorSchedule.add(appointmentSchedule.get(i));
            }
        }
        if (doctorSchedule.size() == 0) {
            System.out.println("You have no appointments\n");
            return;
        } else {
            System.out.println("----" + doctor.getName() + "'s Schedule----");
            for (int i = 0; i < doctorSchedule.size(); i++) {
                System.out.println((i + 1) + ". " + Arrays.toString(doctorSchedule.get(i)));
            }
        }
        while (true) {
            System.out.print("\nChoose the appointment you want to manage (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                }
                if (choice >= 1 && choice <= doctorSchedule.size()) {
                    String[] parts = doctorSchedule.get(choice - 1);
                    if (parts[2].equals("Confirmed") || parts[2].equals("Canceled")) {
                        System.out.println("Appointment already accepted or declined");
                        return;
                    }
                    System.out.print("Accept or Decline (0 to exit): ");
                    String status = scanner.next();
                    if (status.equals("0")) {
                        return;
                    } else if (status.toLowerCase().equals("accept")) {
                        for (int i = 0; i < appointmentSchedule.size(); i++) {
                            String appointmentScheduleString = Arrays.toString(appointmentSchedule.get(i));
                            String doctorScheduleString = Arrays.toString(doctorSchedule.get(choice - 1));
                            if (appointmentScheduleString.equals(doctorScheduleString)) {
                                appointmentSchedule.set(i, new String[]{parts[0], parts[1], "Confirmed", parts[3], parts[4], parts[5]});
                                appointmentLogList.add(new String[]{parts[0], parts[1], "Confirmed", parts[3], parts[4], parts[5]});
                                break;
                            }
                        }
                        break;
                    } else if (status.toLowerCase().equals("decline")) {
                        for (int i = 0; i < appointmentSchedule.size(); i++) {
                            String appointmentScheduleString = Arrays.toString(appointmentSchedule.get(i));
                            String doctorScheduleString = Arrays.toString(doctorSchedule.get(choice - 1));
                            if (appointmentScheduleString.equals(doctorScheduleString)) {
                                appointmentSchedule.set(i, new String[]{parts[0], parts[1], "Canceled", parts[3], parts[4], parts[5]});
                                appointmentLogList.add(new String[]{parts[0], parts[1], "Canceled", parts[3], parts[4], parts[5]});
                                break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("Invalid input. Try again");
                    }
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next();
            }
        }
        appointmentLogList.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        CSVHandler.writeCSV(appointmentLogFile, appointmentLogList);
        appointmentSchedule.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        CSVHandler.writeCSV(appointmentFile, appointmentSchedule);
    }

    public void recordAppointmentOutcome(Scanner scanner, User doctor) {
        List<String[]> appointmentSchedule = CSVHandler.readCSV(appointmentFile);
        List<String[]> recordList = CSVHandler.readCSV(appointmentOutcomeFile);
        List<String[]> appointmentLogList = CSVHandler.readCSV(appointmentLogFile);
        List<String[]> doctorSchedule = new ArrayList<>();
        int choice;
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            String doctorID = appointmentSchedule.get(i)[1];
            String status = appointmentSchedule.get(i)[2];
            String outcome = appointmentSchedule.get(i)[5];
            if (doctor.getId().equals(doctorID) && status.equals("Confirmed") && outcome.equals("-")) {
                doctorSchedule.add(appointmentSchedule.get(i));
            }
        }
        if (doctorSchedule.size() == 0) {
            System.out.println("You have no confirmed appointments\n");
            return;
        } else {
            System.out.println("----" + doctor.getName() + "'s Confirmed Schedule----");
            for (int i = 0; i < doctorSchedule.size(); i++) {
                System.out.println((i + 1) + ". " + Arrays.toString(doctorSchedule.get(i)));
            }
        }
        while (true) {
            System.out.print("\nChoose the appointment you want to record (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                } else if (choice >= 1 && choice <= doctorSchedule.size()) {
                    String[] parts = doctorSchedule.get(choice - 1);
                    for (int i = 0; i < appointmentSchedule.size(); i++) {
                        String appointmentScheduleString = Arrays.toString(appointmentSchedule.get(i));
                        String doctorScheduleString = Arrays.toString(doctorSchedule.get(choice - 1));
                        if (appointmentScheduleString.equals(doctorScheduleString)) {
                            appointmentSchedule.set(i, new String[]{parts[0], parts[1], "Confirmed", parts[3], parts[4], "Refer to Record"});
                            appointmentLogList.add(new String[]{parts[0], parts[1], "Confirmed", parts[3], parts[4], "Refer to Record"});
                            System.out.print("Type of service provided: ");
                            scanner.nextLine();
                            String service = scanner.nextLine();
                            System.out.print("Prescribed Medications (default is pending): ");
                            String medication = scanner.nextLine();
                            System.out.print("Consultation Notes: ");
                            String notes = scanner.nextLine();
                            recordList.add(new String[]{parts[1], parts[0], parts[3], service, medication, notes});
                            System.out.println("Record successfully.");
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next();
            }
        }
        appointmentLogList.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        CSVHandler.writeCSV(appointmentLogFile, appointmentLogList);
        appointmentSchedule.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        CSVHandler.writeCSV(appointmentFile, appointmentSchedule);
        recordList.add(0, new String[]{"Doctor ID,Patient ID,Date,Type of Service, Prescribed Medications, Consultation Notes"});
        CSVHandler.writeCSV(appointmentOutcomeFile, recordList);
    }

    public boolean saveDoctorAvailability(String[] line) {
        Boolean duplicateTime = false;
        List<String[]> data = CSVHandler.readCSV(doctorFile);
        for (int i = 0; i < data.size(); i++) {
            if (line[0].equals(data.get(i)[0]) && line[1].equals(data.get(i)[1])) {
                LocalTime newStartTime = LocalTime.parse(line[2]);
                LocalTime existingStartTime = LocalTime.parse(data.get(i)[2]);
                LocalTime existingEndTime = LocalTime.parse(data.get(i)[3]);
                if (newStartTime.compareTo(existingStartTime) >= 0 && newStartTime.compareTo(existingEndTime) < 0) {
                    duplicateTime = true;
                }
            }
        }
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
            data.add(0, new String[]{"Doctor ID,Date,Start,End"});
            data.add(line);
            CSVHandler.writeCSV(doctorFile, data);
            return true;
        }
    }

    public void saveScheduledAppointment(String[] line) {
        List<String[]> appointmentLogList = CSVHandler.readCSV(appointmentLogFile);
        List<String[]> data = CSVHandler.readCSV(appointmentFile);
        data.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        data.add(line);
        appointmentLogList.add(line);
        appointmentLogList.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        CSVHandler.writeCSV(appointmentLogFile, appointmentLogList);
        CSVHandler.writeCSV(appointmentFile, data);
    }

}
