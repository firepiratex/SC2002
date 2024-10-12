package users;

public class Doctor extends User{
	public Doctor(String uniqueID, String name, Role role, String gender, int age, String password, boolean initialLogin) {
		super(uniqueID, name, role, gender, age, password, initialLogin);
	}
}
