package users;

public class Pharmacist extends User{
	public Pharmacist(String uniqueID, String name, Role role, String gender, int age, String password, boolean initialLogin) {
		super(uniqueID, name, role, gender, age, password, initialLogin);
	}
}
