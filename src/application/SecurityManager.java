package application;

import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class SecurityManager 
{
	private HashMap<String, Student> studentMap;
	private HashMap<String, Staff> staffMap;
	
	public SecurityManager()
	{
		// TODO : Insert File name
		Scanner fileReader = new Scanner(new File(""));
		studentMap = new HashMap<String, Student>();
		staffMap = new HashMap<String, Staff>();
		
		
		fileReader.useDelimiter(",");
		
		// TODO : Do for Student file next
	}
	
	public Boolean insertUser(User user)
	{
		return true;
	}
	
	public User logInAuthentication(Scanner scan)
	{
		User user;
		
        System.out.println("Enter your Username and Password");
		System.out.println("username : ");
		String userID = scan.nextLine();
		System.out.println("password : ");
		String password = scan.nextLine();
		
		
		// If UserID cannot be found in any Map
		if (studentMap.get(userID) == null && staffMap.get(userID) == null)
		{
			System.out.println("User do not exist");
			return null;
		}
		
		// Look Through Student Map and see if password is incorrect
		if (studentMap.get(userID) != null)
		{
			user = studentMap.get(userID);
			if (!user.getPassword().equals(password))
			{
				System.out.println("Incorrect Password!");
				return null;
			}
		}
		// Look Through Staff Map and see if password is incorrect
		else
		{
			user = staffMap.get(userID);
			if (!user.getPassword().equals(password))
			{
				System.out.println("Incorrect Password!");
				return null;
			}
		}
		
		// If password is default, prompt user to change
		if (password.equals("password"))
			changePassword(user, scan);
		return user;
	}
	
	public static void changePassword(User user, Scanner scan)
	{
		System.out.println("New Log In detected");
		System.out.println("Please Enter New Password : ");
		
		String password = scan.nextLine();
		user.changePassword(password);
		
		System.out.println("Password Sucessfully Changed");
	}
}
