package staff;

import java.util.ArrayList;
import java.util.Scanner;
import camp.Camp;
import committeeMember.CommitteeMember;
import enquiry.BaseEnquiry;
import enquiry.Enquiry;
import enquiry.ReplyEnquiry;
import enums.Faculty;
import enums.Role;
import student.Student;
import suggestion.Suggestion;
import user.User;

/**
 * Represents a <code>Staff</code>. Child/Sub/Derived class from the <code>User</code> class.
 * 
 * @author Nah Wei Jie
 * @version 1.0
 * @see <code>Staff</code>
 */

public class Staff extends User implements StaffSuggestion, StaffReport, BaseEnquiry, ReplyEnquiry {
  private Camp createdCamp = null;

  public Staff(String userID, Role role, String name, String email, Faculty faculty) {
    super(userID, role, name, email, faculty);
  }
  
  public Staff(String userID, String password, Role role, String name, String email, Faculty faculty) {
	    super(userID, password, role, name, email, faculty);
	  }
  
  public String getName() {
    return super.getName();
  }

  // non-default methods, to be reflected in UML
  
  public Camp getCreatedCamp() {
    if (this.createdCamp != null) {
      return createdCamp;
    }
    return null;
  }

  /**
   * Returns true or false depending on whether this staff has currently craeted a
   * <code>camp</code>.
   */
  private boolean hasCreatedCamp() {
    if (this.getCreatedCamp() == null) {
      return false;
    }
    return true;
  }

  /**
   * Creates a new camp with this staff as the creator.
   * 
   * @param sc Scanner object to be injected
   */
  public void createCamp(Scanner sc) {
    if (this.hasCreatedCamp()) {
      System.out.println(
          "You have created a camp already. Each staff can only be a creator of a single camp at any given point in time.");
      System.out.println(
          "Please edit to modify the camp details or delete your existing one instead before creating another.");
    }
    this.createdCamp = new Camp(sc, this);
    System.out.println("Camp created successfully.");
  }

  /**
   * Edits the details of a created camp.
   * 
   * @param sc Scanner object to be injected
   */
  public void editCamp(Scanner sc) {
    if (!this.hasCreatedCamp()) {
      System.out.println("Please create a camp before attempting to edit one");
    } else {
      this.createdCamp.setDetails(sc);
      System.out.println("Camp edited successfully.");
    }
  }

  /**
   * Deletes the camp created by this staff.
   * @param sc Scanner object to be injected
   */
  public void deleteCamp() {
    if (!this.hasCreatedCamp()) {
      System.out.println("Please create a camp before trying to delete one");
    }
    else {
      this.createdCamp = null;
      System.out.println("Camp deleted successfully.");
    }

  }

  public void viewAllCamps(ArrayList<Camp> camps) {
    for (Camp c: camps) {
      c.detailedPrint();
    }
  }

  public void viewCreatedCamp() {
    this.createdCamp.toString();
  }

  @Override
  public void viewEnquiries() {
    this.createdCamp.getEnquiries().toString();
  }

  @Override
  public void replyEnquiry(String enquiryID, String replyMessage) {
    ArrayList<Enquiry> enquiries = this.createdCamp.getEnquiries();
    for (Enquiry e : enquiries) {
      if (!e.getEnquiryID().equals(enquiryID)) {
        System.out.println("EnquiryID  " + enquiryID + "provided not found in this camp's list of enquiries, please try again.");
      }
      else {
        // TODO add derrick's method call for creating reply
        System.out.println("Enquiry " + enquiryID + " replied.");
      }
    }

  }

  public void viewSuggestions() {
    this.createdCamp.getSuggestions().toString();
  }

  public void approve(String suggestionID) {
    ArrayList<Suggestion> suggestions = this.createdCamp.getSuggestions();
    for (Suggestion s : suggestions) {
      if (!s.getSuggestionID().equals(suggestionID)) {
        System.out.println("SuggestionID " + suggestionID + " provided not found in this camp's list of suggestions, please try again.");
      }
      else {
        s.setProcessed(true);
        System.out.println("Suggestion " + suggestionID + " approved.");
      }
    }
  }

  @Override
  public void generateParticipantReport() {
    ArrayList<Student> participantsResult = this.createdCamp.getParticipants();
    ArrayList<CommitteeMember> committeeResult = this.createdCamp.getCommittee();
    
    for (Student p: participantsResult) {
      System.out.println("Name: " + ((User) p).getName());
      System.out.println("Email: " + ((User) p).getEmail());
      System.out.println("Role: " + ((User) p).getRole());
      System.out.println("Faculty: " + ((User) p).getFaculty());
      
      
    }
    for (CommitteeMember cm: committeeResult) {
      System.out.println();
    }
    
  }

  public void generatePerformanceReport(String format) {
    ArrayList<CommitteeMember> committeeResult = this.createdCamp.getCommittee();
    
    switch (format) {
      case "csv": {
        for (CommitteeMember cm : committeeResult) {
          System.out.println(cm.getName());
          
        }
      }
      case "txt": {
        
        for (CommitteeMember cm : committeeResult) {
          
        }
      }
    }

  }

  public void generateEnquiryReport() {
    // TODO add methods to be called from Wang Jing side

  }


}
