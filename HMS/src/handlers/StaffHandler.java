package handlers;

import interfaces.AccountSaver;
import java.util.*;
import models.Administrator;
import models.Doctor;
import models.Pharmacist;
import models.User;
/**
 * Handles operations related to managing staff data, such as loading, saving, adding, and removing staff members.
 * Implements the AccountSaver interface to facilitate account saving functionality.
 */
public class StaffHandler implements AccountSaver {

    private static StaffHandler instance;
    private List<User> staff;
    private List<User> doctor;
    private final String staffFile = "./src/data/Staff_List.csv";
    private final String staffTXTFile = "./src/data/Staff_Account.txt";
    /**
     * Private constructor that initializes the StaffHandler and loads staff data.
     */
    private StaffHandler() {
        this.staff = new ArrayList<>();
        this.doctor = new ArrayList<>();
        loadStaff();  // Load staff data when initializing
    }
    /**
     * Returns the singleton instance of StaffHandler.
     *
     * @return the singleton instance
     */
    public static StaffHandler getInstance() {
        if (instance == null) {
            instance = new StaffHandler();
        }
        return instance;
    }
    /**
     * Retrieves a list of all staff members.
     *
     * @return a list of all staff members
     */
    public List<User> getStaffList() {
    	List<User> newList = new ArrayList<>();
    	newList.addAll(this.staff);
    	return newList;
    }
    /**
     * Loads staff data from the CSV file and the text file.
     * Initializes the {@code staff} and {@code doctor} lists.
     */
    private void loadStaff() {
        List<String[]> data = CSVHandler.readCSV(staffFile);
        List<String[]> data2 = TextHandler.readTXT(staffTXTFile);
        for (int index = 0; index < data.size(); index++) {
            String id = data.get(index)[0];
            String password = PasswordHash.hash("password");
            for (int index2 = 0; index2 < data2.size(); index2++) {
                String id2 = data2.get(index2)[0];
                if (id.equals(id2)) {
                    password = data2.get(index2)[1];
                    break;
                }
            }
            String name = data.get(index)[1];
            String role = data.get(index)[2];
            String gender = data.get(index)[3];
            int age = Integer.valueOf(data.get(index)[4]);
            User staffMember = null;

            switch (role) {
                case "Doctor":
                    staffMember = new Doctor(id, name, password, gender, age);
                    doctor.add(staffMember);
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
    }
    /**
     * Saves the account details of all staff members to the text file.
     */
    @Override
    public void saveAccount() {
        List<String[]> data = new ArrayList<>();
        for (User staff : staff) {
            String[] row = {staff.getId(), staff.getPassword()};
            data.add(row);
        }
        TextHandler.writeTXT(staffTXTFile, data);
    }
    /**
     * Saves the current staff list to the CSV file.
     */
    public void saveStaffsList() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Staff ID", "Name", "Role", "Gender", "Age"});
        for (User staff : staff) {
            if (staff.getRole().equals("Administrator")) {
                Administrator admin = (Administrator) staff;
                String[] row = {staff.getId(), staff.getName(), staff.getRole(), staff.getGender(), String.valueOf(admin.getAge())};
                data.add(row);
            }
            if (staff.getRole().equals("Doctor")) {
                Doctor doctor = (Doctor) staff;
                String[] row = {staff.getId(), staff.getName(), staff.getRole(), staff.getGender(), String.valueOf(doctor.getAge())};
                data.add(row);
            }
            if (staff.getRole().equals("Pharmacist")) {
                Pharmacist pharmacist = (Pharmacist) staff;
                String[] row = {staff.getId(), staff.getName(), staff.getRole(), staff.getGender(), String.valueOf(pharmacist.getAge())};
                data.add(row);
            }
        }
        saveAccount();
        CSVHandler.writeCSV(staffFile, data);
    }
    /**
     * Adds a new staff member to the list and updates the staff data file.
     *
     * @param newStaff the new staff member to add
     */
    public void addStaff(User newStaff) {
        staff.add(newStaff);
        saveStaffsList();
    }
    /**
     * Removes a staff member from the list based on their ID.
     *
     * @param staffId the ID of the staff member to remove
     */
    public void removeStaff(String staffId) {
        staff.removeIf(user -> user.getId().equals(staffId));
    }
    /**
     * Finds and returns a staff member by their ID.
     *
     * @param id the ID of the staff member to find
     * @return the User object if found, or null if not found
     */
    public User findStaffById(String id) {
        for (User user : staff) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    /**
     * Displays the IDs of all staff members in the system.
     */
    public void displayStaff() {
        for (User user : staff) {
            System.out.print(user.getId() + " ");
        }
        System.out.println();
    }
    /**
     * Displays a list of available doctors.
     *
     * @return the number of available doctors
     */
    public int displayDoctor() {
        System.out.println("----Available Doctors----");
        for (int index = 0; index < doctor.size(); index++) {
            System.out.println((index + 1) + ". " + doctor.get(index).getName());
        }
        return doctor.size();
    }
    /**
     * Retrieves a doctor from the list based on their position in the list.
     *
     * @param choice the index of the doctor to retrieve
     * @return the User object representing the doctor, or null if not found
     */
    public User getStaff(int choice) {
        if (doctor.get(choice) == null) {
            return null;
        }
        return doctor.get(choice);
    }

}
