package handlers;

import java.util.List;

public class MedicineHandler {
    private List<String[]> medicines;
    private final String medicineFile = "data/Medicine_List.csv";

    public MedicineHandler() {
        loadMedicines();
    }

    // Load medicine data from the CSV file
    public void loadMedicines() {
        // Use readCSV method from CSVHandler to load data from the CSV file
        medicines = CSVHandler.readCSV(medicineFile);
    }

    // Method to display medicine data
    public void displayMedicines() {
        System.out.println("Medicine List:");
        for (String[] medicine : medicines) {
            System.out.println("Medicine: " + medicine[0] + ", Stock: " + medicine[1]);
        }
    }
}
