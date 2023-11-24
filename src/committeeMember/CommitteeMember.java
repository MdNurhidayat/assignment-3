package committeeMember;

import java.util.HashSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;  // Import the Scanner class

import enums.Role;
import enums.RoleFilter;
import file.Input;
import reply.Reply;
import report.BaseReport;
import enums.Faculty;
import enums.Format;
import student.Student;
import suggestion.BaseSuggestion;
import suggestion.Suggestion;
import camp.Camp;
import enquiry.BaseEnquiry;
import enquiry.Enquiry;
import enquiry.ReplyEnquiry;

public class CommitteeMember extends Student implements BaseEnquiry, ReplyEnquiry, BaseReport, CommitteeMemberReport {
	Camp overseeing;
	int points;
	HashMap<String, Suggestion> suggestionList;
    
     // Constructor to initialize attributes
	public CommitteeMember(Student student, Camp camp) {
		super(student.getUserID(), student.getPassword(), Role.COMMITTEE_MEMBER, student.getName(), student.getEmail(), student.getFaculty());
		overseeing = camp;
		points = 0;
	}
    
    public CommitteeMember(String userID, String password, String name, String email, Faculty faculty) {
        super(userID, password, Role.COMMITTEE_MEMBER, name, email, faculty);
        overseeing = null;
        points = 0;
    }

    // Getter methods for attributes
    public String getUserID() {
    	return super.getUserID();
    }
    
    public String getPassword() {
    	return super.getPassword();
    }

    public String getName() {
        return super.getName();
    }

    public Role getRole() {
        return super.getRole();
    }

    public String getEmail() {
    	return super.getEmail();
    }

    public Faculty getFaculty() {
    	return super.getFaculty();
    }
    
    public Camp getOverseeingCamp() {
        return overseeing;
    }

    public void incrementPoint() {
    	this.points++;
	}
    
    public int getPoints() {
    	return points;
    }

    // Setter methods for attributes
    public void setUserID(String userID) {
    	super.setUserID(userID);
    }
    
    public void setPassword(String password) {
    	super.setPassword(password);
    }
    
    public void setName(String name) {
        super.setName(name);;
    }

    public void setOverseeingCamp(Camp camp) {
        overseeing = camp;
    }
    
    public void increasePoints() {
    	points++;
    }
   
    public void registerCamp(Scanner scanner, ArrayList<Camp> campList) {
        super.register(scanner, campList);
    }

    // Method to withdraw a student from a registered camp
    public void withdrawCamp(Scanner scanner) {
        super.withdraw(scanner);
    }
    
    @Override
    public void replyEnquiry(String replyMessage, Scanner sc) {
      viewEnquiries();
      ArrayList<Enquiry> enquiries = this.overseeing.getEnquiries();
      String enquiryID = Input.getStringInput("Enter enquiryID of enquiry to edit: ", sc);
      for (Enquiry e : enquiries) {
        if (!e.getEnquiryID().equals(enquiryID))
          System.out.println("EnquiryID  " + enquiryID
              + "provided not found in this camp's list of enquiries, please try again.");
        else
          // TODO @hid, add Derrick's method call for creating reply
          // TODO @hid, implementation should be very similar to that of CM,
          // align either one to the other. If role of replier is CM remember to increment point.
        	increasePoints();
          System.out.println("Enquiry " + enquiryID + " replied.");
      }
    }

    public void viewSuggestionLists() {
        // View a list of all suggestions
        System.out.println("------------------ Suggestion List ------------------");
        for (Map.Entry<String, Suggestion> suggestionMap : suggestionList.entrySet()) {
            System.out.println(suggestionMap.getValue().getSuggestionID() + " - " + suggestionMap.getValue().getCreatorName());
        }
        System.out.println("--------------- End of Suggestion List --------------");
    }
    
    public void viewSuggestion(String suggestionID) {
        if (suggestionList.containsKey(suggestionID))
            displaySuggestionDetails(suggestionList.get(suggestionID));
    }

    // Display detailed information for a specific suggestion
    private void displaySuggestionDetails(Suggestion suggestion) {
        System.out.println("Suggestion ID: " + suggestion.getSuggestionID());
        System.out.println("Submitted by: " + suggestion.getCreatorName());
        System.out.println("Submitted on: " + suggestion.getDateCreated());
        System.out.println("Suggestion content: " + suggestion.getContent());
        System.out.println("Suggested in Camp " + suggestion.getCampID());
        System.out.println("Status: " + suggestion.isProcessed());
    }


    public void submitSuggestion(Scanner scanner) {
        Suggestion suggestion = new Suggestion(scanner, this);
        suggestionList.put(suggestion.getSuggestionID(), suggestion);
        increasePoints();
        
        System.out.println("Suggestion successfully implemented!");
    }

    private void editOwnSuggestion(Scanner scanner) {

        // Prompt the user to select a suggestion to edit
        System.out.print("Please select the suggestion ID you want to edit:");
        String suggestionID = scanner.nextLine();
        
        if (this.suggestionList.containsKey(suggestionID)){
            // Allow the user to edit the suggestion details
            System.out.print("Enter the updated suggestion content:");
            String updatedContent = scanner.nextLine();
            this.suggestionList.get(suggestionID).setContent(updatedContent);

            // Inform the user about the successful update
            System.out.println("Suggestion " + suggestionID + " has been updated successfully.");
        }
        else
        	System.out.println("Suggestion " + suggestionID + " do not exist. Try Again.");
    }

    public void deleteSuggestion(Scanner scanner) {

        // Prompt the user to select a suggestion to delete
        System.out.print("Please select the suggestion ID you want to delete: ");
        String suggestionID = scanner.nextLine();
        
        if (this.suggestionList.containsKey(suggestionID)){        
	        suggestionList.remove(suggestionID);
	        
	        // Inform the user about the successful deletion
	        System.out.println("Suggestion " + suggestionID + " has been deleted successfully.");
        }
        else
        	System.out.println("Suggestion " + suggestionID + " do not exist. Try Again.");
    }

    /**
     * Generates a report of participants with filters based on role belonging to the camp created by this staff. Option to save given after report generation.
     * 
     * @param sc Scanner object to be injected.
     */
    @Override
    public void generateParticipantReport(Scanner sc) {
      // early exit if no camp created
      if (overseeing == null)
        System.out.println(
            "You are not a camp Committee Member yet. Please join a camp as Committee Member before generating report.");
      else {
        ArrayList<Student> students = overseeing.getParticipants();
        ArrayList<CommitteeMember> committee = overseeing.getCommittee();
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
                    file.FileIO.writeToFile(formatSelection, fileName, toSave);
                    System.out.println("File saved.");
                  } else { // save as .txt
                    String fileName = file.Input.getStringInput(
                        "Please enter the name of the output file (do not include file extension): ",
                        sc);
                    ArrayList<String> toSave = new ArrayList<>();
                    for (Student s : students) {
                      toSave.add(s.toString());
                    }
                    file.FileIO.writeToFile(formatSelection, fileName, toSave);
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


}
