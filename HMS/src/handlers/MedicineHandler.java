package handlers;

import java.util.ArrayList;
import java.util.List;
import management.MedicineManagement;

public class MedicineHandler {

    private final String medicineFile = "src/data/Medicine_List.csv";

    // Read medicine list from file
    public List<MedicineManagement> loadMedicine() {
        // Placeholder for actual file reading logic (e.g., using CSVHandler)
        List<String[]> data = CSVHandler.readCSV(medicineFile);
        List<MedicineManagement> medicineList = new ArrayList<>();

        for (String[] row : data) {
            String name = row[0];
            int stock = Integer.parseInt(row[1]);
            int lowStockAlert = Integer.parseInt(row[2]);
            MedicineManagement medicine = new MedicineManagement(name, stock, lowStockAlert);
            medicineList.add(medicine);
        }

        return medicineList;
    }

    // Save the updated medicine list to file
    public void saveMedicine(List<MedicineManagement> medicineList) {
        List<String[]> data = new ArrayList<>();

        for (MedicineManagement medicine : medicineList) {
            String[] row = {medicine.getMedicineName(), String.valueOf(medicine.getStock()), String.valueOf(medicine.getLowStockAlert())};
            data.add(row);
        }

        CSVHandler.writeCSV(medicineFile, data);
    }
}
