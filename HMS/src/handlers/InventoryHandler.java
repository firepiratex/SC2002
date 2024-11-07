package handlers;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
/**
 * Handles inventory-related tasks such as saving replenishment requests to a CSV file.
 */
public class InventoryHandler {
	/**
     * Saves a list of replenishment requests to a CSV file.
     *
     * @param requestList a list of string arrays where each array represents a replenishment request
     *                    containing staff ID, medicine name, and stock quantity
     */
    public static void saveReplenishmentRequest(List<String[]> requestList) {
        try {
            FileWriter writeFile = new FileWriter(new File("./src/data/Replenishment_Request.csv"));
            StringBuilder line = new StringBuilder();
            line.append("Staff ID,Medicine Name,Stock\n");
            for (String[] eachRequest : requestList) {
                line.append(String.join(",", eachRequest));
                line.append("\n");
            }
            writeFile.write(line.toString());
            writeFile.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
