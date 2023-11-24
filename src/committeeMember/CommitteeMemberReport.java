package committeeMember;

import java.util.Scanner;

public interface CommitteeMemberReport {
	  /**
	   * Generates the participant report of a camp.
	   * 
	   * @param sc The scanner object to be injected.
	   * @see <code>Enquiry</code>
	   */
	  public void generateParticipantReport(Scanner sc);
}
