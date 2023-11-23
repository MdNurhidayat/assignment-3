package student;

import java.util.Scanner;

import enquiry.BaseEnquiry;

public interface StudentEnquiry extends BaseEnquiry {
    void submitEnquiry(Scanner scanner);
    void editEnquiry(Scanner scanner);
    void deleteEnquiry(Scanner scanner);
}