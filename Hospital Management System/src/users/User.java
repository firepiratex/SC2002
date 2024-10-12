package users;

public class User {
	private String uniqueID;
	private String name;
	private String gender;
	private String password;
	private boolean initialLogin;
	private int age;
	private Role role;
	
	public User(String uniqueID, String name, Role role, String gender, int age, String password, boolean initialLogin) {
		this.uniqueID = uniqueID;
		this.name = name;
		this.role = role;
		this.gender = gender;
		this.age = age;
		this.password = password;
		this.initialLogin = initialLogin;
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

	public Role getRole() {
		return role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getInitialLogin() {
		return initialLogin;
	}

	public void setInitialLogin(boolean initialLogin) {
		this.initialLogin = initialLogin;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String toString() {
		return getUniqueID() + " " + getName() + " " + getRole();
	}
}
