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
      //System.out.println("Successfully read from " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static ArrayList<String> readFromFile(String filePath) {
    ArrayList<String> resultArr = new ArrayList<String>();  
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));

      String line;
      while((line = reader.readLine()) != null){
        resultArr.add(line);
        //System.out.println(line);
      }
      //System.out.println("Successfully read from " + filePath);
      reader.close();

    } catch (IOException e) {
      
    }
    return resultArr;
  }
}















