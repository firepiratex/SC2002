package models;
/**
 * Represents an appointment between a doctor and a patient.
 * This class includes details such as the doctor ID, patient ID, date, time, status, and outcome of the appointment.
 */
public class Appointment {
    private String doctorId;
    private String patientId;
    private String date;
    private String time;
    private String status;
    private String outcome;
    /**
     * Constructs an Appointment object with the specified attributes.
     *
     * @param patientId the unique identifier of the patient
     * @param doctorId  the unique identifier of the doctor
     * @param status    the status of the appointment
     * @param date      the date of the appointment
     * @param time      the time of the appointment
     * @param outcome   the outcome of the appointment
     */
    public Appointment(String patientId, String doctorId, String status, String date, String time, String outcome) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.status = status;
        this.date = date;
        this.time = time;
        this.outcome = outcome;
    }
    /**
     * Retrieves the doctor's ID associated with the appointment.
     *
     * @return the doctor's ID
     */
	public String getDoctorId() {
		return doctorId;
	}
	/**
     * Sets the doctor's ID for the appointment.
     *
     * @param doctorId which is used for the appointment
     */
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	/**
     * Retrieves the patient's ID associated with the appointment.
     *
     * @return the patient's ID
     */
	public String getPatientId() {
		return patientId;
	}
	/**
     * Sets the patient's ID for the appointment.
     *
     * @param patientId which is used for the appointment
     */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	/**
     * Retrieves the date of the appointment.
     *
     * @return the date of the appointment
     */
	public String getDate() {
		return date;
	}
	/**
     * Sets the date for the appointment.
     *
     * @param date the new date for the appointment
     */
	public void setDate(String date) {
		this.date = date;
	}
	/**
     * Retrieves the time of the appointment.
     *
     * @return the time of the appointment
     */
	public String getTime() {
		return time;
	}
	/**
     * Sets the time for the appointment.
     *
     * @param time the new time for the appointment
     */
	public void setTime(String time) {
		this.time = time;
	}
	/**
     * Retrieves the status of the appointment.
     *
     * @return the status of the appointment
     */
	public String getStatus() {
		return status;
	}
	/**
     * Sets the status for the appointment.
     *
     * @param status the new status of the appointment
     */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
     * Retrieves the outcome of the appointment.
     *
     * @return the outcome of the appointment
     */
	public String getOutcome() {
		return outcome;
	}
	/**
     * Sets the outcome for the appointment.
     *
     * @param outcome the new outcome of the appointment
     */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	/**
     * Returns a string representation of the appointment, including patient ID, doctor ID, status, date, time, and outcome.
     *
     * @return a string representation of the appointment
     */
    @Override
    public String toString() {
    	return getPatientId() + "," + getDoctorId() + "," + getStatus() + "," + getDate() + "," + getTime() + "," + getOutcome();
    }
}
