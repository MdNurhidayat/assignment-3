package application;

import java.util.Scanner;

import user.User;

public class SecurityManager 
{	
	public User logInAuthentication(Scanner scan, UserManager userManager)
	{
		User user;
		String userID, password = "";
		
        System.out.println("Enter your Username and Password");
		System.out.print("username : ");
		userID = scan.nextLine();
		System.out.print("password : ");
		password = scan.nextLine();
		
		
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
			changePassword(user, scan, userManager);
		System.out.println();
		return user;
	}
	
	public void changePassword(User user, Scanner scan, UserManager userManager)
	{
		System.out.println("New Log In detected");
		System.out.print("Please Enter New Password : ");
		
		String password = scan.nextLine();
		user.setPassword(password);
		
		System.out.println("Password Sucessfully Changed");
		System.out.println("Kindly re enter your username with new password");
		System.out.println();
		
		do {
			user = logInAuthentication(scan, userManager);
		} while(user == null);
		
		System.out.println();
	}
}
