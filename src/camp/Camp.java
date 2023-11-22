package camp;

import java.util.ArrayList;
import java.util.Scanner;
import committeeMember.CommitteeMember;
import enquiry.Enquiry;
import enums.Faculty;
import enums.Role;
import staff.Staff;
import student.Student;
import suggestion.Suggestion;
import file.Input;

/**
 * Represents a <code>camp</code>. Used in conjunction with the <code>Staff</code> class. Can only
 * be created by a <code>Staff</code>.
 * 
 * @author Nah Wei Jie
 * @version 1.04
 * @see <code>Staff</code>
 */
public class Camp {
  /**
   * Represents a list of all <code>camp</code> objects. Calls to the <code>camp</code> constructor
   * method adds the newly created instance to this list.
   */
  private static ArrayList<Camp> allCamps = new ArrayList<>();
  /**
   * Represents the count of camps created so far.
   */
  private static int idCount;
  /**
   * Represents the ID of this camp.
   */
  private String campID;
  /**
   * Represents if this camp is currently visible.
   */
  private boolean isVisible;
  /**
   * Represents the details of this camp.
   * 
   * @see <code>Detail</code>
   */
  private Detail details;
  /**
   * Represents the participants of this camp.
   */
  private ArrayList<Student> participants;
  /**
   * Represents the participants who has withdrawn after registering for this camp.
   */
  private ArrayList<Student> previouslyWithdrawn;
  /**
   * Represents the committee members of this camp.
   */
  private ArrayList<CommitteeMember> committee;
  /**
   * Represents the enquiries submitted for this camp.
   */
  private ArrayList<Enquiry> enquiries;
  /**
   * Represents the suggestions submitted for this camp.
   */
  private ArrayList<Suggestion> suggestions;
  
  public static ArrayList<Camp> getAllCamps(){
    return Camp.allCamps;
  }
  
  public String getCampID() {
    return campID;
  }

  public boolean getisVisible() {
    return isVisible;
  }
  
  public Detail getDetails() {
    return details;
  }
  
  public ArrayList<Student> getParticipants() {
    return participants;
  }

  public ArrayList<Student> getPreviouslyWithdrawn() {
    return previouslyWithdrawn;
  }

  public ArrayList<CommitteeMember> getCommittee() {
    return committee;
  }

  public ArrayList<Enquiry> getEnquiries() {
    return enquiries;
  }

  public ArrayList<Suggestion> getSuggestions() {
    return suggestions;
  }
  
  
  //non-default methods, to be reflected in UML
  
  /**
   * Custom constructor method. Uses multiple set methods from this camp's <code>detail</code>
   * object, various get input methods from the <code>Input</code> class are injected to provide
   * user defined arguments. Only called by a <code>Staff</code> object.
   * 
   * @see <code>Staff</code>, <code>Detail</code>
   * @param sc Scanner object to be injected
   * @param aStaff Staff object to be injected
   */
  public Camp(Scanner sc, Staff aStaff) {
    idCount++;
    this.campID = "CAMP" + idCount;
    this.isVisible = false;
    this.details = new Detail(sc, aStaff);
    this.participants = new ArrayList<>();
    this.previouslyWithdrawn = new ArrayList<>();
    this.committee = new ArrayList<>();
    this.enquiries = new ArrayList<>();
    this.suggestions = new ArrayList<>();
    allCamps.add(this);
  }
  
  /**
   * Sets this camp to be visible.
   */
  public void setVisibleOn() {
    this.isVisible = true;
  }

  /**
   * Sets the camp to not be visible. Only allowed when the <code>hasNoParticipants</code> and
   * <code>hasNoCommittee</code> methods returns true.
   */
  public void setVisibleOff() {
    if (this.hasNoCommittee() && this.hasNoParticipants()) {
      System.out.println(
          "You cannot set this camp to not be visible once student(s) or committee member(s) have registered.");
    } else {
      this.isVisible = true;
    }
  }

