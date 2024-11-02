package handlers;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class InventoryHandler {

    public static void saveReplenishmentRequest(List<String[]> requestList) {
        try {
            FileWriter writeFile = new FileWriter(new File("HMS/src/data/Replenishment_Request.csv"));
            StringBuilder line = new StringBuilder();
            line.append("Staff ID,Medicine Name,Stock\n");  // Header for the CSV
            for (String[] eachRequest : requestList) {
                line.append(String.join(",", eachRequest));  // Convert String[] to CSV string
                line.append("\n");
            }
            writeFile.write(line.toString());
            writeFile.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
