package staff;

import java.util.Scanner;
import suggestion.BaseSuggestion;

public interface StaffSuggestion extends BaseSuggestion {
  
  /**
   * Displays all suggestions belonging to a camp.
   * 
   * @see <code>Suggestion</code>
   */
  public void viewSuggestions();
  
  /**
   * Approves a suggestions belonging to a camp.
   * 
   * @param sc The scanner object to be injected.
   * @see <code>Suggestion</code>
   */
  public void approve(Scanner sc);
}