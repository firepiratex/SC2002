package medicine;

import java.util.*;
import handler.CSVHandler;


public class MedicineHandler {	
	public static List<Medicine> loadMedicine(String name) {
		List<Medicine> medicineList = new ArrayList<>();
		List<String> medicines = CSVHandler.readFile(name);
		for(int i = 0; i < medicines.size(); i++) {
			String row = medicines.get(i);
			String[] parts = row.split(",");
			Medicine medicine = new Medicine(parts[0], Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
			medicineList.add(medicine);
		}
		return medicineList;
	}
	
	public static void main(String[] args) {
		List<Medicine> medicineList = MedicineHandler.loadMedicine("Medicine_List.csv");
		System.out.println(medicineList);
		medicineList.get(0).minusStock();
		System.out.println(medicineList);
		CSVHandler.saveMedicine(medicineList);
	}
}
