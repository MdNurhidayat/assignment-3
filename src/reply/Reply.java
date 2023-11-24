package reply;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import enquiry.Enquiry;
import user.User;
import file.Input;

public class Reply {
	private static int idCount;
	private String enquiryID;	  
	private String replyID;
	private LocalDate dateCreated;
	private LocalTime timeCreated; 
	private User sendor;
	private String contents;
  
    public Reply(Scanner scanner, Enquiry enquiry, User user) {
	    idCount++;
	    this.replyID = "REP" + idCount;
	    this.enquiryID = enquiry.getEnquiryID();
	    this.dateCreated = LocalDate.now();
	    this.timeCreated = LocalTime.now();
	    this.sendor = user;
	    this.contents = Input.getStringInput("Enter the contents of your Reply: ", scanner);
	    enquiry.setProcessed(true);
	    enquiry.addReply(this);
    }
 
    
    public String getEnquiryID()
    {
  	  return enquiryID;
    }
	
    public String getReplyID()
	{
    	return replyID;
	}
  
    public String getContents()
    {
    	return contents;
    }
}
