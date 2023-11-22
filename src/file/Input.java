package input;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * The <code>input</code> class contains various methods to provide support for obtaining inputs of different data types from the client.
 * Assists users of this class to avoid repetition of boilerplate code by presenting it in a more concise manner (one liners),
 * and allows injection of class methods into custom setters, constructors and initialization of variables.
 * 
 * @author Nah Wei Jie
 * @version 1.0
 */
public class Input {

  /**
   * Displays a message and obtains an integer input from the client.
   * 
   *    <p>Examples of expected usage<br>
   *    1) As a way to initialize a variable
   *    <ul><code>Scanner sc = new Scanner();</code><br>
   *    <code>int myNumber = getIntInput("Enter a number: ", sc);</code><br>
   *    <code>-> Enter a number: 5 </code><br><br>
   *    
   *    This is the equivalent of<br>
   *    <code>int myNumber = 5;</code></ul>
   *    
   *    <p>2) To obtain values from a user to pass in as parameters for constructors during runtime
   *    
   *    <ul><code>Scanner sc = new Scanner();</code><br>
   *    <code>int result = add(getIntInput("Enter the first number: ", sc), getIntInput("Enter the second number: ", sc));</code><br>
   *    <code>-> Enter the first number: 5 </code><br>
   *    <code>-> Enter the second number: 3  </code><br><br>
   *    
   *    This is the equivalent of<br>
   *    <code>int result = add(5, 3);</code><br></ul>
   *   
   * @param messageToShow A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as an integer.
   * 
   */
  public static int getIntInput(String messageToShow, Scanner sc) {
    System.out.println(messageToShow);
    int input = sc.nextInt();
    return input;
  }
  
  /**
   * Displays a message and obtains a string input from the client.
   * 
   *    <p>Examples of expected usage<br>
   *    1) As a way to initialize a variable
   *    <ul><code>Scanner sc = new Scanner();</code><br>
   *    <code>String myStr = getStrInput("Enter a string: ", sc);</code><br>
   *    <code>-> Enter a string: abcdefg </code><br><br>
   *    
   *    This is the equivalent of<br>
   *    <code>String myStr = "abcdefg";</code></ul>
   *    
   *    <p>2) To obtain values from a user to pass in as parameters for constructors during runtime<br>
   *    Asssume there is a constructor for the Location class which takes two parameters:<br><br>
   *        String Country<br>
   *        String Address<br>
   *    <ul><code>Scanner sc = new Scanner();</code><br>
   *    <code>Location myLocation = new Location(getStrInput("Enter a country: ", sc), getStrInput("Enter an address: ", sc));</code><br>
   *    <code> -> Enter a country: Singapore </code><br>
   *    <code> -> Enter an address: 76 Nanyang Drive  </code><br></ul>

   *    This is the equivalent of<br>
   *    <code>Location myLocation = Location("Singapore", "76 Nanyang Drive");</code><br><br>
   * 
   * @param messageToShow A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as a string.
   *    
   */
  public static String getStringInput(String messageToShow, Scanner sc) {
    System.out.println(messageToShow);
    String input = sc.nextLine();
    return input;
  }
  
  /**
   * Displays a message and gets a float input from the client.
   * 
   * @param messageToShow A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as an float.
   * 
   */
  
  public static float getFloatInput(String messageToShow, Scanner sc) {
    System.out.println(messageToShow);
    float input = sc.nextFloat();
    return input;
  }
  
  /**
   * Displays a message and gets an double input from the client.
   * 
   * @param messageToShow A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as a double.
   */
  
  public static double getDoubleInput(String messageToShow, Scanner sc) {
    System.out.println(messageToShow);
    double input = sc.nextDouble();
    return input;
  }
  
  /**
   * Displays a message and gets a character input from the client.
   * 
   * @param messageToShow A string message displayed to the client when requesting for an input.
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return The input of the client as a char.
   */
  public static char getCharInput(String messageToShow, Scanner sc) {
    System.out.println(messageToShow);
    char input = sc.next().charAt(0);
    return input;
  }
  
  /**
   * Displays a message and gets three string inputs from the client as day(DD), month(MM), year(YYYY) and converts it into a LocalDate class.
   * Expects string "date" to adhere to the following format (DD/MM/YYYY)
   * 
   *    <p>Examples of expected usage<br>
   *    1) As a way to initialize a variable
   *    
   *    <ul><code>Scanner sc = new Scanner();</code><br>
   *    <code>LocalDate NationalDay = getDateFromInput("National Day", sc);</code><br>
   *    <code>-> Enter the day (DD) for National Day: 09 </code><br>
   *    <code>-> Enter the month (MM) for National Day: 08 </code><br>
   *    <code>-> Enter the year (YYYY) for National Day: 1965 </code></ul>
   *    
   *    This is the equivalent of<br>
   *    <code>LocalDate NationalDay = "09-08-1965";</code><br><br>
   * 
   * @param sc A scanner object used for methods to request for input (dependency).
   * @return A LocalDate class.
   *   
   */
  public static LocalDate getDateFromInput(String messageToShow, Scanner sc) {
    String day = getStringInput("Enter the day (DD) for " + messageToShow, sc);
    String month = getStringInput("Enter the month (MM) for " + messageToShow, sc);
    String year = getStringInput("Enter the year (YYYY)) for " + messageToShow, sc);
    String delimiter = "-";
    LocalDate inputDate = LocalDate.parse(year+delimiter+month+delimiter+day); 
    return inputDate;
  }
  

}
