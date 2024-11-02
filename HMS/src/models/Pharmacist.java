package models;

import handlers.CSVHandler;
import handlers.MedicineHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import management.InventoryManagement;
import management.PrescriptionManagement;
import management.ReplenishmentManagement;

public class Pharmacist extends User {

    private InventoryManagement inventoryManagement;
    private PrescriptionManagement prescriptionManagement;
    private ReplenishmentManagement replenishmentManagement;
    private int age;

    public Pharmacist(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Pharmacist", gender);
        this.age = age;
        new MedicineHandler();
    }

    public int getAge() {
        return age;
    }

    // Managing prescriptions (ensure Prescription is imported)
    public void dispensePrescription(List<Prescription> prescriptions) {
        for (Prescription prescription : prescriptions) {
            prescription.setStatus("Dispensed");
            System.out.println("Prescription for " + prescription.getMedicineName() + " dispensed.");
        }
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
        		if (recordList.get(i)[4].equals("pending")) {
        			System.out.println(Arrays.toString(recordList.get(i)));
        		}
        	}
        	System.out.println("");
        }
    }

    // Method to update prescription status
    public void updatePrescriptionStatus(Scanner scanner) {
        System.out.print("Enter prescription ID to update: ");
        String prescriptionId = scanner.nextLine();
        System.out.print("Enter new prescription status (Filled/Unfilled): ");
        String status = scanner.nextLine();
        boolean updated = prescriptionManagement.updatePrescriptionStatus(prescriptionId, status);
        if (updated) {
            System.out.println("Prescription status updated successfully.");
        } else {
            System.out.println("Failed to update prescription status.");
        }
    }

    // Method to view medication inventory
    public void viewMedicationInventory() {
        System.out.println("---- Medication Inventory ----");
        inventoryManagement.viewInventory();
    }

    // Method to submit a replenishment request
    public void submitReplenishmentRequest(Scanner scanner) {
        System.out.print("Enter medicine name for replenishment: ");
        String medicineName = scanner.nextLine();
        System.out.print("Enter quantity to request: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean requestSubmitted = replenishmentManagement.submitRequest(medicineName, quantity);
        if (requestSubmitted) {
            System.out.println("Replenishment request submitted successfully.");
        } else {
            System.out.println("Failed to submit replenishment request.");
        }
    }

    public static void managePrescription(Scanner scanner, MedicineHandler medicineHandler) {
        List<String[]> recordList = CSVHandler.readCSV("src/data/Appointment_Outcome_Record.csv");
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
	        for (int i = 0; i < recordList.size(); i++) {
	            if (recordList.get(i)[4].equals("Pending")) {
	            	System.out.println((i + 1) + ". " + Arrays.toString(recordList.get(i)));
	            }
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
                } else if (choice >= 1 && choice <= recordList.size()) {
                    for (int i = 0; i < recordList.size(); i++) {
                        if (recordList.get(i).equals(recordList.get(choice - 1))) {
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
                            recordList.set(i, new String[] {parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + ","+ amount + " " + medicineList.get(choice2-1).getMedicineName() + "," + parts[5]});
                            System.out.println("Update successfully.");
                            medicineHandler.saveMedicine(medicineList);
                            recordList.add(0, new String[]{"Doctor ID,Patient ID,Date,Type of Service, Prescribed Medications, Consultation Notes"});
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
