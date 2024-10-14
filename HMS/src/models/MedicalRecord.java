package models;

import java.util.ArrayList;

public class MedicalRecord {
    private ArrayList<String> diagnoses = new ArrayList<>();
    private ArrayList<String> treatments = new ArrayList<>();

    public void addDiagnosis(String diagnosis, String treatment) {
        diagnoses.add(diagnosis);
        treatments.add(treatment);
    }

    public void viewRecord() {
        // Display medical record
    }
}
