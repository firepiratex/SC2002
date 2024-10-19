package handlers;

import java.util.*;

import models.Administrator;
import models.Doctor;
import models.Patient;
import models.Pharmacist;
import models.User;

public class StaffManagement {
    private List<User> staff;
    private final String staffFile = "src/data/Staff_List.csv";
    private final String staffTXTFile = "src/data/Staff_Account.txt";

    public StaffManagement() {
        this.staff = new ArrayList<>();
        loadStaff();  // Load staff data when initializing
    }

    // Load staff from CSV
    public void loadStaff() {
        List<String[]> data = CSVHandler.readCSV(staffFile);
        List<String[]> data2 = TextHandler.readTXT(staffTXTFile);
        for(int index = 0; index < data.size(); index++) {
        	String id = data.get(index)[0];
        	String password = "password";
        	for(int index2 = 0; index2 < data2.size(); index2++) {
        		String id2 = data2.get(index2)[0];
        		if (id.equals(id2)) {
        			password = data2.get(index2)[1];
        			break;
        		}
        	}
        	String name = data.get(index)[1];              // Patient Name
            String role = data.get(index)[2];
            String gender = data.get(index)[3];
            int age = Integer.valueOf(data.get(index)[4]);
            User staffMember = null;
            
            switch (role) {
            case "Doctor":
                staffMember = new Doctor(id, name, password, gender, age);
                break;
            case "Pharmacist":
                staffMember = new Pharmacist(id, name, password, gender, age);
                break;
            case "Administrator":
                staffMember = new Administrator(id, name, password, gender, age);
                break;
            }

	        if (staffMember != null) {
	            staff.add(staffMember);
	        }
        }
        System.out.println(staff);
    }

    // Add new staff member
    public void addStaff(User newStaff) {
        staff.add(newStaff);
        System.out.println("Staff member added successfully.");
    }
    
    public void saveStaffs() {
        List<String[]> data = new ArrayList<>();
        for (User staff : staff) {
            String[] row = {staff.getId(), staff.getPassword()};
            data.add(row);
        }
        TextHandler.writeTXT(staffTXTFile, data);
    }

    // Remove staff member by ID
    public void removeStaff(String staffId) {
        Iterator<User> iterator = staff.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId().equals(staffId)) {
                iterator.remove();  // Use iterator.remove() to properly remove from a list during iteration
                System.out.println("Staff member removed successfully.");
                return;
            }
        }
        System.out.println("Staff member not found.");
    }

    // Find a staff member by ID
    public User findStaffById(String id) {
        for (User user : staff) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    // Display all staff members
    public void displayStaff() {
        System.out.println("Hospital Staff:");
        for (User user : staff) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Role: " + user.getRole());
        }
    }
}
