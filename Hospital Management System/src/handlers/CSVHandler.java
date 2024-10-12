package handlers;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.*;

import users.Patient;
import users.User;

public class CSVHandler {
	public static List<String> loadFile(String name) {
		List<String> fileData = new ArrayList<>();
		String fileName = "src/files/" + name;
		try (Scanner readCSV = new Scanner(Paths.get(fileName))){
			readCSV.nextLine();
			while(readCSV.hasNext()) {
				String row = readCSV.nextLine();
				fileData.add(row);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return fileData;
	}
	
	public static void saveStaffList(List<User> staffList) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/files/Staff_List.csv"));
			StringBuilder line = new StringBuilder();
			line.append("Staff ID,Name,Role,Gender,Age\n");
			for (User eachStaff : staffList) {
				line.append(eachStaff.getUniqueID());
				line.append(",");
				line.append(eachStaff.getName());
				line.append(",");
				line.append(eachStaff.getRole());
				line.append(",");
				line.append(eachStaff.getGender());
				line.append(",");
				line.append(eachStaff.getAge());
				line.append("\n");
			}
			writeFile.write(line.toString());
			writeFile.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void savePatientList(List<User> patientList) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/files/Patient_List.csv"));
			StringBuilder line = new StringBuilder();
			line.append("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Information\n");
			for (User eachPatient : patientList) {
				Patient patient = (Patient) eachPatient;
				line.append(patient.getUniqueID());
				line.append(",");
				line.append(patient.getName());
				line.append(",");
				line.append(patient.getDoB());
				line.append(",");
				line.append(patient.getGender());
				line.append(",");
				line.append(patient.getBloodType());
				line.append(",");
				line.append(patient.getEmail());
				line.append("\n");
			}
			writeFile.write(line.toString());
			writeFile.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void saveAvailableAppointment(List<String> appointment) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/files/Doctor_Availability.csv"));
			StringBuilder line = new StringBuilder();
			line.append("Doctor ID,Date,Start,End\n");
			for (String eachAppointment : appointment) {
				line.append(eachAppointment);
				line.append("\n");
			}
			writeFile.write(line.toString());
			writeFile.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void saveDoctorSchedule(List<String> schedule) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/files/Appointment_Detail.csv"));
			StringBuilder line = new StringBuilder();
			line.append("Patient ID,Doctor ID,Status,Date,Time,Outcome\n");
			for (String eachSchedule : schedule) {
				line.append(eachSchedule);
				line.append("\n");
			}
			writeFile.write(line.toString());
			writeFile.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void saveAppointmentLog(List<String> log) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/files/Appointment_Log.csv"));
			StringBuilder line = new StringBuilder();
			line.append("Patient ID,Doctor ID,Status,Date,Time,Outcome\n");
			for (String eachLog : log) {
				line.append(eachLog);
				line.append("\n");
			}
			writeFile.write(line.toString());
			writeFile.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void saveAppointmentOutcomeRecord(List<String> record) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/files/Appointment_Outcome_Record.csv"));
			StringBuilder line = new StringBuilder();
			line.append("Doctor ID,Patient ID,Date,Type of Service,Prescribed Medications,Consultation Notes\n");
			for (String eachRecord : record) {
				line.append(eachRecord);
				line.append("\n");
			}
			writeFile.write(line.toString());
			writeFile.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void saveReplenishmentRequest(List<String> request) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/files/Replenishment_Request.csv"));
			StringBuilder line = new StringBuilder();
			line.append("Staff ID,Medicine Name,Stock\n");
			for (String eachRequest : request) {
				line.append(eachRequest);
				line.append("\n");
			}
			writeFile.write(line.toString());
			writeFile.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
