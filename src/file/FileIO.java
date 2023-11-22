package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileIO {
  
  public static void writeToFile(String fileName, ArrayList<String> anArray) {
    
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
      for (String line : anArray) {
        writer.write(line + "\n");
      }
      writer.close();
      System.out.println("Successfully read from " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static ArrayList<String> readFromFile(String filePath) {
    ArrayList<String> resultArr = new ArrayList<>();  
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));

      String line;
      while((line = reader.readLine()) != null){
        resultArr.add(line);
        System.out.println(line);
      }
      System.out.println("Successfully read from " + filePath);
      reader.close();

    } catch (IOException e) {
      
    }
    return resultArr;
  }
  
  /*public static void main(String[] args) {
    ArrayList<String> testArr = new ArrayList<>();
    
    // have to ensure each class we want to save to file has proper implementation of getHeader() and toString() methods.
    // getHeader() generates the labels for the first row
    // toString() ensures all attribute values are "stringed" as we can only write string to file
    String campHeaders = "campid, location, description, faculty, startdate, enddate, registrationdeadline, totalslots, staffic, campcommittee, campattendee, ...";
    String row1 = "camp1234, NTU, testdesc, SCSE, 2023-11-16, 2023-11-17, 2023-11-15, 50, somestaff, [..., ... ,...], [...,...,...], ...";
    testArr.add(campHeaders);
    testArr.add(row1);
    
    writeToFile("test.txt", testArr);
    testArr = readFromFile("test.text");
  }*/
}















