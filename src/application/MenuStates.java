package application;

public enum MenuStates
{
	// Application Exclusive Menus
	PRELOG_IN("Prelogin"),
	LOG_IN("Login"),
	PRELOG_OUT("PRELOGOUT"),
	LOG_OUT("LOGOUT"),
	EXIT("EXIT"),
	
	
	// Shared Menu
	PROFILE("Profile"),
	VIEW_CAMPS("ViewCamps"),
	VIEW_JOINED("ViewJoined"),
	CAMP_DETAILS("CampDetails"),
	VIEW_ENQUIRIES("ViewEnquiries"),
	
	// Shared Menu (STAFF & COMMITTEE MEMBER)
	//REPLY_ENQUIRY("ReplyEnquiries"),
	REPORT_PARTICIPANT("ReportParticipant"),
	REPORT_ENQUIRY("ReportEnquiry"),
	
	// STUDENT Exclusive Menus
	STUDENT_MAIN_MENU("StudentMM"),
	SUBMIT_ENQUIRY("SubmitE"),
	
	// COMMITTEE MEMBER Exclusive Menus
	CM_MAIN_MENU("ComitteeMemberMM"),
	SUBMIT_SUGGESTION("SubmitSuggestions"),
	
	// STAFF Exclusive Menus
	STAFF_MAIN_MENU("StaffMM"),
	EDIT_CAMP("EditCamp"),
	CREATE_CAMP("CreateCamp"),
	DELETE_CAMP("DeleteCamp"),
	VIEW_SUGGESTIONS("ViewSuggestions"),
	//APPROVE_SUGGESTION("ApproveSuggestion"),
	REPORT_PERFORMANCE("ReportPerformance");

	public String label;
	
	MenuStates (String label)
	{
		this.label = label;
	}
}
