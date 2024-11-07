package management;

import handlers.CSVHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.Patient;
/**
 * Provides methods to manage and display billing records, including viewing past appointment outcomes
 * and handling payment processes for patients.
 */
public class BillingRecordManagement {

    private static final String dispensedRecord = "./src/data/Dispensed_Record.csv";
    // Private constructor to prevent instantiation
    private BillingRecordManagement() {
    }
    /**
     * Displays past appointment outcomes for a given patient, including payment status and bill details.
     *
     * @param scanner a Scanner object for user input
     * @param patient the patient who is viewing the outcomes and whether they want to manage/view the bill
     */
    public static void displayPastOutcomes(Scanner scanner, Patient patient) {
    	int choice;
    	List<String[]> dispensedList = CSVHandler.readCSV(dispensedRecord);
        List<String[]> patientDispensedList = new ArrayList<>();
        for(int i = 0; i < dispensedList.size(); i++) {
        	if (dispensedList.get(i)[1].equals(patient.getId())) {
        		patientDispensedList.add(dispensedList.get(i));
        	}
        }
        if (patientDispensedList.size() == 0) {
        	System.out.println("You have no past appointment outcome record.\n");
        	return;
        }
     // Display each appointment record with billing details
        System.out.println("Displaying past appointment outcomes for Patient ID: " + patient.getId());
        for(int i = 0; i < patientDispensedList.size(); i++) {
        	String[] getRecord = patientDispensedList.get(i);
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

            System.out.println((i+1) + ". Appointment on: " + appointmentDate + " with the type of service: " + typeOfService);
            System.out.println("Payment Status: " + paymentStatus);

            System.out.println("Medicine and Quantity List:");
            System.out.println("Medicine: " + medicine + ", Quantity: " + quantity);
            
            double costPerUnit = 0.0;
            if (medicine.equalsIgnoreCase("Paracetamol")) {
                costPerUnit = 2.0; // $2 per unit for Paracetamol

            } else if (medicine.equalsIgnoreCase("Ibuprofen")) {
                costPerUnit = 5.0; // $5 per unit for Ibuprofen
            } else if (medicine.equalsIgnoreCase("Amoxicillin")) {
                costPerUnit = 4.0; // $4 per unit for Amoxicillin
            }
            totalBillCost += Integer.valueOf(quantity) * costPerUnit;
            // Print the final bill cost
            System.out.printf("Total Medicine Bill Cost: $%.2f%n", totalBillCost);

            double totalBillWithConsultation = totalBillCost + 20;
            System.out.printf("Total Bill Cost with Consultation Fee: $%.2f%n", totalBillWithConsultation);
        }
        while(true) {
        	System.out.print("Choose an appointment to manage (0 to exit): ");
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
                        costPerUnit = 4.0; // $4 per unit for Amoxicillin
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
    /**
     * Processes the payment for the given patient appointment, updating the record if paid.
     *
     * @param totalBillWithConsultation the total amount due for the appointment
     * @param record                    the record of the appointment
     * @param patient                   the patient making the payment
     * @param scanner                   a Scanner object for user input
     * @return the updated record if payment is successful, or null if unsuccessful
     */
    private static String[] processPayment(double totalBillWithConsultation, String[] record, Patient patient, Scanner scanner) {
        scanner.nextLine();
    	System.out.printf("Your total bill is $%.2f.%n", totalBillWithConsultation);
        System.out.println("Dear " + patient.getName() + ", You have an outstanding bill of $" + totalBillWithConsultation);
        System.out.print("Please enter the amount you wish to pay or type '0' if you do not wish to pay at this moment: ");

        try {
            double paymentAmount = Double.parseDouble(scanner.nextLine().trim());

            if (paymentAmount == 0) {
                System.out.println("Payment has been skipped. You can pay later through the hospital portal.");
            } else if (paymentAmount >= totalBillWithConsultation) {
                double change = paymentAmount - totalBillWithConsultation;
                System.out.println("Processing payment...");
                System.out.printf("Payment of $%.2f has been successfully completed. Your change is $%.2f.%n", paymentAmount, change);
                record[4] = "Paid"; // Update the payment status to "Paid"
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
