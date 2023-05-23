/**
 * Sevag Merdkhanian 40247912, Alec Kirakossian 40244852
 * COMP249
 * Assignment 4
 * Monday, April 17, 2023
 */
package Assignment_4_Driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Assignment_4_Classes.Book;
import Assignment_4_Classes.BookList;
/**
 * BookDriver Driver Class
 * @author Sevag and Alec
 */
public class BookDriver {
	/**
	 * Main Method
	 * @param args
	 */
	public static void main(String[] args) {
		//Creating arrList ArrayList for invalid records and bkList BookList for valid records
		ArrayList<Book> arrList = new ArrayList<Book>();
		BookList bkList = new BookList();
		//Scanner and PrintWriter to read and write to files
		Scanner sc = null;
		PrintWriter pwErrors = null;
		try {
			/*Reading each record in Books.txt, creating a Book Object from them.
			 * If the year of the Book Object is less than 2024, adding it to the 
			 * end of bkList. Otherwise, adding it to arrList
			 */
			sc = new Scanner(new FileInputStream("Books.txt"));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] record = line.split(",");
				String title = record[0];
				String author = record[1];
				double price = Double.parseDouble(record[2]);
				long ISBN = Long.parseLong(record[3]);
				String genre = record[4];
				int year = Integer.parseInt(record[5]);
				Book b = new Book(title,author,price,ISBN,genre,year);
				if (b.getYear() < 2024)
					bkList.addToEnd(b);
				else
					arrList.add(b);
			}
			//using trimToSize on arrList to save memory and closing scanner
			arrList.trimToSize();
			sc.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("The file was not found, exiting program.");
			System.exit(0);
		}
		//If there were any invalid records added to arrList, writing them to YearError.txt
		if (arrList.size() > 0) {
			try {
				pwErrors = new PrintWriter(new FileOutputStream("YearError.txt"));
				for (Book book : arrList)
					pwErrors.println(book);
			}
			catch(FileNotFoundException e) {
				System.out.println("The file was not found, exiting program.");
				System.exit(0);;
			}
			pwErrors.close();
		}
		//Informating the user that YearError was created and displaying the contents of bkList
		System.out.println("YearError File Created");
		System.out.println("Here are the current contents of the list of books");
		System.out.println("=============================================================");
		bkList.displayContent();
		//Running the program until the user exits in option 8
		while (true) {
			//Scanner objects for user input
			Scanner kb = new Scanner(System.in);
			Scanner kbOpt3 = new Scanner(System.in);
			Scanner kbOpt4 = new Scanner(System.in);
			Scanner kbOpt5 = new Scanner(System.in);
			Scanner kbOpt6 = new Scanner(System.in);
			//Menu
			String menu = "Tell me what you want to do? Let's Chat since this is trending now! Here are the options:\n"
						+ "\t1) Give me a year # and I would extract all records of that year and store them in a file for that year;\n"
						+ "\t2) Ask me to delete all consecutive repeated records;\n"
						+ "\t3) Give me an author name and I will create a new list with the records of this author and display them;\n"
						+ "\t4) Give me an ISBN number and a Book object, and I will insert Node with the book before the record with this ISBN;\n"
						+ "\t5) Give me 2 ISBN numbers and a Book object, and I will insert a Node between them, if I find them!\n"
						+ "\t6) Give me 2 ISBN numbers and I will swap them in the list for rearrangement of records; of course if they exist!\n"
						+ "\t7) Tell me to COMMIT! Your command is my wish. I will commit your list to a file called Updated_Books;\n"
						+ "\t8) Tell me to STOP TALKING. Remember, if you do not commit, I will not!\n";
			System.out.println(menu);
			System.out.print("Enter your Selection: ");
			String option = kb.next();
			System.out.println();
			//If the option is anything other than 1,2,3,4,5,6,7,8, reprompting user for a valid option
			if (!(option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4") || 
				option.equals("5") || option.equals("6") || option.equals("7") || option.equals("8") ))
				System.out.println("Invalid option! Please try again. ");
			//Option 1
			if (option.equals("1")) {
				System.out.print("Please enter your chosen year: ");
				String year = kb.next();
				System.out.println();
				try {
					//If the year String cannot be parsed to an Integer, it's invalid
					int yr = Integer.parseInt(year);
					//Storing the records of the given year in a file for that year, then displaying the contents of bkList again
					bkList.storeRecordByYear(yr);
					System.out.println("Here are the current contents of the list of books");
					System.out.println("=============================================================");
					bkList.displayContent();
				}
				//Warning the user that their year input was invalid
				catch (NumberFormatException e) {
					System.out.println("Invalid input! Please try again. ");
				}
			}
			//Option 2
			if (option.equals("2")) {
				//Deleting consecutive records then displaying the updated content of bkList
				bkList.delConsecutiveRepeatedRecords();
				System.out.println("Deleted consecutive records. ");
				System.out.println("Here are the contents of the list of books after removing consective duplicates");
				System.out.println("===============================================================================");
				bkList.displayContent();
			}
			//Option 3
			if (option.equals("3")) {
				System.out.print("Please enter the name of the author to create an extracted list: ");
				String author = kbOpt3.nextLine();
				System.out.println();
				//Extracting author list
				BookList authorBkList = bkList.extractAuthList(author);
				//If authorBkList is empty, it means there were no books written by that author in the list
				if (authorBkList.isEmpty())
					System.out.println("There were no books written by that author in the list. ");
				//If the list was not empty, printing the contents of authorBkList
				else {
					System.out.println("Here are the contents of the author list for " + author);
					System.out.println("=============================================================");
					authorBkList.displayContent();
				}
			}
			//Option 4
			if (option.equals("4")) {
				//Prompting user to enter the ISBN 
				System.out.print("Please enter the ISBN number of the record you want to insert a Node before: ");
				String isbnStr = kbOpt4.next();
				System.out.println();
				try {
					//If the isbnStr String cannot be parsed to an Long, it's invalid
					Long isbn = Long.parseLong(isbnStr);
					//Prompting user to enter a valid Book Object
					System.out.println("Please enter the Book Object you want to insert inside the node in the following format. ");
					System.out.println("title, author, price, ISBN, genre, year ");
					System.out.println("Press press enter after typing each field: ");
					try {
						/*If any of the inputs were invalid, there will be an InputMismatchException, 
						 * except for if the year was inputted as above 2023, in which case there will simply
						 * be a message that warns the user that their year input was invalid. Otherwise, creating a Book 
						 * object from the given inputs, inserting it before the record with the given ISBN, and
						 * displaying the updated contents of bkList
						 */
						boolean validYear = true;
						boolean bookInserted = false;
						String bTitle = "\"" + kbOpt4.next() + "\"" ;
						kbOpt4.nextLine();
						String bAuthor = kbOpt4.nextLine();
						double bPrice = kbOpt4.nextDouble();
						long bIsbn = kbOpt4.nextLong();
						String bGenre = kbOpt4.next();
						int bYear = kbOpt4.nextInt();
						if (bYear >= 2024)
							validYear = false;
						Book b = new Book(bTitle,bAuthor,bPrice,bIsbn,bGenre,bYear);
						if (!validYear)
							System.out.println("Invalid year! The year cannot be above 2023");
						else
							bookInserted = bkList.insertBefore(isbn, b);
						//If no book was inserted, printing this message
						if (!bookInserted && validYear)
							System.out.println("No Book object was inserted; there was no record with the specified ISBN in the list. ");
						else if (validYear)
							System.out.println("ISBN " + isbn + " was found " + "and a Book object was inserted right before it");
						System.out.println("Here are the current contents of the list of books");
						System.out.println("=============================================================");
						bkList.displayContent();
					}
					//Warning the user that one of their inputs was invalid
					catch (InputMismatchException e) {
						System.out.println("Invalid input for one of the Book fields!");
					}	
				}
				//Warning the user that their ISBN input was invalid
				catch (NumberFormatException e) {
					System.out.println("Invalid ISBN input! Please try again. ");
				}
			}
			//Option 5
			if (option.equals("5")) {
				//Prompting user for both ISBN numbers
				System.out.print("Please enter the first ISBN number: ");
				String isbnStr1 = kbOpt5.next();
				System.out.println();
				System.out.print("Please enter the second ISBN number: ");
				String isbnStr2 = kbOpt5.next();
				System.out.println();
				try {
					//If the isbnStr1 or isbnStr2 Strings cannot be parsed to an Long, invalid
					Long isbn1 = Long.parseLong(isbnStr1);
					Long isbn2 = Long.parseLong(isbnStr2);
					System.out.println("Please enter the Book Object you want to insert inside the node in the following format. ");
					System.out.println("title, author, price, ISBN, genre, year ");
					System.out.println("Press enter after typing each field: ");
					try {
						/*If any of the inputs were invalid, there will be an InputMismatchException, 
						 * except for if the year was inputted as above 2023, in which case there will simply
						 * be a message that warns the user that their year input was invalid. Otherwise, creating a Book 
						 * object from the given inputs, inserting it between the records with the given ISBN, and
						 * displaying the updated contents of bkList
						 */
						boolean validYear = true;
						boolean bookInserted = false;
						String bTitle = "\"" + kbOpt5.next() + "\"" ;
						kbOpt5.nextLine();
						String bAuthor = kbOpt5.nextLine();
						double bPrice = kbOpt5.nextDouble();
						long bIsbn = kbOpt5.nextLong();
						String bGenre = kbOpt5.next();
						int bYear = kbOpt5.nextInt();
						if (bYear >= 2024)
							validYear = false;
						Book b = new Book(bTitle,bAuthor,bPrice,bIsbn,bGenre,bYear);
						if (!validYear)
							System.out.println("Invalid year! The year cannot be above 2023");
						else
							bookInserted = bkList.insertBetween(isbn1, isbn2, b);
						//If no book was inserted, printing this message
						if (!bookInserted && validYear)
							System.out.println("No Book object was inserted; there were no two consecutive record with the specified ISBNs in the list. ");
						else if (validYear)
							System.out.println("Records with ISBN " + isbn1 + " and " + isbn2 + " were found and the Book object was inserted between them. ");
						System.out.println("Here are the current contents of the list of books");
						System.out.println("=============================================================");
						bkList.displayContent();
					}
					//Warning the user that one of their inputs was invalid
					catch (InputMismatchException e) {
						System.out.println("Invalid input for one of the Book fields!");
					}
				}
				//Warning the user that one of their ISBN inputs was invalid
				catch (NumberFormatException e) {
					System.out.println("Invalid ISBN input! Please try again. ");
				}
			}
			//Option 6
			if (option.equals("6")) {
				//Prompting user for both ISBN numbers
				System.out.print("Please enter the first ISBN number of the record you want to swap: ");
				String isbnStr1 = kbOpt6.next();
				System.out.println();
				System.out.print("Please enter the second ISBN number of the record you want to swap: ");
				String isbnStr2 = kbOpt6.next();
				System.out.println();
				try {
					//If the isbnStr1 or isbnStr2 Strings cannot be parsed to an Long, invalid
					Long isbn1 = Long.parseLong(isbnStr1);
					Long isbn2 = Long.parseLong(isbnStr2);
					//If user inputs two of the same ISBN, a warning is printed
					if (isbn1.equals(isbn2)) 
						System.out.println("No records were swapped; the inputted ISBNs were the same! ");
					else {
						//Swapping the records of both given ISBNs
						boolean booksSwapped = bkList.swap(isbn1, isbn2);
						//If no records were swapped, printing this message
						if (!booksSwapped)
							System.out.println("No records were swapped; there were no two records with the specified ISBNs in the list. ");
						else
							System.out.println("Records with ISBN " + isbn1 + " and " + isbn2 + " were found and swapped. ");
					}
					//Displaying the updated contents of bkList
					System.out.println("Here are the current contents of the list of books");
					System.out.println("=============================================================");
					bkList.displayContent();
				}
					
				//Warning the user that one of their ISBN inputs was invalid
				catch (NumberFormatException e) {
					System.out.println("Invalid ISBN input! Please try again. ");
				}
			}
			//Option 7 commits (writes) the the contents of bkList to Update_Books, then displays the contents
			if (option.equals("7")) {
				System.out.println("Committing! The list of books was written to Update_Books.txt");
				bkList.commit();
				System.out.println("Here are the current contents of the list of books");
				System.out.println("=============================================================");
				bkList.displayContent();
			}
			//Option 8 simply exits the program
			if (option.equals("8")) {
				kb.close();
				kbOpt3.close();
				kbOpt4.close();
				kbOpt5.close();
				kbOpt6.close();
				System.out.println("Thanks for using me! Exiting Program");
				System.exit(0);
				
			}
		}
	}

}
