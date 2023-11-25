package suggestion;

import java.time.LocalDate;
import java.util.Scanner;
import committeeMember.CommitteeMember;

public class Suggestion {
  private static int idCount;
  private String suggestionID;
  private LocalDate dateCreated;
  private CommitteeMember createdBy;
  private String content;
  private boolean isProcessed;

  public Suggestion(Scanner sc, CommitteeMember aCM) {
    idCount++;
    this.suggestionID = "SUG" + (idCount);
    this.dateCreated = LocalDate.now();
    this.createdBy = aCM;
    this.content = file.Input.getStringInput("Enter your suggestions: ", sc);
    this.isProcessed = false;
  }

  public String getSuggestionID() {
    return suggestionID;
  }

  public void setSuggestionID(String suggestionID) {
    this.suggestionID = suggestionID;
  }

  public String getCampID() {
    return createdBy.getOverseeingCamp().getCampID();
  }

  public LocalDate getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(LocalDate dateCreated) {
    this.dateCreated = dateCreated;
  }

  public CommitteeMember getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(CommitteeMember createdBy) {
    this.createdBy = createdBy;
  }

  public String getCreatorName() {
    return createdBy.getName();
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

  public void incrementPoint() {
    this.createdBy.incrementPoint();
  }
  
  @Override
  public String toString()
  {
	  String delimiter = " | ";
	  return this.suggestionID + delimiter + this.dateCreated + delimiter + this.createdBy + delimiter + this.content + delimiter + this.isProcessed + delimiter;
  }
}
