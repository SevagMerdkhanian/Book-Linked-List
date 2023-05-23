/**
 * Sevag Merdkhanian 40247912, Alec Kirakossian 40244852
 * COMP249
 * Assignment 4
 * Monday, April 17, 2023
 */
package Assignment_4_Classes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
/**
 * BookList is a circular Linked List Class that has one head attribute 
 * Circular means that the last item in the list is linked to the first item in the list
 * @author Sevag and Alec
 *
 */
public class BookList {
	/**
	 * Private Inner Node Class with a Book b attribute and a Node next attribute
	 * @author Sevag Merdkhanian
	 *
	 */
	private class Node {
		private Book b;
		private Node next;
		/**
		 * Default Constructor for Node
		 */
		public Node() {
			b = null;
			next = null;
		}
		/**
		 * Paramaterized Constructor for Node
		 * @param b Book object
		 * @param next Node object
		 */
		public Node(Book b, Node next) {
			this.b = b;
			this.next = next;
		}
		/**
		 * Copy Constructor for Node
		 * @param n
		 */
		public Node(Node n) {
			b = n.b.clone();
			next = n.next;
		}
		/**
		 * Accessor for Book b
		 * @return b
		 */
		public Book getB() {
			return b.clone();
		}
		/**
		 * Mutator for Book b
		 * @param b
		 */
		public void setB(Book b) {
			this.b = b;
		}
		/**
		 * Accessor for Node next
		 * @return next
		 */
		public Node getNext() {
			return next;
		}
		/**
		 * Mutator for Node next
		 * @param next
		 */
		public void setNext(Node next) {
			this.next = next;
		}
	}
	private Node head;
	/**
	 * Default Constructof for BookList
	 */
	public BookList() {
		head = null;
	}
	/**
	 * Paramaterized Constructor for Node sets head to null anyway
	 * @param n
	 */
	public BookList(Node n) {
		head = null;
	}
	/**
	 * addToStart method adds a Book object to the start of the list
	 * @param b
	 */
	public void addToStart(Book b) {
	    Node newNode = new Node(b, null);
		//If the list is empty, simply set head to be newNode and set the next reference of newNode to itself
	    if (isEmpty()) {
	    	head = newNode;
	        newNode.setNext(newNode);
	        
	    }
	    else {
	    	//If the list is not empty, find the last node
	    	Node lastNode = head;
	        while (lastNode.getNext() != head) 
	            lastNode = lastNode.getNext();
	        //Set the next reference of newNode to head, set the head to newNode and set the next reference of lastNode to the new head node
	        newNode.setNext(head);
	        head = newNode;
	        lastNode.setNext(head);
	    }
	}
	/**
	 * addToEnd method adds a Book object to the end of the list
	 * @param b
	 */
	public void addToEnd(Book b) {
		Node newNode = new Node(b, null);
		//If the list is empty, simply set head to be newNode and set the next reference of newNode to itself
		if (isEmpty()) {
			head = newNode;
			newNode.setNext(newNode);
		}
		//If the list is empty, find the last node
		else {
			Node lastNode = head;
			while (lastNode.getNext() != head) {
				lastNode = lastNode.getNext();
			}
			//Set lastNode's next reference to newNode and set newNode's next reference to head
			lastNode.setNext(newNode);
			newNode.setNext(head);
		}
	}
	/**
	 * storeRecordByYear method stores the records of a given year in a file with the name of year
	 * @param yr
	 */
	public void storeRecordByYear(int yr) {
		PrintWriter pw = null;
		String fileName = yr + ".txt";
		Node presentNode = head;
		boolean yearMatch = false;
		/*Checking if there even is a record that matches the given year by looping through each 
		 * record until a record with the given year is found or when it has looped through each item in the list
		 */
		if (!isEmpty()) {
			do {
				if (presentNode.getB().getYear() == yr) {
					yearMatch = true;
					break;
				}
				presentNode = presentNode.getNext();
				} while (presentNode != head);
		}
		presentNode = head;
		//Only creating the file if there is a record that matches the given year
		if (yearMatch) {
			try {
				pw = new PrintWriter(new FileOutputStream(fileName));
				//Looping through each record and printing records with the given year to the year.txt file
				do {
					if (presentNode.getB().getYear() == yr) 
						pw.println(presentNode.getB());
					presentNode = presentNode.getNext();
					} while (presentNode != head);
				pw.close();
				
			}
			
			catch (FileNotFoundException e) {
				System.out.println("File was not found");
			}
			System.out.println("Stored the records of the specified year. ");
		}
		//If there was no record with that year (or if the list was empty), simply printing this message
		else 
			System.out.println("There is no record with that year!");
	}
	/**
	 * insertBefore Method inserts a Book before a record with a given ISBN
	 * @param isbn the given ISBN
	 * @param b Book object
	 * @return true if there is the specified ISBN in the list, false otherwise
	 */
	public boolean insertBefore(long isbn, Book b) {
		//If the list is empty, simply returning false
	    if (isEmpty())
	        return false;
	    //If the ISBN of the head matches the given ISBN, just adding Book b to the start of the list and returning true
	    if (head.getB().getIsbn() == isbn) {
	        addToStart(b);
	        return true;
	    }
	    /*Looping through each record in the list, checking if the record matches the given ISBN.
	     * If it does, creating newNode to be a node who's next reference is presentNode, 
	     * then setting previousNode's next reference to be newNode, effectively inserting newNode
	     * right before presentNode. Then returing true 
	     */
	    Node previousNode = head;
	    Node presentNode = head.getNext();
	    do {
	    	if (presentNode.getB().getIsbn() == isbn) {
	            Node newNode = new Node(b, presentNode);
	            previousNode.setNext(newNode);
	            return true;
	    	}
	            previousNode = presentNode;
		        presentNode = presentNode.getNext();
		        
	    	} while (presentNode != head);
	    //If no record with the given ISBN is found, returning false
	    return false;
	}
	/**
	 * insertBetween Method inserts a Book between two given consecutive ISBNs 
	 * @param isbn1 the first given ISBN
	 * @param isbn2 the second given ISBN
	 * @param b Book object
	 * @returns true if there are the specified consecutive ISBNs in the list, false otherwise
	 */
	public boolean insertBetween(long isbn1, long isbn2, Book b) {
		//If the list is empty, simply returning false
		if (isEmpty())
			return false;
		/*Looping through each record in the list, checking if consecutive records matche the given ISBNs.
	     * If they do, inserting Book b before isbn2
	     */
		Node previousNode = head;
		Node presentNode = head.getNext();
		while (presentNode.getNext() != head) {
			if(previousNode.getB().getIsbn() == isbn1 && presentNode.getB().getIsbn() == isbn2) {
	            insertBefore(isbn2,b);
	            return true;
			}
			previousNode = presentNode;
			presentNode = presentNode.getNext();
		}
		/*At this point, presentNode is the last node. Since the last node and the head are technically
		 * two consecutive nodes, also checking if those two contain the two given consecutive ISBNs. 
		 * If they do, simply adding the given Book object to the start of the list and returning true
		 */
		if (presentNode.getB().getIsbn() == isbn1 && head.getB().getIsbn() == isbn2) {
            addToStart(b);
            return true;
	    }
		//If the two given consecutive ISBNs were not found, returning false
		return false;
	}
	/**
	 * displayContent Method just displays the content of the list in the specified format
	 */
	public void displayContent() {
		Node presentNode = head;
		//Looping through each each record in the list, printing the contents of the book for each record
		do {
			System.out.println(presentNode.getB() + " ==> ");
			presentNode = presentNode.getNext();
		} while (presentNode != head);
		System.out.println("===> head");			
	}
	/**
	 * delConsecutiveRepeatedRecords deletes consecutive records in the list
	 * @return true if there are consecutive repeated records in the list, false otherwise
	 */
	public boolean delConsecutiveRepeatedRecords() {
		//If the list is empty, simply returning false
		if (isEmpty())
	        return false;
		Node previousNode = head;
		Node presentNode = head.getNext();
		boolean removed = false;
		/**Looping through each record in the list, checking if it is equal to the record before it
		 * If it is, setting previousNode's next reference to the node after presentNode, then setting
		 * presentNode to its next reference, effectively deleting the second of the consecutive records.
		 * Then, setting removed to true;
		 */
		while (presentNode != head) {
			if(previousNode.getB().equals(presentNode.getB())) {
	            previousNode.setNext(presentNode.getNext());
				presentNode = presentNode.getNext();
				removed = true;
			}
			else {
				previousNode = presentNode;
				presentNode = presentNode.getNext();
				
			}
		}
		//This deletes consecutive records if the consecutive records are in the head node and the last node
		 if (head.getB().equals(previousNode.getB())) {
	            previousNode.setNext(head.getNext());
		        head = head.getNext();
		        removed = true;
		    }
		//Returning true if there were consecutive repeated records in the list, false otherwise
		return removed;
	}
	/**
	 * extractAuthList returns the list of all the Books from a specified author
	 * @param aut the author
	 * @return the list of Books from that author
	 */
	public BookList extractAuthList(String aut) {
		//New BookList object that will be returned
		BookList authList = new BookList();
		//If the original BookList is empty, then authList will also be empty
		if (isEmpty()) 
			return authList;
		Node presentNode = head;
		/**Looping through each record in the list, checking if the author of the record
		 * matches the specified author, and adding it to the end of authList if it does 
		 */
		do {
			if(presentNode.getB().getAuthor().equals(aut)) {
				Book b = presentNode.getB().clone();
				authList.addToEnd(b);
			}
			presentNode = presentNode.getNext();
		} while (presentNode != head);
		//returning authList
		return authList;
	}
	/**
	 * swap Method swaps two records with the given ISBNs
	 * @param isbn1 the first specified ISBN
	 * @param isbn2 the second specified ISBN
	 * @return true if there were two records with the given ISBNs in the list, false otherwise
	 */
	public boolean swap(long isbn1, long isbn2) {
		//If the list is empty, simply returning false
	    if (isEmpty()) 
	        return false;
	    Node node1 = null;
	    Node node2 = null;
	    Node presentNode = head;
	    /**Looping through each record, checking if the record's ISBN is that of either of
	     * the specified ISBNs until either both specified ISBNs are found or when each item
	     * in the list has been checked
	     */
	    do {
	    	//Setting node1 to presentNode if its ISBN is isbn1
	        if (presentNode.getB().getIsbn() == isbn1) {
	            node1 = presentNode;
		    //Setting node2 to presentNode if its ISBN is isbn2
	        } else if (presentNode.getB().getIsbn() == isbn2) {
	            node2 = presentNode;
	        }
	        presentNode = presentNode.getNext();
	    } while (presentNode != head && (node1 == null || node2 == null));
	    //If either of the ISBNs is not found, returning false
	    if (node1 == null || node2 == null) 
	        return false;
	    /*Setting a temp Book object to a clone of node1's Book object, setting node1's Book object
	     * to a clone of node2's Book object, then setting node2's Book object to temp, effectively 
	     * swapping the records. Then returning true
	     */
	    Book temp = node1.getB();
	    node1.setB(node2.getB());
	    node2.setB(temp);
	    return true;
	}
	/**
	 * Commit Method prints the records in the current state into Update_Books.txt
	 */
	public void commit() {
		PrintWriter pw = null;
		try {
			//Only creating the file if the list is not empty
			if (!isEmpty()) {
				pw = new PrintWriter(new FileOutputStream("Update_Books.txt"));
				Node presentNode = head;
				//Looping through each record and printing it to the Update_Books file
				do {
					pw.println(presentNode.getB());
					presentNode = presentNode.getNext();
				} while (presentNode != head);
				pw.close();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File was not found");
		}
	}
	/**
	 * isEmpty() simply checks if the list is empty by checking if head (the first node) is null
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return (head == null);
	}
}
