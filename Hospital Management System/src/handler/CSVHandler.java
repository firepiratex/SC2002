package handler;

import java.nio.file.Paths;
import java.util.*;

public class CSVHandler {
	public List<String> readFile(String name) {
		List<String> fileData = new ArrayList<>();
		String fileName = "src/file/" + name;
		try (Scanner readCSV = new Scanner(Paths.get(fileName))){
			readCSV.nextLine();
			while(readCSV.hasNext()) {
				String line = readCSV.nextLine();
				fileData.add(line);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return fileData;
	}
}
