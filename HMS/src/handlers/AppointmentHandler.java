package handlers;

import interfaces.DateAndTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.Appointment;
import models.Patient;
import models.User;
/**
 * Handles the management of appointments in the system, including scheduling, rescheduling, viewing, recording,
 * and canceling appointments. This class implements the DateAndTime interface for date validation.
 */
public class AppointmentHandler implements DateAndTime {

    private static AppointmentHandler instance;
    private List<String> timeList;
    private List<Appointment> appointments;
    private List<String[]> appointmentLogList;
    private final String appointmentFile = "./src/data/Appointment_Detail.csv";
    private static final String appointmentLogFile = "./src/data/Appointment_Log.csv";
    private final String appointmentOutcomeFile = "./src/data/Appointment_Outcome_Record.csv";
    private final String doctorFile = "./src/data/Doctor_Availability.csv";
    private String startTime;
    private String endTime;
    private String time;
    private String date;
    private int choice;
    /**
     * Private constructor to enforce singleton pattern and initialize appointments and appointment logs.
     */
    private AppointmentHandler() {
        this.timeList = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.appointmentLogList = CSVHandler.readCSV(appointmentLogFile);
        loadAppointment();
    }
    /**
     * Loads appointments from the CSV file and store the list of appointments as Appointment object.
     */
    private void loadAppointment() {
        List<String[]> appointmentSchedule = CSVHandler.readCSV(appointmentFile);
        for (int i = 0; i < appointmentSchedule.size(); i++) {
            String patientID = appointmentSchedule.get(i)[0];
            String doctorID = appointmentSchedule.get(i)[1];
            String status = appointmentSchedule.get(i)[2];
            String date = appointmentSchedule.get(i)[3];
            String time = appointmentSchedule.get(i)[4];
            String outcome = appointmentSchedule.get(i)[5];

            Appointment appointment = null;
            appointment = new Appointment(patientID, doctorID, status, date, time, outcome);

            appointments.add(appointment);
        }
    }
    /**
     * Saves the current state of appointments to a CSV file, sorted by date and time.
     */
    private void saveAppointment() {
        List<String[]> data = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        appointments.sort((s1,s2) -> {
        	LocalDate date1 = LocalDate.parse(s1.getDate(), dateFormatter);
            LocalDate date2 = LocalDate.parse(s2.getDate(), dateFormatter);
            int dateComparison = date1.compareTo(date2);

            if (dateComparison != 0) {
                return dateComparison;
            }

            LocalTime time1 = LocalTime.parse(s1.getTime());
            LocalTime time2 = LocalTime.parse(s2.getTime());
            return time1.compareTo(time2);
        });
        for (int i = 0; i < appointments.size(); i++) {
            String patientID = appointments.get(i).getPatientId();
            String doctorID = appointments.get(i).getDoctorId();
            String status = appointments.get(i).getStatus();
            String date = appointments.get(i).getDate();
            String time = appointments.get(i).getTime();
            String outcome = appointments.get(i).getOutcome();

            data.add(new String[]{patientID, doctorID, status, date, time, outcome});
        }
        data.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        CSVHandler.writeCSV(appointmentFile, data);
    }
    /**
     * Returns a singleton instance of AppointmentHandler. 
     * Initializing the default startTime and endTime, also clearing the timeList list too.
     *
     * @return the singleton instance
     */
    public static AppointmentHandler getInstance() {
        if (instance == null) {
            instance = new AppointmentHandler();
        }
        instance.startTime = "09:00";
        instance.endTime = "17:00";
        instance.timeList.clear();
        instance.appointmentLogList = CSVHandler.readCSV(appointmentLogFile);
        return instance;
    }
    /**
     * Displays available appointment slots for a given doctor on a specified date.
     *
     * @param doctor the doctor whose availability is being checked
     * @param scanner a Scanner object for user input
     */
    public void viewAvailableAppointment(User doctor, Scanner scanner) {
        List<String[]> doctorSchedule = CSVHandler.readCSV(doctorFile);
        List<String> appointmentList = new ArrayList<>();
        while (true) {
            System.out.print("Enter the date (DD/MM/YYYY): ");
            date = scanner.next();
            if (DateAndTime.dateChecker(date)) {
                break;
            }
            System.out.println("Incorrect date. Try again.");
        }
        for (int i = 0; i < appointments.size(); i++) {
            if (doctor.getId().equals(appointments.get(i).getDoctorId()) && date.equals(appointments.get(i).getDate())) {
                appointmentList.add(appointments.get(i).getTime());
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
    /**
     * Displays the personal schedule for a given doctor, showing confirmed appointments.
     *
     * @param doctor the doctor whose schedule is to be viewed
     */
    public void viewPersonalSchedule(User doctor) {
        List<Appointment> doctorSchedule = new ArrayList<>();
        for (int i = 0; i < appointments.size(); i++) {
            String doctorID = appointments.get(i).getDoctorId();
            String status = appointments.get(i).getStatus();
            if (doctor.getId().equals(doctorID) && status.equals("Confirmed")) {
                doctorSchedule.add(appointments.get(i));
            }
        }
        if (doctorSchedule.size() == 0) {
            System.out.println("You have no appointments\n");
        } else {
            System.out.println("----" + doctor.getName() + "'s Schedule----");
            for (int i = 0; i < doctorSchedule.size(); i++) {
                System.out.println((i + 1) + ". " + doctorSchedule.get(i));
            }
        }
    }
    /**
     * Displays all upcoming appointments for a given doctor.
     *
     * @param doctor the doctor whose upcoming appointments are to be viewed
     */
    public void viewUpcomingAppointment(User doctor) {
        List<Appointment> doctorSchedule = new ArrayList<>();
        for (int i = 0; i < appointments.size(); i++) {
            String doctorID = appointments.get(i).getDoctorId();
            if (doctor.getId().equals(doctorID)) {
                doctorSchedule.add(appointments.get(i));
            }
        }
        if (doctorSchedule.size() == 0) {
            System.out.println("You have no appointments\n");
        } else {
            System.out.println("----" + doctor.getName() + "'s Schedule----");
            for (int i = 0; i < doctorSchedule.size(); i++) {
                System.out.println((i + 1) + ". " + doctorSchedule.get(i));
            }
        }
    }
    /**
     * Displays all appointments in the system.
     */
    public void viewAllAppointment() {
        System.out.println("\n----All Appointments----");
        if (appointments.size() == 0) {
            System.out.println("No appointments.");
        } else {
            for (Appointment eachAppointment : appointments) {
                System.out.println(eachAppointment);
            }
        }
        System.out.println("");
    }
    /**
     * Displays scheduled appointments for a given patient.
     *
     * @param patient the patient whose scheduled appointments are to be viewed
     */
    public void viewScheduledAppointment(Patient patient) {
    	List<Appointment> patientAppointmentList = new ArrayList<>();
    	for(int i = 0; i < appointments.size(); i++) {
    		if (appointments.get(i).getPatientId().equals(patient.getId()) && appointments.get(i).getStatus().equals("Confirmed") && appointments.get(i).getOutcome().equals("-")) {
    			patientAppointmentList.add(appointments.get(i));
    		}
    	}
    	if (patientAppointmentList.size() == 0) {
            System.out.println("No scheduled appointments.");
        } else {
        	System.out.println("\n----Scheduled Appointments----");
            for (Appointment eachAppointment : patientAppointmentList) {
                System.out.println(eachAppointment);
            }
        }  	
    	System.out.println("");
    }
    /**
     * Sets a new appointment for a doctor and patient on a chosen date and time.
     *
     * @param doctor   the doctor for the appointment
     * @param patient  the patient for the appointment
     * @param scanner  a Scanner object for user input
     */
    public void setAppointment(User doctor, Patient patient, Scanner scanner) {
        List<String[]> doctorSchedule = CSVHandler.readCSV(doctorFile);
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
        for (int i = 0; i < appointments.size(); i++) {
            if (doctor.getId().equals(appointments.get(i).getDoctorId()) && date.equals(appointments.get(i).getDate())) {
                appointmentList.add(appointments.get(i).getTime());
            }
        }
        for (int i = 0; i < appointments.size(); i++) {
            if (patient.getId().equals(appointments.get(i).getPatientId()) && date.equals(appointments.get(i).getDate())) {
                patientScheduleList.add(appointments.get(i).getTime());
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
                    break;
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
        appointmentLogList.add(new String[]{patient.getId(), doctor.getId(), "Pending", date, time, "-"});
        appointmentLogList.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        CSVHandler.writeCSV(appointmentLogFile, appointmentLogList);
        appointments.add(new Appointment(patient.getId(), doctor.getId(), "Pending", date, time, "-"));
        saveAppointment();
    }
    /**
     * Sets a new appointment and removing the old appointment for a doctor and patient on a chosen date and time.
     *
     * @param doctor   				the doctor for the appointment
     * @param patient  				the patient for the appointment
     * @param scanner  				a Scanner object for user input
     * @param existingAppointment 	the old Appointment object that will be changed
     */
    public void setAppointment(User doctor, Patient patient, Scanner scanner, Appointment existingAppointment) {
        String[] row;
        List<String[]> doctorSchedule = CSVHandler.readCSV(doctorFile);
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
        for (int i = 0; i < appointments.size(); i++) {
            if (doctor.getId().equals(appointments.get(i).getDoctorId()) && date.equals(appointments.get(i).getDate())) {
                appointmentList.add(appointments.get(i).getTime());
            }
        }
        for (int i = 0; i < appointments.size(); i++) {
            if (patient.getId().equals(appointments.get(i).getPatientId()) && date.equals(appointments.get(i).getDate())) {
                patientScheduleList.add(appointments.get(i).getTime());
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
                    for (int i = 0; i < appointments.size(); i++) {
                        if (appointments.get(i).equals(existingAppointment)) {
                            appointments.remove(i);
                        }
                    }
                    appointments.add(new Appointment(patient.getId(), doctor.getId(), "Pending", date, time, "-"));
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
        saveAppointment();
    }
    /**
     * Reschedules an existing appointment for a patient with a chosen doctor.
     *
     * @param patient the patient whose appointment is to be rescheduled
     * @param scanner a Scanner object for user input
     */
    public void rescheduleAppointment(Patient patient, Scanner scanner) {
        int choice, choice2, size;
        User doctor;
        String doctorID, status;
        List<Appointment> patientExistingAppointment = new ArrayList<>();
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getPatientId().equals(patient.getId()) && !appointments.get(i).getStatus().equals("Canceled") && appointments.get(i).getOutcome().equals("-")) {
                patientExistingAppointment.add(appointments.get(i));
            }
        }
        if (patientExistingAppointment.size() == 0) {
            System.out.println("You have no existing Appointment.");
            return;
        }
        System.out.println("----Existing Appointment----");
        for (int i = 0; i < patientExistingAppointment.size(); i++) {
            doctorID = patientExistingAppointment.get(i).getDoctorId();
            status = patientExistingAppointment.get(i).getStatus();
            date = patientExistingAppointment.get(i).getDate();
            time = patientExistingAppointment.get(i).getTime();
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
    /**
     * Cancels an existing appointment for a patient.
     *
     * @param patient the patient whose appointment is to be canceled
     * @param scanner a Scanner object for user input
     */
    public void cancelAppointment(Patient patient, Scanner scanner) {
        int choice;
        String doctorID, status;
        List<Appointment> patientExistingAppointment = new ArrayList<>();
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getPatientId().equals(patient.getId()) && !appointments.get(i).getStatus().equals("Canceled") && appointments.get(i).getOutcome().equals("-")) {
                patientExistingAppointment.add(appointments.get(i));
            }
        }
        if (patientExistingAppointment.size() == 0) {
            System.out.println("You have no existing Appointment.");
            return;
        }
        System.out.println("----Existing Appointment----");
        for (int i = 0; i < patientExistingAppointment.size(); i++) {
            doctorID = patientExistingAppointment.get(i).getDoctorId();
            status = patientExistingAppointment.get(i).getStatus();
            date = patientExistingAppointment.get(i).getDate();
            time = patientExistingAppointment.get(i).getTime();
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
                    Appointment cancel = patientExistingAppointment.get(choice - 1);
                    for (int i = 0; i < appointments.size(); i++) {
                        if (appointments.get(i).equals(cancel)) {
                            appointments.remove(i);
                        }
                    }
                    appointmentLogList.add(new String[]{cancel.getPatientId(), cancel.getDoctorId(), "Canceled(Patient)", cancel.getDate(), cancel.getTime(), cancel.getOutcome()});
                    appointmentLogList.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
                    CSVHandler.writeCSV(appointmentLogFile, appointmentLogList);
                    System.out.println("You have cancelled the appointment.");
                    saveAppointment();
                    break;
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next();
            }
        }
    }
    /**
     * Manages pending appointments for a doctor by allowing them to accept or decline them.
     *
     * @param scanner a Scanner object for user input
     * @param doctor  the doctor managing their appointments
     */
    public void manageAppointment(Scanner scanner, User doctor) {
        List<String[]> appointmentLogList = CSVHandler.readCSV(appointmentLogFile);
        List<Appointment> doctorSchedule = new ArrayList<>();
        Appointment schedule = null;
        int choice;
        for (int i = 0; i < appointments.size(); i++) {
            String doctorID = appointments.get(i).getDoctorId();
            String status = appointments.get(i).getStatus();
            if (doctor.getId().equals(doctorID) && status.equals("Pending")) {
                doctorSchedule.add(appointments.get(i));
            }
        }
        if (doctorSchedule.size() == 0) {
            System.out.println("You have no appointments\n");
            return;
        } else {
            System.out.println("----" + doctor.getName() + "'s Schedule----");
            for (int i = 0; i < doctorSchedule.size(); i++) {
                System.out.println((i + 1) + ". " + doctorSchedule.get(i));
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
                    schedule = doctorSchedule.get(choice - 1);
                    if (schedule.getStatus().equals("Confirmed") || schedule.getStatus().equals("Canceled")) {
                        System.out.println("Appointment already accepted or declined");
                        return;
                    }
                    System.out.print("Accept or Decline (0 to exit): ");
                    String status = scanner.next();
                    if (status.equals("0")) {
                        return;
                    } else if (status.toLowerCase().equals("accept")) {
                        schedule.setStatus("Confirmed");
                        break;
                    } else if (status.toLowerCase().equals("decline")) {
                        schedule.setStatus("Canceled");
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
        appointmentLogList.add(new String[]{schedule.getPatientId(), schedule.getDoctorId(), schedule.getStatus(), schedule.getDate(), schedule.getTime(), schedule.getOutcome()});
        appointmentLogList.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        CSVHandler.writeCSV(appointmentLogFile, appointmentLogList);
        saveAppointment();
    }
    /**
     * Records the outcome of a completed appointment for a doctor.
     *
     * @param scanner a Scanner object for user input
     * @param doctor  the doctor recording the appointment outcome
     */
    public void recordAppointmentOutcome(Scanner scanner, User doctor) {
        List<String[]> recordList = CSVHandler.readCSV(appointmentOutcomeFile);
        List<Appointment> doctorSchedule = new ArrayList<>();
        Appointment schedule = null;
        int choice;
        for (int i = 0; i < appointments.size(); i++) {
            String doctorID = appointments.get(i).getDoctorId();
            String status = appointments.get(i).getStatus();
            String outcome = appointments.get(i).getOutcome();
            if (doctor.getId().equals(doctorID) && status.equals("Confirmed") && outcome.equals("-")) {
                doctorSchedule.add(appointments.get(i));
            }
        }
        if (doctorSchedule.size() == 0) {
            System.out.println("You have no confirmed appointments\n");
            return;
        } else {
            System.out.println("----" + doctor.getName() + "'s Confirmed Schedule----");
            for (int i = 0; i < doctorSchedule.size(); i++) {
                System.out.println((i + 1) + ". " + doctorSchedule.get(i));
            }
        }
        while (true) {
            System.out.print("\nChoose the appointment you want to record (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                } else if (choice >= 1 && choice <= doctorSchedule.size()) {
                    schedule = doctorSchedule.get(choice - 1);
                    schedule.setOutcome("Refer to Record");
                    System.out.print("Type of service provided: ");
                    scanner.nextLine();
                    String service = scanner.nextLine();
                    String prescriptedStatus = "Pending";
                    System.out.print("Consultation Notes: ");
                    String notes = scanner.nextLine();
                    recordList.add(new String[]{schedule.getDoctorId(), schedule.getPatientId(), schedule.getDate(), service, prescriptedStatus, notes});
                    System.out.println("Record successfully.");
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next();
            }
        }
        appointmentLogList.add(new String[]{schedule.getPatientId(), schedule.getDoctorId(), schedule.getStatus(), schedule.getDate(), schedule.getTime(), schedule.getOutcome()});
        appointmentLogList.add(0, new String[]{"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
        CSVHandler.writeCSV(appointmentLogFile, appointmentLogList);
        recordList.add(0, new String[]{"Doctor ID,Patient ID,Date,Type of Service, Prescription Status, Consultation Notes"});
        CSVHandler.writeCSV(appointmentOutcomeFile, recordList);
        saveAppointment();
    }
    /**
     * Saves the availability of a doctor to a CSV file and checking if it clashes with the current schedule.
     *
     * @param line an array representing the doctor's new availability data
     * @return true if saved successfully, else false if there is a time conflict
     */
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
    /**
     * Retrieves a list of appointments for a specific patient by their ID.
     *
     * @param patientId the ID of the patient
     * @return a list of Appointment objects for the patient
     */
    public List<Appointment> getAppointmentsForPatient(String patientId) {
        List<Appointment> patientAppointments = new ArrayList<>();
        
        for (Appointment appointment : appointments) {
            if (appointment.getPatientId().equals(patientId)) {
                    patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }
    
}
