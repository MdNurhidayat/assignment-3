package enums;

import java.util.Scanner;

import file.Input;

public enum Faculty {
  SCSE,
  ADM,
  EEE,
  NBS,
  SSS,
  NTU;

  public static void printAll() {
    Faculty[] facultyList = Faculty.values();
    System.out.println("List of faculties:");
    for (Faculty f: facultyList) {
      System.out.println(f);
    }
  }
  
  public static Faculty search(String str) {
    Faculty[] facultyList = Faculty.values();
    for (Faculty f: facultyList) {
      if (str.equals(f.toString())) {
        return f;
      }
    }
    return null;
  }
  
  public static Faculty getFacultyFromStringInput(Scanner sc) {
    printAll();
    Faculty result = null;
    do {
      String userInput = Input.getStringInput("Enter your selection: ", sc);
      result = search(userInput.toUpperCase());
      if (result != null) {
        return result;
      }
      System.out.println("Your selection does not match any faculties, please re-enter.");
    } while(result == null);
    return result; // Logically, this line will never be reached. Included to keep the compiler from showing error of not returning.
  }
  
  //TODO for testing, remove before submission or demo-ing
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    getFacultyFromStringInput(sc);
  }
}
