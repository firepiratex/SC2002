package models;

public class Prescription {
    private String prescriptionId;
    private String medicineName;
    private int quantity;
    private String status;  // e.g., Pending, Dispensed

    public Prescription(String prescriptionId, String medicineName, int quantity) {
        this.prescriptionId = prescriptionId;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.status = "Pending";  // Default status
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
