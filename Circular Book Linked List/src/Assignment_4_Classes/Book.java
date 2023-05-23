/**
 * Sevag Merdkhanian 40247912, Alec Kirakossian 40244852
 * COMP249
 * Assignment 4
 * Monday, April 17, 2023
 */
package Assignment_4_Classes;

import java.io.Serializable;

/**
 * Book Class
 * @author Sevag and Alec
 *
 */
public class Book implements Serializable{
	private String title;
	private String author;
	private double price;
	private long ISBN;
	private String genre;
	private int year;
	/**
	 * Default Constructor for Book
	 */
	public Book() {
		
	}
	/**
	 * Parameterized Constructor for Book
	 * @param title of type String
	 * @param author of type String
	 * @param price of type double
	 * @param ISBN of type long
	 * @param genre of type String
	 * @param year of type int
	 */
	public Book(String title, String author, double price, long ISBN, String genre, int year) {
		this.title = title;
		this.author = author;
		this.price = price;
		this.ISBN = ISBN;
		this.genre = genre;
		this.year = year;
	}
	/**
	 * Copy constructor for Book
	 * @param aBook another Book object
	 */
	public Book(Book aBook) {
		this.title = aBook.title;
		this.author = aBook.author;
		this.price = aBook.price;
		this.ISBN = aBook.ISBN;
		this.genre = aBook.genre;
		this.year = aBook.year;
	}
	/**
	 * clone Method for Book
	 */
	public Book clone() {
		return new Book(this);
	}
	/**
	 * Accessor Method for title
	 * @return title Title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Mutator Method for title
	 * @param title Title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Accessor Method for author
	 * @return author Author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * Mutator Method for author
	 * @param author Author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * Accessor Method for price
	 * @return price Price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * Mutator Method for price
	 * @param price Price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * Accessor Method for ISBN
	 * @return ISBN isbn
	 */
	public long getIsbn() {
		return ISBN;
	}
	/**
	 * Mutator Method for ISBN
	 * @param isbn ISBN
	 */
	public void setIsbn(long isbn) {
		this.ISBN = isbn;
	}
	/**
	 * Accessor Method for genre
	 * @return genre Genre
	 */
	public String getGenre() {
		return genre;
	}
	/**
	 * Mutator Method for genre
	 * @param genre Genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	/**
	 * Accessor Method for year
	 * @return year Year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * Accessor Method for year
	 * @param year Year
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * Equals Method for Book
	 */
	public boolean equals(Object other) {
		if (this == null || other==null || getClass() != other.getClass())
			return false;
		Book b = (Book) other;
		return (title.equals(b.title) && author.equals(b.author) && price == b.price && 
				ISBN == b.ISBN && genre.equals(b.genre) && year == b.year);
		
	}
	/**
	 * toString Method for Book
	 */
	public String toString() {
		return (title + ", " + author + ", " + price + ", " + ISBN + ", " + year);
	}	
}
