package enquiry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import camp.Camp;
import camp.Detail;
import enums.Role;
import reply.Reply;
import staff.Staff;
import suggestion.Suggestion;
import user.User;

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
    this.contents = file.Input.getStringInput("Enter the contents of your enquiry: ", sc);
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

  public void addReply(Reply reply) {
    this.replies.add(reply);
  }
  
  public boolean isProcessed() {
    return isProcessed;
  }

  public void setProcessed(boolean isProcessed) {
    this.isProcessed = isProcessed;
  }
  public String toCSV() {
    return null;
    // TODO Auto-generated method stub
    
  }

  public static String generateCSVHeaders() {
    return null;
    // TODO Auto-generated method stub
    
  }


}

