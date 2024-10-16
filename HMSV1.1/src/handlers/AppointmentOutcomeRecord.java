package handlers;

public class AppointmentOutcomeRecord {

    public void displayPastOutcomes(String patientId) {
        // Logic to display past outcomes for a specific patient
        // Fetch from database or data storage
        System.out.println("Displaying past appointment outcomes for Patient ID: " + patientId);
        // Example outcomes:
        System.out.println("Appointment ID: 001, Doctor: Dr. John, Outcome: Diagnosis - Flu, Status: Completed");
        System.out.println("Appointment ID: 002, Doctor: Dr. Smith, Outcome: Diagnosis - Cold, Status: Completed");
    }
}
