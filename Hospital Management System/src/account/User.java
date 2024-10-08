package account;

public class User {
	private String uniqueID;
	private String name;
	private String gender;
	private int age;
	private String password;
	private Boolean initialLogin;
	private Role role;
	
	public User(String uniqueID, String name, Role role, String gender, int age, String password) {
		this.uniqueID = uniqueID;
		this.name = name;
		this.role = role;
		this.gender = gender;
		this.age = age;
		this.password = password;
		this.initialLogin = true;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public String getPassword() {
		return password;
	}

	public Boolean getInitialLogin() {
		return initialLogin;
	}

	public Role getRole() {
		return role;
	}
	
	public String toString() {
		return getUniqueID() + " " + getName() + " " + getRole();
	}
}
