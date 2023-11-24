package staff;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import camp.Camp;
import committeeMember.CommitteeMember;
import enquiry.BaseEnquiry;
import enquiry.Enquiry;
import enquiry.ReplyEnquiry;
import enums.DateFilter;
import enums.Faculty;
import enums.Format;
import enums.Role;
import enums.RoleFilter;
import student.Student;
import suggestion.Suggestion;
import user.User;
import file.*;
import reply.Reply;

/**
 * Represents a <code>Staff</code>. Child/Sub/Derived class from the <code>User</code> class.
 * 
 * @author Nah Wei Jie
 * @version 1.0
 * @see <code>Staff</code>
 */

public class Staff extends User implements StaffSuggestion, StaffReport, BaseEnquiry, ReplyEnquiry {
  private Camp createdCamp = null;

  // default methods, Javadoc skipped as methods are self-explanatory
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

  /**
   * Returns a created <code>Camp</code> created by this staff.
   * 
   * @return <code>Camp</code> object tied to the <code>createdCamp</code> attribute.
   */
  public Camp getCreatedCamp() {
	  return createdCamp;
  }

  /**
   * Returns true or false depending on whether this staff has currently created a camp.
   * <code>camp</code>.
   */
  public boolean hasCreatedCamp() {
    if (getCreatedCamp() == null) {
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
   * 
   */
  public void deleteCamp() {
    if (!this.hasCreatedCamp()) {
      System.out.println("Please create a camp before trying to delete one");
    } else {
      // TODO @hid include method to delete the camp from the master list of camps.
      this.createdCamp = null;
      System.out.println("Camp deleted successfully.");
    }

  }

  /**
   * Displays the detailed information of all the camps created which belongs to
   * <code>Faculty.NTU</code> or the same <code>Faculty</code> as this staff.
   * 
   */
  public void viewAllCamps(ArrayList<Camp> camps) {
    // TODO @hid, please modify line below to assign results to retrieve all camps from your end
    // instead of the static list (allCamps) previously declared under the camp class.
    if (camps.isEmpty())
      System.out.println("No camps created so far.");
    else {
      for (Camp c : camps) {
        if (c.getDetails().getFaculty() == Faculty.NTU
            || c.getDetails().getFaculty() == this.getFaculty()) {
          System.out.println();
          c.detailedPrint();
        }
      }
    }
  }

  /**
   * Displays the detailed information of the camp created by this staff.
   */
  public void viewCreatedCamps() {
    if (!hasCreatedCamp())
      System.out.println("No camp to show. Please create a camp first.");
    else
      this.createdCamp.detailedPrint();
  }

  /**
   * Displays all the enquiries belonging to the camp created by this staff.
   */
  public void viewEnquiries() {
    if (!hasCreatedCamp())
      System.out.println("Please create a camp first.");
    else {
      ArrayList<Enquiry> results = this.createdCamp.getEnquiries();
      if (!results.isEmpty()) {
        String delimiter = "-";
        String paddingParameters = "| %-10s | %-25s | %-20s | %-10s | %-40s | %-10s | \n";
        System.out.println("Your Camp's Enquiries");
        System.out.println(delimiter.repeat(130));
        System.out.printf(paddingParameters, "CampID", "Date Created", "EnquirerName", "EnquiryID",
            "EnquiryMessage", "IsProcessed");
        System.out.println(delimiter.repeat(130));
        for (Enquiry e : results) {
          e.toString();
        }
        System.out.println(delimiter.repeat(130));
        System.out.println();
      }
      else
    	  System.out.println("Current camp has no enquiries to show. Check back later.");
    }
  }

  /**
   * Reply a specific enquiry belonging to the camp created by this staff.
   * 
   * @param replyMessage Message containing the reply of this staff.
   * @param sc Scanner object to be injected.
   */
  @Override
  public void replyEnquiry(Scanner sc) {
      ArrayList<Enquiry> enquiries = this.createdCamp.getEnquiries();
      String enquiryID = Input.getStringInput("Enter enquiryID of enquiry to edit: ", sc);
      for (Enquiry e : enquiries) {
        if (e.getEnquiryID().equals(enquiryID))
        {
        	Reply reply = new Reply(sc, e, this);
        	e.addReply(reply);
          System.out.println("Enquiry " + enquiryID + " replied.");
        }
      }
      System.out.println("EnquiryID  " + enquiryID + " provided not found in this camp's list of enquiries, please try again.");
  }

  /**
   * Displays all the suggestions belonging to the camp created by this staff.
   */
  @Override
  public void viewSuggestions() {
    if (!hasCreatedCamp())
      System.out.println("Please create a camp first.");
    else {
      ArrayList<Suggestion> results = this.createdCamp.getSuggestions();
      if (!results.isEmpty()) {
        String delimiter = "-";
        String paddingParameters = "| %-10s | %-25s | %-20s | %-10s | %-40s | %-10s | \n";
        System.out.println("Suggestion");
        System.out.println(delimiter.repeat(130));
        System.out.printf(paddingParameters, "CampID", "Date Created", "SuggestorName",
            "SuggestionID", "SuggestionMessage", "IsProcessed");
        System.out.println(delimiter.repeat(130));
        for (Suggestion s : results) {
          s.toString();
        }
        System.out.println(delimiter.repeat(130));
        System.out.println();
      }
      System.out.println("Current camp has no suggestions to show. Check back later.");
    }
  }

  /**
   * Approves a specific suggestion belonging to the camp created by this staff.
   * 
   * @param sc Scanner object to be injected.
   */
  @Override
  public void approve(Scanner sc) {
    String suggestionID = file.Input.getStringInput("Enter the suggestionID to approve: ", sc);
    ArrayList<Suggestion> results = this.createdCamp.getSuggestions();
    if (results.isEmpty())
      System.out.println("SuggestionID " + suggestionID
          + " provided not found in this camp's list of suggestions, please try again.");
    else {
      for (Suggestion s : results) {
        if (s.getSuggestionID().equals(suggestionID)) {
          
          s.setProcessed(true);
          s.getCreatedBy().incrementPoint();
          System.out.println("Suggestion " + suggestionID + " approved.");
          return;
        }
      }
      System.out.println("SuggestionID " + suggestionID
              + " provided not found in this camp's list of suggestions, please try again.");
    }
  }

  /**
   * Generates a report of participants with filters based on role belonging to the camp created by this staff. Option to save given after report generation.
   * 
   * @param sc Scanner object to be injected.
   */
  @Override
  public void generateParticipantReport(Scanner sc) {
    // early exit if no camp created
    if (!hasCreatedCamp())
      System.out.println(
          "You have not created a camp yet. Please create a camp before generating report.");
    else {
      ArrayList<Student> students = this.createdCamp.getParticipants();
      ArrayList<CommitteeMember> committee = this.createdCamp.getCommittee();
      // prompt for filter
      String filterYesOrNo = file.Input
          .getStringInput("Do you wish to filter the report by role?: (y/n) ", sc).toLowerCase();
      RoleFilter filterSelection = enums.RoleFilter.getRoleFilterFromStringInput(sc);
      // prompt for format
      Format formatSelection = enums.Format.getFormatFromStringInput(sc);
      if (filterYesOrNo == "y") {
        // generate report with filters base on format selection
        switch (filterSelection) {
          case STUDENT: {
            if (formatSelection == Format.CSV) {
              for (Student s : students) {
                s.toCSV();
              }
            } else {
              for (Student s : students) {
                s.toString();
              }
            }
          }
          case COMMITTEE_MEMBER: {
            if (formatSelection == Format.CSV) {
              for (CommitteeMember cm : committee) {
                cm.toCSV();
              }
            } else {
              for (CommitteeMember cm : committee) {
                cm.toString();
              }
            }
          }
          default:
            if (formatSelection == Format.CSV) {
              for (Student s : students) {
                s.toCSV();
              }
              for (CommitteeMember cm : committee) {
                cm.toCSV();
              }
            } else {
              for (Student s : students) {
                s.toString();
              }
              for (CommitteeMember cm : committee) {
                cm.toString();
              }
            }
        }
        // prompt if user wishes to save the report
        String saveYesOrNo = file.Input
            .getStringInput("Do you wish to save the report as a file?: (y/n) ", sc).toLowerCase();
        if (saveYesOrNo == "y") {
          if (filterYesOrNo == "y") {
            switch (filterSelection) {
              case STUDENT: {
                if (formatSelection == Format.CSV) { // save as .csv
                  String fileName = file.Input.getStringInput(
                      "Please enter the name of the output file (do not include file extension): ",
                      sc);
                  ArrayList<String> toSave = new ArrayList<>();
                  toSave.add(student.Student.generateCSVHeaders());
                  for (Student s : students) {
                    toSave.add(s.toCSV());
                  }
                  FileIO.writeToFile(formatSelection, fileName, toSave);
                  System.out.println("File saved.");
                } else { // save as .txt
                  String fileName = file.Input.getStringInput(
                      "Please enter the name of the output file (do not include file extension): ",
                      sc);
                  ArrayList<String> toSave = new ArrayList<>();
                  for (Student s : students) {
                    toSave.add(s.toString());
                  }
                  FileIO.writeToFile(formatSelection, fileName, toSave);
                  System.out.println("File saved.");
                }
              }
              case COMMITTEE_MEMBER: {
                if (formatSelection == Format.CSV) { // save as .csv
                  String fileName = file.Input.getStringInput(
                      "Please enter the name of the output file (do not include file extension): ",
                      sc);
                  ArrayList<String> toSave = new ArrayList<>();
                  toSave.add(committeeMember.CommitteeMember.generateCSVHeaders());
                  for (CommitteeMember cm : committee) {
                    toSave.add(cm.toCSV());
                  }
                  file.FileIO.writeToFile(formatSelection, fileName, toSave);
                  System.out.println("File saved.");
                } else { // save as .txt
                  String fileName = file.Input.getStringInput(
                      "Please enter the name of the output file (do not include file extension): ",
                      sc);
                  ArrayList<String> toSave = new ArrayList<>();
                  for (CommitteeMember cm : committee) {
                    toSave.add(cm.toString());
                  }
                  file.FileIO.writeToFile(formatSelection, fileName, toSave);
                  System.out.println("File saved.");
                }
              }
              default: {
                if (formatSelection == Format.CSV) { // save as .csv
                  String fileName = file.Input.getStringInput(
                      "Please enter the name of the output file (do not include file extension): ",
                      sc);
                  ArrayList<String> toSave = new ArrayList<>();
                  toSave.add(committeeMember.CommitteeMember.generateCSVHeaders());
                  for (CommitteeMember cm : committee) {
                    toSave.add(cm.toCSV());
                  }
                  for (Student s : students) {
                    toSave.add(s.toCSV());
                  }
                  file.FileIO.writeToFile(formatSelection, fileName, toSave);
                  System.out.println("File saved.");
                } else {
                  String fileName = file.Input.getStringInput(
                      "Please enter the name of the output file (do not include file extension): ",
                      sc);
                  ArrayList<String> toSave = new ArrayList<>();
                  for (CommitteeMember cm : committee) {
                    toSave.add(cm.toCSV());
                  }
                  for (Student s : students) {
                    toSave.add(s.toCSV());
                  }
                  file.FileIO.writeToFile(formatSelection, fileName, toSave);
                  System.out.println("File saved.");
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * Generates a report of committee member performace belonging to the camp created by this staff. Option to save given after report generation.
   * 
   * @param sc Scanner object to be injected.
   */
  @Override
  public void generatePerformanceReport(Scanner sc) {
    // early exit if no camp created
    if (!hasCreatedCamp())
      System.out.println(
          "You have not created a camp yet. Please create a camp before generating report.");
    else {
      // generate report based on format selection
      ArrayList<CommitteeMember> committee = this.createdCamp.getCommittee();
      Format formatSelection = enums.Format.getFormatFromStringInput(sc);
      if (formatSelection == Format.CSV) {
        committeeMember.CommitteeMember.generateCSVHeaders();
        for (CommitteeMember cm : committee) {
          cm.toCSV();
        }
      } else {
        for (CommitteeMember cm : committee) {
          cm.toString();
        }
      }

      // prompt if user wishes to save
      String saveYesOrNo = file.Input
          .getStringInput("Do you wish to save the report as a file?: (y/n) ", sc).toLowerCase();
      if (saveYesOrNo == "y") {
        // save as .csv
        if (formatSelection == Format.CSV) {
          String fileName = file.Input.getStringInput(
              "Please enter the name of the output file (do not include file extension): ", sc);
          ArrayList<String> toSave = new ArrayList<>();
          toSave.add(committeeMember.CommitteeMember.generateCSVHeaders());
          for (CommitteeMember cm : committee) {
            toSave.add(cm.toCSV());
          }
          file.FileIO.writeToFile(formatSelection, fileName, toSave);
          System.out.println("File saved.");

        }
        // save as .txt
        else {
          String fileName = file.Input.getStringInput(
              "Please enter the name of the output file (do not include file extension): ", sc);
          ArrayList<String> toSave = new ArrayList<>();
          for (CommitteeMember cm : committee) {
            toSave.add(cm.toString());
          }
          file.FileIO.writeToFile(formatSelection, fileName, toSave);
          System.out.println("File saved.");
        }
      }
    }
  }

  /**
   * Generates a report of enquiries with filters based on date belonging to the camp created by this staff. Option to save given after report generation.
   * 
   * @param sc Scanner object to be injected.
   */
  @Override
  public void generateEnquiryReport(Scanner sc) {
    // early exit if no camp created
    if (!hasCreatedCamp())
      System.out.println(
          "You have not created a camp yet. Please create a camp before generating report.");
    else {
      ArrayList<Enquiry> unfilteredResult = this.createdCamp.getEnquiries();
      ArrayList<Enquiry> filteredResult = new ArrayList<>();
      // prompt for filter
      DateFilter filterSelection = enums.DateFilter.getDateFilterFromStringInput(sc);
      String filterYesOrNo = file.Input
          .getStringInput("Do you wish to filter the report by date?: (y/n) ", sc).toLowerCase();
      // prompt for format
      Format formatSelection = enums.Format.getFormatFromStringInput(sc);
      if (filterYesOrNo == "y") {
        // generate report with filters base on format selection
        switch (filterSelection) {
          case ON: {
            LocalDate dateQuery = file.Input.getDateFromIntInputs("your filter: ", sc);
            for (Enquiry e : unfilteredResult) {
              if (dateQuery == e.getDateCreated())
                filteredResult.add(e);
            }
          }
          case BEFORE: {
            LocalDate dateQuery = file.Input.getDateFromIntInputs("your filter: ", sc);
            for (Enquiry e : unfilteredResult) {
              if (e.getDateCreated().isBefore(dateQuery))
                filteredResult.add(e);
            }
          }
          case AFTER: {
            LocalDate dateQuery = file.Input.getDateFromIntInputs("your filter: ", sc);
            for (Enquiry e : unfilteredResult) {
              if (e.getDateCreated().isAfter(dateQuery))
                filteredResult.add(e);
            }
          }
          default:
            break;
        }
        if (formatSelection == Format.CSV) {
          enquiry.Enquiry.generateCSVHeaders();
          for (Enquiry e : filteredResult) {
            e.toCSV();
          }

        } else {
          for (Enquiry e : filteredResult) {
            e.toString();
          }
        }
      } else {
        // generate report without filters based on format selection
        if (formatSelection == Format.CSV) {
          committeeMember.CommitteeMember.generateCSVHeaders();
          for (Enquiry e : unfilteredResult) {
            e.toCSV();
          }
        } else {
          for (Enquiry e : unfilteredResult) {
            e.toString();
          }
        }
      }

      // prompt if user wishes to save the report
      String saveYesOrNo = file.Input
          .getStringInput("Do you wish to save the report as a file?: (y/n) ", sc).toLowerCase();
      if (saveYesOrNo == "y") {
        if (filterYesOrNo == "y") {
          if (formatSelection == Format.CSV) { // save as .csv
            String fileName = file.Input.getStringInput(
                "Please enter the name of the output file (do not include file extension): ", sc);
            ArrayList<String> toSave = new ArrayList<>();
            toSave.add(enquiry.Enquiry.generateCSVHeaders());
            for (Enquiry e : filteredResult) {
              toSave.add(e.toCSV());
            }
            file.FileIO.writeToFile(formatSelection, fileName, toSave);
            System.out.println("File saved.");
          } else { // save as .txt
            String fileName = file.Input.getStringInput(
                "Please enter the name of the output file (do not include file extension): ", sc);
            ArrayList<String> toSave = new ArrayList<>();
            for (Enquiry e : filteredResult) {
              toSave.add(e.toString());
            }
            file.FileIO.writeToFile(formatSelection, fileName, toSave);
            System.out.println("File saved.");
          }
        } else {
          if (formatSelection == Format.CSV) { // save as .csv
            String fileName = file.Input.getStringInput(
                "Please enter the name of the output file (do not include file extension): ", sc);
            ArrayList<String> toSave = new ArrayList<>();
            toSave.add(enquiry.Enquiry.generateCSVHeaders());
            for (Enquiry e : unfilteredResult) {
              toSave.add(e.toCSV());
            }
            file.FileIO.writeToFile(formatSelection, fileName, toSave);
            System.out.println("File saved.");
          } else { // save as .txt
            String fileName = file.Input.getStringInput(
                "Please enter the name of the output file (do not include file extension): ", sc);
            ArrayList<String> toSave = new ArrayList<>();
            for (Enquiry e : unfilteredResult) {
              toSave.add(e.toString());
            }
            file.FileIO.writeToFile(formatSelection, fileName, toSave);
            System.out.println("File saved.");
          }
        }
      }
    }
  }
}

