package models;

import handlers.CSVHandler;
import handlers.MedicineHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import management.InventoryManagement;
import management.MedicalRecordManagement;
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
        System.out.print("Enter patient ID to view outcome: ");
        String patientId = scanner.nextLine();
        // Read the CSV file to check if there are any records for the patient
        List<String[]> recordList = CSVHandler.readCSV("HMS/src/data/Appointment_Outcome_Record.csv");

        // Filter the records to check if there's a match for the patient
        boolean hasRecord = false;
        for (String[] record : recordList) {
            if (record[1].equals(patientId)) {  // Assuming patient ID is at index 1
                hasRecord = true;
                break;
            }
        }

// If record exists, view the patient medical record, otherwise show a message
        if (hasRecord) {
            // Create a temporary Patient object with the patient ID
            Patient tempPatient = new Patient(patientId, "TemporaryName", "password", "UnknownGender", "DOB", "Address", "Phone");

            // Call the method from MedicalRecordManagement (static method call)
            MedicalRecordManagement.viewPatientMedicalRecord(tempPatient);
        } else {
            System.out.println("No appointment outcome record found for Patient ID: " + patientId);
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
		List<MedicineManagement> medicineList = medicineHandler.loadMedicine();

        int choice, medication, amount;
        String[] parts;
        String row = "";
        String[] updatedRow;
        System.out.println("----Patient Medical Records----");
        for (int i = 0; i < recordList.size(); i++) {
            System.out.println((i + 1) + ". " + Arrays.toString(recordList.get(i)));
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
                            updatedRow = row.split(",");  // Convert the string back to a String array
                            recordList.set(i, updatedRow);  // Update the record
                            System.out.println("----Medications----");
                            for (int j = 0; j < medicineList.size(); j++) {
                                System.out.println((j + 1) + ". " + medicineList.get(j).getMedicineName());
                            }
                            System.out.print("\nChoose a medication to prescribe: ");
                            if (scanner.hasNextInt()) {
                                medication = scanner.nextInt();
                                System.out.print("Amount to give: ");
                                if (scanner.hasNextInt()) {
                                    amount = scanner.nextInt();
                                    if (amount < 1 || amount > medicineList.get(medication - 1).getStock()) {
                                        System.out.println("Invalid amount.");
                                        return;
                                    }
                                    medicineList.get(medication - 1).minusStock(amount);
                                } else {
                                    System.out.println("Invalid amount.");
                                    return;
                                }
                            } else {
                                System.out.println("Invalid medication.");
                                return;
                            }
                            row = parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + ",dispensed," + parts[5];
                            updatedRow = row.split(",");  // Assuming row is a CSV-formatted string
                            recordList.set(i, updatedRow);
							System.out.println("Update successfully.");
							medicineHandler.saveMedicine(medicineList);  // Assuming saveMedicine is an instance method
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
