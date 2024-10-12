package managements;

import java.util.*;
import java.time.*;
import java.time.format.*;

import handlers.CSVHandler;
import users.Role;
import users.User;

public class AppointmentManagement {
	public static void viewDoctorSchedule(User doctor) {
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		List<String> doctorSchedule = new ArrayList<>();
		for(String eachSchedule : scheduleList) {
			String[] parts = eachSchedule.split(",");
			if (doctor.getUniqueID().equals(parts[1])) {
				doctorSchedule.add(eachSchedule);
			}
		}
		if (doctorSchedule.size() == 0) {
			System.out.println("You have no appointments\n");
		} else {
			System.out.println("----" + doctor.getName() + "'s Schedule----");
			for (String eachSchedule : doctorSchedule) {
				System.out.println(eachSchedule);
			}
		}
	}
	
	public static void manageAppointment(User doctor, Scanner scanner) {
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		List<String> appointmentLogList = CSVHandler.loadFile("Appointment_Log.csv");
		List<String> doctorSchedule = new ArrayList<>();
		int choice;
		String row;
		for(String eachSchedule : scheduleList) {
			String[] parts = eachSchedule.split(",");
			if (doctor.getUniqueID().equals(parts[1])) {
				doctorSchedule.add(eachSchedule);
			}
		}
		if (doctorSchedule.size() == 0) {
			System.out.println("You have no appointments\n");
			return;
		} else {
			System.out.println("----" + doctor.getName() + "'s Schedule----");
			for(int i = 0; i < doctorSchedule.size(); i++) {
				System.out.println((i+1) + ". " + doctorSchedule.get(i));
			}
		}
		while(true) {
			System.out.print("\nChoose the appointment you want to manage (0 to exit): ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return;
				}
				if (choice >= 1 && choice <= doctorSchedule.size()) {
					String[] parts = doctorSchedule.get(choice-1).split(",");
					if (parts[2].equals("Confirmed") || parts[2].equals("Canceled")) {
						System.out.println("Appointment already accepted or declined");
						return;
					}
					System.out.print("Accept or Decline (0 to exit): ");
					String status = scanner.next();
					if (status.equals("0")) {
						return;
					} else if (status.toLowerCase().equals("accept")) {
						for (int i = 0; i < scheduleList.size(); i++) {
							if (scheduleList.get(i).equals(doctorSchedule.get(choice-1))) {
								parts = scheduleList.get(i).split(",");
								row = parts[0] + "," + parts[1] + "," + "Confirmed" + "," + parts[3] + "," + parts[4] + ",-";
								appointmentLogList.add(row);
								scheduleList.set(i, row);
								break;
							}
						}
						break;
					} else if (status.toLowerCase().equals("decline")) {
						for (int i = 0; i < scheduleList.size(); i++) {
							if (scheduleList.get(i).equals(doctorSchedule.get(choice-1))) {
								parts = scheduleList.get(i).split(",");
								row = parts[0] + "," + parts[1] + "," + "Canceled" + "," + parts[3] + "," + parts[4] + ",-";
								appointmentLogList.add(row);
								scheduleList.set(i, row);
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
		CSVHandler.saveAppointmentLog(appointmentLogList);
		CSVHandler.saveDoctorSchedule(scheduleList);
	}
	
	public static void viewUpcomingAppointment(User doctor) {
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		List<String> doctorSchedule = new ArrayList<>();
		for(String eachSchedule : scheduleList) {
			String[] parts = eachSchedule.split(",");
			if (doctor.getUniqueID().equals(parts[1]) && parts[2].equals("Confirmed")) {
				doctorSchedule.add(eachSchedule);
			}
		}
		if (doctorSchedule.size() == 0) {
			System.out.println("You have no appointments\n");
		} else {
			System.out.println("----" + doctor.getName() + "'s Schedule----");
			for(int i = 0; i < doctorSchedule.size(); i++) {
				System.out.println((i+1) + ". " + doctorSchedule.get(i));
			}
		}
	}
	
	public static void viewAppointmentLog() {
		List<String> logList = CSVHandler.loadFile("Appointment_Log.csv");
		for(String eachLog : logList) {
			System.out.println(eachLog);
		}
	}
	
	public static void viewAllAppointment() {
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		for(String eachAppointment : scheduleList) {
			System.out.println(eachAppointment);
		}
	}
	
	public static void setAvailableAppointment(User doctor, Scanner scanner) {
		List<String> availableList = CSVHandler.loadFile("Doctor_Availability.csv");
		String date, startTime, endTime;
		Boolean duplicateTime = false;
		while (true) {
			System.out.print("Enter the date (DD/MM/YYYY): ");
			date = scanner.next();
			if (dateChecker(date)) {
				break;
			}
			System.out.println("Incorrect date. Try again.");
		}
		while (true) {
			System.out.print("Enter the start time (00:00 - 23:59): ");
			startTime = scanner.next();
			if (timeOfDay(startTime)) {
				if (timeChecker(startTime)) {
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
			if (timeOfDay(endTime)) {
				if (timeChecker(startTime, endTime)) {
					break;
				}
			}
			System.out.println("Incorrect time. Try again.");
		}
		String row = doctor.getUniqueID() + "," + date + "," + startTime + "," + endTime + "\n";
		for (String eachRow : availableList) {
			String[] parts = eachRow.split(",");
			if (date.equals(parts[1])) {
				LocalTime newStartTime = LocalTime.parse(startTime);
		        LocalTime existingStartTime = LocalTime.parse(parts[2]);
		        LocalTime existingEndTime = LocalTime.parse(parts[3]);
		        if (newStartTime.compareTo(existingStartTime) >= 0 && newStartTime.compareTo(existingEndTime) < 0) {
		        	duplicateTime = true;
		        }
			}
		}
		if (duplicateTime) {
			System.out.println("Time clashes with existing available time.\n");
		} else {
			System.out.println("Set successfully.\n");
			availableList.add(row);
			CSVHandler.saveAvailableAppointment(availableList);
		}
	}
	
	public static void viewAvailableAppointment(Scanner scanner, List<User> staffList) {
		List<String> availableList = CSVHandler.loadFile("Doctor_Availability.csv");
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		List<String> timeList = new ArrayList<>();
		String date, startTime = "09:00", endTime = "17:00";
		int choice;
		System.out.println("----Available Doctors----");
		for(int i = 0; i < staffList.size(); i++) {
			if (staffList.get(i).getRole() == Role.Doctor) {
				System.out.println((i+1) + ". " + staffList.get(i).getName());
			}
		}
		while (true) {
			System.out.print("\nEnter the doctor you want: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				for(int i = 1; i <= staffList.size(); i++) {
					if (i == choice) {
						if (staffList.get(i-1) != null) {
							while (true) {
								System.out.print("Enter the date (DD/MM/YYYY): ");
								date = scanner.next();
								if (dateChecker(date)) {
									break;
								}
								System.out.println("Incorrect date. Try again.");
							}
							List<String> doctorScheduleList = new ArrayList<>();
							for (String eachRow : scheduleList) {
								String[] parts = eachRow.split(",");
								if (parts[1].equals(staffList.get(i-1).getUniqueID()) && parts[3].equals(date)) {
									doctorScheduleList.add(parts[4]);
								}
							}
							for(String eachRow : availableList) {
								String[] parts = eachRow.split(",");
								if (parts[0].equals(staffList.get(i-1).getUniqueID()) && parts[1].equals(date)) {
									startTime = parts[2];
									endTime = parts[3];
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
							timeList.removeAll(doctorScheduleList);
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
							break;
						} else {
							System.out.println("Invalid input. Try again.");
							break;
						}
					}
				}
				break;
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
		}		
	}
	
	public static void scheduleAppointment(User patient, Scanner scanner, List<User> staffList) {
		List<String> availableList = CSVHandler.loadFile("Doctor_Availability.csv");
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		List<String> appointmentLogList = CSVHandler.loadFile("Appointment_Log.csv");
		List<String> timeList = new ArrayList<>();
		List<String> doctorScheduleList = new ArrayList<>();
		List<String> patientAppointmentList = new ArrayList<>();
		String date, startTime = "09:00", endTime = "17:00";
		int choice,option;
		System.out.println("----Available Doctors----");
		for(int i = 0; i < staffList.size(); i++) {
			if (staffList.get(i).getRole() == Role.Doctor) {
				System.out.println((i+1) + ". " + staffList.get(i).getName());
			}
		}
		while (true) {
			System.out.print("\nEnter the doctor you want: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				for(int i = 1; i <= staffList.size(); i++) {
					if (i == choice) {
						if (staffList.get(i-1) != null) {
							while (true) {
								System.out.print("Enter the date (DD/MM/YYYY): ");
								date = scanner.next();
								if (dateChecker(date)) {
									break;
								}
								System.out.println("Incorrect date. Try again.");
							}
							for (String eachRow : scheduleList) {
								String[] parts = eachRow.split(",");
								if (parts[1].equals(staffList.get(i-1).getUniqueID()) && parts[3].equals(date)) {
									doctorScheduleList.add(parts[4]);
								}
							}
							for (String eachRow : scheduleList) {
								String[] parts = eachRow.split(",");
								if (parts[0].equals(patient.getUniqueID()) && parts[3].equals(date)) {
									patientAppointmentList.add(parts[4]);
								}
							}
							for (String eachRow : availableList) {
								String[] parts = eachRow.split(",");
								if (parts[0].equals(staffList.get(i-1).getUniqueID()) && parts[1].equals(date)) {
									startTime = parts[2];
									endTime = parts[3];
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
							timeList.removeAll(doctorScheduleList);
							timeList.removeAll(patientAppointmentList);
							if (timeList.size() == 0) {
								System.out.println("No available timeslot for this doctor.");
								break;
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
							while (true) {
								scanner.nextLine();
								System.out.print("\n\nChoose a timeslot (0 to exit): ");
								if (scanner.hasNextInt()) {
									option = scanner.nextInt();
									if (option == 0) {
										return;
									}
									if (option >= 1 && option <= timeList.size()) {
										String row = patient.getUniqueID() + "," + staffList.get(i-1).getUniqueID() + ",Pending," + date + "," + timeList.get(option-1).toString() + ",-";
										scheduleList.add(row);
										appointmentLogList.add(row);
										System.out.println("Request an appointment successfully.");
										break;
									} else {
										System.out.println("Invalid timeslot. Try again");
									}
								} else {
									System.out.println("Invalid input. Try again.");
									continue;
								}
							}
							break;
						} else {
							System.out.println("Invalid input. Try again.");
							break;
						}
					}
				}
				break;
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
		}
		CSVHandler.saveAppointmentLog(appointmentLogList);
		CSVHandler.saveDoctorSchedule(scheduleList);
	}
	
	public static void scheduleAppointment(User patient, Scanner scanner, List<User> staffList, String existingAppointment) {
		List<String> availableList = CSVHandler.loadFile("Doctor_Availability.csv");
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		List<String> appointmentLogList = CSVHandler.loadFile("Appointment_Log.csv");
		List<String> timeList = new ArrayList<>();
		List<String> doctorScheduleList = new ArrayList<>();
		List<String> patientAppointmentList = new ArrayList<>();
		String date, startTime = "09:00", endTime = "17:00";
		int choice,option;
		System.out.println("----Available Doctors----");
		for(int i = 0; i < staffList.size(); i++) {
			if (staffList.get(i).getRole() == Role.Doctor) {
				System.out.println((i+1) + ". " + staffList.get(i).getName());
			}
		}
		while (true) {
			System.out.print("\nEnter the doctor you want: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				for(int i = 1; i <= staffList.size(); i++) {
					if (i == choice) {
						if (staffList.get(i-1) != null) {
							while (true) {
								System.out.print("Enter the date (DD/MM/YYYY): ");
								date = scanner.next();
								if (dateChecker(date)) {
									break;
								}
								System.out.println("Incorrect date. Try again.");
							}
							for (String eachRow : scheduleList) {
								String[] parts = eachRow.split(",");
								if (parts[1].equals(staffList.get(i-1).getUniqueID()) && parts[3].equals(date)) {
									doctorScheduleList.add(parts[4]);
								}
							}
							for (String eachRow : scheduleList) {
								String[] parts = eachRow.split(",");
								if (parts[0].equals(patient.getUniqueID()) && parts[3].equals(date)) {
									patientAppointmentList.add(parts[4]);
								}
							}
							for (String eachRow : availableList) {
								String[] parts = eachRow.split(",");
								if (parts[0].equals(staffList.get(i-1).getUniqueID()) && parts[1].equals(date)) {
									startTime = parts[2];
									endTime = parts[3];
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
							timeList.removeAll(doctorScheduleList);
							timeList.removeAll(patientAppointmentList);
							if (timeList.size() == 0) {
								System.out.println("No available timeslot for this doctor.");
								break;
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
							while (true) {
								scanner.nextLine();
								System.out.print("\n\nChoose a timeslot (0 to exit): ");
								if (scanner.hasNextInt()) {
									option = scanner.nextInt();
									if (option == 0) {
										return;
									}
									if (option >= 1 && option <= timeList.size()) {
										String row = patient.getUniqueID() + "," + staffList.get(i-1).getUniqueID() + ",Pending," + date + "," + timeList.get(option-1).toString() + ",-";
										for(int k = 0; k < scheduleList.size(); k++) {
											if (scheduleList.get(k).equals(existingAppointment)) {
												scheduleList.remove(k);
												break;
											}
										}
										scheduleList.add(row);
										appointmentLogList.add(row);
										System.out.println("Reschedule an appointment successfully.");
										break;
									} else {
										System.out.println("Invalid timeslot. Try again");
									}
								} else {
									System.out.println("Invalid input. Try again.");
									continue;
								}
							}
							break;
						} else {
							System.out.println("Invalid input. Try again.");
							break;
						}
					}
				}
				break;
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
		}
		CSVHandler.saveAppointmentLog(appointmentLogList);
		CSVHandler.saveDoctorSchedule(scheduleList);
	}
	
	public static void reScheduleAppointment(User patient, Scanner scanner, List<User> staffList) {
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		List<String> patientExistingAppointment = new ArrayList<>();
		String doctorName = "";
		int choice;
		for(String eachRow : scheduleList) {
			String[] parts = eachRow.split(",");
			if (parts[0].equals(patient.getUniqueID()) && !parts[2].equals("Canceled") && parts[5].equals("-")) {
				patientExistingAppointment.add(eachRow);
			}
		}
		if (patientExistingAppointment.size() == 0) {
			System.out.println("You have no existing Appointment.");
			return;
		}
		System.out.println("----Existing Appointment----");
		for(int i = 0; i < patientExistingAppointment.size(); i++) {
			String[] parts = patientExistingAppointment.get(i).split(",");
			for (User eachStaff : staffList) {
				if (eachStaff.getUniqueID().equals(parts[1])) {
					doctorName = eachStaff.getName();
					break;
				}
			}
			System.out.println((i+1) + ". " + doctorName + " " + parts[3] + " " + parts[4] + " (" + parts[2] + ")");
		}
		while (true) {
			System.out.print("\nEnter the appointment you want to reschedule (0 to exit): ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return;
				}
				if (choice >= 1 && choice <= patientExistingAppointment.size()) {
					scheduleAppointment(patient, scanner, staffList, patientExistingAppointment.get(choice-1));
					break;
				}
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
		}
	}
	
	public static void cancelAppointment(User patient, Scanner scanner, List<User> staffList) {
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		List<String> appointmentLogList = CSVHandler.loadFile("Appointment_Log.csv");
		List<String> patientExistingAppointment = new ArrayList<>();
		List<String> cancelAppointment = new ArrayList<>();
		String doctorName = "";
		int choice;
		for(String eachRow : scheduleList) {
			String[] parts = eachRow.split(",");
			if (parts[0].equals(patient.getUniqueID()) && !parts[2].equals("Canceled") && parts[5].equals("-")) {
				patientExistingAppointment.add(eachRow);
			}
		}
		if (patientExistingAppointment.size() == 0) {
			System.out.println("You have no existing Appointment.");
			return;
		}
		System.out.println("----Existing Appointment----");
		for(int i = 0; i < patientExistingAppointment.size(); i++) {
			String[] parts = patientExistingAppointment.get(i).split(",");
			for (User eachStaff : staffList) {
				if (eachStaff.getUniqueID().equals(parts[1])) {
					doctorName = eachStaff.getName();
					break;
				}
			}
			System.out.println((i+1) + ". " + doctorName + " " + parts[3] + " " + parts[4] + " (" + parts[2] + ")");
		}
		while (true) {
			System.out.print("\nEnter the appointment you want to cancel (0 to exit): ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return;
				}
				if (choice >= 1 && choice <= patientExistingAppointment.size()) {
					cancelAppointment.add(patientExistingAppointment.get(choice-1));
					scheduleList.removeAll(cancelAppointment);
					String[] parts = patientExistingAppointment.get(choice-1).split(",");
					String row = parts[0] + "," + parts[1] + ",Canceled(Patient)," + parts[3] + "," + parts[4] + "," + parts[5];
					appointmentLogList.add(row);
					System.out.println("You have cancelled the appointment.");
					CSVHandler.saveAppointmentLog(appointmentLogList);
					CSVHandler.saveDoctorSchedule(scheduleList);
					break;
				}
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
		}
	}
	
	public static void viewScheduledAppointment(User patient, List<User> staffList) {
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		List<String> patientExistingAppointment = new ArrayList<>();
		String doctorName = "";
		for(String eachRow : scheduleList) {
			String[] parts = eachRow.split(",");
			if (parts[0].equals(patient.getUniqueID())) {
				patientExistingAppointment.add(eachRow);
			}
		}
		if (patientExistingAppointment.size() == 0) {
			System.out.println("You have no Scheduled Appointment.");
			return;
		}
		System.out.println("----Scheduled Appointment----");
		for(int i = 0; i < patientExistingAppointment.size(); i++) {
			String[] parts = patientExistingAppointment.get(i).split(",");
			for (User eachStaff : staffList) {
				if (eachStaff.getUniqueID().equals(parts[1])) {
					doctorName = eachStaff.getName();
					break;
				}
			}
			System.out.println((i+1) + ". " + doctorName + " " + parts[3] + " " + parts[4] + " (" + parts[2] + ")");
		}
	}
	
	public static void recordAppointmentOutcome(User doctor, Scanner scanner) {
		List<String> scheduleList = CSVHandler.loadFile("Appointment_Detail.csv");
		List<String> appointmentLogList = CSVHandler.loadFile("Appointment_Log.csv");
		List<String> recordList = CSVHandler.loadFile("Appointment_Outcome_Record.csv");
		List<String> doctorSchedule = new ArrayList<>();
		int choice;
		String row;
		String[] parts;
		for(String eachSchedule : scheduleList) {
			parts = eachSchedule.split(",");
			if (doctor.getUniqueID().equals(parts[1]) && parts[2].equals("Confirmed") && parts[5].equals("-")) {
				doctorSchedule.add(eachSchedule);
			}
		}
		if (doctorSchedule.size() == 0) {
			System.out.println("You have no confirmed appointments\n");
			return;
		} else {
			System.out.println("----" + doctor.getName() + "'s Confirmed Schedule----");
			for (int i = 0; i < doctorSchedule.size(); i++) {
				System.out.println((i+1) + ". " + doctorSchedule.get(i));
			}
		}
		while(true) {
			System.out.print("\nChoose the appointment you want to record (0 to exit): ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return;
				} else if (choice >= 1 && choice <= doctorSchedule.size()) {
					for(int i = 0; i < scheduleList.size(); i++) {
						if (scheduleList.get(i).equals(doctorSchedule.get(choice-1))) {
							parts = scheduleList.get(i).split(",");
							row = parts[0] + "," + parts[1] + "," + "Confirmed" + "," + parts[3] + "," + parts[4] + ",Refer to Record";
							appointmentLogList.add(row);
							scheduleList.set(i, row);
							System.out.print("Type of service provided: ");
							scanner.nextLine();
							String service = scanner.nextLine();
							System.out.print("Prescribed Medications (default is pending): ");
							String medication = scanner.nextLine();
							System.out.print("Consultation Notes: ");
							String notes = scanner.nextLine();
							row = parts[1] + "," + parts[0] + "," + parts[3] + "," + service + "," + medication + "," + notes;
							recordList.add(row);
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
		CSVHandler.saveAppointmentOutcomeRecord(recordList);
		CSVHandler.saveAppointmentLog(appointmentLogList);
		CSVHandler.saveDoctorSchedule(scheduleList);
	}
	
    private static boolean timeChecker(String time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(time, timeFormatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static boolean timeChecker(String startTime, String endTime) {
        if (!timeChecker(endTime)) {
            return false;
        }
        
        LocalTime from = LocalTime.parse(startTime);
        LocalTime to = LocalTime.parse(endTime);
        
        return to.isAfter(from);
    }
    
    private static boolean dateChecker(String date) {
    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");   	
    	try {
    		LocalDate furtherDate = LocalDate.parse(date, dateFormatter);
    		LocalDate now = LocalDate.now();
    		return furtherDate.isAfter(now);
    	} catch (Exception e) {
    		return false;
    	}
    }
    
    private static boolean timeOfDay(String time) {
        if (time.matches("([0-1][0-9]|2[0-3]):([0-5][0-9])")) {
            return true;
        } else {
            return false;
        }
    }
}
