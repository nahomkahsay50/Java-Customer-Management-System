package week15finalproject;

import java.util.Scanner;

/**
 * Name: GetUserInput
 * Abstract: Main program class that interacts with users and provided menu options
 * to view customer records or create modified customer XML file
 * 
 * @author Nahom Kahsay
 * @version 1.0
 * @since 12/09/2025
 */
public class GetUserInput {
	
	private static Scanner scanner = new Scanner(System.in);

	/**
	 * Name: main
	 * Abstract: this is where the program starts
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			System.out.println("Customer Management System");
			System.out.println("-----------------------------");
			System.out.println();
			
			// Process user selection
			int intUserChoice = DisplayMenuAndGetChoice();
			
			ProcessUserChoice(intUserChoice);
			
		} catch (Exception e) {
			System.out.println("Error in main method: " + e.getMessage());
		} finally {
			scanner.close();
			System.out.println();
			System.out.println("Processing Complete");
		}
	}
	
	/**
	 * Name: GetMenuAndDisplayChoice
	 * Abstract: Displays the menu options and gets user input
	 * @return intChoice - the user's menu selection (1-2)
	 */
	public static int DisplayMenuAndGetChoice() {
		System.out.println("Please select an option");
		System.out.println("1 - View Customers");
		System.out.println("2 - Create Modified Customers File");
		System.out.print("Your choice: ");
		
		return ReadIntegerFromUser();
	}
	
	/**
	 * Name:ProcessUserChoice
	 * Abstract: Processes the user's menu selection and calls appropriate methods
	 * 
	 * @param intChoice - the user's menu selection
	 */
	public static void ProcessUserChoice(int intChoice) {
		try {
			switch(intChoice) {
			case 1:
				System.out.println();
				System.out.println("--- Option 1: Viewing Customer Records ---");
				System.out.println();
				ViewCustomers.DisplayCustomers();
				break;
				
			case 2:
				System.out.println();
				System.out.println("--- Option 2: Creating Modified Customer File ---");
				System.out.println();
				ModifyCustomers.CreateModifiedCustomerFile();
				break;
				
			default:
				System.out.println("Invalid choice. Please select option 1 or 2.");
				break;
			}
		} catch (Exception e) {
			System.out.println("Error processing user choice: " + e.getMessage());
		}
	}
	
	/**
	 * Name: ReadIntegerFromUser
	 * Abstract: Reads and integer value from user input with validation
	 * 
	 * @return intValue - the integer value entered by user
	 */
	public static int ReadIntegerFromUser() {
		
		int intValue = 0;
		boolean blnValidInput = false;
		
		while(!blnValidInput) {
			try {
				String strInput = scanner.nextLine().trim();
				intValue = Integer.parseInt(strInput);
				blnValidInput = true;
			} catch (NumberFormatException e) {
				System.out.print("Invalid input. Please enter a valid integer: ");
			}
		}
		
		return intValue;
	}
}
