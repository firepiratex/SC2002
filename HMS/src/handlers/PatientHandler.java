package handlers;

import interfaces.AccountSaver;
import java.util.*;
import models.Patient;
/**
 * Handles operations related to managing patient data, such as loading, saving, and retrieving patients.
 * Implements the AccountSaver interface for saving account details.
 */
public class PatientHandler implements AccountSaver {

    private static PatientHandler instance;
    private List<Patient> patients;
    private final String patientFile = "./src/data/Patient_List.csv";
    private final String patientTXTFile = "./src/data/Patient_Account.txt";
    /**
     * Private constructor that initializes the PatientHandler by loading patient data from files.
     */
    private PatientHandler() {
        this.patients = new ArrayList<>();
        loadPatients();
    }
    /**
     * Returns the singleton instance of the PatientHandler.
     *
     * @return the singleton instance
     */
    public static PatientHandler getInstance() {
        if (instance == null) {
            instance = new PatientHandler();
        }
        return instance;
    }
    /**
     * Displays the IDs of all patients in the system.
     */
    public void displayPatient() {
        for (Patient eachPatient : patients) {
            System.out.print(eachPatient.getId() + " ");
        }
        System.out.println();
    }
    /**
     * Loads patient data from the CSV file and the text file.
     * Initializes the patient list with this data.
     */
    private void loadPatients() {
        List<String[]> data = CSVHandler.readCSV(patientFile);
        List<String[]> data2 = TextHandler.readTXT(patientTXTFile);
        for (int index = 0; index < data.size(); index++) {
            String id = data.get(index)[0];
            String password = PasswordHash.hash("password");
            String contactNumber = "-";
            for (int index2 = 0; index2 < data2.size(); index2++) {
                String id2 = data2.get(index2)[0];
                if (id.equals(id2)) {
                    password = data2.get(index2)[1];
                    contactNumber = data2.get(index2)[2];
                    break;
                }
            }
            String name = data.get(index)[1];
            String dateOfBirth = data.get(index)[2];
            String gender = data.get(index)[3];
            String bloodType = data.get(index)[4];
            String contactInfo = data.get(index)[5];
            patients.add(new Patient(id, name, password, dateOfBirth, gender, bloodType, contactInfo, contactNumber));
        }
    }
    /**
     * Saves the account details of all patients to the text file.
     */
    public void saveAccount() {
        List<String[]> data = new ArrayList<>();
        for (Patient patient : patients) {
            String[] row = {patient.getId(), patient.getPassword(), patient.getContactNumber()};
            data.add(row);
        }
        TextHandler.writeTXT(patientTXTFile, data);
    }
    /**
     * Finds and returns a patient by their ID.
     *
     * @param id the ID of the patient to find
     * @return the Patient object if found, or null if not found
     */
    public Patient findPatientById(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }
}
