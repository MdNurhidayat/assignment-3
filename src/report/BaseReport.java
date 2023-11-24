package report;

import java.util.Scanner;

/**
 * Represents the BaseReport interface. Abstract methods relating to the interactions
 * with the <code>Staff</code> and <code>CommitteeMember</code> class.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 * @see <code>Staff</code>, <code>CommitteeMember</code>
 */
public interface BaseReport {
  
  /**
   * Generates the participant report of a camp.
   * 
   * @see <code>Enquiry</code>
   */
  public void generateParticipantReport(Scanner sc);
}
