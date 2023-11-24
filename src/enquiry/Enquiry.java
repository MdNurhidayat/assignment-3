package enquiry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import camp.Camp;
import reply.Reply;
import student.Student;

/**
 * The Enquiry class represents a user's inquiry about a specific camp.
 * It contains information about the enquirer, the creation date, and any replies.
 * @author Wang Jing
 * @version 1.4
 * @since 2023-11-13
 */
public class Enquiry {
  private String enquiryID;
  private String campID;
  private String enquirerName;
  private LocalDate dateCreated;
  private String enquiryMessage;
  private ArrayList<Reply> replies;
  private boolean isProcessed = false;
  
  //TODO override equals method to compare enquiryID only

  public Enquiry(Scanner sc, Camp aCamp, Student std) {
    this.enquiryID = std.getUserID() + (std.getEnquiryCounter() + 1);
    std.setEnquiryCounter(std.getEnquiryCounter() + 1);
    this.dateCreated = LocalDate.now();
    this.campID = aCamp.getCampID();
    this.enquiryMessage = file.Input.getStringInput("Enter the contents of your enquiry: ", sc);
    this.replies = new ArrayList<>();
    this.isProcessed = false;
  }

  public String getEnquiryID() {
    return enquiryID;
  }
  
  public String getCampID() {
    return campID;
  }

  public LocalDate getDateCreated() {
    return dateCreated;
  }

  public String getContents() {
    return enquiryMessage;
  }

  public void setContents(String contents) {
    this.enquiryMessage = contents;
  }

  public ArrayList<Reply> getReplies() {
    if (this.replies.isEmpty()) {
      return null;
    }
    else {
      return replies;
    }
  }

  public void addReply(Reply reply) {
    this.replies.add(reply);
  }
  
  public boolean isProcessed() {
    return isProcessed;
  }

  public void setProcessed(boolean isProcessed) {
    this.isProcessed = isProcessed;
  }
  
  /**
   * Converts the Enquiry object to a TXT format string.
   *
   * @return A string containing TXT-formatted enquiry information.
   */
  public String toString() {
      String delimiter = " | ";
      return this.campID + delimiter + this.dateCreated + delimiter + this.enquirerName + delimiter
              + this.enquiryID + delimiter + this.enquiryMessage + delimiter + this.isProcessed;
  }

  /**
   * Generates CSV headers for the Enquiry class.
   *
   * @return A string containing CSV headers.
   */
  public static String generateCSVHeaders() {
      String delimiter = ", ";
      return "CampID" + delimiter + "DateCreated" + delimiter + "EnquirerName" + delimiter
              + "EnquiryID" + delimiter + "EnquiryMessage" + delimiter + "IsProcessed";
  }

  /**
   * Converts the Enquiry object to a CSV format string.
   *
   * @return A string containing CSV-formatted enquiry information.
   */
  public String toCSV() {
      String delimiter = ", ";
      return this.campID + delimiter + this.dateCreated + delimiter + this.enquirerName + delimiter
              + this.enquiryID + delimiter + this.enquiryMessage + delimiter + this.isProcessed;
  }
}

