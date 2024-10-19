package handlers;

import java.io.*;
import java.util.*;

public class CSVHandler {

    // Method to read CSV file and return list of String arrays
    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Method to write CSV data back to a file (if needed)
    public static void writeCSV(String filePath, List<String[]> data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("Staff ID,Name,Role,Gender,Age");
        	for (String[] row : data) {
                pw.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
