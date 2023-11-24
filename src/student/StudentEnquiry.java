package student;

import java.util.ArrayList;
import java.util.Scanner;

import camp.Camp;
import enquiry.BaseEnquiry;

public interface StudentEnquiry extends BaseEnquiry {
    void submitEnquiry(Scanner scanner, ArrayList<Camp> camps);
    void editEnquiry(Scanner scanner);
    void deleteEnquiry(Scanner scanner, ArrayList<Camp> camps);
}