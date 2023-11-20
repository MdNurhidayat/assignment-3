package application;
import enums.Role;
import enums.Faculty;

import java.util.HashSet;
import java.util.Set;

public class User {

    private String userID;
    private String password; // Storing the plain text password (not recommended for production)
    private Set<Role> roles; // Using a Set to allow multiple roles
    private String name;
    private String email;
    private Faculty faculty;
    private boolean firstLogin;
    private boolean loggedIn; // Added field to track login status

    // Default constructor with default password
    public User() {
        this.roles = new HashSet<>();
        this.password = "password";
        this.firstLogin = true;
        this.loggedIn = false; // Default login status is false
    }

    // Parameterized constructor with dependency injection and default password
    public User(String userID, String email, Set<Role> roles, String name, Faculty faculty) {
        this.userID = userID; // Set the userID here
        this.roles = roles;
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.password = "password";
        this.firstLogin = true;
        this.loggedIn = false;
    }

   
    // Getters and setters
    
    public String getUserID() {
        return userID;
    }

 // Assuming that user ID is derived from the email
    public void setUserID(String email) {
        int atIndex = email.indexOf('@'); // Find the position of '@' in the email
        if (atIndex != -1) {
            this.userID = email.substring(0, atIndex);
        } else {
            // Handle the case where '@' is not present in the email
            // You can throw an exception, log a message, or handle it in a way that fits your requirements
            // For example, setting userID to the entire email in this case
            this.userID = email;
        }
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void login() {
        this.loggedIn = true;
    }

    public void logout() {
        this.loggedIn = false;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
