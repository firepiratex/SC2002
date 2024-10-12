package handlers;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import users.User;

public class AccountHandler {
	public static void saveStaffAccount(List<User> list) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/files/staffaccount.txt"));
			StringBuilder line = new StringBuilder();
			for (User eachUser : list) {
				line.append(eachUser.getUniqueID());
				line.append(",");
				line.append(eachUser.getPassword());
				line.append(",");
				line.append(eachUser.getInitialLogin());
				line.append("\n");
			}
			writeFile.write(line.toString());
			writeFile.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void savePatientAccount(List<User> list) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/files/patientaccount.txt"));
			StringBuilder line = new StringBuilder();
			for (User eachUser : list) {
				line.append(eachUser.getUniqueID());
				line.append(",");
				line.append(eachUser.getPassword());
				line.append(",");
				line.append(eachUser.getInitialLogin());
				line.append("\n");
			}
			writeFile.write(line.toString());
			writeFile.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static List<String> loadStaffAccount(String name) {
		List<String> fileData = new ArrayList<>();
		String fileName = "src/files/" + name;
		try (Scanner readTXT = new Scanner(Paths.get(fileName))){
			while(readTXT.hasNext()) {
				String row = readTXT.nextLine();
				fileData.add(row);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return fileData;
	}
	
	public static List<String> loadPatientAccount(String name) {
		List<String> fileData = new ArrayList<>();
		String fileName = "src/files/" + name;
		try (Scanner readTXT = new Scanner(Paths.get(fileName))){
			while(readTXT.hasNext()) {
				String row = readTXT.nextLine();
				fileData.add(row);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return fileData;
	}
}
