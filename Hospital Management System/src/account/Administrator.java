package account;

public class Administrator extends User{
	public Administrator(String uniqueID, String name, Role role, String gender, int age, String password) {
		super(uniqueID, name, role, gender, age, password);
	}
}
