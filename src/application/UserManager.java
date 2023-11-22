package application;

import enums.Role;
import file.FileIO;
import user.User;
import staff.Staff;
import enums.Faculty;
import java.util.*;

/**@author Chen yan Jin 
@version 3.0 */

public class UserManager {
    
    // List to store user objects
    private HashMap<String, User> studentList;
    private HashMap<String, Staff> staffList;

    // Constructor to initialize the list of users
    public UserManager() {
    	studentList = new HashMap<>();
    	staffList = new HashMap<>();
        
    	initialise();
    }

    // Example method to initialize users based on the provided text information
    /* public static List<User> initializeUsersFromText(String staffListText, String studentListText) {
        List<User> users = new ArrayList<>();

        // Initialize staff users
        users.addAll(readUsersFromText(staffListText, Role.STAFF));

        // Initialize student users
        users.addAll(readUsersFromText(studentListText, Role.STUDENT));

        return users;
    }

    // Helper method to read users from text and set their properties
    /*private static List<User> readUsersFromText(String text, Role defaultRole) {
        List<User> users = new ArrayList<>();

        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] tokens = line.trim().split("\t");
            if (tokens.length >= 3) {
                String name = tokens[0];
                String email = tokens[1];
                String facultyString = tokens[2];
                
                try {
                    // Parse the faculty value from the string
                    Faculty faculty = Faculty.valueOf(facultyString);

                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setFaculty(faculty);

                    // Set the userID directly from the email
                    int atIndex = email.indexOf('@'); // Find the position of '@' in the email
                    if (atIndex != -1) {
                        user.setUserID(email.substring(0, atIndex));
                    } else {
                        user.setUserID(email);
                    }

                    // Create a mutable set for roles
                    Set<Role> roles = new HashSet<>();
                    roles.add(defaultRole);
                    user.setRoles(roles);

                    users.add(user);
                } catch (IllegalArgumentException e) {
                    // Handle the case where facultyString is not a valid enum constant
                    // Log a warning or handle it as needed
                    System.out.println("Invalid faculty: " + facultyString);
                }
            }
        }

        return users;
    }*/

    // Method to get a user by their userID
    public User getStudentByID(String userID) {
        if (studentList.containsKey(userID))
        	return studentList.get(userID);
        else
        	return null;
    }
    
    public Staff getStaffByID(String userID) {
        if (staffList.containsKey(userID))
        	return staffList.get(userID);
        else
        	return null;
    }

    // Method to get the list of users
    public HashMap<String, User> getStudentList() {
        return studentList;
    }
    
    public HashMap<String, Staff> getStaffList() {
        return staffList;
    }
    
    // Method to add a user to the list
    public void addStudent(String userID, User user) {
       studentList.put(userID, user);
    }
    
    public void addStaff(String userID, Staff staff) {
    	staffList.put(userID, staff);
    }
    
    static void initialise()
    {
    	// TODO Update file to read from
    	ArrayList<String> studentList = FileIO.readFromFile("studentlist.txt");
    	ArrayList<String> staffList = FileIO.readFromFile("stafflist.txt");
    	
    	// TODO Insert each arraylist into HashMap<String, User / Staff>
    	
    }
}
