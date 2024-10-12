package handlers;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import users.Administrator;
import users.Doctor;
import users.Patient;
import users.Pharmacist;
import users.Role;
import users.User;

public class UserHandler {
	
	public static List<User> loadStaff(String name) {
		List<User> staffs = new ArrayList<>();
		List<String> account = CSVHandler.loadFile(name);
		List<String> accountTXT = AccountHandler.loadStaffAccount("staffaccount.txt");
		for(int i = 0; i < account.size(); i++) {
			Boolean existInTXT = false;
			String[] parts = account.get(i).split(",");
			String[] parts2 = null;
			for(int j = 0; j < accountTXT.size(); j++) {
				parts2 = accountTXT.get(j).split(",");
				if (parts[0].equals(parts2[0])) {
					existInTXT = true;
					break;
				}
			}
			String role = parts[2];
			if (!existInTXT) {
				switch (role) {
					case "Doctor":
						User doctor = new Doctor(parts[0], parts[1], Role.Doctor, parts[3], Integer.valueOf(parts[4]), "password", true);
						staffs.add(doctor);
						break;
					case "Pharmacist":
						User pharmacist = new Pharmacist(parts[0], parts[1], Role.Pharmacist, parts[3], Integer.valueOf(parts[4]), "password", true);
						staffs.add(pharmacist);
						break;
					case "Administrator":
						User administrator = new Administrator(parts[0], parts[1], Role.Administrator, parts[3], Integer.valueOf(parts[4]), "password", true);
						staffs.add(administrator);
						break;				
				}
			} else if (existInTXT == true && parts2 != null) {
				switch (role) {
				case "Doctor":
					User doctor = new Doctor(parts2[0], parts[1], Role.Doctor, parts[3], Integer.valueOf(parts[4]), parts2[1], Boolean.valueOf(parts2[2]));
					staffs.add(doctor);
					break;
				case "Pharmacist":
					User pharmacist = new Pharmacist(parts2[0], parts[1], Role.Pharmacist, parts[3], Integer.valueOf(parts[4]), parts2[1], Boolean.valueOf(parts2[2]));
					staffs.add(pharmacist);
					break;
				case "Administrator":
					User administrator = new Administrator(parts2[0], parts[1], Role.Administrator, parts[3], Integer.valueOf(parts[4]), parts2[1], Boolean.valueOf(parts2[2]));
					staffs.add(administrator);
					break;				
				}
			}
		}
		staffs = staffs.stream()
				.sorted((s1,s2) -> Integer.compare(s1.getRole().ordinal(), s2.getRole().ordinal()))
				.collect(Collectors.toList());
		AccountHandler.saveStaffAccount(staffs);
		return staffs;
	}
	
	public static List<User> loadPatient(String name) {
		List<User> patients = new ArrayList<>();
		List<String> account = CSVHandler.loadFile(name);
		List<String> accountTXT = AccountHandler.loadPatientAccount("patientaccount.txt");
		
		for(int i = 0; i < account.size(); i++) {
			Boolean existInTXT = false;
			String[] parts = account.get(i).split(",");
			String uniqueID = parts[0];
			String patientName = parts[1];
			String DoB = parts[2];
			String Gender = parts[3];
			String BloodType = parts[4];
			String email = parts[5];
			
			Role role = Role.Patient; 

			int age = CalculateAge(DoB);
			
			String password = "password";
			
			String[] parts2 = null;
			for(int j = 0; j < accountTXT.size(); j++) {
				parts2 = accountTXT.get(j).split(",");
				if (parts[0].equals(parts2[0])) {
					existInTXT = true;
					break;
				}
			}
			if (!existInTXT) {
				User patient = new Patient(uniqueID, patientName, role, Gender, age, password, true, DoB, email, BloodType);
				patients.add(patient);
			} else if (existInTXT == true && parts2 != null) {
				User patient = new Patient(parts2[0], patientName, role, Gender, age, parts2[1], Boolean.valueOf(parts2[2]), DoB, email, BloodType);
				patients.add(patient);
			}
		}
		AccountHandler.savePatientAccount(patients);
		return patients;
	}
	
	public static int CalculateAge(String DoB){
		LocalDate birthDate = LocalDate.parse(DoB);
		LocalDate currentData = LocalDate.now(); // to obtain current date
		return Period.between(birthDate, currentData).getYears(); //calculates the time between birthDate and currentDate
	}
}
