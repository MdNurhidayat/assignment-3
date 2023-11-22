package staff;

import suggestion.BaseSuggestion;

public interface StaffSuggestion extends BaseSuggestion {
  public void viewSuggestions();
  public void approve(String suggestionID);
}
