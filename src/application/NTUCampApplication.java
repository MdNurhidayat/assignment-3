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
        System.out.print("Please pick a menu: ");
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
        		System.out.println("| 1. Join Camp                        |");
        		System.out.println("| 2. Withdraw Camp                    |");
        		if (user.getRole() == Role.STUDENT)
        			System.out.println("| 3. Sign up as Committee Member      |");
        		System.out.println("---------------------------------------");
        		System.out.print("Please pick a menu: ");
        		
        		choice = scan.nextInt();
        		scan.nextLine();
        		
        		((Student)user).viewAvailableCampsToJoin(campList);
        		
        		switch (choice)
        		{
        		case 1:
        		{
        			((Student)user).register(scan, campList);
        			setMenuState(prevMenu);
        			break;
        		}
        		case 2:
        		{
        			((Student)user).withdraw(scan);
        			setMenuState(prevMenu);
        			break;
        		}
        		case 3:
        		{
        			if (user.getRole() == Role.COMMITTEE_MEMBER)
        				System.out.println("Already a Committee Member of Camp " + ((CommitteeMember)user).getOverseeingCamp().getDetails().getName());
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
        

        if (user.getRole() == Role.STUDENT)
        {
        	System.out.println("| 1. Back                             |");
            System.out.println("---------------------------------------");
        	ArrayList<Camp> camps = ((Student)user).getRegisteredFor();
        	for (Camp c : camps)
        		c.print();
        } 
        else if (user.getRole() == Role.COMMITTEE_MEMBER)
        {
        	System.out.println("| 1. Back                             |");
            System.out.println("---------------------------------------");
        	((CommitteeMember)user).getOverseeingCamp().detailedPrint();
        	
        }
        else // Staff Logics
        {
            System.out.println("| 1. Edit Camp                        |");
            System.out.println("| 2. Back                             |");
            System.out.println("---------------------------------------");
            ((Staff)user).getCreatedCamp().detailedPrint();
        }
        	
        
        System.out.print("Enter 1 to return back to Menu : ");
        int choice = scan.nextInt();
        scan.nextLine();
        
        if (choice == 1)
        	setMenuState(prevMenu);
        
        System.out.println();
	}
	
	// Shared Menu Between CM & STAFF
	static void viewEnquiriesMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|             ENQUIRY MENU            |");
        System.out.println("---------------------------------------");
		
        int choice;
        
		if (user.getRole() == Role.STUDENT)
		{
			System.out.println("| 1. Create Enquiry                   |");
			System.out.println("| 2. Edit Enquiry                     |");
			System.out.println("| 3. Back                             |");
			System.out.println("---------------------------------------");
			((Student)user).viewEnquiries();
			
			choice = scan.nextInt();
			scan.nextLine();
			
			switch (choice)
			{
			case 1:
			{
				((Student)user).submitEnquiry(scan, campList);
				break;
			}
			case 2:
			{
				((Student)user).editEnquiry(scan);
				break;
			}
			case 3:
				setMenuState(prevMenu);
				break;
			}
		}
		else if (user.getRole() == Role.COMMITTEE_MEMBER)
		{
			System.out.println("| 1. Create Enquiry                   |");
			System.out.println("| 2. Edit Enquiry                     |");
			System.out.println("| 3. Reply Enquiry                    |");
			System.out.println("| 4. Back                             |");
			System.out.println("---------------------------------------");
			((CommitteeMember)user).viewEnquiries();
			
			choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice)
			{
			case 1:
				((Student)user).submitEnquiry(scan, campList);
				break;
			case 2:
				((Student)user).editEnquiry(scan);
				break;
			case 3:
				((CommitteeMember)user).replyEnquiry(scan);
				break;
			case 4:
				setMenuState(prevMenu);
				break;
			}
		}
		else
		{
			System.out.println("| 1. Reply Enquiry                    |");
			System.out.println("| 2. Back                             |");
			System.out.println("---------------------------------------");
			
			((Staff)user).viewEnquiries();
			
			choice = scan.nextInt();
			scan.nextLine();
			
			switch (choice)
			{
			case 1:
			{
				if (((Staff)user).getCreatedCamp().getEnquiries().isEmpty())
				{
					errorChoice();
					System.out.println("Returning to menu..");
					setMenuState(prevMenu);
				}
				else
				{
					((Staff)user).replyEnquiry(scan);
				}
				break;
			}
			case 2:
				setMenuState(prevMenu);
				break;
			}
		}
	}
	
	static void generateReportParticipantsMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|          GENERATING REPORT          |");
        System.out.println("---------------------------------------");
		if (user.getRole() == Role.COMMITTEE_MEMBER)
		{
			((CommitteeMember)user).generateParticipantReport(scan);
		}
		else
		{
			((Staff)user).generateParticipantReport(scan);
		}
		
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
		System.out.println("---------------------------------------");
        System.out.println("|          CREATING ENQUIRY           |");
        System.out.println("---------------------------------------");
		((Student)user).submitEnquiry(scan, campList);
		setMenuState(prevMenu);
	}

	// COMMITTEE METHODS
	static void committeeMainMenu()
	{
		System.out.println("---------------------------------------");
        System.out.println("|       NTU COMMITTEE MAIN MENU       |");
        System.out.println("---------------------------------------");
        System.out.println("| 1. View Available Camps             |");
        System.out.println("| 2. View Enquiries                   |");
        System.out.println("| 3. Submit Suggestion                |");
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
    	System.out.println("---------------------------------------");
        System.out.println("|          SUBMIT SUGGESTION          |");
        System.out.println("---------------------------------------");
        
        ((CommitteeMember)user).submitSuggestion(scan);
        
		setMenuState(MenuStates.CM_MAIN_MENU);
	}
	
	// STAFF METHODS
    static void staffMainMenu()
	{
    	System.out.println("---------------------------------------");
        System.out.println("|         NTU STAFF MAIN MENU         |");
        System.out.println("---------------------------------------");
        if (((Staff)user).hasCreatedCamp() == false)
        System.out.println("| 1. Create Camp                      |");
        else
        System.out.println("| 1. View Camp Details                |");
        System.out.println("| 2. Delete Camp                      |");
        System.out.println("| 3. View Camps (School)              |");
        System.out.println("| 4. View Enquiries                   |");
        System.out.println("| 5. View Suggestions                 |");
        System.out.println("| 6. Generate Participant (R)         |");
        System.out.println("| 7. Generate Performance (R)         |");
        System.out.println("| 8. View Profile                     |");
        System.out.println("| 9. Log Out                          |");
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
			setMenuState(MenuStates.DELETE_CAMP);
			break;
		}
		case 3:
		{
			setMenuState(MenuStates.VIEW_CAMPS);
			break;
		}
		case 4:
		{
			setMenuState(MenuStates.VIEW_ENQUIRIES);
			break;
		}
		case 5:
		{
			setMenuState(MenuStates.VIEW_SUGGESTIONS);
			break;
		}
		case 6:
		{
			setMenuState(MenuStates.REPORT_PARTICIPANT);
			break;
		}
		case 7:
		{
			setMenuState(MenuStates.REPORT_PERFORMANCE);
			break;
		}
		case 8:
		{
			setMenuState(MenuStates.PROFILE);
			break;
		}
		case 9:
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
    	System.out.println("---------------------------------------");
        System.out.println("|            EDITING CAMP             |");
        System.out.println("---------------------------------------");
        ((Staff)user).editCamp(scan);
        
        int choice = scan.nextInt();
        scan.nextLine();
        
        System.out.print("Set camp to visible? Enter 1 to set visible, otherwise camp will be invisible");
        if (choice == 1)
        	((Staff)user).getCreatedCamp().setVisibleOn();
        else
        	((Staff)user).getCreatedCamp().setVisibleOff();
        
        System.out.println();
    	setMenuState(prevMenu);
    }
    
    static void createCampMenu()
    {
		System.out.println("---------------------------------------");
        System.out.println("|            CREATING CAMP            |");
        System.out.println("---------------------------------------");
        
        ((Staff)user).createCamp(scan);
        campList.add(((Staff)user).getCreatedCamp());
        
        System.out.println("Set Camp to Visible? Press 1 to set visbile, otherwise, camp will be unvisible");
        System.out.print("Choice : ");
        
        int choice = scan.nextInt();
        scan.nextLine();
        
        if (choice == 1)
        	((Staff)user).getCreatedCamp().setVisibleOn();
        
        System.out.println();
        
    	setMenuState(prevMenu);
    }
    
    static void deleteCampMenu()
    {
		System.out.println("---------------------------------------");
        System.out.println("|            DELETING CAMP            |");
        System.out.println("---------------------------------------");
        System.out.println("| 1. Delete Camp                      |");
        System.out.println("| 2. Back                             |");
        System.out.println("---------------------------------------");
        System.out.print("Deleting Camp. Confirm? Pick a number : ");
        int choice = scan.nextInt();
        scan.nextLine();
        
        if (choice == 1)
        	((Staff)user).deleteCamp();
        else if (choice < 1 && choice > 2)
        {
        	errorChoice();
        	return;
        }
    	setMenuState(prevMenu);
    }

    static void viewSuggestionsMenu()
    {
    	System.out.println("---------------------------------------");
        System.out.println("|          SUGGESTION MENU            |");
        System.out.println("---------------------------------------");
        
        int choice;
        
        if (user.getRole() == Role.COMMITTEE_MEMBER)
        {
        	System.out.println("| 1. Create Suggestion                |");
        	System.out.println("| 2. Edit Suggestion                  |");
        	System.out.println("| 3. Back                             |");
        	System.out.println("---------------------------------------");
        	
        	((CommitteeMember)user).viewSuggestionLists();
        	
        	choice = scan.nextInt();
        	scan.nextLine();
        	
        	switch(choice)
        	{
        	case 1:
        		((CommitteeMember)user).submitSuggestion(scan);
        		setMenuState(prevMenu);
        		break;
        	case 2:
        		((CommitteeMember)user).editOwnSuggestion(scan);
        		setMenuState(prevMenu);
        		break;
        	default:
        		errorChoice();
        		break;
        	}
        }
        else
        {
        	System.out.println("| 1. Approve Suggestion               |");
        	System.out.println("| 2. Back                             |");
        	System.out.println("---------------------------------------");
        	
        	((Staff)user).viewSuggestions();
        	
        	choice = scan.nextInt();
        	scan.nextLine();
        	
        	switch (choice)
        	{
        	case 1:
        		if (!((Staff)user).getCreatedCamp().getEnquiries().isEmpty())
        			((Staff)user).approve(scan);
        		else
        			System.out.println("There is no SUGGESTION! Returning to Menu.");
        		setMenuState(prevMenu);
        		break;
        	case 2:
        		setMenuState(prevMenu);
        		break;
        	default:
        		errorChoice();
        		break;
        	}
        }
    }

    static void generateReportPerformanceMenu()
    {
    	System.out.println("---------------------------------------");
        System.out.println("|          GENERATING REPORT          |");
        System.out.println("---------------------------------------");
    	((Staff)user).generatePerformanceReport(scan);
    	setMenuState(prevMenu);
    }

}
