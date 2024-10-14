package models;

public class Prescription {
    private String medicineName;
    private String status;

    public Prescription(String medicineName) {
        this.medicineName = medicineName;
        this.status = "Pending"; // Default status
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void viewPrescription() {
        System.out.println("Medicine: " + medicineName);
        System.out.println("Status: " + status);
    }
}
