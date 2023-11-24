package committeeMember;

import java.util.Scanner;

import report.BaseReport;

public interface CommitteeMemberReport extends BaseReport {
	  /**
	   * Generates the participant report of a camp.
	   * 
	   * @param sc The scanner object to be injected.
	   * @see <code>Enquiry</code>
	   */
	  public void generateParticipantReport(Scanner sc);
	  
	  /**
	   * Generates the enquiry report of a camp.
	   * 
	   * @param sc The scanner object to be injected.
	   * @see <code>Enquiry</code>
	   */
	  public void generateEnquiryReport(Scanner sc);
}
