package handlers;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

import medicine.Medicine;


public class MedicineHandler {	
	public static List<Medicine> loadMedicine(String name) {
		List<Medicine> medicineList = new ArrayList<>();
		List<String> medicines = CSVHandler.loadFile(name);
		for(int i = 0; i < medicines.size(); i++) {
			String row = medicines.get(i);
			String[] parts = row.split(",");
			Medicine medicine = new Medicine(parts[0], Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
			medicineList.add(medicine);
		}
		return medicineList;
	}
	
	public static void saveMedicine(List<Medicine> medicine) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/files/Medicine_List.csv"));
			StringBuilder line = new StringBuilder();
			line.append("Medicine Name,Initial Stock,Low Stock Level Alert\n");
			for (Medicine eachMedicine : medicine) {
				line.append(eachMedicine.getMedicineName());
				line.append(",");
				line.append(eachMedicine.getStock());
				line.append(",");
				line.append(eachMedicine.getLowStockAlert());
				line.append("\n");
			}
			writeFile.write(line.toString());
			writeFile.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
