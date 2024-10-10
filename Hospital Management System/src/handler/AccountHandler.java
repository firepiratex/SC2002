package handler;

import java.util.*;

import account.Administrator;
import account.Doctor;
import account.Pharmacist;
import account.Role;
import account.User;
import java.time.LocalDate;
import java.time.Period;
import account.Patient;

public class AccountHandler {
	
	public static List<User> load() {
		List<User> users = new ArrayList<>();
		List<String> account = CSVHandler.readFile("Staff_List.csv");
		for(int i = 0; i < account.size(); i++) {
			String[] parts = account.get(i).split(",");
			String role = parts[2];
			switch (role) {
				case "Doctor":
					User doctor = new Doctor(parts[0], parts[1], Role.Doctor, parts[3], Integer.valueOf(parts[4]), "password");
					users.add(doctor);
					break;
				case "Pharmacist":
					User pharmacist = new Pharmacist(parts[0], parts[1], Role.Pharmacist, parts[3], Integer.valueOf(parts[4]), "password");
					users.add(pharmacist);
					break;
				case "Administrator":
					User administrator = new Administrator(parts[0], parts[1], Role.Administrator, parts[3], Integer.valueOf(parts[4]), "password");
					users.add(administrator);
					break;				
			}
		}
		return users;
	}
	
	public static List<User> loadPatient() {
		List<User> users = new ArrayList<>();
		List<String> account = CSVHandler.readFile("Patient_List.csv");

		for (String line : account) {
			String[] parts = line.split(",");  // Split the line by commas
			
			String uniqueID = parts[0];
			String name = parts[1];
			String DoB = parts[2];
			String Gender = parts[3];
			String BloodType = parts[4];
			String email = parts[5];

			Role role = Role.Patient; 

			int age = CalculateAge(DoB);

			String password = "password";

			User patient = new Patient(uniqueID, name, role, Gender, age, password, DoB, email, BloodType);

			users.add(patient);
		}
		System.out.println(account);
		return users;
	}

	public static int CalculateAge(String DoB){
		LocalDate birthDate = LocalDate.parse(DoB);
		LocalDate currentData = LocalDate.now(); // to obtain current date
		return Period.between(birthDate, currentData).getYears(); //calculates the time between birthDate and currentDate
	}

	public static void main(String[] args){

		List<User> userlist = AccountHandler.loadPatient();
		System.out.println(userlist.get(0));
		/*List<User> userlist = AccountHandler.load();
		System.out.println(userlist.get(0));*/
	} 
}
