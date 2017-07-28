package com.example.tutorial.services;

import com.example.tutorial.entities.Books;
import com.example.tutorial.entities.MyLibBooks;
import java.util.List;

/**
 * Service for interface Books which implement by MyLibBooks. MyLibBooks is entyty
 * table in database. It might be use in case several tables of books in 
 * database, after some correct.
 * 
 * @author roma
 */
public interface BooksService {

	/**
	 * Find books by authors full name.
	 *
	 * @param searchStr
	 * @return
	 */
	List<Books> getBooksImplByAuthors(String searchStr);

	/**
	 * Find books by title.
	 *
	 * @param searchStr
	 * @return
	 */
	List<Books> getBooksImplByTitle(String searchStr);

	/**
	 * Find books by authors full name.
	 *
	 * @param searchStr authors full name
	 * @return
	 */
	public List<Books> getMyLibBooksByAuthors(String searchStr);

	/**
	 * Find books by title.
	 *
	 * @param searchStr book title
	 * @return
	 */
	public List<Books> getMyLibBooksByTitle(String searchStr);

	/**
	 * Save book in database.
	 *
	 * @param books
	 */
	void addBooks(MyLibBooks books);

	/**
	 * Find all books in database, without condition.
	 *
	 * @return
	 */
	List<Books> getAllMyLibBooks();

	/**
	 * Delete book in database.
	 *
	 * @param booksId book id
	 */
	void deleteMyLibBooks(Long booksId);

	/**
	 * Update books in database.
	 *
	 * @param books
	 */
	void updateBooks(MyLibBooks books);
}
