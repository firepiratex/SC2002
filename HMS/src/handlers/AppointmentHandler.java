package handlers;

import java.time.LocalTime;
import java.util.*;

import interfaces.DateAndTime;
import models.Patient;
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
            instance = new AppointmentHandler();
        }
        return instance;
    }
    
    public void viewAvailableAppointment(User doctor, Scanner scanner) {
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
    	for(int i = 0; i < appointmentSchedule.size(); i++) {
    		if (doctor.getId().equals(appointmentSchedule.get(i)[1]) && date.equals(appointmentSchedule.get(i)[3])) {
				appointmentList.add(appointmentSchedule.get(i)[4]);
			}
    	}
    	for(int i = 0; i < doctorSchedule.size(); i++) {
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
		while(j < timeList.size()) {
			System.out.print((j+1) + ". " + timeList.get(j) + "\t");
			j++;
			if (j >= timeList.size()) {
				break;
			}
			System.out.println((j+1) + ". " + timeList.get(j));
			j++;
		}
		timeList.clear();
    }
    
    public String[] setAppointment(User doctor, Patient patient, Scanner scanner) {
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
    	for(int i = 0; i < appointmentSchedule.size(); i++) {
    		if (doctor.getId().equals(appointmentSchedule.get(i)[1]) && date.equals(appointmentSchedule.get(i)[3])) {
				appointmentList.add(appointmentSchedule.get(i)[4]);
			}
    	}
    	for(int i = 0; i < doctorSchedule.size(); i++) {
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
		while(j < timeList.size()) {
			System.out.print((j+1) + ". " + timeList.get(j) + "\t");
			j++;
			if (j >= timeList.size()) {
				break;
			}
			System.out.println((j+1) + ". " + timeList.get(j));
			j++;
		}
		while(true) {
			System.out.print("Enter the slot (0 to exit): ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return null;
				} else if (choice >= 1 && choice <= timeList.size()) {
					time = timeList.get(choice-1);
					break;
				} else {
					System.out.println("Invalid option. Try again.");
				}
			} else {
				System.out.println("Invalid input. Try again.");
			}
		}
		timeList.clear();
		return new String[] {"Patient ID",doctor.getId(), "Pending", date, time, "-"};
    }
    
    public void setAppointment(User doctor, Patient patient, Scanner scanner, String[] existingAppointment) {
    	int choice;
    	String time, date, startTime = "09:00", endTime = "17:00";
    	String[] row;
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
    	for(int i = 0; i < appointmentSchedule.size(); i++) {
    		if (doctor.getId().equals(appointmentSchedule.get(i)[1]) && date.equals(appointmentSchedule.get(i)[3])) {
				appointmentList.add(appointmentSchedule.get(i)[4]);
			}
    	}
    	for(int i = 0; i < doctorSchedule.size(); i++) {
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
		while(j < timeList.size()) {
			System.out.print((j+1) + ". " + timeList.get(j) + "\t");
			j++;
			if (j >= timeList.size()) {
				break;
			}
			System.out.println((j+1) + ". " + timeList.get(j));
			j++;
		}
		while(true) {
			System.out.print("Enter the slot (0 to exit): ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return;
				} else if (choice >= 1 && choice <= timeList.size()) {
					time = timeList.get(choice-1);
					row = new String[] {patient.getId(), doctor.getId(), "Pending", date, time, "-"};
					for(int i = 0; i < appointmentSchedule.size(); i++) {
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
		timeList.clear();
		appointmentSchedule.add(0, new String[] {"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
		appointmentSchedule.add(row);
		CSVHandler.writeCSV(appointmentFile, appointmentSchedule);
    }
    
    public void rescheduleAppointment(Patient patient, Scanner scanner) {
    	int choice, size;
    	User doctor;
    	String doctorID, status, date, time;
    	List<String[]> appointmentSchedule = CSVHandler.readCSV(appointmentFile);
    	List<String[]> patientExistingAppointment = new ArrayList<>();
    	for(int i = 0; i < appointmentSchedule.size(); i++) {
    		if (appointmentSchedule.get(i)[0].equals(patient.getId()) && !appointmentSchedule.get(i)[2].equals("Canceled") && appointmentSchedule.get(i)[5].equals("-")) {
    			patientExistingAppointment.add(appointmentSchedule.get(i));
    		}
    	}
    	if (patientExistingAppointment.size() == 0) {
			System.out.println("You have no existing Appointment.");
			return;
		}
    	System.out.println("----Existing Appointment----");
		for(int i = 0; i < patientExistingAppointment.size(); i++) {
			doctorID = patientExistingAppointment.get(i)[1];
			status = patientExistingAppointment.get(i)[2];
			date = patientExistingAppointment.get(i)[3];
			time = patientExistingAppointment.get(i)[4];
			System.out.println((i+1) + ". " + StaffManagement.getInstance().findStaffById(doctorID).getName() + " " + date + " " + time + " (" + status + ")");
		}
		while (true) {
			System.out.print("\nEnter the appointment you want to reschedule (0 to exit): ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return;
				}
				if (choice >= 1 && choice <= patientExistingAppointment.size()) {
					size = StaffManagement.getInstance().displayDoctor();
					while(true) {
						System.out.print("Enter the doctor (0 to exit): ");
						if (scanner.hasNextInt()) {
							choice = scanner.nextInt();
							if (choice == 0) {
								return;
							} else if (choice >= 1 && choice <= size) {
								doctor = StaffManagement.getInstance().getStaff(choice-1);
								break;
							} else {
								System.out.println("Invalid option. Try again.");
							}
						} else {
							System.out.println("Invalid input. Try again.");
						}
					}
					setAppointment(doctor, patient, scanner, patientExistingAppointment.get(choice-1));
					break;
				}
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
		}
    }
    
    public boolean saveDoctorAvailability(String[] line) {
    	Boolean duplicateTime = false;
    	List<String[]> data = CSVHandler.readCSV(doctorFile);
    	for(int i = 0; i < data.size(); i++) {
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
			data.add(0, new String[] {"Doctor ID,Date,Start,End"});
			data.add(line);
			CSVHandler.writeCSV(doctorFile, data);
			return true;
		}
    }
    
    public void saveScheduledAppointment(String[] line) {
    	List<String[]> data = CSVHandler.readCSV(appointmentFile);
    	data.add(0, new String[] {"Patient ID,Doctor ID,Status,Date,Time,Outcome"});
    	data.add(line);
    	CSVHandler.writeCSV(appointmentFile, data);
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
