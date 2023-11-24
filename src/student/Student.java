package student;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

import camp.Camp;
import committeeMember.CommitteeMember;
import enquiry.BaseEnquiry;
import enquiry.Enquiry;
import user.User;
import enums.Faculty;
import enums.Role;
import reply.Reply;

public class Student extends User implements Withdrawable, StudentEnquiry, BaseEnquiry {
    private ArrayList<Camp> registeredFor;
    private ArrayList<Enquiry> enquiries;

    // constructor
    public Student(String UserID, String Password, Role role, String name, String email, Faculty faculty) {
        super(UserID, Password, role, name, email, faculty);
        this.registeredFor = new ArrayList<>(); // Initialize with an empty ArrayList
        this.enquiries = new ArrayList<>();
    }

    // Getter method for registeredFor
    public ArrayList<Camp> getRegisteredFor() {
        return registeredFor;
    }

    // Getter method for enquiries
    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }

    // Setter method for registeredFor (add camp)
    public void addCampToRegisteredFor(Camp camp) {
        registeredFor.add(camp);
    }

    // Setter method for registeredFor (remove camp)
    public void removeCampFromRegisteredFor(Camp camp) {
        registeredFor.remove(camp);
    }

    // Setter method for enquiries (add enquiry)
    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    // Setter method for enquiries (remove enquiry)
    public void removeEnquiry(Enquiry enquiry) {
        enquiries.remove(enquiry);
    }

    // Method to view available camps to join
    public void viewAvailableCampsToJoin(ArrayList<Camp> allCamps) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-15s | %-10s | %-10s | %-10s | %-25s | %-15s |\n", "CampID", "Name", "Location", "Description", "Start Date", "End Date", "Registration Closing Date", "Remaining Slots");
        System.out.println("---------------------------------------------------------------------------");

        for (Camp camp : allCamps) {
            if (camp.getisVisible() && !camp.getPreviouslyWithdrawn().contains(this) && camp.getDetails().getFaculty().equals(this.getFaculty())) {
                
		int remainingSlots = camp.calculateRemainingSlots();

                System.out.printf("| %-10s | %-30s | %-15s | %-10s | %-10s | %-10s | %-25s | %-15s |\n",
                camp.getCampID(),
                camp.getDetails().getName(),
                camp.getDetails().getLocation(),
                camp.getDetails().getDescription(),
                camp.getDetails().getStartDate(),
                camp.getDetails().getEndDate(),
                camp.getDetails().getRegistrationClosingDate(),
                remainingSlots);
            }
        }
    }

    // Method to view registered camps
    public void viewRegisteredCamps() {
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-15s | %-10s | %-10s | %-10s | %-25s | %-15s |\n", "CampID", "Name", "Location", "Description", "Start Date", "End Date", "Registration Closing Date", "Remaining Slots");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        for (Camp camp : registeredFor) {
            int remainingSlots = camp.calculateRemainingSlots();

            System.out.printf("| %-10s | %-30s | %-15s | %-10s | %-10s | %-10s | %-25s | %-15s |\n",
                camp.getCampID(),
                camp.getDetails().getName(),
                camp.getDetails().getLocation(),
                camp.getDetails().getDescription(),
                camp.getDetails().getStartDate(),
                camp.getDetails().getEndDate(),
                camp.getDetails().getRegistrationClosingDate(),
                remainingSlots);
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }
    
