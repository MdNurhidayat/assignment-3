package enquiry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import camp.Camp;
import camp.Detail;
import enums.Role;
import reply.Reply;
import suggestion.Suggestion;
import user.User;
import file.Input;

public class Enquiry {
  private static int idCount;
  private String enquiryID;
  private String campID;
  private LocalDate dateCreated;
  private String contents;
  private ArrayList<Reply> replies;
  private boolean isProcessed = false;
  
  //TODO override equals method to compare enquiryID only

  public Enquiry(Scanner sc, Camp aCamp) {
    idCount++;
    this.enquiryID = "ENQ" + (idCount);
    this.dateCreated = LocalDate.now();
    this.campID = aCamp.getCampID();
    this.contents = Input.getStringInput("Enter the contents of your enquiry: ", sc);
    this.replies = new ArrayList<Reply>();
    this.isProcessed = false;
  }
  
  public Enquiry(String campID, LocalDate now, String enquiryID, String enquiryMessage) 
  {
	  idCount++;
	  this.enquiryID = enquiryID;
	  this.dateCreated = now;
	  this.campID = campID;
	  this.contents = enquiryMessage;
	  this.replies = new ArrayList<Reply>();
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
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public ArrayList<Reply> getReplies() {
    if (this.replies.isEmpty()) {
      return null;
    }
    else {
      return replies;
    }
  }

  public void addReply(Role role, String content, User aUser) {
    this.replies.add(new Reply(role, content, aUser));
  }
  
  public boolean isProcessed() {
    return isProcessed;
  }

  public void setProcessed(boolean isProcessed) {
    this.isProcessed = isProcessed;
  }


  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Enquiry test = new Enquiry(sc, null);
    System.out.println(test.getEnquiryID());
    System.out.println(test.getDateCreated());
    System.out.println(test.getContents());
    System.out.println(test.getReplies());
    System.out.println(test.isProcessed());
    Enquiry test2 = new Enquiry(sc, null);
    System.out.println(test2.getEnquiryID());
    System.out.println(test2.getDateCreated());
    System.out.println(test2.getContents());
    System.out.println(test2.getReplies());
    System.out.println(test2.isProcessed());
  }

}

