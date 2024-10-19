package handlers;

import java.util.*;
import models.Patient;

public class PatientHandler {
    private List<Patient> patients;
    private final String patientFile = "src/data/Patient_List.csv";
    private final String patientTXTFile = "src/data/Patient_Account.txt";

    public PatientHandler() {
        this.patients = new ArrayList<>();
        loadPatients();
    }

    // Load patient data from the CSV file
    public void loadPatients() {
        List<String[]> data = CSVHandler.readCSV(patientFile);
        List<String[]> data2 = TextHandler.readTXT(patientTXTFile);
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
            String dateOfBirth = data.get(index)[2];
            String gender = data.get(index)[3];
            String bloodType = data.get(index)[4];
            String contactInfo = data.get(index)[5];       // Contact Information
            patients.add(new Patient(id, name, password, dateOfBirth, gender, bloodType, contactInfo));
        }
        System.out.println(patients);
    }

    public void savePatients() {
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
