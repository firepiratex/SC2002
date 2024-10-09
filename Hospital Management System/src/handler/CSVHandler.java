package handler;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.*;

import medicine.Medicine;

public class CSVHandler {
	public static List<String> readFile(String name) {
		List<String> fileData = new ArrayList<>();
		String fileName = "src/file/" + name;
		try (Scanner readCSV = new Scanner(Paths.get(fileName))){
			readCSV.nextLine();
			while(readCSV.hasNext()) {
				String row = readCSV.nextLine();
				fileData.add(row);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return fileData;
	}
	
	public static void saveMedicine(List<Medicine> medicine) {
		try {
			FileWriter writeFile = new FileWriter(new File("src/file/Medicine_List.csv"));
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
