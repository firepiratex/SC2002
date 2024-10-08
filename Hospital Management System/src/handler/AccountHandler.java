package handler;

import java.util.*;

import account.Administrator;
import account.Doctor;
import account.Pharmacist;
import account.Role;
import account.User;

public class AccountHandler {
	
	public static List<User> load() {
		CSVHandler csv = new CSVHandler();
		List<User> users = new ArrayList<>();
		List<String> account = csv.readFile("Staff_List.csv");
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
	
	public static void main(String[] args) {
		List<User> user = AccountHandler.load();
		System.out.println(user);
	}
}
