package models;

import java.util.HashMap;

public class InventoryManager {
    private static HashMap<String, Integer> inventory = new HashMap<>();

    public static void initializeInventory() {
        // Logic to read inventory data from file and initialize inventory map
    }

    public static void viewInventory() {
        // Logic to display inventory items
    }

    public static void updateInventory(String medicineName, int quantity) {
        // Logic to update inventory
    }
}
