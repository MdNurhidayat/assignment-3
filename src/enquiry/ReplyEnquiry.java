package enquiry;

import java.util.Scanner;
/**
 * Represents the ReplyEnquiry interface. Abstract methods relating to the interactions
 * with the <code>Enquiry</code> class must be implemented.
 * 
 * @author Nah Wei Jie
 * @version 1.1
 */
public interface ReplyEnquiry {

  /**
   * Reply to an enquiry belonging to a camp.
   * 
   * @see <code>Enquiry</code>
   */
  void replyEnquiry(String replyMessage, Scanner sc);
}
