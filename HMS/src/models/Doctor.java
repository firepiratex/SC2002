package models;

import management.AppointmentManagement;

public class Doctor extends User {

    private int age;

    public Doctor(String id, String name, String password, String gender, int age) {
        super(id, name, password, "Doctor", gender);
        this.age = age;
        new AppointmentManagement();
    }

    public int getAge() {
        return age;
    }

    @Override
    public void displayMenu() {
        System.out.println("Doctor Menu:");
        System.out.println("\n1. View Patient Medical Records\n"
                + "2. Update Patient Medical Records\n"
                + "3. View Personal Schedule\n"
                + "4. Set Availability for Appointments\n"
                + "5. Accept or Decline Appointment Requests\n"
                + "6. View Upcoming Appointments\n"
                + "7. Record Appointment Outcome\n"
                + "8. Logout\n");
    }
}
