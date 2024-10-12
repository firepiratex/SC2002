package users;

public class Patient extends User{
	private String DoB; 
	private String email;
	private String BloodType;

	public Patient(String uniqueID, String name, Role role, String gender, int age, String password, boolean initialLogin, String DoB, String email, String BloodType) {
		super(uniqueID, name, role, gender, age, password, initialLogin);
		this.DoB = DoB;
		this.email = email;
		this.BloodType = BloodType;
	}

	public String patientInformation() {
		return "----" + super.getName() + "'s Information----\n"
				+ "Patient ID: " + super.getUniqueID() + "\n"
				+ "Name: " + super.getName() + "\n"
				+ "Date of Birth (YYYY-MM-DD): " + getDoB() + "\n"
				+ "Gender: " + super.getGender() + "\n"
				+ "Email Address: " + getEmail() + "\n"
				+ "Blood Type: " + getBloodType() + "\n";
	}

	public String getDoB() {
		return DoB;
	}

	public String getEmail() {
		return email;
	}

	public String getBloodType() {
		return BloodType;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}