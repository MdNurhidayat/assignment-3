package application;

public enum MENU_STATES 
{
	// Application Exclusive Menus
	PRELOG_IN("Prelogin"),
	LOG_IN("Login"),
	PRELOG_OUT("PRELOGOUT"),
	LOG_OUT("LOGOUT"),
	EXIT("EXIT"),
	
	
	// Shared Menu
	VIEW_CAMPS("ViewCamps"),
	CAMP_DETAILS("CampDetails"),
	
	// Shared Menu (STAFF & COMMITTEE MEMBER)
	SUBMIT_SUGGESTION("SubmitS"),
	SUBMIT_ENQUIRY("SubmitE"),
	APPROVE_SUGGESTION("ReplyS"),
	REPLY_ENQUIRY("ReplyE"),
	REPORT_PARTICIPANT("ReportParticipant"),
	
	// STUDENT Exclusive Menus
	STUDENT_MAIN_MENU("StudentMM"),
	
	// STAFF Exclusive Menus
	STAFF_MAIN_MENU("StaffMM"),
	EDIT_CAMP("EditCamp"),
	CREATE_CAMP("CreateCamp"),
	DELETE_CAMP("DeleteCamp"),
	REPORT_PERFORMANCE("ReportPerformance");

	public String label;
	
	MENU_STATES (String label)
	{
		this.label = label;
	}
}
