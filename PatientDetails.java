public class PatientDetails {

    //check the access levels again
    protected int hospitalID;
    protected int patientID =1;
    protected String name;
    protected String DoB;
    protected boolean gender; // true = male , false = female
    protected String password = "password";
    protected int phoneNum;
    protected String email;
    protected String bloodType= "B";
    protected String PDAT ="asthma"; //past diagnosis and treatment

    
    public PatientDetails(int patientID){
        this.patientID = patientID;
        this.name = name;
        this.DoB = DoB;
        this.gender = gender;
        this.password = "password";
        this.phoneNum = phoneNum;
        this.email = email;
        this.bloodType = bloodType;
        this.PDAT = PDAT;
    }

    public String getPDAT(){
        return PDAT;
    }

    public String getBloodType(){
        return bloodType;
    }

    public void UpdatePersonalInfo(int patientID){
        this.patientID = patientID;
        this.name = name;
        this.DoB = DoB;
        this.gender = gender;
        this.password = password;
        this.phoneNum = phoneNum;
        this.email = email;
    }
}
