package models;

import handlers.MedicalCertificateHandler;
import management.AppointmentManagement;

public class Doctor extends User {
    private AppointmentManagement appointmentManagement;
    private int age;

    public Doctor(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Doctor", gender);
        this.age = age;
        this.appointmentManagement = new AppointmentManagement();  // Initialize appointment management
    }

    public void setAvailability(String date, String time) {
        //appointmentManagement.setDoctorAvailability(this.getId(), date, time);  // Call setDoctorAvailability method
    }

    public void viewUpcomingAppointments() {
        //appointmentManagement.viewAppointments(this.getId());  // Call viewAppointments method
    }

    public int getAge() {
        return age;
    }

    public void viewPatientCertificates(Patient patient) {
        MedicalCertificateHandler.viewCertificatesForPatient(patient);
    }

    public void approveOrRejectCertificate(String patientId, String newStatus) {
        MedicalCertificateHandler.updateCertificateStatus(patientId, newStatus);
    }

    @Override
    public void displayMenu() {
        System.out.println("Doctor Menu:");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. View Patient Medical Certificates");
        System.out.println("9. Approve/Reject Medical Certificates");
        System.out.println("10. Logout");
    }
}
