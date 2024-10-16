package management;

import java.util.HashMap;

public class MedicalRecordManagement {
    private HashMap<String, String> medicalRecords;

    public MedicalRecordManagement() {
        this.medicalRecords = new HashMap<>();
        // Load medical records from CSV if needed
    }

    public void viewMedicalRecord(String patientId) {
        if (medicalRecords.containsKey(patientId)) {
            System.out.println("Medical Record for Patient ID " + patientId + ": " + medicalRecords.get(patientId));
        } else {
            System.out.println("No medical record found for patient ID: " + patientId);
        }
    }

    public void addDiagnosis(String patientId, String diagnosis) {
        if (medicalRecords.containsKey(patientId)) {
            medicalRecords.put(patientId, medicalRecords.get(patientId) + "; " + diagnosis);
        } else {
            medicalRecords.put(patientId, diagnosis);
        }
        System.out.println("Diagnosis added for patient ID " + patientId);
    }
}
