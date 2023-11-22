import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

import user.User;
import camp.Camp;
import enums.Role;

public class Student extends User implements Registration, Withdrawable, StudentEnquiry, BaseEnquiry {
    private static List<Student> allStudents = new ArrayList<>(); // Declaration and initialization
    private ArrayList<Camp> registeredFor;
    private Role role;
    private ArrayList<Enquiry> enquiries;
    private int enquiryCounter; // New counter for generating unique enquiry IDs

    // constructor
    public Student(String UserID, String Password, Role role, String name, String email, Faculty faculty) {
        super(UserID, Password, role, name, email, faculty);
        this.registeredFor = new ArrayList<>(); // Initialize with an empty ArrayList
        this.enquiries = new ArrayList<>();
        this.enquiryCounter = 1; // Initialize counter to 1
	allStudents.add(this); //maintain a list of students
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

    public static List<Student> getAllStudents() {
        return allStudents;
    }

    // Method to view available camps to join
    public void viewAvailableCampsToJoin() {
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-15s |\n", "CampID", "Details", "Remaining Slots");
        System.out.println("---------------------------------------------------------------------------");

        List<Camp> allCamps = Camp.getAllCamps();

        for (Camp camp : allCamps) {
            if (camp.getIsVisible() && !camp.getPreviouslyWithdrawn().contains(this) && camp.getFaculty().contains(this.getFaculty())) {
                
		int remainingSlots = camp.getDetail.getTotalSlot() - camp.getParticipants().size();

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
            int remainingSlots = camp.getDetail.getTotalSlot() - camp.getParticipants().size();

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
public void register() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter the CampID to register:");
    String campID = scanner.nextLine();

    List<Camp> allCamps = Camp.getAllCamps();
    Camp selectedCamp = null;

    // Find the camp with the specified CampID
    for (Camp camp : allCamps) {
        if (camp.getCampID().equals(campID)) {
            selectedCamp = camp;
            break;
        }
    }

    // Check if the camp is found and can be registered
    if (selectedCamp != null && selectedCamp.getIsVisible() && !selectedCamp.getPreviouslyWithdrawn().contains(this)
            && selectedCamp.getFaculty().contains(this.getFaculty())) {
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
                selectedCamp.addParticipants(this);

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
    public void withdraw() {
        Scanner scanner = new Scanner(System.in);

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
                selectedCamp.addPreviouslyWithdrawn(this);

                System.out.println("Withdrawal successful!");
            } else {
                System.out.println("Withdrawal canceled.");
            }
        } else {
            System.out.println("Camp not found in the registered list. Cannot withdraw.");
        }
    }

    // Method to submit an enquiry for a specific camp	
    public void submitEnquiry() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the CampID for the enquiry:");
        String campID = scanner.nextLine();

        System.out.println("Enter your enquiry message:");
        String enquiryMessage = scanner.nextLine();

        List<Camp> allCamps = Camp.getAllCamps();
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
            String enquiryID = getUserID() + enquiryCounter++;
            Enquiry newEnquiry = new Enquiry(campID, LocalDate.now(), enquiryID, new ArrayList<>(), enquiryMessage);

            // Add the enquiry to the student's enquiries list
            enquiries.add(newEnquiry);

            // Add the enquiry to the camp's enquiries list
            selectedCamp.addEnquires(newEnquiry);

            System.out.println("Enquiry submitted successfully!");
        } else {
            System.out.println("Camp not found. Enquiry submission failed.");
        }
    }

    // Method to view enquiries
    public void viewEnquiries() {
        System.out.println("------------------------------------------------------------");
        System.out.println("| Enquiry ID | Date Created | Messages          | Replies  |");
        System.out.println("------------------------------------------------------------");

        for (Enquiry enquiry : enquiries) {
            System.out.printf("| %-11s | %-13s | %-17s | %-8s |\n",
                    enquiry.getEnquiryID(),
                    enquiry.getDateCreated(),
                    enquiry.getEnquiryMessage(),
                    enquiry.getReplies().size());

            // Iterate over replies and print each one
            for (Reply reply : enquiry.getReplies()) {
                System.out.println("|   Reply: " + reply.getMessage());
            }

            System.out.println("------------------------------------------------------------");
        }
    }

// Method to edit a student-specific enquiry by EnquiryID
public void editEnquiries() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter the Enquiry ID you want to edit:");
    String enquiryID = scanner.nextLine();

    for (Enquiry enquiry : enquiries) {
        if (enquiry.getEnquiryID().equals(enquiryID) && !enquiry.getIsProcessed()) {
            // Display the current enquiry details
            System.out.println("Current Enquiry Details:");
            System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
            System.out.println("Date Created: " + enquiry.getDateCreated());
            System.out.println("Current Message: " + enquiry.getEnquiryMessage());

            // Prompt the user for a new enquiry message
            System.out.println("Enter the new enquiry message:");
            String newEnquiryMessage = scanner.nextLine();

            // Update the enquiry message using setEnquiryMessage() method
            enquiry.setEnquiryMessage(newEnquiryMessage);

            System.out.println("Enquiry updated successfully!");
            return; // No need to continue searching once found
        }
    }

    System.out.println("Enquiry not found or already processed. Editing failed.");
}


