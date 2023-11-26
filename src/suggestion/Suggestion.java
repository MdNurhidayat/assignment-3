package suggestion;

import java.time.LocalDate;
import java.util.Scanner;
import committeeMember.CommitteeMember;

/**
 *The Suggestion class is a part of the CommitteeMember fucntion in the system.
 * It extends from the CommitteeMember class and implements various interfaces
 * related to camp-related inquiries.
 * @author Derrick 
 * @version 1.4
 */

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

  /**
   * Set a new getSuggestionID
   *
   * @param getSuggestionID The new value to be assigned
   */
  public String getSuggestionID() {
    return suggestionID;
  }

  /**
   * Set a new setSuggestionID
   *
   * @param setSuggestionID The new value to be assigned
   */
  public void setSuggestionID(String suggestionID) {
    this.suggestionID = suggestionID;
  }

  /**
   * Set a new getCampID
   *
   * @param getCampID tagged to the CommitteeMember
   */
  public String getCampID() {
    return createdBy.getOverseeingCamp().getCampID();
  }

  /**
   * Create a new LocalDate data
   *
   * @param LocalDate to retreive the current date
   */  public LocalDate getDateCreated() {
    return dateCreated;
  }

   /**
    * Set a new setDateCreated
    *
    * @param setDateCreated call the localdate data
    */
  public void setDateCreated(LocalDate dateCreated) {
    this.dateCreated = dateCreated;
  }

  /**
   * Call the CommitteeMember that created it
  *
  * @param record down the Committeemember
  */
  public CommitteeMember getCreatedBy() {
    return createdBy;
  }

  /**
   * Set a new setCreatedBy
   *
   * @param setCreatedBy call from the createedBy method
   */
  public void setCreatedBy(CommitteeMember createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Set a new getCreatorName
   *
   * @param getCreatorName call the CommitteeMember name 
   */
  public String getCreatorName() {
    return createdBy.getName();
  }

  /**
   * Create a new getContent
   *
   * @param getContent create to record down the input
   */
  public String getContent() {
    return content;
  }

  /**
   * Set a new setContent
   *
   * @param setContent call the content function
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Cretae a boolean isProcessed
   *
   * @param isProcessed boolean to true or false
   */
  public boolean isProcessed() {
    return isProcessed;
  }

  /**
   * Set a new setProcessed
   *
   * @param setProcessed call for isProcessed
   */
  public void setProcessed(boolean isProcessed) {
    this.isProcessed = isProcessed;
  }

  /**
   * create a new incrementPoint
   *
   * @param incrementPoint increase after a new suggestion is created
   */
  public void incrementPoint() {
    this.createdBy.incrementPoint();
  }
  
  @Override
  /**
   * Return a new toString
   *
   * @param suggestionID 	The suggestion ID of the sugegestion created.
   * @param dateCreated   	The date created when suggestion is created.
   * @param content 		The content of the suggestion.
   * @param createdBy     	The name of the CommitteeMember.
   * @param isProcessed     The check of the suggestion being processed.
   */
  public String toString()
  {
	  String delimiter = " | ";
	  return this.suggestionID + delimiter + this.dateCreated + delimiter + this.createdBy + delimiter + this.content + delimiter + this.isProcessed + delimiter;
  }
}
