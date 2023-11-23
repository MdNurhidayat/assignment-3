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
		System.out.println("Choose Again");
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
	
	
	// Shared Menu Throughout All Roles
	static void viewCampsMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|              ALL CAMPS              |");
        System.out.println("---------------------------------------");
		// TODO : Print all camp according to Student/CM Faculty
		// Staff will print all camp
        if (campList.size() == 0)
        {
        	System.out.println("There are no camps");
            System.out.print("Enter 1 to return back to Menu : ");
            while(scan.nextInt() != 1) {scan.nextLine();}
            setMenuState(prevMenu);
        }
        else
        {
        	if (user instanceof Student || user instanceof CommitteeMember)
        	{
        		System.out.println("| 1. ");
        		
        	}
        	else if (user instanceof Staff)
        	{
        		// TODO For instance of Staff
        		//((user))
        	}
        }
		
	}
	
	static void viewCampDetailsMenu()//Camp camp)
	{
		System.out.println("---------------------------------------");
        System.out.println("|             CAMP DETAILS            |");
        System.out.println("---------------------------------------");
        if (user instanceof Student)
        {
        	ArrayList<Camp> camps = ((Student)user).getRegisteredFor();
        	for (Camp c : camps)
        	{
        		c.print();
        	}
        }
        else if (user instanceof CommitteeMember)
        {
        	((CommitteeMember)user).getOverseeingCamp().detailedPrint();
        }
        else // Staff Logics
        {
        	((Staff)user).getCreatedCamp().detailedPrint();
        }
        
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
		// TODO Hid Create STUDENT Menu
		System.out.println("---------------------------------------");
        System.out.println("|        NTU STUDENT MAIN MENU        |");
        System.out.println("---------------------------------------");
        System.out.println("| 1. View Available Camps             |");
        System.out.println("| 2. View Available Camps             |");
	}
	
	static void submitEnquiryMenu()
	{
		// TODO Call Students Submit Enquiry Method
		setMenuState(MenuStates.STUDENT_MAIN_MENU);
	}

	// COMMITTEE METHODS
	static void committeeMainMenu()
	{
		// TODO Hid Create COMMITTEE Main Menu
		System.out.println("---------------------------------------");
        System.out.println("|       NTU COMMITTEE MAIN MENU       |");
        System.out.println("---------------------------------------");
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
        // TODO Create getCamp Method in Staff class and uncomment the next few lines
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
        System.out.println("| 9. Generate Enquiry (R)             |");
        System.out.println("| 10.Log Out                          |");
        System.out.println("---------------------------------------");
		System.out.print("Welcome " + user.getName() + ". Please pick a menu : ");
		
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
			setMenuState(MenuStates.REPORT_ENQUIRY);
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