  /**
   * Custom settor method. Uses multiple set methods from this camp's <code>detail</code> object,
   * various get input methods from the <code>Input</code> class are injected to provide user
   * defined arguments. Only called by a <code>Staff</code> object.
   * 
   * @see <code>Staff</code>, <code>Detail</code>
   * @param sc Scanner object to be injected
   */
  public void setDetails(Scanner sc) {
    String facultyAttribute = null;
    this.details.setName(Input.getStringInput("Enter the name of the camp: ", sc));
    this.details.setLocation(Input.getStringInput("Enter the location of the camp: ", sc));
    this.details
        .setDescription(Input.getStringInput("Enter the description of the camp: ", sc));
    int inputValue;
    do {
      inputValue = Input.getIntInput(
          "(1) Staff\n(2) Student \n(3) Committee Member\n Enter your selection: ", sc);
      switch (inputValue) {
        case 1: {
          facultyAttribute = "STAFF";
          break;
        }
        case 2: {
          facultyAttribute = "STUDENT";
          break;
        }
        case 3: {
          facultyAttribute = "COMMITTEE_MEMBER";
          break;
        }
        default: {
          System.out.println("You have selected an invalid input please re-enter");
          break;
        }
      }

    } while (facultyAttribute == null);
    this.details.setFaculty(enums.Faculty.getFacultyForAttribute(facultyAttribute));
    this.details.setStartDate(Input.getDateFromInput("starting date", sc));
    this.details.setEndDate(Input.getDateFromInput("ending date", sc));
    this.details
        .setRegistrationClosingDate(Input.getDateFromInput("registration deadline", sc));
    this.details
        .setTotalSlots(Input.getIntInput("Enter the total slots for this camp: ", sc));
  }

  /**
   * Adds a student into this camp's <code>participants</code> list.
   * 
   * @param aStudent <code>Student</code> object
   */
  public void addParticipant(Student aStudent) {
    this.participants.add(aStudent);

  }
  
  /**
   * Return true if there are no participants in this camp.
   */
  public boolean hasNoParticipants() {
    return this.participants.isEmpty();
  }

  /**
   * Return true if there are no committee in this camp.
   */
  public boolean hasNoCommittee() {
    return this.committee.isEmpty();
  }

  /**
   * Adds a student into this camp's <code>previouslyWithdrawn</code> list.
   * 
   * @param aStudent <code>Student</code> object
   */
  public void addWithdrawn(Student aStudent) {
    this.previouslyWithdrawn.add(aStudent);
  }
  
  /**
   * Removes a student from this camp's <code>participants</code> list.
   * 
   * @param aStudent <code>Student</code> object
   */
  public void removeParticipant(Student aStudent) {
    this.participants.remove(aStudent);

  }

  /**
   * Adds a committee membber into this camp's <code>committee</code> list.
   * 
   * @param aCommitteeMember <code>CommitteeMember</code> object
   */
  public void addCommittee(CommitteeMember aCommitteeMember) {
    this.committee.add(aCommitteeMember);
  }

  /**
   * Calculates and returns the remaining number of slots for this camp.
   * 
   * @return integer result of this camp details' number of total slots subtracted by the size of
   *         this camp's participants list and the size of the committee array.
   */
  public int calculateRemainingSlots() {
    return this.details.getTotalSlots() - this.participants.size() - this.committee.size();
  }
  
  /**
   * Calculates and returns the remaining number of slots for committee members for this camp.
   * 
   * @return integer result of this camp details' number of committee slots subtracted by the size
   *         of this camp's committee list.
   */
  public int calculateRemainingCommitteeSlots() {
    return this.details.getCOMMITTEE_SLOTS() - this.committee.size();
  }
  
  public void deleteCamp() {
    Camp.allCamps.remove(this);
  }
  
  public String toString() {
    String delimiter = " ";
    return this.campID + delimiter 
           + this.isVisible + delimiter
           + this.details.toString();
    }
  
  public String generateCSVHeaders() {
    String delimiter = ", ";
    return "campID" + delimiter 
        + "isVisible" + delimiter
        + this.details.generateCSVHeaders();
  }
  
  public String toCSV() {
    generateCSVHeaders();
    String delimiter = ", ";
    return this.campID + delimiter 
           + this.isVisible + delimiter
           + this.details.toCSV();
    }
  
  public void print() {
    System.out.println("StaffInCharge: " + this.details.getStaffInCharge());
    System.out.println("Name: " + this.details.getName());
    System.out.println("Location: " + this.details.getLocation());
    System.out.println("Description: " + this.details.getDescription());
    System.out.println("Faculty: " + this.details.getFaculty());
    System.out.println("StaffInCharge: " + this.details.getStaffInCharge());
    System.out.println("StartDate: " + this.details.getStartDate().getDayOfMonth()
                       + "-" + this.details.getStartDate().getMonthValue() 
                       + "-" + this.details.getStartDate().getYear());
    System.out.println("EndDate: " + this.details.getEndDate().getDayOfMonth()
                       + "-" + this.details.getEndDate().getMonthValue() 
                       + "-" + this.details.getEndDate().getYear());
    System.out.println("RegistrationClosingDate: " + this.details.getRegistrationClosingDate().getDayOfMonth()
                       + "-" + this.details.getRegistrationClosingDate().getMonthValue() 
                       + "-" + this.details.getRegistrationClosingDate().getYear());
    System.out.println("TotalSlots: " + this.details.getTotalSlots());
    System.out.println("CommitteeSlots: " + this.details.getCOMMITTEE_SLOTS());
  }
}
