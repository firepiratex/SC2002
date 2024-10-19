package handlers;

import java.util.*;
import models.Administrator;
import models.Doctor;
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

    // Load staff from CSV and text file
    public void loadStaff() {
        List<String[]> data = CSVHandler.readCSV(staffFile);
        List<String[]> data2 = TextHandler.readTXT(staffTXTFile);
        for(int index = 0; index < data.size(); index++) {
            String id = data.get(index)[0];
            String password = "password";
            for (String[] dataRow : data2) {
                String id2 = dataRow[0];
                if (id.equals(id2)) {
                    password = dataRow[1];
                    break;
                }
            }
            String name = data.get(index)[1];
            String role = data.get(index)[2];
            String gender = data.get(index)[3];
            int age = Integer.parseInt(data.get(index)[4]);
            User staffMember = null;

            switch (role) {
                case "Doctor":
                    staffMember = new Doctor(id, name, password, gender, age);
                    break;
                case "Pharmacist":
                    staffMember = new Pharmacist(id, name, password, gender, age);
                    break;
                case "Administrator":
                    // Avoid recursive Administrator creation
                    staffMember = new Administrator(id, name, password, gender, age, true);
                    break;
            }

            if (staffMember != null) {
                staff.add(staffMember);
            }
        }
    }

    public void saveStaffs() {
        List<String[]> data = new ArrayList<>();
        for (User staff : this.staff) {
            String[] row = {
                staff.getId(),
                staff.getName(),
                staff.getPassword(),
                staff.getRole(),
                staff.getGender(),
                //String.valueOf(staff.getAge())
            };
            data.add(row);
        }
        CSVHandler.writeCSV(staffFile, data);  // Save staff list to CSV
        System.out.println("Staff data saved successfully!");
    }

    public void addStaff(User newStaff) {
        staff.add(newStaff);
        saveStaffs();  // Save to CSV after adding new staff
    }

    public void removeStaff(String staffId) {
        staff.removeIf(user -> user.getId().equals(staffId));
        saveStaffs();  // Save after removing staff
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
        System.out.println("Hospital Staff List:");
        for (User user : staff) {
            System.out.println("ID: " + user.getId() + " | Name: " + user.getName() + " | Role: " + user.getRole());
        }
    }
}
