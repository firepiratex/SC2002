package handlers;

import java.io.*;
import java.util.*;
/**
 * Utility class for reading from and writing to text files.
 * Provides methods for reading data from and writing data to text files.
 */
public class TextHandler {
	/**
     * Reads data from a text file, where each line represents a row of text data.
     *
     * @param filePath the path to the text file
     * @return a list of string arrays, where each array represents a row of text data
     */
    public static List<String[]> readTXT(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * Writes data to a text file, where each row is written as a line of text data.
     *
     * @param filePath the path to the text file
     * @param data a list of string arrays, where each array represents a row of text data
     */
    public static void writeTXT(String filePath, List<String[]> data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                pw.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
