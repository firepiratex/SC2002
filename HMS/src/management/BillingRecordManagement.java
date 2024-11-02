package management;

import handlers.CSVHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Patient;

public class BillingRecordManagement {

    private static final String appointmentOutcomeFile = "src/data/Appointment_Outcome_Record.csv";

    public static void displayPastOutcomes(Patient patient) {
        List<String[]> recordList = CSVHandler.readCSV(appointmentOutcomeFile);

        // Display past outcomes for a specific patient
        System.out.println("Displaying past appointment outcomes for Patient ID: " + patient.getId());
        boolean recordFound = false;

        for (int i = 0; i < recordList.size(); i++) { // Skip header if present at index 0
            String[] record = recordList.get(i);
            if (record[1].equals(patient.getId())) { // Assuming the second column is the Patient ID
                // Extract the appointment date and type of service
                String appointmentDate = record[2]; // 3rd column
                String typeOfService = record[3];   // 4th column

                // Extract columns from the 6th (index 5) onwards (medicines and quantities)
                String[] medicinesAndQuantities = Arrays.copyOfRange(record, 5, record.length);

                // Create a list to hold medicine and quantity pairs
                List<Map<String, Integer>> medicineQuantityList = new ArrayList<>();

                // Total cost of the bill
                double totalBillCost = 0.0;

                // Parse the array into a structured format and calculate costs
                for (String entry : medicinesAndQuantities) {
                    String[] parts = entry.split(" \\("); // Split by " (" to separate name and quantity
                    if (parts.length == 2) {
                        String medicine = parts[0].trim();
                        String quantityStr = parts[1].replace(")", "").trim(); // Remove the trailing ")"
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            Map<String, Integer> medicineEntry = new HashMap<>();
                            medicineEntry.put(medicine, quantity);
                            medicineQuantityList.add(medicineEntry);

                            // Calculate the cost based on medicine type
                            double costPerUnit = 0.0;
                            if (medicine.equalsIgnoreCase("Paracetamol")) {
                                costPerUnit = 2.0; // $2 per unit for Paracetamol

                            } else if (medicine.equalsIgnoreCase("Ibuprofen")) {
                                costPerUnit = 5.0; // $5 per unit for Ibuprofen
                            } else if (medicine.equalsIgnoreCase("Amoxicillin")) {
                                costPerUnit = 4.0; // $5 per unit for Ibuprofen
                            } else {
                                costPerUnit = 10.0; // Default $10 per unit for other medicines
                            }

                            totalBillCost += quantity * costPerUnit;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid quantity format for entry: " + entry);
                        }
                    } else {
                        System.out.println("Invalid entry format: " + entry);
                    }
                }

                // Print the appointment details, type of service, medicine/quantity pairs, and total bill cost
                System.out.println("Appointment on: " + appointmentDate + " with the type of service: " + typeOfService);
                System.out.println("Medicine and Quantity List:");
                for (Map<String, Integer> entry : medicineQuantityList) {
                    entry.forEach((medicine, quantity) -> {
                        System.out.println("Medicine: " + medicine + ", Quantity: " + quantity);
                    });
                }

                // Print the final bill cost
                System.out.printf("Total Medicine Bill Cost: $%.2f%n", totalBillCost);

                System.out.printf("Total Bill Cost with Consultation Fee: $%.2f%n", totalBillCost + 20);

                recordFound = true;
            }
        }

        if (!recordFound) {
            System.out.println("No past appointment outcomes found for Patient ID: " + patient.getId());
        }
    }
}
