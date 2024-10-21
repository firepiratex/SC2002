package management;

import java.util.*;

public class InventoryManagement {
    private Map<String, Integer> inventory;
    private List<String> replenishmentRequests;

    public InventoryManagement() {
        this.inventory = new HashMap<>();
        this.replenishmentRequests = new ArrayList<>();
        // Load inventory from CSV if needed
    }

    public void displayInventory() {
        System.out.println("Medicine Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Medicine: " + entry.getKey() + ", Stock: " + entry.getValue());
        }
    }

    public void submitReplenishmentRequest(String medicineName) {
        replenishmentRequests.add(medicineName);
        System.out.println("Replenishment request submitted for: " + medicineName);
    }

    public void approveReplenishmentRequest(String medicineName) {
        if (replenishmentRequests.contains(medicineName)) {
            replenishmentRequests.remove(medicineName);
            inventory.put(medicineName, inventory.getOrDefault(medicineName, 0) + 100); // Restocking 100 units
            System.out.println("Replenishment request approved for: " + medicineName);
        } else {
            System.out.println("No replenishment request found for: " + medicineName);
        }
    }
}
