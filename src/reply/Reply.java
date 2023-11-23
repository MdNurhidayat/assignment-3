package reply;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import enums.Role;
import user.User;

public class Reply {
	private String enquiryID;
	private String replyID;
	private static int idCount;
	private LocalDate dateCreated;
	private LocalTime timeCreated; 
	private User sendor;
	private String sendorName;
	private String contents;

  
	public Reply(Role role, String contents, User aUser) {
		idCount++;
	    this.replyID = "REP" + idCount;
	    this.dateCreated = LocalDate.now();
	    this.timeCreated = LocalTime.now();
	    this.sendor = aUser;
	    this.sendorName = aUser.getName();
	    this.contents = contents;
	}
 
}
