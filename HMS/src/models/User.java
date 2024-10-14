package models;

public abstract class User {
    protected String userID;
    protected String name;
    protected String contactInfo;
    protected String password = "password"; // Default password

    public User(String userID, String name, String contactInfo) {
        this.userID = userID;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public boolean login(String userID, String password) {
        return this.userID.equals(userID) && this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public abstract void displayMenu();
}
