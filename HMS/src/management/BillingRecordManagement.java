package management;

import handlers.CSVHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import models.Patient;

public class BillingRecordManagement {

    private static final String appointmentOutcomeFile = "src/data/Appointment_Outcome_Record.csv";

    public static void displayPastOutcomes(Scanner scanner, Patient patient) {
        int choice;
        String[] completedAppointment;
    	List<String[]> recordList = CSVHandler.readCSV(appointmentOutcomeFile);
        List<String[]> billingList = new ArrayList<>();
        
        for(int i = 0; i < recordList.size(); i++) {
        	if (recordList.get(i)[1].equals(patient.getId())) {
        		billingList.add(recordList.get(i));
        	}
        }
        
        if (billingList.size() == 0) {
        	System.out.println("No completed Appointment.\n");
        	return;
        }
        
        System.out.println("----" + patient.getId() + "'s Completed Appointment Outcome Record---- ");
        for(int i = 0; i < billingList.size(); i++) {
        	System.out.println((i+1) + ". " + Arrays.toString(billingList.get(i)));
        }
        System.out.println("");
        System.out.print("Choose which appointment to settle (0 to exit): ");
        while (true) {
	        if (scanner.hasNextInt()) {
	        	choice = scanner.nextInt();
	        	if (choice == 0) {
	        		return;
	        	} else if (choice >= 1 && choice <= billingList.size()) {
	        		completedAppointment = billingList.get(choice-1);
	        		break;
	        	}
	        } else {
	        	System.out.println("Invalid choice.\n");
	        }
        }
        double totalBillCost = 0.0, costPerUnit;
        String prescribedMedicine = completedAppointment[4];
        String[] parts = prescribedMedicine.split(" ");
        
        if (parts[1].equalsIgnoreCase("Paracetamol")) {
            costPerUnit = 2.0; // $2 per unit for Paracetamol
        } else if (parts[1].equalsIgnoreCase("Ibuprofen")) {
            costPerUnit = 5.0; // $5 per unit for Ibuprofen
        } else if (parts[1].equalsIgnoreCase("Amoxicillin")) {
            costPerUnit = 4.0; // $5 per unit for Ibuprofen
        } else {
            costPerUnit = 10.0; // Default $10 per unit for other medicines
        }
        totalBillCost += Integer.valueOf(parts[0]) * costPerUnit;

        // Print the appointment details, type of service, medicine/quantity pairs, and total bill cost
        System.out.println("Appointment on: " + completedAppointment[2] + " with the type of service: " + completedAppointment[3]);
        System.out.println("Medicine(s) and Quantity List");
        System.out.println("Medicine:" + parts[1] + ", Quantity: " + parts[0]);
        // Print the final bill cost
        System.out.printf("Total Medicine Bill Cost: $%.2f%n", totalBillCost);

        System.out.printf("Total Bill Cost with Consultation Fee: $%.2f%n", totalBillCost + 20);
        System.out.println("");
    }
}
