package users;

public class Administrator extends User{
	public Administrator(String uniqueID, String name, Role role, String gender, int age, String password, boolean initialLogin) {
		super(uniqueID, name, role, gender, age, password, initialLogin);
	}
}
