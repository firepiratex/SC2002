package handlers;

import java.util.ArrayList;
import java.util.List;
import models.Medicine;
/**
 * Handles the loading and saving of the medicine list from and to a CSV file.
 * Provides methods to read from and write to the medicine inventory file.
 */
public class MedicineHandler {

    private final String medicineFile = "./src/data/Medicine_List.csv";
    /**
     * Loads the list of medicines from the CSV file.
     *
     * @return a list of Medicine objects representing the medicines in the inventory
     */
    public List<Medicine> loadMedicine() {
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
    /**
     * Saves the updated list of medicines to the CSV file.
     *
     * @param medicineList a list of Medicine objects representing the updated inventory
     */
    public void saveMedicine(List<Medicine> medicineList) {
        List<String[]> data = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            String[] row = {medicine.getMedicineName(), String.valueOf(medicine.getStock()), String.valueOf(medicine.getLowStockAlert())};
            data.add(row);
        }
        data.add(0, new String[]{"Medicine Name,Initial Stock,Low Stock Level Alert"});
        CSVHandler.writeCSV(medicineFile, data);
    }
}
