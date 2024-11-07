package handlers;

import java.io.*;
import java.util.*;
/**
 * Utility class for handling reading and writing of CSV files.
 * Provides methods for reading data from a CSV file into a list and writing data from a list to a CSV file.
 */
public class CSVHandler {
	/**
     * Reads data from a CSV file and returns it as a list of string arrays.
     * Each array represents a row in the CSV file, split by commas.
     *
     * @param filePath the path of the CSV file to read from
     * @return a list of string arrays containing the data from the CSV file
     */
    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = br.readLine(); // Remove the first row of the CSV that contains the column name
            while ((line = br.readLine()) != null && !line.isEmpty()) {
            	data.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * Writes data to a CSV file from a list of string arrays.
     * Each array represents a row to be written to the CSV file, with elements joined by commas.
     *
     * @param filePath the path of the CSV file to write to
     * @param data     a list of string arrays representing the data to write
     */
    public static void writeCSV(String filePath, List<String[]> data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
        	for (String[] row : data) {
                pw.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
