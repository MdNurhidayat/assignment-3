package suggestion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import camp.Camp;
import committeeMember.CommitteeMember;
import file.Input;

public class Suggestion {
  private static int idCount;
  private String suggestionID;
  private String campID;
  private LocalDate dateCreated;
  private String creatorName;
  private String content;
  private boolean isProcessed;
  private boolean isApproved;

  public Suggestion(Scanner sc, CommitteeMember aCM, Camp aCamp) {
    idCount++;
    this.suggestionID = "SUG" + (idCount);
    this.campID = aCamp.getCampID();
    this.dateCreated = LocalDate.now();
    this.creatorName = aCM.getName();
    this.content = Input.getStringInput("Enter your suggestions: ", sc);
    this.isProcessed = false;
    this.isApproved = false;
  }

  public String getSuggestionID() {
    return suggestionID;
  }

  public void setSuggestionID(String suggestionID) {
    this.suggestionID = suggestionID;
  }

  public String getCampID() {
    return campID;
  }

  public void setCampID(String campID) {
    this.campID = campID;
  }

  public LocalDate getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(LocalDate dateCreated) {
    this.dateCreated = dateCreated;
  }

  public String getCreatorName() {
    return creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public String getContent() {
    return content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }

  public boolean isProcessed() {
    return isProcessed;
  }

  public void setProcessed(boolean isProcessed) {
    this.isProcessed = isProcessed;
  }
  
  public boolean isApproved()
  {
	  return this.isApproved;
  }

  public void setApproved(boolean approveStatus)
  {
	  this.isProcessed = true;
	  this.isApproved = approveStatus;
  }
}
