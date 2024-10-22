package management;

import java.util.HashMap;
import java.util.Map;

public class PrescriptionManagement {

    private Map<String, String> prescriptions = new HashMap<>();

    public PrescriptionManagement() {
        // Sample prescriptions
        prescriptions.put("RX001", "Unfilled");
        prescriptions.put("RX002", "Filled");
    }

    public boolean updatePrescriptionStatus(String prescriptionId, String newStatus) {
        if (prescriptions.containsKey(prescriptionId)) {
            prescriptions.put(prescriptionId, newStatus);
            return true;
        }
        return false;
    }
}
