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

    private static final String dispensedRecord = "./src/data/Dispensed_Record.csv";

    public static void displayPastOutcomes(Patient patient) {
        Scanner scanner = new Scanner(System.in);
    	int choice;
    	List<String[]> dispensedList = CSVHandler.readCSV(dispensedRecord);
        List<String[]> patientDispensedList = new ArrayList<>();
        // Display past outcomes for a specific patient
        for(int i = 0; i < dispensedList.size(); i++) {
        	if (dispensedList.get(i)[1].equals(patient.getId())) {
        		patientDispensedList.add(dispensedList.get(i));
        	}
        }
        System.out.println("Displaying past appointment outcomes for Patient ID: " + patient.getId());
        for(int i = 0; i < patientDispensedList.size(); i++) {
        	System.out.println(Arrays.toString(patientDispensedList.get(i)));
        }
        System.out.println("----");
        for(int i = 0; i < patientDispensedList.size(); i++) {
        	System.out.println((i+1) + ". " + Arrays.toString(patientDispensedList.get(i)));
        }
        while(true) {
        	System.out.println("Enter your choice (0 to exit): ");
        	if (scanner.hasNextInt()) {
        		choice = scanner.nextInt();
        		if (choice == 0) {
        			return;
        		} else if (choice >= 1 && choice <= patientDispensedList.size()) {
        			String[] getRecord = patientDispensedList.get(choice-1);
        			String appointmentDate = getRecord[2];
        			String typeOfService = getRecord[3];
        			String paymentStatus = getRecord[4];
        			String[] parts = getRecord[5].split(" ");
        			String quantity = parts[0];
        			String medicine = parts[1];
        			double totalBillCost = 0.0;
        			if (paymentStatus.equals("Dispensed")) {
        				paymentStatus = "Unpaid";
        			}
        			System.out.println("========================================================================");

                    System.out.println("Appointment on: " + appointmentDate + " with the type of service: " + typeOfService);
                    System.out.println("Payment Status: " + paymentStatus);

                    System.out.println("Medicine and Quantity List:");
                    System.out.println("Medicine: " + medicine + ", Quantity: " + quantity);
                    
                    double costPerUnit = 0.0;
                    if (medicine.equalsIgnoreCase("Paracetamol")) {
                        costPerUnit = 2.0; // $2 per unit for Paracetamol

                    } else if (medicine.equalsIgnoreCase("Ibuprofen")) {
                        costPerUnit = 5.0; // $5 per unit for Ibuprofen
                    } else if (medicine.equalsIgnoreCase("Amoxicillin")) {
                        costPerUnit = 4.0; // $5 per unit for Amoxicillin
                    }
                    totalBillCost += Integer.valueOf(quantity) * costPerUnit;
                    // Print the final bill cost
                    System.out.printf("Total Medicine Bill Cost: $%.2f%n", totalBillCost);

                    double totalBillWithConsultation = totalBillCost + 20;
                    System.out.printf("Total Bill Cost with Consultation Fee: $%.2f%n", totalBillWithConsultation);

                    // Ask if the patient wants to pay the bill if unpaid
                    if (paymentStatus.equalsIgnoreCase("Unpaid")) {
                        getRecord = processPayment(totalBillWithConsultation, getRecord, patient, scanner);
                        for(int i = 0; i < dispensedList.size(); i++) {
                        	if (dispensedList.get(i).equals(getRecord)) {
                        		dispensedList.set(i, getRecord);
                        		break;
                        	}
                        }
                        dispensedList.add(0, new String[]{"Doctor ID,Patient ID,Date,Type of Service,Prescribed Status,Medications"});
                        CSVHandler.writeCSV("src/data/Dispensed_Record.csv", dispensedList);
                    }
        		} else {
        			System.out.println("Invalid input");
        		}
        	} else {
        		System.out.println("Invalid input");
        	}
        }
    }

    private static String[] processPayment(double totalBillWithConsultation, String[] record, Patient patient, Scanner scanner) {
        System.out.printf("Your total bill is $%.2f.%n", totalBillWithConsultation);
        System.out.println("Dear " + patient.getName() + ", You have an outstanding bill of $" + totalBillWithConsultation);
        System.out.print("Please enter the amount you wish to pay or type '0' if you do not wish to pay at this moment");

        try {
            double paymentAmount = Double.parseDouble(scanner.nextLine().trim());

            if (paymentAmount == 0) {
                System.out.println("Payment has been skipped. You can pay later through the hospital portal.");
            } else if (paymentAmount >= totalBillWithConsultation) {
                double change = paymentAmount - totalBillWithConsultation;
                System.out.println("Processing payment...");
                System.out.printf("Payment of $%.2f has been successfully completed. Your change is $%.2f.%n", paymentAmount, change);
                record[4] = "Paid"; // Update the payment status to "Paid"
                //record = new String[] {record[0] + "," + record[1] + "," + record[2] + "," + record[3] + "," + record[4] + "," + record[5]};
                return record;
            } else {
                System.out.println("Invalid payment amount. Please enter a valid amount.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numerical value.");
        }
        return null;
    }
}
