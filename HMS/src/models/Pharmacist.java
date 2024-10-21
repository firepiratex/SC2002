package models;

import java.util.List;
import management.AppointmentOutcomeRecord;
import management.InventoryManagement;

public class Pharmacist extends User {

    private int age;

    public Pharmacist(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Pharmacist", gender);
        this.age = age;
    }

    public void viewAppointmentOutcome(AppointmentOutcomeRecord appointmentOutcomeRecord, String appointmentId) {
        //appointmentOutcomeRecord.displayOutcome(appointmentId);
    }

    public void updatePrescriptionStatus(AppointmentOutcomeRecord appointmentOutcomeRecord, String appointmentId, String status) {
        //appointmentOutcomeRecord.updatePrescriptionStatus(appointmentId, status);
    }

    public void viewInventory(InventoryManagement inventoryManagement) {
        inventoryManagement.viewInventory();
    }

    public void submitReplenishmentRequest(InventoryManagement inventoryManagement, String medicineName) {
        // inventoryManagement.submitReplenishmentRequest(medicineName);
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
}
