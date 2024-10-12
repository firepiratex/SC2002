package managements;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import handlers.CSVHandler;
import handlers.MenuHandler;
import users.Doctor;
import users.Pharmacist;
import users.Role;
import users.User;

public class StaffManagement {
	public static void viewStaff(Scanner scanner, List<User> staffList) {
		int choice;
		while (true) {
			MenuHandler.filterBy();
			System.out.print("Enter your choice: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 1) {
					filterByRole(staffList, scanner);
					break;
				} else if (choice == 2) {
					filterByGender(staffList, scanner);
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
	
	public static void manageStaff(Scanner scanner, List<User> staffList) {
		int choice;
		while (true) {
			MenuHandler.manageStaff();
			System.out.print("Enter your choice: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 1) {
					addStaff(staffList, scanner);
					break;
				} else if (choice == 2) {
					updateStaff(staffList, scanner);
					break;
				} else if (choice == 3) {
					removeStaff(staffList, scanner);
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
	
	private static void addStaff(List<User> staffList, Scanner scanner) {
		int numberOfDoctor = 0, numberOfPharmacist = 0;
		String uniqueID;
		for (User eachStaff : staffList) {
			if (eachStaff.getRole() == Role.Doctor) {
				numberOfDoctor++;
			}
			if (eachStaff.getRole() == Role.Pharmacist) {
				numberOfPharmacist++;
			}
		}
		while (true) {
			scanner.nextLine();
			System.out.print("Enter the new staff name: ");
			String name = scanner.nextLine();
			String role;
			while(true) {
				System.out.print("Enter the new staff role: ");
				role = scanner.nextLine();
				if (role.toLowerCase().equals("doctor")) {
					role = "Doctor";
					break;
				} else if (role.toLowerCase().equals("pharmacist")) {
					role = "Pharmacist";
					break;
				} else {
					System.out.println("Invalid role. Try again");
				}
			}
			String gender;
			while(true) {
				System.out.print("Enter the new staff gender: ");
				gender = scanner.next();
				if (gender.toLowerCase().equals("male")) {
					gender = "Male";
					break;
				} else if (gender.toLowerCase().equals("female")) {
					gender = "Female";
					break;
				} else {
					System.out.println("Invalid gender. Try again");
				}
			}
			int age;
			while (true) {
				System.out.print("Enter the new staff age: ");
				if (scanner.hasNextInt()) {
					age = scanner.nextInt();
					break;
				} else {
					System.out.println("Invalid age. Try again.");
					scanner.next();
				}
			}
			switch (role) {
				case "Doctor":
					numberOfDoctor++;
					if (numberOfDoctor <= 9) {
						uniqueID = "D00" + String.valueOf(numberOfDoctor);
					} else if (numberOfDoctor >= 10 && numberOfDoctor <= 99) {
						uniqueID = "D0" + String.valueOf(numberOfDoctor);
					} else {
						uniqueID = "D" + String.valueOf(numberOfDoctor);
					}
					User doctor = new Doctor(uniqueID, name, Role.Doctor, gender, age, "password", true);
					staffList.add(doctor);
					break;
				case "Pharmacist":
					numberOfPharmacist++;
					if (numberOfPharmacist <= 9) {
						uniqueID = "P00" + String.valueOf(numberOfPharmacist);
					} else if (numberOfPharmacist >= 10 && numberOfPharmacist <= 99) {
						uniqueID = "P0" + String.valueOf(numberOfPharmacist);
					} else {
						uniqueID = "P" + String.valueOf(numberOfPharmacist);
					}
					User pharmacist = new Pharmacist(uniqueID, name, Role.Pharmacist, gender, age, "password", true);
					staffList.add(pharmacist);
					break;
			}
			break;
		}
		System.out.println("\nStaff added successfully.");
		staffList = staffList.stream()
				.sorted((s1,s2) -> Integer.compare(s1.getRole().ordinal(), s2.getRole().ordinal()))
				.collect(Collectors.toList());
		CSVHandler.saveStaffList(staffList);
		
	}
	
	private static void updateStaff(List<User> staffList, Scanner scanner) {
		int choice, index2;
		while (true) {
			System.out.println("----Staff List----");
			for(int index = 0; index < staffList.size(); index++) {
				if (staffList.get(index).getRole() != Role.Administrator) {
					System.out.println((index+1) + ". " + staffList.get(index));
				}
			}
			System.out.print("\nChoose a staff you want to edit (0 to exit): ");
			if (scanner.hasNextInt()) {
				index2 = scanner.nextInt();
				if (index2 == 0) {
					break;
				}
				index2--;
				if (staffList.get(index2) != null) {
					while(true) {
						MenuHandler.updateStaff();
						System.out.print("Enter your choice: ");
						if (scanner.hasNextInt()) {
							choice = scanner.nextInt();
							if (choice == 1) {
								scanner.nextLine();
								System.out.print("Enter staff new name: ");
								String name = scanner.nextLine();
								staffList.get(index2).setName(name);
								break;
							} else if (choice == 2) {
								System.out.print("Enter staff new role: ");
								String role = scanner.next();
								if (role.toLowerCase().equals("doctor")) {
									staffList.get(index2).setRole(Role.Doctor);
									break;
								} else if (role.toLowerCase().equals("pharmacist")) {
									staffList.get(index2).setRole(Role.Pharmacist);
									break;
								} else {
									System.out.println("Invalid role.");
								}
							} else if (choice == 3) {
								System.out.print("Enter staff new gender: ");
								String gender = scanner.next();
								if (gender.toLowerCase().equals("male")) {
									staffList.get(index2).setGender("Male");
									break;
								} else if (gender.toLowerCase().equals("female")) {
									staffList.get(index2).setGender("Female");
									break;
								} else {
									System.out.println("Invalid gender.");
								}
							} else if (choice == 4) {
								System.out.print("Enter staff new age: ");
								if (scanner.hasNextInt()) {
									int age = scanner.nextInt();
									staffList.get(index2).setAge(age);
									break;
								} else {
									System.out.println("Invalid age.");
								}
							} else {
								System.out.println("Invalid choice.");
							}
						} else {
							System.out.println("Invalid input. Try again.");
			                scanner.next();
						}
					}
					break;
				} else {
					System.out.println("Invalid staff. Try again.");
				}
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
		}
		staffList = staffList.stream()
				.sorted((s1,s2) -> Integer.compare(s1.getRole().ordinal(), s2.getRole().ordinal()))
				.collect(Collectors.toList());
		CSVHandler.saveStaffList(staffList);
	}
	
	private static void removeStaff(List<User> staffList, Scanner scanner) {
		int index2;
		while(true) {
			System.out.println("----Staff List----");
			for(int index = 0; index < staffList.size(); index++) {
				if (staffList.get(index).getRole() != Role.Administrator) {
					System.out.println((index+1) + ". " + staffList.get(index));
				}
			}
			System.out.print("\nChoose a staff you want to remove (0 to exit): ");
			if (scanner.hasNextInt()) {
				index2 = scanner.nextInt();
				if (index2 == 0) {
					break;
				}
				index2--;
				if (staffList.get(index2) != null && staffList.get(index2).getRole() != Role.Administrator) {
					staffList.remove(index2);
					System.out.println("Staff has been removed.");
					break;
				} else {
					System.out.println("Invalid staff");
				}
			} else {
				System.out.println("Invalid input. Try again.");
                scanner.next();
			}
		}
		staffList = staffList.stream()
				.sorted((s1,s2) -> Integer.compare(s1.getRole().ordinal(), s2.getRole().ordinal()))
				.collect(Collectors.toList());
		CSVHandler.saveStaffList(staffList);
	}
	
	private static void filterByRole(List<User> staffList, Scanner scanner) {
		int choice;
		Role role = null;
		while (true) {
			MenuHandler.filterByRole();
			System.out.print("Enter your choice: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 1) {
					role = Role.Doctor;
					break;
				} else if (choice == 2) {
					role = Role.Pharmacist;
					break;
				} else if (choice == 3) {
					role = Role.Administrator;
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
		
		if (role != null) {
			System.out.println("");
			for (User eachStaff : staffList) {
				if (eachStaff.getRole() == role) {
					System.out.println(eachStaff);
				}
			}
		}
	}
	
	private static void filterByGender(List<User> staffList, Scanner scanner) {
		int choice;
		String gender = null;
		while (true) {
			MenuHandler.filterByGender();
			System.out.print("Enter your choice: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				if (choice == 1) {
					gender = "Male";
					break;
				} else if (choice == 2) {
					gender = "Female";
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
		
		if (gender != null) {
			System.out.println("");
			for (User eachStaff : staffList) {
				if (eachStaff.getGender().equals(gender)) {
					System.out.println(eachStaff);
				}
			}
		}
	}
}
