package handlers;

import java.util.*;

public class InventoryManagement {
    private Map<String, Integer> inventory;
    private List<String> replenishmentRequests;

    public InventoryManagement() {
        this.inventory = new HashMap<>();
        this.replenishmentRequests = new ArrayList<>();
        loadInventory();
    }

    public void loadInventory() {
        // Load inventory data (this is a simulation)
        inventory.put("Paracetamol", 100);
        inventory.put("Ibuprofen", 50);
        inventory.put("Amoxicillin", 75);
    }

    public void displayInventory() {
        System.out.println("Medication Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Medicine: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }

    public void approveReplenishmentRequest(String medicineName) {
        if (replenishmentRequests.contains(medicineName)) {
            System.out.println("Replenishment request for " + medicineName + " approved.");
            replenishmentRequests.remove(medicineName);
            inventory.put(medicineName, inventory.getOrDefault(medicineName, 0) + 50);  // Add 50 units
        } else {
            System.out.println("No replenishment request found for " + medicineName);
        }
    }
}
