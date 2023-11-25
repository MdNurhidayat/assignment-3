package committeeMember;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;  // Import the Scanner class

import camp.Camp;
import enquiry.BaseEnquiry;
import enquiry.Enquiry;
import enquiry.ReplyEnquiry;
import enums.Role;
import enums.RoleFilter;
import enums.DateFilter;
import enums.Faculty;
import enums.Format;
import file.FileIO;
import file.Input;
import reply.Reply;
import student.Student;
import suggestion.*;

public class CommitteeMember extends Student implements BaseEnquiry, ReplyEnquiry, BaseSuggestion, CommitteeMemberReport {
	Camp overseeing;
	int points;
	HashMap<String, Suggestion> suggestionList;
    
     // Constructor to initialize attributes
	public CommitteeMember(Student student, Camp camp) {
		super(student.getUserID(), student.getPassword(), Role.COMMITTEE_MEMBER, student.getName(), student.getEmail(), student.getFaculty());
		overseeing = camp;
		points = 0;
		suggestionList = new HashMap<String, Suggestion>();
		for (int i = 0; i < student.getRegisteredFor().size(); i++) { super.addCampToRegisteredFor(student.getRegisteredFor().get(i)); }
		for (int i = 0; i < student.getEnquiries().size(); i++) { super.addEnquiry(student.getEnquiries().get(i)); }
	}
    
