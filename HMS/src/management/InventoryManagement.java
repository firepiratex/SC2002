package management;

import handlers.MedicineHandler;
import java.util.List;
import java.util.Scanner;

public class InventoryManagement {

    private List<MedicineManagement> medicineList;
    private MedicineHandler medicineHandler;

    // Constructor that loads the inventory using the handler
    public InventoryManagement() {
        this.medicineHandler = new MedicineHandler();
        this.medicineList = medicineHandler.loadMedicine();
    }

    // View the current inventory
    public void viewInventory() {
        System.out.println("---- Medicine Inventory ----");
        for (MedicineManagement eachMedicine : medicineList) {
            System.out.println(eachMedicine);
        }
    }

    public void inventoryMenu(Scanner scanner) {
        int choice;
        while (true) {
            System.out.println("1. View Inventory of Medicine");
            System.out.println("2. Manage Inventory of Medicine");
            System.out.println("3. Update Medicine Stock Level Alert");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        viewInventory();
                        break;
                    case 2:
                        manageInventory(scanner);
                        break;
                    case 3:
                        manageStockLevelAlert(scanner);
                        break;
                    case 0:
                        return;  // Exit management
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    // Manage the inventory (add or take medicine)
    public void manageInventory(Scanner scanner) {
        int choice;
        while (true) {
            viewInventory();
            System.out.println("1. Add Medicine");
            System.out.println("2. Take Medicine");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        addMedicine(scanner);
                        break;
                    case 2:
                        takeMedicine(scanner);
                        break;
                    case 0:
                        return;  // Exit management
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    // Add medicine to the inventory
    private void addMedicine(Scanner scanner) {
        System.out.println("---- Add Medicine ----");
        System.out.print("Enter medicine name: ");
        String name = scanner.nextLine();
        System.out.print("Enter quantity to add: ");
        int amount = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        for (MedicineManagement medicine : medicineList) {
            if (medicine.getMedicineName().equalsIgnoreCase(name)) {
                medicine.addStock(amount);
                System.out.println(amount + " units of " + name + " added.");
                medicineHandler.saveMedicine(medicineList);
                return;
            }
        }
        System.out.println("Medicine not found.");
    }

    // Take medicine from the inventory
    private void takeMedicine(Scanner scanner) {
        System.out.println("---- Take Medicine ----");
        System.out.print("Enter medicine name: ");
        String name = scanner.nextLine();
        System.out.print("Enter quantity to take: ");
        int amount = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        for (MedicineManagement medicine : medicineList) {
            if (medicine.getMedicineName().equalsIgnoreCase(name)) {
                if (medicine.getStock() >= amount) {
                    medicine.minusStock(amount);
                    System.out.println(amount + " units of " + name + " taken.");
                    medicineHandler.saveMedicine(medicineList);
                } else {
                    System.out.println("Not enough stock available.");
                }
                return;
            }
        }
        System.out.println("Medicine not found.");
    }

    // Update the stock level alert
    public void manageStockLevelAlert(Scanner scanner) {
        System.out.println("---- Update Stock Level Alert ----");

        for (int i = 0; i < medicineList.size(); i++) {
            System.out.println((i + 1) + ". " + medicineList.get(i).getMedicineName() + " (Current Alert: " + medicineList.get(i).getLowStockAlert() + ")");
        }

        System.out.print("Enter the option of the medicine to change: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (choice > 0 && choice <= medicineList.size()) {
            MedicineManagement selectedMedicine = medicineList.get(choice - 1);
            System.out.print("Enter new stock alert level for " + selectedMedicine.getMedicineName() + ": ");
            int newAlertLevel = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            selectedMedicine.setLowStockAlert(newAlertLevel);
            System.out.println("Stock alert updated.");
            medicineHandler.saveMedicine(medicineList);
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
