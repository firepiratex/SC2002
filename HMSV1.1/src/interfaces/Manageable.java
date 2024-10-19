package interfaces;

import models.User;

public interface Manageable {
    void addStaff(User newStaff);
    void removeStaff(String staffId);
    void viewAllStaff();
}
