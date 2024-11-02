package models;

public class Appointment {
    private String doctorId;
    private String patientId;
    private String date;
    private String time;
    private String status;
    private String outcome;

    public Appointment(String patientId, String doctorId, String status, String date, String time, String outcome) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.status = status;
        this.date = date;
        this.time = time;
        this.outcome = outcome;
    }

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
    
    public String toString() {
    	return getPatientId() + "," + getDoctorId() + "," + getStatus() + "," + getDate() + "," + getTime() + "," + getOutcome();
    }
}
