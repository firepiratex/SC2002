package handlers;

import interfaces.AccountSaver;
import java.util.*;
import models.Patient;

public class PatientHandler implements AccountSaver{
	private static PatientHandler instance;
    private List<Patient> patients;
    private final String patientFile = "HMS/src/data/Patient_List.csv";
    private final String patientTXTFile = "HMS/src/data/Patient_Account.txt";

    private PatientHandler() {
        this.patients = new ArrayList<>();
        loadPatients();
    }
    
    public static PatientHandler getInstance() {
        if (instance == null) {
            instance = new PatientHandler();  // Initialize only when needed
        }
        return instance;
    }
    
    public void displayPatient() {
    	for(Patient eachPatient : patients) {
    		System.out.print(eachPatient + " ");
    	}
    	System.out.println();
    }
    
    // Load patient data from the CSV file
    private void loadPatients() {
        List<String[]> data = CSVHandler.readCSV(patientFile);
        List<String[]> data2 = TextHandler.readTXT(patientTXTFile);
        for(int index = 0; index < data.size(); index++) {
        	String id = data.get(index)[0];
        	String password = PasswordHash.hash("password");
        	for(int index2 = 0; index2 < data2.size(); index2++) {
        		String id2 = data2.get(index2)[0];
        		if (id.equals(id2)) {
        			password = data2.get(index2)[1];
        			break;
        		}
        	}
        	String name = data.get(index)[1];              // Patient Name
            String dateOfBirth = data.get(index)[2];
            String gender = data.get(index)[3];
            String bloodType = data.get(index)[4];
            String contactInfo = data.get(index)[5];       // Contact Information
            patients.add(new Patient(id, name, password, dateOfBirth, gender, bloodType, contactInfo));
        }
    }
    
    public void saveAccount() {
    	List<String[]> data = new ArrayList<>();
        for (Patient patient : patients) {
            String[] row = {patient.getId(), patient.getPassword()};
            data.add(row);
        }
        TextHandler.writeTXT(patientTXTFile, data);
    }

    public Patient findPatientById(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }
}
