package account;

public class Patient extends User{
	private String DoB; 
	private String email;
	private String BloodType;

	public Patient(String uniqueID, String name, Role role, String gender, int age, String password, String DoB, String email, String BloodType) {
		super(uniqueID, name, role, gender, age, password);
		this.DoB = DoB;
		this.email = email;
		this.BloodType = BloodType;
	}


}