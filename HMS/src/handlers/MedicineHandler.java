package handlers;

import java.util.ArrayList;
import java.util.List;
import models.Medicine;

public class MedicineHandler {

    private final String medicineFile = "src/data/Medicine_List.csv";

    // Read medicine list from file
    public List<Medicine> loadMedicine() {
        // Placeholder for actual file reading logic (e.g., using CSVHandler)
        List<String[]> data = CSVHandler.readCSV(medicineFile);
        List<Medicine> medicineList = new ArrayList<>();

        for (String[] row : data) {
            String name = row[0];
            int stock = Integer.parseInt(row[1]);
            int lowStockAlert = Integer.parseInt(row[2]);
            Medicine medicine = new Medicine(name, stock, lowStockAlert);
            medicineList.add(medicine);
        }

        return medicineList;
    }

    // Save the updated medicine list to file
    public void saveMedicine(List<Medicine> medicineList) {
        List<String[]> data = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            String[] row = {medicine.getMedicineName(), String.valueOf(medicine.getStock()), String.valueOf(medicine.getLowStockAlert())};
            data.add(row);
        }
        data.add(0, new String[]{"Medicine Name,Initial Stock,Low Stock,Level Alert"});
        CSVHandler.writeCSV(medicineFile, data);
    }
}
