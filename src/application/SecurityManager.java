package application;

import java.util.Scanner;

import user.User;

public class SecurityManager 
{	
	public User logInAuthentication(Scanner scan, UserManager userManager)
	{
		User user;
		
        System.out.println("Enter your Username and Password");
		System.out.println("username : ");
		String userID = scan.nextLine();
		System.out.println("password : ");
		String password = scan.nextLine();
		
		
		// If UserID cannot be found in any Map
		if (userManager.getStudentByID(userID) == null && userManager.getStaffByID(userID) == null)
		{
			System.out.println("User do not exist");
			return null;
		}
		
		// Look Through Student Map and see if password is incorrect
		if (userManager.getStudentByID(userID) != null)
		{
			user = userManager.getStudentByID(userID);
			if (!user.getPassword().equals(password))
			{
				System.out.println("Incorrect Password!");
				return null;
			}
		}
		// Look Through Staff Map and see if password is incorrect
		else
		{
			user = userManager.getStaffByID(userID);
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