// Method to register for a camp
public void register(Scanner scanner, ArrayList<Camp> allCamps) {

    System.out.println("Enter the CampID to register:");
    String campID = scanner.nextLine();

    Camp selectedCamp = null;

    // Find the camp with the specified CampID
    for (Camp camp : allCamps) {
        if (camp.getCampID().equals(campID)) {
            selectedCamp = camp;
            break;
        }
    }

    // Check if the camp is found and can be registered
    if (selectedCamp != null && selectedCamp.getisVisible() && !selectedCamp.getPreviouslyWithdrawn().contains(this)
            && selectedCamp.getDetails().getFaculty().equals(this.getFaculty())) {
        int remainingSlots = selectedCamp.getDetails().getTotalSlots() - selectedCamp.getParticipants().size();

        // Check if there are available slots and the current date is before the registration closing date
        if (remainingSlots > 0 && selectedCamp.getDetails().getRegistrationClosingDate().isAfter(LocalDate.now())) {
            
            // Check for date clashes with already registered camps
            boolean hasDateClash = false;
            for (Camp registeredCamp : registeredFor) {
                LocalDate registeredStartDate = registeredCamp.getDetails().getStartDate();
                LocalDate registeredEndDate = registeredCamp.getDetails().getEndDate();
                
                if ((selectedCamp.getDetails().getStartDate().isAfter(registeredStartDate) &&
                     selectedCamp.getDetails().getStartDate().isBefore(registeredEndDate)) ||
                    (selectedCamp.getDetails().getEndDate().isAfter(registeredStartDate) &&
                     selectedCamp.getDetails().getEndDate().isBefore(registeredEndDate)) ||
                    selectedCamp.getDetails().getStartDate().isEqual(registeredStartDate) ||
                    selectedCamp.getDetails().getEndDate().isEqual(registeredEndDate)) {
                    
                    hasDateClash = true;
                    break;
                }
            }

            if (!hasDateClash) {
                // Add the student to the camp's participants
                selectedCamp.addParticipant(this);

                // Add the camp to the student's registeredFor list
                addCampToRegisteredFor(selectedCamp);

                System.out.println("Registration successful!");
            } else {
                System.out.println("Cannot register for the camp. Date clashes with already registered camps.");
            }
        } else {
            System.out.println("Cannot register for the camp. Check available slots and registration closing date.");
        }
    } else {
        System.out.println("Camp not found or cannot be registered for.");
    }
}
   

    // Method to withdraw from a camp with user verification
	@Override
    public void withdraw(Scanner scanner) {

        System.out.println("Enter the CampID to withdraw from:");
        String campID = scanner.nextLine();

        // Find the camp with the specified CampID in the registeredFor list
        Camp selectedCamp = null;

        for (Camp camp : registeredFor) {
            if (camp.getCampID().equals(campID)) {
                selectedCamp = camp;
                break;
            }
        }

        // Check if the camp is found in the registeredFor list
        if (selectedCamp != null) {
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
                System.out.println("Withdrawal canceled.");
            }
        } else {
            System.out.println("Camp not found in the registered list. Cannot withdraw.");
        }
    }

    // Method to submit an enquiry for a specific camp	
	@Override
    public void submitEnquiry(Scanner scanner, ArrayList<Camp> allCamps) {

        System.out.println("Enter the CampID for the enquiry:");
        String campID = scanner.nextLine();

        Camp selectedCamp = null;

        // Find the camp with the specified CampID
        for (Camp camp : allCamps) {
            if (camp.getCampID().equals(campID)) {
                selectedCamp = camp;
                break;
            }
        }

        // Check if the camp is found
        if (selectedCamp != null) {
            Enquiry newEnquiry = new Enquiry(scanner, selectedCamp);

            // Add the enquiry to the student's enquiries list
            enquiries.add(newEnquiry);

            // Add the enquiry to the camp's enquiries list
            selectedCamp.addEnquiry(newEnquiry);

            System.out.println("Enquiry submitted successfully!");
        } else {
            System.out.println("Camp not found. Enquiry submission failed.");
        }
    }

    // Method to view enquiries
	@Override
    public void viewEnquiries() {
        System.out.println("------------------------------------------------------------");
        System.out.println("| Enquiry ID | Date Created | Messages          | Replies  |");
        System.out.println("------------------------------------------------------------");

        for (Enquiry enquiry : enquiries) {
            System.out.printf("| %-11s | %-13s | %-17s | %-8s |\n",
                    enquiry.getEnquiryID(),
                    enquiry.getDateCreated(),
                    enquiry.getContents(),
                    enquiry.getReplies().size());

            // Iterate over replies and print each one
            for (Reply reply : enquiry.getReplies()) {
                System.out.println("|   Reply: " + reply.getContents());
            }

            System.out.println("------------------------------------------------------------");
        }
    }

	// Method to edit a student-specific enquiry by EnquiryID
	@Override
	public void editEnquiry(Scanner scanner) {
	
	    System.out.println("Enter the Enquiry ID you want to edit:");
	    String enquiryID = scanner.nextLine();
	
	    for (Enquiry enquiry : enquiries) {
	        if (enquiry.getEnquiryID().equals(enquiryID) && !enquiry.isProcessed()) {
	            // Display the current enquiry details
	            System.out.println("Current Enquiry Details");
	            System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
	            System.out.println("Date Created: " + enquiry.getDateCreated());
	            System.out.println("Current Message: " + enquiry.getContents());
	
	            // Prompt the user for a new enquiry message
	            System.out.print("Enter the new enquiry message:");
	            String newEnquiryMessage = scanner.nextLine();
	
	            // Update the enquiry message using setEnquiryMessage() method
	            enquiry.setContents(newEnquiryMessage);
	
	            System.out.println("Enquiry updated successfully!");
	            return; // No need to continue searching once found
	        }
	    }
	
	    System.out.println("Enquiry not found or already processed. Editing failed.");
	}


    // Method to delete a student-specific enquiry by EnquiryID
    public void deleteEnquiry(Scanner scanner, ArrayList<Camp> allCamps) {

        System.out.println("Enter the Enquiry ID you want to delete:");
        String enquiryID = scanner.nextLine();

        for (Enquiry enquiry : enquiries) {
            if (enquiry.getEnquiryID().equals(enquiryID) && !enquiry.isProcessed()) {
                // Display the enquiry details
                System.out.println("Enquiry to delete:");
                System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
                System.out.println("Date Created: " + enquiry.getDateCreated());
                System.out.println("Messages: " + enquiry.getContents());

                // Verify if the user wants to delete the enquiry
                System.out.println("Do you want to delete this enquiry? (yes/no)");
                String deleteChoice = scanner.nextLine();

                if (deleteChoice.equalsIgnoreCase("yes")) {
                    // Remove the Enquiry object from the student's enquiries list
                    enquiries.remove(enquiry);

                    // Find the associated camp and remove the Enquiry object from the camp's enquiries list
                    for (Camp camp : allCamps) {
                        for (Enquiry campEnquiry : camp.getEnquiries()) {
                            if (campEnquiry.getEnquiryID().equals(enquiryID)) {
                                camp.removeEnquiry(campEnquiry);
                                break;
                            }
                        }
                    }

                    System.out.println("Enquiry deleted successfully!");
                } else {
                    System.out.println("Enquiry deletion canceled.");
                }

                return; // No need to continue searching once found
            }
        }

        System.out.println("Enquiry not found or already processed. Deletion failed.");
    }

	// Method to register as Committee Member for a camp
	public CommitteeMember registerAsCM(Scanner scanner, ArrayList<Camp> allCamps, Student student) {
	    // Check if the role is STUDENT
	    if (this.getRole() != Role.STUDENT) {
	        System.out.println("Only students are eligible to register as Committee Members.");
	        return null;
	    }
	
	    System.out.println("Enter the CampID to register as Committee Member:");
	    String campID = scanner.nextLine();
	
	    Camp selectedCamp = null;
	
	    // Find the camp with the specified CampID
	    for (Camp camp : allCamps) {
	        if (camp.getCampID().equals(campID)) {
	            selectedCamp = camp;
	            break;
	        }
	    }
	
	    // Check if the camp is found and can be registered as Committee Member
	    if (selectedCamp != null && selectedCamp.getisVisible() && !selectedCamp.getPreviouslyWithdrawn().contains(this)
	            && selectedCamp.getDetails().getFaculty().equals(this.getFaculty())) {
	    	
	        // Check if there are available committee slots and the current date is before the registration closing date
	        int remainingSlots = selectedCamp.getDetails().getCommitteeSlots() - selectedCamp.getCommittee().size();
	
	        if (remainingSlots > 0 && selectedCamp.getDetails().getRegistrationClosingDate().isAfter(LocalDate.now())) {
	        	CommitteeMember cm = new CommitteeMember(student, selectedCamp);
	            // Add the student to the camp's committee
	            selectedCamp.addCommittee(cm);
	
	            System.out.println("Registration as Committee Member successful! Your menu will be changed.");
	            return cm;
	        } else {
	            System.out.println("Cannot register as Committee Member for the camp. Check available committee slots and registration closing date.");
	            return null;
	        }
	    } else {
	        System.out.println("Camp not found or cannot be registered as Committee Member.");
	        return null;
	    }
	}
    @Override
    public String toString() {
        String delimiter = " | ";
        return this.getUserID() + delimiter + this.getRole() + delimiter + this.getName() + delimiter
                + this.getEmail() + delimiter + this.getFaculty();
    }

    public static String generateCSVHeaders() {
        String delimiter = ", ";
        return "UserID" + delimiter + "Role" + delimiter + "Name" + delimiter
                + "Email" + delimiter + "Faculty";
    }

    public String toCSV() {
        String delimiter = ", ";
        return this.getUserID() + delimiter + this.getRole() + delimiter + this.getName() + delimiter
                + this.getEmail() + delimiter + this.getFaculty();
    }


}

