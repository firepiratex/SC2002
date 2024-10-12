package managements;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import handlers.CSVHandler;
import handlers.MedicineHandler;
import handlers.MenuHandler;
import medicine.Medicine;
import users.User;

public class InventoryManagement {
	public static void viewInventory(List<Medicine> medicineList) {
		System.out.println("");
		for(Medicine eachMedicine : medicineList) {
			if (eachMedicine.getStock() > eachMedicine.getLowStockAlert()) {
				System.out.println(eachMedicine);
			} else {
				System.out.println(eachMedicine + " (Low Stock Level)");
			}
		}
	}
	
	public static void submitReplenishmentRequest(User pharmacist, Scanner scanner, List<Medicine> medicineList) {
		List<String> requestList = CSVHandler.loadFile("Replenishment_Request.csv");
		List<String> lowMedicineStock = new ArrayList<>();
		int choice, amount;
		String row;
		for (Medicine eachMedicine : medicineList) {
			if (eachMedicine.getStock() < eachMedicine.getLowStockAlert()) {
				lowMedicineStock.add(eachMedicine.getMedicineName());
			}
		}
		if (lowMedicineStock.size() == 0) {
			System.out.println("No medications that are low in level.");
			return;
		}
		System.out.println("----Low Stock Level Medicine----");
		for (int i = 0; i < lowMedicineStock.size(); i++) {
			System.out.println((i+1) + ". " + lowMedicineStock.get(i));
		}
		System.out.println("0. Exit");
		while (true) {
			System.out.print("\nEnter the medicine no. you want to submit request: ");
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
						row = pharmacist.getUniqueID() + "," + lowMedicineStock.get(choice-1) + "," + amount;
						requestList.add(row);
						System.out.println("Request submitted successfully.");
						CSVHandler.saveReplenishmentRequest(requestList);
						return;
					} else {
						System.out.println("Invalid input. Try again.");
					}
				} else {
					System.out.println("Invalid choice. Try again.");
				}
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
		}
	}
	
	public static void manageReplenishmentRequest(Scanner scanner, List<Medicine> medicineList) {
		List<String> requestList = CSVHandler.loadFile("Replenishment_Request.csv");
		int choice, option;
		String[] parts;
		if (requestList.size() == 0) {
			System.out.println("No request available.");
			return;
		}
		System.out.println("----Request List----");
		for(int i = 0; i < requestList.size(); i++) {
			System.out.println((i+1) + ". " + requestList.get(i));
		}
		System.out.println("0. Exit\n");
		while(true) {
			System.out.print("Choose a request to manage: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					return;
				} else if (choice >= 1 && choice <= requestList.size()) {
					while(true) {
						System.out.println("1. Approve\n"
										+ "2. Reject\n"
										+ "0. Exit");
						System.out.print("\nEnter a choice: ");
						if (scanner.hasNextInt()) {
							option = scanner.nextInt();
							if (option == 0) {
								return;
							} else if (option == 1) {
								parts = requestList.get(choice-1).split(",");
								System.out.println(medicineList);
								for(int i = 0; i < medicineList.size(); i++) {
									if (parts[1].equals(medicineList.get(i).getMedicineName())) {
										requestList.remove(choice-1);
										medicineList.get(i).addStock(Integer.valueOf(parts[2]));
										CSVHandler.saveReplenishmentRequest(requestList);
										MedicineHandler.saveMedicine(medicineList);
										return;
									}
								}
							} else if (option == 2) {
								requestList.remove(choice-1);
								CSVHandler.saveReplenishmentRequest(requestList);
								return;
							}
						} else {
							System.out.println("Invalid input. Try again.");
			                scanner.next();
						}
					}
				} else {
					System.out.println("Invalid choice. Try again.");
				}
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
		}
	}
	
	public static void manageInventory(Scanner scanner, List<Medicine> medicineList) {
		int choice;
		while (true) {
			MenuHandler.manageMedicine();
			System.out.print("Enter your choice: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 1) {
					addMedicine(medicineList, scanner);
					break;
				} else if (choice == 2) {
					takeMedicine(medicineList, scanner);
					break;
				} else if (choice == 0) {
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
	
	public static void manageStockLevelAlert(Scanner scanner, List<Medicine> medicineList) {
		int choice, amount;
		Boolean updateFinish = false;
		System.out.println("----Current Stock Level Alert----");
		for(int i = 0; i < medicineList.size(); i++) {
			System.out.println((i+1) + ". " + medicineList.get(i).getMedicineName() + " (" + medicineList.get(i).getLowStockAlert() + ")");
		}
		while(true) {
			System.out.print("\nEnter the medicine no. you want to update: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				for(int i = 1; i <= medicineList.size(); i++) {
					if (i == choice) {
						if (medicineList.get(i-1) != null) {
							System.out.print("Enter the new stock level alert for " + medicineList.get(i-1).getMedicineName() + ": ");
							if (scanner.hasNextInt()) {
								amount = scanner.nextInt();
								if (amount <= 0) {
									break;
								}
								medicineList.get(i-1).setLowStockAlert(amount);
								System.out.println("\nUpdate successfully");
								updateFinish = true;
								break;
							} else {
								System.out.println("Invalid input. Try again.");
				                scanner.next();
				                break;
							}
						} else {
							System.out.println("Invalid input. Try again.");
							break;
						}
					}
				}
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
			if (updateFinish) {
				MedicineHandler.saveMedicine(medicineList);
				break;
			}
			System.out.println("Invalid input. Try again.");
		}
	}
	
	private static void addMedicine(List<Medicine> medicineList, Scanner scanner) {
		int choice, amount;
		Boolean updateFinish = false;
		while(true) {
			System.out.println("----Add Medicine----");
			for(int i = 0; i < medicineList.size(); i++) {
				System.out.println((i+1) + ". " + medicineList.get(i).getMedicineName());
			}
			System.out.println("0. Exit\n");
			System.out.print("Enter your choice: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					break;
				}
				for(int i = 1; i <= medicineList.size(); i++) {
					if (i == choice) {
						if (medicineList.get(i-1) != null) {
							System.out.print("How many " + medicineList.get(i-1).getMedicineName() + " do you want to add: ");
							if (scanner.hasNextInt()) {
								amount = scanner.nextInt();
								if (amount <= 0) {
									break;
								}
								medicineList.get(i-1).addStock(amount);
								System.out.println("\nAdded successfully");
								updateFinish = true;
								break;
							} else {
								System.out.println("Invalid input. Try again.");
				                scanner.next();
				                break;
							}
						} else {
							System.out.println("Invalid input. Try again.");
							break;
						}
					}
				}
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
			if (updateFinish) {
				MedicineHandler.saveMedicine(medicineList);
				break;
			}
		}
	}
	
	private static void takeMedicine(List<Medicine> medicineList, Scanner scanner) {
		int choice, amount;
		Boolean updateFinish = false;
		while(true) {
			System.out.println("----Take Medicine----");
			for(int i = 0; i < medicineList.size(); i++) {
				System.out.println((i+1) + ". " + medicineList.get(i).getMedicineName());
			}
			System.out.println("0. Exit\n");
			System.out.print("Enter your choice: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 0) {
					break;
				}
				for(int i = 1; i <= medicineList.size(); i++) {
					if (i == choice) {
						if (medicineList.get(i-1) != null) {
							System.out.print("How many " + medicineList.get(i-1).getMedicineName() + " do you want to take: ");
							if (scanner.hasNextInt()) {
								amount = scanner.nextInt();
								if (amount <= 0) {
									break;
								}
								if (amount > medicineList.get(i-1).getStock()) {
									System.out.println("\nTake not successful. Trying to take more than the stock.\n");
									break;
								}
								medicineList.get(i-1).minusStock(amount);
								System.out.println("\nTake successfully");
								updateFinish = true;
								break;
							} else {
								System.out.println("Invalid input. Try again.");
				                scanner.next();
				                break;
							}
						} else {
							System.out.println("Invalid input. Try again.");
							break;
						}
					}
				}
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
			if (updateFinish) {
				MedicineHandler.saveMedicine(medicineList);
				break;
			}
		}
	}
}
