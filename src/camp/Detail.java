package camp;

import java.time.LocalDate;
import java.util.Scanner;
import committeeMember.CommitteeMember;
import enums.Faculty;
import enums.Role;
import staff.Staff;
import file.Input;

/**
 * Represents details of this <code>camp</code>. Can only be modified by a <code>Staff</code>. Created at the same time when a <code>camp</code> is created.
 * @author Nah Wei Jie
 * @version 1.04
 * @see <code>Staff</code>, <code>Camp</code>
 */
public class Detail {
  /**
   * Represents the staff in charge of this camp. The staff in charge is also the creator of this camp.
   * @see <code>Staff</code>
   */
  private Staff staffInCharge;
  /**
   * Represents the name of the staff in charge of this camp.
   */
  private String staffInChargeName;
  /**
   * Represents the name of this camp. 
   */
  private String name;
  /**
   * Represents the location of this camp. 
   */
  private String location;
  /**
   * Represents the description of this camp. 
   */
  private String description;
  /**
   * Represents the faculty of this camp. Uses the <code>Faculty</code> enum class.
   */
  private Faculty faculty;
  /**
   * Represents the starting date of this camp. Uses the built-in <code>LocalDate</code> class from the <code>java.time</code> package.
   * @see <code>LocalDate</code>, <code>java.time</code>
   */
  private LocalDate startDate;
  /**
   * Represents the ending date of this camp. Uses the built-in <code>LocalDate</code> class from the <code>java.time</code> package.
   * @see <code>LocalDate</code>, <code>java.time</code>
   */
  private LocalDate endDate;
  /**
   * Represents the registration deadline of this camp. Uses the built-in <code>LocalDate</code> class from the <code>java.time</code> package.
   * @see <code>LocalDate</code>, <code>java.time</code>
   */
  private LocalDate registrationClosingDate;
  /**
   * Represents the number of slots for participants of this camp.
   * @see <code>Student</code>, <code>CommitteeMember</code>
   */
  private int totalSlots;
  /**
   * Represents the number of slots for committee members of this camp. Defaults to a value of 10 as per requirements.
   * @see <code>CommitteeMember</code>
   */
  private final int COMMITTEE_SLOTS = 10;
  
  /**
   * Constructor for a Detail object.
   * @see <code>Detail</code>
   */
  public Detail(Scanner sc, Staff aStaff) {
    this.staffInCharge = aStaff;
    this.staffInChargeName = aStaff.getName();
    this.name = Input.getStringInput("Enter the name of this camp: ", sc);
    this.location = Input.getStringInput("Enter the location of this camp: ", sc);
    this.description = Input.getStringInput("Enter the description of this camp: ", sc);
    this.faculty = Faculty.valueOf(Input.getStringInput("Enter the faculty of this camp: ", sc));
    this.startDate = Input.getDateFromInput("Starting date: ", sc);
    this.endDate = Input.getDateFromInput("Ending date: ", sc);
    this.registrationClosingDate = Input.getDateFromInput("Registration deadline: ", sc);
    this.totalSlots = Input.getIntInput("Enter the combined total slots for this camp: ", sc);
  }

  public String getName() {
    return name;
  }
  
  public Staff getStaffInCharge() {
    return staffInCharge;
  }
  public String getStaffInChargeName() {
    return staffInChargeName;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public String getLocation() {
    return location;
  }
  
  public void setLocation(String location) {
    this.location = location;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public Faculty getFaculty() {
    return faculty;
  }
  
  public void setFaculty(Faculty faculty) {
    this.faculty = faculty;
  }
  
  public LocalDate getStartDate() {
    return startDate;
  }
  
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }
  
  public LocalDate getEndDate() {
    return endDate;
  }
  
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
  
  public LocalDate getRegistrationClosingDate() {
    return registrationClosingDate;
  }
  
  public void setRegistrationClosingDate(LocalDate registrationClosingDate) {
    this.registrationClosingDate = registrationClosingDate;
  }
  
  public int getTotalSlots() {
    return totalSlots;
  }
  
  public void setTotalSlots(int totalSlots) {
    this.totalSlots = totalSlots;
  }
  
  public int getCOMMITTEE_SLOTS() {
    return COMMITTEE_SLOTS;
  }

  // non-default methods, to be reflected in UML
  public String toString() {
    String delimiter = " ";
    return this.staffInChargeName + delimiter 
           + this.name + delimiter
           + this.location + delimiter
           + this.description + delimiter
           + this.faculty + delimiter
           + this.startDate + delimiter
           + this.endDate + delimiter
           + this.registrationClosingDate + delimiter
           + this.totalSlots + delimiter
           + this.COMMITTEE_SLOTS;
    }
  
  public String generateCSVHeaders() {
    String delimiter = ", ";
    return "staffInChargeName" + delimiter 
        + "name" + delimiter
        + "location" + delimiter
        + "description" + delimiter
        + "faculty" + delimiter
        + "startDate" + delimiter
        + "endDate" + delimiter
        + "registrationClosingDate" + delimiter
        + "totalSlots" + delimiter
        + "COMMITTEE_SLOTS";
  }
  
  public String toCSV() {
    String delimiter = ", ";
    return this.staffInChargeName + delimiter 
           + this.name + delimiter
           + this.location + delimiter
           + this.description + delimiter
           + this.faculty + delimiter
           + this.startDate + delimiter
           + this.endDate + delimiter
           + this.registrationClosingDate + delimiter
           + this.totalSlots + delimiter
           + this.COMMITTEE_SLOTS;
    }
  
  public void print() {
    System.out.println("staffInChargeName: " + this.staffInChargeName);
    System.out.println("Name: " + this.name);
    System.out.println("Location: " + this.location);
    System.out.println("Description: " + this.description);
    System.out.println("Faculty: " + this.faculty);
    System.out.println("StartDate: " + this.startDate.getDayOfMonth()
                       + "-" + this.startDate.getMonthValue() 
                       + "-" + this.startDate.getYear());
    System.out.println("EndDate: " + this.endDate.getDayOfMonth()
                       + "-" + this.endDate.getMonthValue() 
                       + "-" + this.endDate.getYear());
    System.out.println("RegistrationClosingDate: " + this.registrationClosingDate.getDayOfMonth()
                       + "-" + this.registrationClosingDate.getMonthValue() 
                       + "-" + this.registrationClosingDate.getYear());
    System.out.println("TotalSlots: " + this.totalSlots);
    System.out.println("CommitteeSlots: " + this.COMMITTEE_SLOTS);
  }
  
  // TODO main for own testing, remove when running from application
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Staff testStaff = new Staff("Alex123", "password", Role.STAFF, "Alex", "Alex123@ntu.edu.sg", "SCSE");
    Detail testDetail = new Detail(sc, testStaff);
    
    testDetail.print();
    System.out.println();
    String testDetailString = testDetail.toString();
    System.out.println(testDetailString);
    System.out.println();
    String csvHeaders = testDetail.generateCSVHeaders();
    System.out.println(csvHeaders);
    System.out.println();
    String sampleCSV = testDetail.toCSV();
    System.out.println(sampleCSV);
    System.out.println();
  }
}
