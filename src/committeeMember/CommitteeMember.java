package committeeMember;

import java.util.HashSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Scanner;  // Import the Scanner class

import enums.Role;
import student.Student;
import camp.Camp;
import 

public class CommitteeMember {
	 // Encapsulate attributes
    private String name;
    private Student student;
    private String userID;
    private String password; // Storing the plain text password (not recommended for production)
    private Set<Role> roles; // Using a Set to allow multiple roles
    private String email;
    
     // Constructor to initialize attributes
    public CommitteeMember(String userID, String email, Set<Role> roles, String name, String role, Student student) {
        this.name = name;
        this.role = role;
        this.student = student;
        this.camp = camp;
        this.userID = userID; // Set the userID here
        this.roles = roles;
        this.name = name;
        this.email = email;
    }

    // Getter methods for attributes
    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public Student getStudent() {
        return student;
    }

    public Camp getCamp() {
        return camp;
    }

    // Setter methods for attributes
    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }

    // Method to print committee member information
    public void printCommitteeMemberInfo() {
        System.out.println("Committee Member Name: " + name);
        System.out.println("Committee Member Role: " + role);
        System.out.println("Student Assigned: " + student.getName());
        System.out.println("Camp Assigned: " + camp.getName());
    }
    
    public void startMenu() {
        System.out.println("---------- CommitteeMember Menu ----------");
        System.out.println("1. Register for Camp");
        System.out.println("2. Withdraw from Camp");
        System.out.println("3. Reply to Enquiry");
        System.out.println("4. Submit Suggestion");
        System.out.println("5. View All Suggestions");
        System.out.println("6. Edit Own Suggestion");
        System.out.println("7. Delete Own Suggestion");
        System.out.println("8. Generate Participant Report Card");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                registerForCamp();
                break;
            case 2:
                withdrawFromCamp();
                break;
            case 3:
                replyToEnquiry();
                break;
            case 4:
                submitSuggestion();
                break;
            case 5:
                viewAllSuggestions();
                break;
            case 6:
                editOwnSuggestion();
                break;
            case 7:
                deleteOwnSuggestion();
                break;
            case 8:
                generateParticipantReportCard();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
        }
    }
    
    public void registerCamp(Student student, Camp camp) {
        // Check if the student is already registered for a camp
        if (student.getRegisteredCamp() != null) {
            System.out.println("Student " + student.getName() + " is already registered for camp " + student.getRegisteredCamp().getName());
            return;
        }

        // Register the student for the specified camp
        student.setRegisteredCamp(camp);
        camp.addRegisteredStudent(student);
        System.out.println("Student " + student.getName() + " successfully registered for camp " + camp.getName());
    }

    // Method to withdraw a student from a registered camp
    public void withdrawCamp(Student student) {
        // Check if the student is registered for a camp
        if (student.getRegisteredCamp() == null) {
            System.out.println("Student " + student.getName() + " is not registered for any camp");
            return;
        }

        // Withdraw the student from the registered camp
        Camp registeredCamp = student.getRegisteredCamp();
        student.setRegisteredCamp(null);
        registeredCamp.removeRegisteredStudent(student);
        System.out.println("Student " + student.getName() + " successfully withdrawn from camp " + registeredCamp.getName());
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

    public int getPoints() {
        return points;
    }
  
}
