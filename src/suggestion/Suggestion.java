package suggestion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import camp.Camp;
import committeeMember.CommitteeMember;

public class Suggestion {
  private static ArrayList<Suggestion> allSuggestions = new ArrayList<>();
  private static int idCount;
  private String suggestionID;
  private String campID;
  private LocalDate dateCreated;
  private CommitteeMember createdBy;
  private String creatorName;
  private String content;
  private boolean isProcessed;

  public Suggestion(Scanner sc, CommitteeMember aCM, Camp aCamp) {
    idCount++;
    this.suggestionID = "SUG" + (idCount);
    this.campID = aCamp.getCampID();
    this.dateCreated = LocalDate.now();
    this.createdBy = aCM;
    this.creatorName = aCM.getName();
    this.content = input.Input.getStringInput("Enter your suggestions: ", sc);
    this.isProcessed = false;
    allSuggestions.add(this);
  }
  
  
  
  public static ArrayList<Suggestion> getAllSuggestions() {
    return allSuggestions;
  }



  public static void setAllSuggestions(ArrayList<Suggestion> allSuggestions) {
    Suggestion.allSuggestions = allSuggestions;
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



  public CommitteeMember getCreatedBy() {
    return createdBy;
  }



  public void setCreatedBy(CommitteeMember createdBy) {
    this.createdBy = createdBy;
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



  public void incrementPoint() {
    this.createdBy.incrementPoint();
  }
  
}
