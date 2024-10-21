package models;

public abstract class User {
    protected String id;
    protected String name;
    protected String password;
    protected String role;
    protected String gender;

    public User(String id, String name, String password, String role, String gender) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getRole() {
        return role;
    }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public String getPassword() {
        return password;
    }
    
    public String toString() {
    	return getId();
    }
    public String getGender() {
        return gender;
    }

    public abstract void displayMenu();
}
