package management;

import handlers.CSVHandler;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import models.Patient;

public class BillingRecordManagement {

    private static final String appointmentOutcomeFile = "./src/data/Appointment_Outcome_Record.csv";

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
                String paymentStatus = record[5]; // Assuming the last column is the payment status

                // Extract columns from the 6th (index 5) onwards (medicines and quantities)
                String[] medicinesAndQuantities = Arrays.copyOfRange(record, 6, record.length);

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
                System.out.println("========================================================================");

                System.out.println("Appointment on: " + appointmentDate + " with the type of service: " + typeOfService);
                System.out.println("Payment Status: " + paymentStatus);

                System.out.println("Medicine and Quantity List:");
                for (Map<String, Integer> entry : medicineQuantityList) {
                    entry.forEach((medicine, quantity) -> {
                        System.out.println("Medicine: " + medicine + ", Quantity: " + quantity);
                    });
                }

                // Print the final bill cost
                System.out.printf("Total Medicine Bill Cost: $%.2f%n", totalBillCost);

                double totalBillWithConsultation = totalBillCost + 20;
                System.out.printf("Total Bill Cost with Consultation Fee: $%.2f%n", totalBillWithConsultation);

                // Ask if the patient wants to pay the bill if unpaid
                if (paymentStatus.equalsIgnoreCase("Unpaid")) {
                    processPayment(totalBillWithConsultation, record, i, patient);
                }

                recordFound = true;
            }
        }

        if (!recordFound) {
            System.out.println("No past appointment outcomes found for Patient ID: " + patient.getId());
        }
    }

    private static void processPayment(double totalBillWithConsultation, String[] record, int recordIndex, Patient patient) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Your total bill is $%.2f.%n", totalBillWithConsultation);
        System.out.println("Dear " + patient.getName() + ", You have an outstanding bill of $" + totalBillWithConsultation);
        System.out.println("Please enter the amount you wish to pay or type '0' if you do not wish to pay at this moment");

        try {
            double paymentAmount = Double.parseDouble(scanner.nextLine().trim());

            if (paymentAmount == 0) {
                System.out.println("Payment has been skipped. You can pay later through the hospital portal.");
            } else if (paymentAmount >= totalBillWithConsultation) {
                double change = paymentAmount - totalBillWithConsultation;
                System.out.println("Processing payment...");
                System.out.printf("Payment of $%.2f has been successfully completed. Your change is $%.2f.%n", paymentAmount, change);
                record[5] = "Paid"; // Update the payment status to "Paid"
                updateCSVRecord(recordIndex, record);
            } else if (paymentAmount > 0) {
                double remainingBalance = totalBillWithConsultation - paymentAmount;
                System.out.println("Processing partial payment...");
                System.out.printf("Partial payment of $%.2f has been successfully completed. Remaining balance: $%.2f.%n", paymentAmount, remainingBalance);
                record[5] = "Partial Payment"; // Update the payment status to "Partial Payment"
                updateCSVRecord(recordIndex, record);
            } else {
                System.out.println("Invalid payment amount. Please enter a valid amount.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numerical value.");
        }
    }

    private static void updateCSVRecord(int recordIndex, String[] updatedRecord) {
        List<String[]> recordList = CSVHandler.readCSV(appointmentOutcomeFile);

        if (recordIndex >= 0 && recordIndex < recordList.size()) { // Ensure the index does not point to the header
            recordList.set(recordIndex, updatedRecord); // Update the specific row with the new record
            writeCSVWithHeader(appointmentOutcomeFile, recordList); // Write the list back to the CSV file
            System.out.println("CSV file updated successfully.");
            recordList.add(0, new String[]{"Doctor ID,Patient ID,Date,Type of Service,Consultation Notes,Payment Status, Prescribed Medications "});
            CSVHandler.writeCSV(appointmentOutcomeFile, recordList);
        } else {
            System.out.println("Invalid record index. Unable to update CSV file.");
        }
    }

    private static void writeCSVWithHeader(String filePath, List<String[]> data) {
        if (data.isEmpty()) {
            System.out.println("No data to write.");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write the header separately and then the data rows
            writer.println(String.join(",", data.get(0))); // Write the header row

            // Write the rest of the data (excluding the header)
            for (int i = 1; i < data.size(); i++) {
                writer.println(String.join(",", data.get(i)));
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