    public CommitteeMember(String userID, String password, String name, String email, Faculty faculty) {
        super(userID, password, Role.COMMITTEE_MEMBER, name, email, faculty);
        overseeing = null;
        points = 0;
        suggestionList = new HashMap<String, Suggestion>();
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
    public void viewEnquiries()
    {
    	if (overseeing == null)
    	      System.out.println("Please join a camp as a committee member first.");
    	else {
    	      ArrayList<Enquiry> results = overseeing.getEnquiries();
    	      if (!results.isEmpty()) {
    	    	  String delimiter = "-";
    	    	  String paddingParameters = "| %-10s | %-25s | %-20s | %-10s | %-40s | %-10s | \n";
    	    	  System.out.println("Your Camp's Enquiries");
    	    	  System.out.println(delimiter.repeat(130));
    	    	  System.out.printf(paddingParameters, "CampID", "Date Created", "EnquirerName", "EnquiryID",
    	    			  "EnquiryMessage", "IsProcessed");
    	    	  System.out.println(delimiter.repeat(130));
    	    	  
    	    	  for (Enquiry e : results) { e.toString(); }
    	    	  
    	    	  System.out.println(delimiter.repeat(130));
    	    	  System.out.println();
    	      }
    	      else
    	    	  System.out.println("Current camp has no enquiries to show. Check back later.");
    	    }
    	super.viewEnquiries();
    }
    
    @Override
    public void withdraw(Scanner scanner)
    {
    	System.out.println("Enter the CampID to withdraw from:");
        String campID = scanner.nextLine();
        
        Camp selectedCamp = null;
        
        for (Camp camp : super.getRegisteredFor())
        {
        	if (camp.getCampID().equals(campID))
        	{
        		selectedCamp = camp;
        		break;
        	}
        }
        
        // Check if the camp is found in the registeredFor list
        if (selectedCamp != null) {
        	if (selectedCamp.equals(overseeing))
        	{
        		System.out.println("Cannot withdraw from camp " + selectedCamp.getCampID() + " as you are a Committee Member for this camp. Withdrawal cancelled");
        		return;
        	}
        	
            // Display camp details for verification
            System.out.println("Camp Details:");
            System.out.println("Camp ID: " + selectedCamp.getCampID());
            System.out.println("Camp Name: " + selectedCamp.getDetails().getName());

            // Verify if the user wants to withdraw from the camp
            System.out.println("Do you want to withdraw from this camp? (yes/no)");
            String withdrawalChoice = scanner.nextLine();

            if (withdrawalChoice.equalsIgnoreCase("yes")) {
                // Remove the student from the camp's participants
                selectedCamp.removeParticipant(this);

                // Remove the camp from the student's registeredFor list
                removeCampFromRegisteredFor(selectedCamp);

                // Add the student to the camp's previouslyWithdrawn list
                selectedCamp.addWithdrawn(this);

                System.out.println("Withdrawal successful!");
            } else {
                System.out.println("Withdrawal cancelled.");
            }
        } else {
            System.out.println("Camp not found in the registered list. Cannot withdraw.");
        }
    }
    
    @Override
    public void editEnquiry(Scanner sc)
    {
    	super.editEnquiry(sc);
    }
    
    @Override
    public void replyEnquiry(Scanner sc) {
      ArrayList<Enquiry> enquiries = this.overseeing.getEnquiries();
      String enquiryID = Input.getStringInput("Enter enquiryID of enquiry to edit: ", sc);
      for (Enquiry e : enquiries) {
        if (e.getEnquiryID().equals(enquiryID))
        {
        	Reply reply = new Reply(sc, e, this);
        	e.addReply(reply);
            increasePoints();
            System.out.println("Enquiry " + enquiryID + " replied.");
        }
      }
      System.out.println("EnquiryID  " + enquiryID + " provided not found in this camp's list of enquiries, please try again.");
    }

    @Override
    public void viewSuggestions() {
        // View a list of all suggestions
        System.out.println("------------------ Suggestion List ------------------");
        for (Map.Entry<String, Suggestion> suggestionMap : suggestionList.entrySet()) {
            System.out.println(suggestionMap.getValue().getSuggestionID() + " - " + suggestionMap.getValue().getCreatorName());
            System.out.println("Suggestion contents : " + suggestionMap.getValue().getContent());
            System.out.println();
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

    public void editOwnSuggestion(Scanner scanner) {

        // Prompt the user to select a suggestion to edit
        System.out.print("Please select the suggestion ID you want to edit:");
        String suggestionID = scanner.nextLine();
        
        if (this.suggestionList.containsKey(suggestionID) && !this.suggestionList.get(suggestionID).isProcessed()){
            // Allow the user to edit the suggestion details
            System.out.print("Enter the updated suggestion content:");
            String updatedContent = scanner.nextLine();
            this.suggestionList.get(suggestionID).setContent(updatedContent);

            // Inform the user about the successful update
            System.out.println("Suggestion " + suggestionID + " has been updated successfully.");
        }
        else
        	System.out.println("Suggestion " + suggestionID + " do not exist / already processed. Try Again.");
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
          "You have not created a camp yet. Please create a camp before generating report.");
     else {
       ArrayList<Student> students = this.overseeing.getParticipants();
       ArrayList<CommitteeMember> committee = this.overseeing.getCommittee();
       // prompt for filter
       String filterYesOrNo = file.Input
           .getStringInput("Do you wish to filter the report by role?: (y/n) ", sc).toLowerCase();
       RoleFilter filterSelection = enums.RoleFilter.getRoleFilterFromStringInput(sc);
       // prompt for format
       Format formatSelection = enums.Format.getFormatFromStringInput(sc);
       if (filterYesOrNo .equals("y")) {
         // generate report with filters base on format selection
         switch (filterSelection) {
           case STUDENT: {
             if (formatSelection == Format.CSV) {
               for (Student s : students) {
                 System.out.println(s.toCSV());
               }
             } else {
               for (Student s : students) {
            	  System.out.println(s.toString());
               }
             }
           }
           case COMMITTEE_MEMBER: {
             if (formatSelection == Format.CSV) {
               for (CommitteeMember cm : committee) {
            	   System.out.println(cm.toCSV());
               }
             } else {
               for (CommitteeMember cm : committee) {
            	  System.out.println(cm.toString());
               }
             }
           }
           default:
             if (formatSelection == Format.CSV) {
               for (Student s : students) {
            	  System.out.println(s.toCSV());
               }
               for (CommitteeMember cm : committee) {
             	   System.out.println(cm.toCSV());
               }
             } else {
               for (Student s : students) {
            	   System.out.println(s.toString());
               }
               for (CommitteeMember cm : committee) {
             	   System.out.println(cm.toString());
               }
             }
         }
         // prompt if user wishes to save the report
         String saveYesOrNo = file.Input
            .getStringInput("Do you wish to save the report as a file?: (y/n) ", sc).toLowerCase();
        if (saveYesOrNo.equals("y")) {
          if (filterYesOrNo.equals("y")) {
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
   * Generates a report of enquiries with filters based on date belonging to the camp created by this staff. Option to save given after report generation.
   * 
   * @param sc Scanner object to be injected.
   */
  @Override
  public void generateEnquiryReport(Scanner sc) {
    // early exit if no camp created
    if (overseeing == null)
      System.out.println(
          "You have not created a camp yet. Please create a camp before generating report.");
    else {
      ArrayList<Enquiry> unfilteredResult = this.overseeing.getEnquiries();
      ArrayList<Enquiry> filteredResult = new ArrayList<>();
      // prompt for filter
      DateFilter filterSelection = enums.DateFilter.getDateFilterFromStringInput(sc);
      String filterYesOrNo = file.Input
          .getStringInput("Do you wish to filter the report by date?: (y/n) ", sc).toLowerCase();
      // prompt for format
      Format formatSelection = enums.Format.getFormatFromStringInput(sc);
      if (filterYesOrNo.equals("y")) {
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
        	  System.out.println(e.toCSV());
          }

        } else {
          for (Enquiry e : filteredResult) {
        	  System.out.println(e.toString());
          }
        }
      } else {
        // generate report without filters based on format selection
        if (formatSelection == Format.CSV) {
          committeeMember.CommitteeMember.generateCSVHeaders();
          for (Enquiry e : unfilteredResult) {
        	  System.out.println(e.toCSV());
          }
        } else {
          for (Enquiry e : unfilteredResult) {
        	  System.out.println(e.toString());
          }
        }
      }

      // prompt if user wishes to save the report
      String saveYesOrNo = file.Input
          .getStringInput("Do you wish to save the report as a file?: (y/n) ", sc).toLowerCase();
      if (saveYesOrNo.equals("y")) {
        if (filterYesOrNo.equals("y")) {
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

  /**
   * Converts the Student object to a TXT format string.
   *
   * @return A string containing TXT-formatted student information.
   */
  @Override
  public String toString() {
      String delimiter = " | ";
      return super.getUserID() + delimiter + super.getRole() + delimiter + super.getName() + delimiter
              + super.getEmail() + delimiter + super.getFaculty() + this.points;
  }

  /**
   * Generates CSV headers for the Student class.
   *
   * @return A string containing CSV headers.
   */
  public static String generateCSVHeaders() {
      String delimiter = ", ";
      return "UserID" + delimiter + "Role" + delimiter + "Name" + delimiter
              + "Email" + delimiter + "Faculty" + delimiter + "Points";
  }

  /**
   * Converts the Student object to a CSV format string.
   *
   * @return A string containing CSV-formatted student information.
   */
  public String toCSV() {
      String delimiter = ", ";
      return super.getUserID() + delimiter + super.getRole() + delimiter + super.getName() + delimiter
              + super.getEmail() + delimiter + super.getFaculty() + delimiter + this.points;
  }
}
