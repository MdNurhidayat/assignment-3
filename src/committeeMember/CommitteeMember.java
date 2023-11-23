package committeeMember;

import java.util.HashSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Scanner;  // Import the Scanner class

import enums.Role;
import enums.Faculty;
import student.Student;
import camp.Camp;

public class CommitteeMember extends Student {
	Camp overseeing;
	int points;
    
     // Constructor to initialize attributes
	public CommitteeMember(Student student) {
		super(student.getUserID(), Role.COMMITTEE_MEMBER, student.getName(), student.getEmail(), student.getFaculty());
	}
	
    public CommitteeMember(String userID, String name, String email, Faculty faculty) {
        super(userID, Role.COMMITTEE_MEMBER, name, email, faculty);        
    }
    
    public CommitteeMember(String userID, String password, String name, String email, Faculty faculty) {
        super(userID, password, Role.COMMITTEE_MEMBER, name, email, faculty);
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

    public int getPoints() {
    	return points;
    }
    
    public ArrayList<String> getCommitteeInformation() {
    	ArrayList<String> info = new ArrayList<String>();
    	info.add(getName());
    	info.add(getOverseeingCamp().getDetails().getName());
    	info.add(Integer.toString(getPoints()));
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
    
    public void replies(Enquiry enquiry) {
        // Check if the enquiry has already been processed
    	if (enquiry.isProcessed()) {
            return;
        }
        // Check if the current object is either a CommitteeMember or Staff instance
        if (!(this instanceof CommitteeMember || this instanceof Staff)) {
            return;
        }
        // Check if the current object is either a CommitteeMember or Staff instance
        if (this instanceof CommitteeMember || this instanceof Staff) {
            // Create a Scanner object to read user input
            Scanner scanner = new Scanner(System.in);

            // Prompt the user to enter their reply message
            System.out.println("Enter your reply message: ");
            String replyMessage = scanner.nextLine();

            // Mark the enquiry as processed
            enquiry.setProcessed(true);

            // Create a Reply object with the user-provided reply message and current date and time
            Reply reply = new Reply();
            reply.setReplyMessage(replyMessage);
            reply.setCreateDate(LocalDate.now());
            reply.setTimeCreated(LocalTime.now());

            // Link the reply to the CommitteeMember or Staff using has-a relationship
            reply.setSentBy(this);

            // Validate the reply message length (max 250 characters)
            if (replyMessage.length() > 250) {
                System.out.println("Reply message exceeds the maximum character limit of 250.");
                return;
            }

            // Add the reply to the enquiry's replies list
            enquiry.addReply(reply);

            // Send a notification to the enquirer
            System.out.println("Enquiry " + enquiry.getEnquiryID() + " has been responded to with a reply.");
        } else {
            System.out.println("Only CommitteeMember and Staff instances can reply to enquiries.");
        }
    }

    public void viewSuggestion() {
        // Implement code to view suggestions
        System.out.println("Viewing suggestions...");
        
        public void viewSuggestion(String suggestionID) {
            if (!suggestionID.isEmpty()) {
                // Search for the corresponding suggestion based on the ID
                List<Suggestion> suggestions = retrieveSuggestions();
                for (Suggestion suggestion : suggestions) {
                    if (suggestion.getSuggestionID().equals(suggestionID)) {
                        // Display the specific suggestion details
                        displaySuggestionDetails(suggestion);
                        break;
                    }
                }
            } else {
                // View a list of all suggestions
                List<Suggestion> suggestions = retrieveSuggestions();
                System.out.println("--------------- Suggestion List ----------------");
                for (Suggestion suggestion : suggestions) {
                    displaySuggestionSummary(suggestion);
                }
                System.out.println("--------------- End of Suggestion List --------------");
            }
        }
    }

        // Retrieve suggestions from the database or designated repository
        private List<Suggestion> retrieveSuggestions() {
            // Implement database or repository interaction to retrieve suggestions
            return null;
        }

        // Display detailed information for a specific suggestion
        private void displaySuggestionDetails(Suggestion suggestion) {
            System.out.println("Suggestion ID: " + suggestion.getSuggestionID());
            System.out.println("Submitted by: " + suggestion.getSubmittedBy());
            System.out.println("Submitted on: " + suggestion.getSubmissionDate());
            System.out.println("Suggestion content: " + suggestion.getSuggestionContent());
            System.out.println("Suggested camps or activities: " + suggestion.getSuggestedCampsOrActivities());
            System.out.println("Status: " + suggestion.getStatus());
        }

        // Display a summary of a suggestion
        private void displaySuggestionSummary(Suggestion suggestion) {
            System.out.println(suggestion.getSuggestionID() + " - " + suggestion.getSubmittedBy());
        }
    }

    @Override
    public void submitSuggestion(String suggestion) {
        // Implement code to submit a suggestion
        System.out.println("Submitting suggestion: " + suggestion);
    }

    private void editOwnSuggestion() {
        // Retrieve all suggestions submitted by the current committee member
        List<Suggestion> suggestions = retrieveOwnSuggestions();

        // Prompt the user to select a suggestion to edit
        System.out.println("Please select the suggestion ID you want to edit:");
        for (Suggestion suggestion : suggestions) {
            System.out.println(suggestion.getSuggestionID() + " - " + suggestion.getSubmittedBy());
        }
        Scanner scanner = new Scanner(System.in);
        String suggestionID = scanner.nextLine();

        // Retrieve the selected suggestion details
        Suggestion suggestion = retrieveSuggestion(suggestionID);

        // Allow the user to edit the suggestion details
        System.out.println("Enter the updated suggestion content:");
        String updatedContent = scanner.nextLine();
        suggestion.setSuggestionContent(updatedContent);

        // Update the suggestion in the database or designated repository
        updateSuggestion(suggestion);

        // Inform the user about the successful update
        System.out.println("Suggestion " + suggestionID + " has been updated successfully.");
    }

    @Override
    public void deleteSuggestion(int suggestionId) {
        // Implement code to delete a suggestion
        List<Suggestion> suggestions = retrieveOwnSuggestions();

        // Prompt the user to select a suggestion to delete
        System.out.println("Please select the suggestion ID you want to delete:");
        for (Suggestion suggestion : suggestions) {
            System.out.println(suggestion.getSuggestionID() + " - " + suggestion.getSubmittedBy());
        }
        Scanner scanner = new Scanner(System.in);
        String suggestionID = scanner.nextLine();

        // Delete the selected suggestion from the database or designated repository
        deleteSuggestion(suggestionID);

        // Inform the user about the successful deletion
        System.out.println("Suggestion " + suggestionID + " has been deleted successfully.");
    }
    
    private void generateParticipantReportCard() {
        // Prompt the user to enter the camp ID for which they want to generate report cards
        System.out.println("Enter the camp ID for which you want to generate report cards:");
        Scanner scanner = new Scanner(System.in);
        String campID = scanner.nextLine();

        // Retrieve the list of participants for the specified camp
        List<Participant> participants = retrieveParticipants(campID);

        // Generate report cards for each participant
        for (Participant participant : participants) {
            generateReportCard(participant);
        }

        // Inform the user about the successful report card generation
        System.out.println("Participant report cards for camp " + campID + " have been generated successfully.");
    }
}
