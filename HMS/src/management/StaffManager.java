package management;

import java.util.List;
import java.util.Scanner;

import handlers.PasswordHash;
import handlers.StaffHandler;
import models.Administrator;
import models.Doctor;
import models.Pharmacist;
import models.User;

public class StaffManager {
    
    private Administrator admin;  // Administrator who manages staff

    // Constructor for StaffManager
    public StaffManager(Administrator admin) {
        this.admin = admin;
    }

    // Manage staff menu
    public void manageStaff(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Manage Hospital Staff:");
            System.out.println("1. Add Staff");
            System.out.println("2. Remove Staff");
            System.out.println("3. View Staff");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addStaffHandler(scanner);  // Handles adding a staff member
                    break;
                case 2:
                    removeStaffHandler(scanner);  // Handles removing a staff member
                    break;
                case 3:
                	viewStaffMenu();
                    System.out.print("Enter your choice (0 to exit): ");
                    if (scanner.hasNextInt()) {
                    	choice = scanner.nextInt();
                    	if (choice == 0) {
                    		return;
                    	} else if (choice >= 1 && choice <= 3) {
                    		if (choice == 1) {
                    			filterByRole(scanner);
                    		} else if (choice == 2) {
                    			filterByGender(scanner);
                    		} else if (choice == 3) {
                    			filterByAge();
                    		}
                    	} else {
                    		System.out.println("Invalid choice.");
                    	}
                    } else {
                    	System.out.println("Invalid input.");
                    }
                    break;
                case 4:
                    exit = true;  // Back to the main menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handles adding a staff member
    private void addStaffHandler(Scanner scanner) {
        String id = promptForInput("Enter staff ID: ", scanner);
        String name = promptForInput("Enter staff name: ", scanner);
        String gender = promptForInput("Enter staff gender: ", scanner);
        int age = promptForAge("Enter staff age: ", scanner);
        String role = promptForInput("Enter staff role (Doctor, Pharmacist, Administrator): ", scanner);
        String password = PasswordHash.hash("password");
        User newStaff = createStaffByRole(id, name, password, gender, age, role);

        if (newStaff != null) {
            admin.addStaff(newStaff);  // Add the new staff member
            System.out.println("New staff added: " + newStaff.getName() + " (" + newStaff.getRole() + ")");
        } else {
            System.out.println("Invalid role! Please enter Doctor, Pharmacist, or Administrator.");
        }
    }

    // Handles removing a staff member
    private void removeStaffHandler(Scanner scanner) {
        String staffId = promptForInput("Enter staff ID to remove: ", scanner);
        admin.removeStaff(staffId);
    }

    // Helper method to prompt for string input
    private String promptForInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Helper method to prompt for integer input (age)
    private int promptForAge(String prompt, Scanner scanner) {
        System.out.print(prompt);
        int age = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        return age;
    }

    // Helper method to create staff based on role
    private User createStaffByRole(String id, String name, String password, String gender, int age, String role) {
        switch (role) {
            case "Doctor":
                return new Doctor(id, name, password, gender, age);
            case "Pharmacist":
                return new Pharmacist(id, name, password, gender, age);
            case "Administrator":
                return new Administrator(id, name, password, gender, age);
            default:
                return null;  // Invalid role
        }
    }
    
    private void viewStaffMenu() {
    	System.out.println("----Filter Staff----");
    	System.out.println("1. By Role");
        System.out.println("2. By Gender");
        System.out.println("3. By Age");
        System.out.println("");
    }
    
    private void filterByRole(Scanner scanner) {
    	int choice;
    	List<User> staffList = StaffHandler.getInstance().getStaffList();
    	System.out.println("----Role(s)----");
    	System.out.println("1. Doctor");
    	System.out.println("2. Pharmacist");
    	System.out.println("3. Administrator");
    	System.out.println("0. Exit");
    	System.out.print("\nChoose an option: ");
    	if (scanner.hasNextInt()) {
    		choice = scanner.nextInt();
    		if (choice == 0) {
    			return;
    		} else if (choice >= 1 && choice <= 3) {
    			if (choice == 1) {
    				staffList.stream()
                    .filter(user -> user.getRole().equals("Doctor"))
                    .forEach(user -> {
                        Doctor doctor = (Doctor) user;
                        System.out.println(doctor);
                    });
    			} else if (choice == 2) {
    				staffList.stream()
                    .filter(user -> user.getRole().equals("Pharmacist")) 
                    .forEach(user -> {
                    	Pharmacist pharmacist = (Pharmacist) user;
                    	System.out.println(pharmacist);
                    });
    			} else if (choice == 3) {
    				staffList.stream()
                    .filter(user -> user.getRole().equals("Administrator")) 
                    .forEach(user -> {
                    	Administrator admin = (Administrator) user;
                    	System.out.println(admin);
                    });
    			}
    		} else {
    			System.out.println("Invalid choice.");
    		}
    	} else {
    		System.out.println("Invalid input.");
    	}
    }
    
    private void filterByGender(Scanner scanner) {
    	int choice;
    	List<User> staffList = StaffHandler.getInstance().getStaffList();
    	System.out.println("----Role(s)----");
    	System.out.println("1. Male");
    	System.out.println("2. Female");
    	System.out.println("0. Exit");
    	System.out.print("\nChoose an option: ");
    	if (scanner.hasNextInt()) {
    		choice = scanner.nextInt();
    		if (choice == 0) {
    			return;
    		} else if (choice >= 1 && choice <= 2) {
    			if (choice == 1) {
    				staffList.stream()
                    .filter(user -> user.getGender().equals("Male"))
                    .forEach(user -> {
                    	if (user.getRole().equals("Doctor")) {
                    		Doctor doctor = (Doctor) user;
                            System.out.println(doctor);
                    	} else if (user.getRole().equals("Pharmacist")) {
                    		Pharmacist pharmacist = (Pharmacist) user;
                        	System.out.println(pharmacist);
                    	} else {
                    		Administrator admin = (Administrator) user;
                        	System.out.println(admin);
                    	}
                    });
    			} else if (choice == 2) {
    				staffList.stream()
                    .filter(user -> user.getGender().equals("Female"))
                    .forEach(user -> {
                    	if (user.getRole().equals("Doctor")) {
                    		Doctor doctor = (Doctor) user;
                            System.out.println(doctor);
                    	} else if (user.getRole().equals("Pharmacist")) {
                    		Pharmacist pharmacist = (Pharmacist) user;
                        	System.out.println(pharmacist);
                    	} else {
                    		Administrator admin = (Administrator) user;
                        	System.out.println(admin);
                    	}
                    });
    			}
    		} else {
    			System.out.println("Invalid choice.");
    		}
    	} else {
    		System.out.println("Invalid input.");
    	}
    }
    
    private void filterByAge() {
    	int choice;
    	List<User> staffList = StaffHandler.getInstance().getStaffList();
    	staffList.stream()
        .sorted((s1, s2) -> {
            int age1 = 0;
            int age2 = 0;

            if (s1 instanceof Doctor) {
                age1 = ((Doctor) s1).getAge();
            } else if (s1 instanceof Pharmacist) {
                age1 = ((Pharmacist) s1).getAge();
            } else if (s1 instanceof Administrator) {
                age1 = ((Administrator) s1).getAge();
            }

            if (s2 instanceof Doctor) {
                age2 = ((Doctor) s2).getAge();
            } else if (s2 instanceof Pharmacist) {
                age2 = ((Pharmacist) s2).getAge();
            } else if (s2 instanceof Administrator) {
                age2 = ((Administrator) s2).getAge();
            }

            return Integer.compare(age1, age2);
        })
        .forEach(System.out::println);
    }
}
