package application;

import java.util.ArrayList;
import java.util.Scanner;

import camp.Camp;
import committeeMember.CommitteeMember;
import enums.Role;
import user.User;
import file.FileIO;
import staff.Staff;
import student.Student;

public class NTUCampApplication 
{	
	// Application Managers
	static Scanner scan;
	static SecurityManager authenticator;
	static UserManager userManager;
	static FileIO fileEditor;
	static MenuStates menu;
	static MenuStates prevMenu;
	static User user;
	
	// Camp List
	static ArrayList<Camp> campList;
	
	public static void main(String[] args) 
	{
		// Initialise variables through this method
		initialise();
		
		while (menu != MenuStates.EXIT)
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
			case PROFILE:
				viewProfileMenu();
				break;
			case VIEW_CAMPS:
				viewCampsMenu();
				break;
			case CAMP_DETAILS:
				viewCampDetailsMenu();
				break;
			case VIEW_ENQUIRIES:
				viewEnquiriesMenu();
				break;
			case REPLY_ENQUIRY:
				replyEnquiryMenu();
				break;
			case REPORT_PARTICIPANT:
				generateReportParticipantsMenu();
				break;
			case STUDENT_MAIN_MENU:
				studentMainMenu();
				break;
			case SUBMIT_ENQUIRY:
				submitEnquiryMenu();
				break;
			case CM_MAIN_MENU:
				committeeMainMenu();
				break;
			case SUBMIT_SUGGESTION:
				submitSuggestionMenu();
				break;
			case STAFF_MAIN_MENU:
				staffMainMenu();
				break;
			case EDIT_CAMP:
				editCampMenu();
				break;
			case CREATE_CAMP:
				createCampMenu();
				break;
			case DELETE_CAMP:
				deleteCampMenu();
				break;
			case VIEW_SUGGESTIONS:
				viewSuggestionsMenu();
				break;
			case APPROVE_SUGGESTION:
				approveSuggestionMenu();
				break;
			case REPORT_PERFORMANCE:
				generateReportPerformanceMenu();
				break;
			default:
				System.out.println("ERROR, MENU DONT EXIST");
				break;
			}
		}
		
		System.out.println("END PROGRAMME");
	}
	
	// Application Methods
	static void setMenuState(MenuStates newState)
	{
		prevMenu = menu;
		menu = newState;
	}
	
	static void errorChoice()
	{
		System.out.println("Invalid Choice!");
		System.out.print("Choose Again : ");
	}
	
	static void initialise()
	{
		scan = new Scanner(System.in);
		authenticator = new SecurityManager();
		fileEditor = new FileIO();
		userManager = new UserManager();
		campList = new ArrayList<Camp>();
		prevMenu = menu = MenuStates.PRELOG_IN;
	}
	
	// Application Menus
	static void preLogInMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|         NTU CAMP APPLICATION        |");
        System.out.println("---------------------------------------");
        System.out.println("|            1. Login                 |");
        System.out.println("|            2. Exit                  |");
        System.out.println("---------------------------------------");

        System.out.print("Enter your choice: ");
        int choice = scan.nextInt();
        
        if (choice == 1)
        	setMenuState(MenuStates.LOG_IN);
        else if (choice == 2)
        	setMenuState(MenuStates.EXIT);
        else
        	errorChoice();
	}
	
	static void logInMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|           NTU CAMP LOG IN           |");
        System.out.println("---------------------------------------");

		
		user = authenticator.logInAuthentication(scan,userManager);
		
		if (user == null)
			return;
		
		if (user.getRole() == Role.STUDENT)
			setMenuState(MenuStates.STUDENT_MAIN_MENU);
		else if (user.getRole() == Role.COMMITTEE_MEMBER)
			setMenuState(MenuStates.CM_MAIN_MENU);
		else
			setMenuState(MenuStates.STAFF_MAIN_MENU);
	}
	
	static void preLogOutMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|         CONFIRM LOG OUT?            |");
        System.out.println("---------------------------------------");
        System.out.println("|            1. Logout                |");
        System.out.println("|            2. Back                  |");
        System.out.println("---------------------------------------");
        
        int choice = scan.nextInt();
        
        if (choice == 1)
        	setMenuState(MenuStates.LOG_OUT);
        else if (choice == 2)
        	setMenuState(prevMenu);
        else
        	errorChoice();
    }
	
	static void logOutMenu()
	{
		System.out.println("Sucessfully Logged Out! Thank you " + user.getName());
		setMenuState(MenuStates.PRELOG_IN);
	}
	
	static void viewProfileMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|           YOUR PROFILE              |");
        System.out.println("---------------------------------------");
        System.out.println("Name : " + user.getName());
        System.out.println("UserID : " + user.getUserID());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Faculty : " + user.getFaculty().toString());
        System.out.println("Role : " + user.getRole().toString());
        System.out.println();
        System.out.print("Enter 1 to return back to Menu : ");
        while(scan.nextInt() != 1) {scan.nextLine();}
        setMenuState(prevMenu);
	}
	
	// Shared Menu Throughout All Roles
	static void viewCampsMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|              ALL CAMPS              |");
        System.out.println("---------------------------------------");
		// TODO : Print all camp according to Student/CM Faculty
		// Staff will print all camp
     
        int choice;
        if (campList.size() == 0)
        {
        	System.out.println("There are no camps");
            System.out.print("Enter 1 to return back to Menu : ");
            while(scan.nextInt() != 1) {scan.nextLine();}
            setMenuState(prevMenu);
        }
        else
        {
        	if (user.getRole() == Role.STUDENT || user.getRole() == Role.COMMITTEE_MEMBER)
        	{
        		System.out.println("| 1. Join Camp              |");
        		System.out.println("| 2. Withdraw Camp          |");
        		if (user.getRole() == Role.STUDENT)
        			System.out.println("| 3. Sign up as Committee Member      |");
        		System.out.println("---------------------------------------");
        		System.out.print("Please pick a menu: ");
        		
        		choice = scan.nextInt();
        		scan.nextLine();
        		
        		switch (choice)
        		{
        		case 1:
        		{
        			((Student)user).register(scan, campList);
        			setMenuState(MenuStates.STUDENT_MAIN_MENU);
        			break;
        		}
        		case 2:
        		{
        			((Student)user).withdraw(scan);
        			setMenuState(MenuStates.STUDENT_MAIN_MENU);
        			break;
        		}
        		case 3:
        		{
        			if (user.getRole() == Role.COMMITTEE_MEMBER)
        			{
        				System.out.println("Already a Committee Member of Camp " + ((CommitteeMember)user).getOverseeingCamp().getDetails().getName());
        			}
        			else
        			{
        				CommitteeMember cm = ((Student)user).registerAsCM(scan, campList, (Student)user);
        				
        				userManager.removeUser(user);
        				
        				// Change current student to become CM
        				user = cm;
        				userManager.addUser(user);
        				setMenuState(MenuStates.CM_MAIN_MENU);
        			}
        			break;
        		}
        		}
        	}
        	else
        	{
        		for (Camp c : campList)
        			c.detailedPrint();
        		System.out.print("Enter 1 to return back to Menu : ");
        		
        		do
        		{
        			choice = scan.nextInt();
        			scan.nextLine();
        			if (choice != 1)
        				errorChoice();
        		}
        		while (choice != 1);
        		setMenuState(prevMenu);
        	}
        }
		
	}
	
	static void viewCampDetailsMenu()//Camp camp)
	{
		System.out.println("---------------------------------------");
        System.out.println("|             CAMP DETAILS            |");
        System.out.println("---------------------------------------");
        
        if (user instanceof CommitteeMember)
        	((CommitteeMember)user).getOverseeingCamp().detailedPrint();
        else if (user instanceof Student)
        {
        	ArrayList<Camp> camps = ((Student)user).getRegisteredFor();
        	for (Camp c : camps)
        		c.print();
        }
        else // Staff Logics
        	((Staff)user).getCreatedCamp().detailedPrint();
        
        System.out.print("Enter 1 to return back to Menu : ");
        int choice = scan.nextInt();
        scan.nextLine();
        
        if (choice == 1)
        	setMenuState(prevMenu);
	}
	
	// Shared Menu Between CM & STAFF
	static void viewEnquiriesMenu()
	{
		// TODO Print out Enquiries of their own camp
		// BIG LIST OF IF ELSE DEPENING ON STUDENT, CM OR STAFF
		
		if (user.getRole() == Role.STUDENT)
		{
			
		}
		else if (user.getRole() == Role.COMMITTEE_MEMBER)
		{
			
		}
		else
		{
			((Staff)user).getCreatedCamp().getEnquiries();
		}
		
		// TODO Hid
		// Change Menu State according to logic above
	}
	
	static void replyEnquiryMenu()
	{
		// TODO Call Method from each classes replyEnquiry
		
		// TODO Hid
		// Change Menu State according to logic above
		// if user -> CM, setMenuState(CM main menu)
		// if user -> STAFF, setMenuState(STAFF MAIN MENU)
	}
	
	static void generateReportParticipantsMenu()
	{
		// TODO Call CM / STAFF Method for generating Report Participants
		// if user -> CM, setMenuState(CM main menu)
		// if user -> STAFF, setMenuState(STAFF MAIN MENU)
		
		setMenuState(prevMenu);
	}
	
	// STUDENT METHODS
	static void studentMainMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|        NTU STUDENT MAIN MENU        |");
        System.out.println("---------------------------------------");
        System.out.println("| 1. View Available Camps             |");
        System.out.println("| 2. View Enquiries                   |");
        System.out.println("| 3. See Profile                      |");
        System.out.println("| 4. Log Out                          |");
        System.out.println("---------------------------------------");
        System.out.print("Welcome " + user.getRole().toString() + " " + user.getName() + ". Please pick a menu : ");
        
        int choice = scan.nextInt();
        scan.nextLine();
        
        switch(choice)
        {
        case 1:
        {
        	setMenuState(MenuStates.VIEW_CAMPS);
        	break;
        }
        case 2:
        {
        	setMenuState(MenuStates.VIEW_ENQUIRIES);
        	break;
        }
        case 3:
        {
        	setMenuState(MenuStates.PROFILE);
        	break;
        }
        case 4:
        {
        	setMenuState(MenuStates.PRELOG_OUT);
        	break;
        }
        default:
        	errorChoice();
        	break;
        }
        
        
	}
	
	static void submitEnquiryMenu()
	{
		// TODO Call Students Submit Enquiry Method
		((Student)user).submitEnquiry(scan, campList);
		
		setMenuState(MenuStates.STUDENT_MAIN_MENU);
	}

	// COMMITTEE METHODS
	static void committeeMainMenu()
	{
		// TODO Hid Create COMMITTEE Main Menu
		System.out.println("---------------------------------------");
        System.out.println("|       NTU COMMITTEE MAIN MENU       |");
        System.out.println("---------------------------------------");
        System.out.println("| 1. View Available Camps             |");
        System.out.println("| 2. View Enquiries                   |");
        System.out.println("| 3. Submit Suggestions               |");
        System.out.println("| 4. View Suggestions                 |");
        System.out.println("| 5. View Profile                     |");
        System.out.println("| 6. Log Out                          |");
        System.out.println("---------------------------------------");
        System.out.print("Welcome " + user.getRole().toString() + " " + user.getName() + ". Please pick a menu : ");
        
        int choice = scan.nextInt();
        scan.nextLine();
        
        switch(choice)
        {
        case 1:
        	setMenuState(MenuStates.VIEW_CAMPS);
        	break;
        case 2:
        	setMenuState(MenuStates.VIEW_ENQUIRIES);
        	break;
        case 3:
        	setMenuState(MenuStates.SUBMIT_SUGGESTION);
        	break;
        case 4:
        	setMenuState(MenuStates.VIEW_SUGGESTIONS);
        	break;
        case 5:
        	setMenuState(MenuStates.PROFILE);
        	break;
        case 6:
        	setMenuState(MenuStates.PRELOG_OUT);
        	break;
        default:
        	errorChoice();
        	break;
        }
	}
	
	static void submitSuggestionMenu()
	{
		// TODO Call CM SubmitSuggestion Method
		setMenuState(MenuStates.CM_MAIN_MENU);
	}
	
	// STAFF METHODS
    static void staffMainMenu()
	{
    	System.out.println("---------------------------------------");
        System.out.println("|         NTU STAFF MAIN MENU         |");
        System.out.println("---------------------------------------");
        if (((Staff)user).getCreatedCamp() == null)
        System.out.println("| 1. Create Camp                      |");
        else
        System.out.println("| 1. View Camp Details                |");
        System.out.println("| 2. View Camps (School)              |");
        System.out.println("| 3. View Enquiries                   |");
        System.out.println("| 4. View Suggestions                 |");
        System.out.println("| 5. Reply Enquiries                  |");
        System.out.println("| 6. Approve Suggestions              |");
        System.out.println("| 7. Generate Participant (R)         |");
        System.out.println("| 8. Generate Performance (R)         |");
        System.out.println("| 9. View Profile                     |");
        System.out.println("| 10.Log Out                          |");
        System.out.println("---------------------------------------");
        System.out.print("Welcome " + user.getRole().toString() + " " + user.getName() + ". Please pick a menu : ");
		
		int choice = scan.nextInt();
		scan.nextLine();
		switch (choice)
		{
		case 1:
		{
			if (((Staff)user).getCreatedCamp() == null)
				setMenuState(MenuStates.CREATE_CAMP);
			else
				setMenuState(MenuStates.CAMP_DETAILS);
			break;
		}
		case 2:
		{
			setMenuState(MenuStates.VIEW_CAMPS);
			break;
		}
		case 3:
		{
			setMenuState(MenuStates.VIEW_ENQUIRIES);
			break;
		}
		case 4:
		{
			setMenuState(MenuStates.VIEW_SUGGESTIONS);
			break;
		}
		case 5:
		{
			setMenuState(MenuStates.REPLY_ENQUIRY);
			break;
		}
		case 6:
		{
			setMenuState(MenuStates.APPROVE_SUGGESTION);
			break;
		}
		case 7:
		{
			setMenuState(MenuStates.REPORT_PARTICIPANT);
			break;
		}
		case 8:
		{
			setMenuState(MenuStates.REPORT_PERFORMANCE);
			break;
		}
		case 9:
		{
			setMenuState(MenuStates.PROFILE);
			break;
		}
		case 10:
		{
			setMenuState(MenuStates.PRELOG_OUT);
			break;
		}
		default:
		{
			errorChoice();
			break;
		}
		}
	}
	
    static void editCampMenu()
    {
    	// TODO Call STAFF Edit Camp Method
    	setMenuState(prevMenu);
    }
    
    static void createCampMenu()
    {
		System.out.println("---------------------------------------");
        System.out.println("|            CREATING CAMP            |");
        System.out.println("---------------------------------------");
        
        ((Staff)user).createCamp(scan);
        campList.add(((Staff)user).getCreatedCamp());
        
    	setMenuState(prevMenu);
    }
    
    static void deleteCampMenu()
    {
    	// TODO Call Staff DeleteCamp Method
    	((Staff)user).deleteCamp();
    	setMenuState(prevMenu);
    }

    static void viewSuggestionsMenu()
    {
    	// TODO Call Staff View Suggestion Method
    	
    	
    	// TODO Hid
    	// Call the necessary Menu
    }
    
    static void approveSuggestionMenu()
    {
    	// TODO Call Staff Approve Suggestion Method
    	setMenuState(MenuStates.STAFF_MAIN_MENU);
    }

    static void generateReportPerformanceMenu()
    {
    	// TODO Call Staff Report Performance Method
    	setMenuState(prevMenu);
    }

}
