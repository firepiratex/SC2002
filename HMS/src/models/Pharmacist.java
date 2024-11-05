package models;

import handlers.CSVHandler;
import handlers.MedicineHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import management.InventoryManagement;

public class Pharmacist extends User {

    private InventoryManagement inventoryManagement;
    private int age;

    public Pharmacist(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Pharmacist", gender);
        this.age = age;
        new MedicineHandler();
    }

    public int getAge() {
        return age;
    }
    
    public String toString() {
    	return super.toString() + " " + getAge();
    }
    
    @Override
    public void displayMenu() {
        System.out.println("Pharmacist Menu:");
        System.out.println("1. View Appointment Outcome Record");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");
    }

    // Method to view appointment outcome record
    public void viewAppointmentOutcomeRecord(Scanner scanner) {
        List<String[]> recordList = CSVHandler.readCSV("src/data/Appointment_Outcome_Record.csv");

        if (recordList.size() == 0) {
        	System.out.println("No appointment outcome record found.");
        } else {
        	System.out.println("----Appointment Outcome Record(s)----");
        	for(int i = 0; i < recordList.size(); i++) {
        		if (recordList.get(i)[4].equals("Pending")) {
        			System.out.println(Arrays.toString(recordList.get(i)));
        		}
        	}
        	System.out.println("");
        }
    }

    // Method to view medication inventory
    public void viewMedicationInventory() {
        System.out.println("---- Medication Inventory ----");
        inventoryManagement.viewInventory();
    }

    public static void managePrescription(Scanner scanner, MedicineHandler medicineHandler) {
        List<String[]> recordList = CSVHandler.readCSV("src/data/Appointment_Outcome_Record.csv");
        List<String[]> dispensedList = CSVHandler.readCSV("src/data/Dispensed_Record.csv");
        List<String[]> pendingList = new ArrayList<>();
        List<Medicine> medicineList = medicineHandler.loadMedicine();
        int choice, choice2, amount;
        String[] parts;
        if (recordList.size() == 0) {
        	System.out.println("No appointment outcome record found.\n");
        	return;
        } else {
	        for (int i = 0; i < recordList.size(); i++) {
	            if (recordList.get(i)[4].equals("Pending")) {
	            	pendingList.add(recordList.get(i));
	            }
	        }
        }
        if (pendingList.size() != 0) {
        	System.out.println("----Appointment Outcome Record(s)----");
	        for (int i = 0; i < pendingList.size(); i++) {
            	System.out.println((i + 1) + ". " + Arrays.toString(pendingList.get(i)));
	        }
        } else {
        	System.out.println("No appointment outcome record found.\n");
        	return;
        }
        while (true) {
            System.out.print("\nChoose a record to update (0 to exit): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                } else if (choice >= 1 && choice <= pendingList.size()) {
                    for (int i = 0; i < recordList.size(); i++) {
                        if (recordList.get(i).equals(pendingList.get(choice-1))) {
                            parts = recordList.get(i);
                            System.out.println("----Medications----");
                            for (int j = 0; j < medicineList.size(); j++) {
                                System.out.println((j + 1) + ". " + medicineList.get(j).getMedicineName());
                            }
                            System.out.print("\nChoose a medication to prescribe: ");
                            if (scanner.hasNextInt()) {
                            	choice2 = scanner.nextInt();
                            	if (choice2 >= 1 && choice2 <= medicineList.size()) {
                            		System.out.print("Amount to give: ");
                                    if (scanner.hasNextInt()) {
                                        amount = scanner.nextInt();
                                        if (amount < 1 || amount > medicineList.get(choice2 - 1).getStock()) {
                                            System.out.println("Invalid amount.\n");
                                            return;
                                        }
                                        medicineList.get(choice2 - 1).minusStock(amount);
                                    } else {
                                        System.out.println("Invalid amount.\n");
                                        return;
                                    }
                            	} else {
                            		System.out.println("Invalid medication.\n");
                                    return;
                            	}
                            } else {
                                System.out.println("Invalid medication.\n");
                                return;
                            }
                            recordList.set(i, new String[] {parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + "," + "Dispensed" + "," + parts[5]});
                            dispensedList.add(new String[] {parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + "," + "Dispensed" + "," + amount + " " + medicineList.get(choice2 - 1).getMedicineName()});
                            System.out.println("Update successfully.");
                            medicineHandler.saveMedicine(medicineList);
                            recordList.add(0, new String[]{"Doctor ID,Patient ID,Date,Type of Service, Prescribed Status, Consultation Notes"});
                            dispensedList.add(0, new String[]{"Doctor ID,Patient ID,Date,Type of Service,Prescribed Status,Medications"});
                            CSVHandler.writeCSV("src/data/Dispensed_Record.csv", dispensedList);
                            CSVHandler.writeCSV("src/data/Appointment_Outcome_Record.csv", recordList);
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next();
            }
        }
    }
}
