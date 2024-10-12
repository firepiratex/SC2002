import java.util.*;

import handlers.LoginHandler;
import handlers.MedicineHandler;
import handlers.MenuHandler;
import handlers.UserHandler;
import medicine.Medicine;
import users.User;

public class HospitalApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HospitalApp.run();
	}
	
	public static void run() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			List<User> staffList = UserHandler.loadStaff("Staff_List.csv");
			List<User> patientList = UserHandler.loadPatient("Patient_List.csv");
			List<Medicine> medicineList = MedicineHandler.loadMedicine("Medicine_List.csv");
			User authenticatedUser = LoginHandler.login(staffList, patientList);
			System.out.println(authenticatedUser.getUniqueID() + " " + authenticatedUser.getName());
			MenuHandler.handleUser(authenticatedUser, staffList, patientList, medicineList, scanner);
		}
	}
}
