package handlers;

import java.util.*;
import models.Patient;

public class PatientHandler {
    private List<Patient> patients;
    private final String patientFile = "data/Patient_List.csv";

    public PatientHandler() {
        this.patients = new ArrayList<>();
        loadPatients();
    }

    // Load patient data from the CSV file
    public void loadPatients() {
        List<String[]> data = CSVHandler.readCSV(patientFile);
        for (String[] row : data) {
            String id = row[0];                // Patient ID
            String name = row[1];              // Patient Name
            String medicalRecord = row[2];     // Medical Record (or other information)
            String contactInfo = row[4];       // Contact Information
            String defaultPassword = "password";  // Assign default password
            patients.add(new Patient(id, name, defaultPassword, medicalRecord, contactInfo));
        }
    }

    public void savePatients() {
        List<String[]> data = new ArrayList<>();
        for (Patient patient : patients) {
            String[] row = {patient.getId(), patient.getName(), patient.getPassword(), patient.getMedicalRecord(), patient.getContactInfo()};
            data.add(row);
        }
        CSVHandler.writeCSV(patientFile, data);
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
