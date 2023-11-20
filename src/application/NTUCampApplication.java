package application;

import java.util.ArrayList;
import java.util.Scanner;

import camp.Camp;
import staff.Staff;

public class NTUCampApplication 
{	
	// Application Managers
	static Scanner scan;
	static SecurityManager authenticator;
	static MENU_STATES menu;
	static MENU_STATES prevMenu;
	
	// Camp Managers
	static ArrayList<Camp> campList;
	static ArrayList<User> userList;
	static User user; 
	
	public static void main(String[] args) 
	{
		// Initialise variables through this method
		initialise();
		
		while (menu != MENU_STATES.EXIT)
		{
			switch (menu)
			{
			case PRELOG_IN:
				preLogInMenu();
				break;
			case LOG_IN:
				logInMenu();
				break;
			case PRELOG_OUT:
				preLogOutMenu();
				break;
			case LOG_OUT:
				logOutMenu();
				break;
			case VIEW_CAMPS:
				// TODO Insert View Camps Method
				break;
			case CAMP_DETAILS:
				// TODO Insert Camp Details Method
				break;
			case SUBMIT_SUGGESTION:
				// TODO Insert Submit Suggestion Method
				break;
			case SUBMIT_ENQUIRY:
				// TODO Insert Submit Enquiry Method
				break;
			case APPROVE_SUGGESTION:
				// TODO Indert Approve Suggestion Method
				break;
			case REPLY_ENQUIRY:
				// TODO Insert Reply Enquiry
				break;
			case REPORT_PARTICIPANT:
				// TODO Insert Report Participant Method
				break;
			case STUDENT_MAIN_MENU:
				// TODO Insert Student Main Menu Method
				break;
			case STAFF_MAIN_MENU:
				staffMainMenu();
				break;
			case EDIT_CAMP:
				// TODO Insert Edit Camp Method
				break;
			case CREATE_CAMP:
				// TODO Insert Create Camp Method
				break;
			case DELETE_CAMP:
				// TODO Insert Delete Camp Method
				break;
			case REPORT_PERFORMANCE:
				// TODO Insert Report Performance Menu + Method
				break;
			default:
				System.out.println("ERROR, CHOICE DONT EXIST");
				break;
			}
		}
	}
	
	// Application Methods
	static void setMenuState(MENU_STATES newState)
	{
		prevMenu = menu;
		menu = newState;
	}
	
	static void errorChoice()
	{
		System.out.println("Invalid Choice!");
		System.out.println("Choose Again");
	}
	
	static void initialise()
	{
		scan = new Scanner(System.in);
		authenticator = new SecurityManager();
		campList = new ArrayList<Camp>();
		prevMenu = menu = MENU_STATES.PRELOG_IN;
	}
	
	
	static void preLogInMenu()
	{
		System.out.println("***************************************");
        System.out.println("*         NTU CAMP APPLICATION        *");
        System.out.println("***************************************");
        System.out.println("*            1. Login                 *");
        System.out.println("*            2. Exit                  *");
        System.out.println("***************************************");

        System.out.print("Enter your choice: ");
        int choice = scan.nextInt();
        
        if (choice == 1)
        	setMenuState(MENU_STATES.LOG_IN);
        else if (choice == 2)
        	setMenuState(MENU_STATES.EXIT);
        else
        	errorChoice();
	}
	
	
	static void logInMenu()
	{
		System.out.println("username : ");
		String username = scan.nextLine();
		System.out.println("password : ");
		String password = scan.nextLine();
		
		if (username.equals("std") && password.equals("stdpassword"))
			setMenuState(MENU_STATES.STUDENT_MAIN_MENU);
		else if (username.equals("staff") && password.equals("staffpassword"))
			setMenuState(MENU_STATES.STAFF_MAIN_MENU);
		
		Boolean success = authenticator.logInAuthentication(username, password);
		
		if (!success)
		{
			System.out.println("WRONG USERNAME OR/AND PASSWORD");
		}
		else
		{
			// TODO: SET USER
			
			
			setMenuState(MENU_STATES.STAFF_MAIN_MENU);
			//menu = MENU_STATES.STUDENT_MAIN_MENU;
		}
	}
	
	static void preLogOutMenu()
	{
		System.out.println("***************************************");
        System.out.println("*         NTU CAMP APPLICATION        *");
        System.out.println("***************************************");
        System.out.println("*            1. Logout                *");
        System.out.println("*            2. Back                  *");
        System.out.println("***************************************");
        
        int choice = scan.nextInt();
        
        if (choice == 1)
        	setMenuState(MENU_STATES.LOG_OUT);
        else if (choice == 2)
        	setMenuState(prevMenu);
        else
        	errorChoice();
    }
	
	static void logOutMenu()
	{
		System.out.println("Sucessfully Logged Out! Thank you " + user.getName());
		setMenuState(MENU_STATES.PRELOG_IN);
	}
	
	// STAFF METHODS
	// STAFF MENUS
    static void staffMainMenu()
	{
		System.out.println("***************************************");
        System.out.println("*         NTU STAFF MAIN MENU         *");
        System.out.println("***************************************");
        // TODO Create getCamp Method in Staff class and uncomment the next few lines
        // if (((Staff)user).getCamp() == null)
        System.out.println("* 1. Create Camp                      *");
        // else
        // System.out.println("* 1. View Camp                        *");
        System.out.println("* 2. View Camps (School)              *");
        System.out.println("* 3. View Enquiries                   *");
        System.out.println("* 4. View Suggestions                 *");
        System.out.println("* 5. Reply Enquiries                  *");
        System.out.println("* 6. Approve Suggestions              *");
        System.out.println("* 7. Generate Participant (R)         *");
        System.out.println("* 8. Generate Performance (R)         *");
        System.out.println("* 9. Generate Enquiry (R)             *");
        System.out.println("* 10. Log Out                         *");
        System.out.println("***************************************");
		System.out.println("Welcome " + user.getName());
		
		int choice = scan.nextInt();
		
		switch (choice)
		{
		case 1:
		{
			// TODO: Add Create Camp Method here and later add Camp into Application
			
			// TODO: Uncomment and add the class here
			// Boolean success = campList.add( *ADD_CAMP_CLASS_HERE*);
			// if (success)
			// 		System.out.println("Camp " + camp.GetName() + " is successfull");
			// 
			break;
		}
		case 2:
		{
			menu = MENU_STATES.VIEW_CAMPS;
			break;
		}
		case 3:
		{
			break;
		}
		case 4:
		{
			break;
		}
		
		}
	}
	
	
	// STUDENT METHODS
	static void studentMainMenu()
	{
		// TODO Fill in STUDENT MENUS
	}
}