    // Method to delete a student-specific enquiry by EnquiryID
    public void deleteEnquiries() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Enquiry ID you want to delete:");
        String enquiryID = scanner.nextLine();

        for (Enquiry enquiry : enquiries) {
            if (enquiry.getEnquiryID().equals(enquiryID) && !enquiry.getIsProcessed()) {
                // Display the enquiry details
                System.out.println("Enquiry to delete:");
                System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
                System.out.println("Date Created: " + enquiry.getDateCreated());
                System.out.println("Messages: " + enquiry.getEnquiryMessage());

                // Verify if the user wants to delete the enquiry
                System.out.println("Do you want to delete this enquiry? (yes/no)");
                String deleteChoice = scanner.nextLine();

                if (deleteChoice.equalsIgnoreCase("yes")) {
                    // Remove the Enquiry object from the student's enquiries list
                    enquiries.remove(enquiry);

                    // Remove the Enquiry object from List<Enquiry> allEnquiries
                    Enquiry.removeEnquiry(enquiry);

                    // Find the associated camp and remove the Enquiry object from the camp's enquiries list
                    List<Camp> allCamps = Camp.getAllCamps();
                    for (Camp camp : allCamps) {
                        for (Enquiry campEnquiry : camp.getEnquiries()) {
                            if (campEnquiry.getEnquiryID().equals(enquiryID)) {
                                camp.getEnquiries().remove(campEnquiry);
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
public void registerAsCM() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter the CampID to register as Committee Member:");
    String campID = scanner.nextLine();

    List<Camp> allCamps = Camp.getAllCamps();
    Camp selectedCamp = null;

    // Find the camp with the specified CampID
    for (Camp camp : allCamps) {
        if (camp.getCampID().equals(campID)) {
            selectedCamp = camp;
            break;
        }
    }

    // Check if the camp is found and can be registered as Committee Member
    if (selectedCamp != null && selectedCamp.getIsVisible() && !selectedCamp.getPreviouslyWithdrawn().contains(this)
            && selectedCamp.getFaculty().contains(this.getFaculty()) && this.getRole() == Role.STUDENT) {

        // Check if the role is STUDENT
        int remainingSlots = selectedCamp.getDetails().getCOMMITTEE_SLOTS() - selectedCamp.getCommittee().size();

        // Check if there are available committee slots and the current date is before the registration closing date
        if (remainingSlots > 0 && selectedCamp.getDetails().getRegistrationClosingDate().isAfter(LocalDate.now())) {

            // Add the student to the camp's committee
            selectedCamp.addCommittee(this);

            // Set the object's role from STUDENT to COMMITTEE_MEMBER
            this.setRole(Role.COMMITTEE_MEMBER);

	    // Set the overseeingCamp attribute to the selected camp
	    this.setOverseeing(selectedCamp);

            System.out.println("Registration as Committee Member successful! Your menu will be changed.");
        } else {
            System.out.println("Cannot register as Committee Member for the camp. Check available committee slots and registration closing date.");
        }
    } else {
        System.out.println("Camp not found, cannot be registered as Committee Member, or role is not STUDENT.");
    }
}

}

