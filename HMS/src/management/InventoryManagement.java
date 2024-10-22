package management;

import handlers.CSVHandler;
import handlers.InventoryHandler;
import handlers.MedicineHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import models.User;

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
            if (eachMedicine.getStock() > eachMedicine.getLowStockAlert()) {
				System.out.println(eachMedicine);
			} else {
				System.out.println(eachMedicine + " (Low Stock Level)");
			}
        }
        return;
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

    public void submitReplenishmentRequest(User pharmacist, Scanner scanner) {
        List<String[]> requestList = CSVHandler.readCSV("src/data/Replenishment_Request.csv");
        List<String> lowMedicineStock = new ArrayList<>();
        int choice, amount;
        String[] row;
    
        // Check for low stock medicines
        for (MedicineManagement eachMedicine : medicineList) {
            if (eachMedicine.getStock() < eachMedicine.getLowStockAlert()) {
                lowMedicineStock.add(eachMedicine.getMedicineName());
            }
        }
    
        // If no medicines are low in stock
        if (lowMedicineStock.isEmpty()) {
            System.out.println("No medications are low in level.");
            return;
        }
    
        // Display low stock medicines
        System.out.println("----Low Stock Level Medicine----");
        for (int i = 0; i < lowMedicineStock.size(); i++) {
            System.out.println((i + 1) + ". " + lowMedicineStock.get(i));
        }
        System.out.println("0. Exit");
    
        // Handle user input and submission
        while (true) {
            System.out.print("\nEnter the medicine no. you want to submit a request for: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                } else if (choice >= 1 && choice <= lowMedicineStock.size()) {
                    System.out.print("Amount to request: ");
                    if (scanner.hasNextInt()) {
                        amount = scanner.nextInt();
                        if (amount < 1) {
                            System.out.println("Amount must be more than 0.");
                            break;
                        }
    
                        // Prepare row for CSV request
                        row = new String[]{
                            pharmacist.getId(),  // Pharmacist ID
                            lowMedicineStock.get(choice - 1),  // Medicine name
                            String.valueOf(amount)  // Amount to replenish
                        };
                        requestList.add(row);  // Add the replenishment request
    
                        System.out.println("Request submitted successfully.");
    
                        // Save the replenishment request with updated requestList
                        InventoryHandler.saveReplenishmentRequest(requestList);
                        return;
                    } else {
                        System.out.println("Invalid input. Try again.");
                        scanner.next();  // Clear invalid input
                    }
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next();  // Clear invalid input
            }
        }
    }

    public void manageReplenishmentRequest(Scanner scanner) {
        List<String[]> requestList = CSVHandler.readCSV("src/data/Replenishment_Request.csv");
        int choice, option;

        if (requestList.isEmpty()) {
            System.out.println("No request available.");
            return;
        }

        System.out.println("----Request List----");
        for (int i = 0; i < requestList.size(); i++) {
            System.out.println((i + 1) + ". " + Arrays.toString(requestList.get(i)));  // Displaying each request
        }
        System.out.println("0. Exit\n");

        while (true) {
            System.out.print("Choose a request to manage: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 0) {
                    return;
                } else if (choice >= 1 && choice <= requestList.size()) {
                    while (true) {
                        System.out.println("1. Approve\n" + "2. Reject\n" + "0. Exit");
                        System.out.print("\nEnter a choice: ");
                        if (scanner.hasNextInt()) {
                            option = scanner.nextInt();
                            if (option == 0) {
                                return;
                            } else if (option == 1) {
                                String[] parts = requestList.get(choice - 1);  // Correct: parts is a String[]
                                System.out.println(medicineList);
                                for (int i = 0; i < medicineList.size(); i++) {
                                    if (parts[1].equals(medicineList.get(i).getMedicineName())) {
                                        requestList.remove(choice - 1);
                                        medicineList.get(i).addStock(Integer.parseInt(parts[2]));  // Correct parse to int
                                        InventoryHandler.saveReplenishmentRequest(requestList);

                                        // Fix: create an instance of MedicineHandler to call the non-static method
                                        MedicineHandler medicineHandler = new MedicineHandler();
                                        medicineHandler.saveMedicine(medicineList);
                                        System.out.println("Request approved and stock updated.");
                                        return;
                                    }
                                }
                            } else if (option == 2) {
                                requestList.remove(choice - 1);
                                InventoryHandler.saveReplenishmentRequest(requestList);
                                System.out.println("Request rejected.");
                                return;
                            }
                        } else {
                            System.out.println("Invalid input. Try again.");
                            scanner.next();  // Clear invalid input
                        }
                    }
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next();  // Clear invalid input
            }
        }
    }
}
