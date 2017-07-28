package com.example.tutorial.dao;

import com.example.tutorial.entities.Books;
import com.example.tutorial.entities.MyLibBooks;
import java.util.List;

/**
 * Dao for interface Books which implement by MyLibBooks. MyLibBooks is entyty
 * table in database. It might be use in case several tables of books in 
 * database, after some correct.
 *
 * @author roma
 */
public interface BooksDao {

	List<Books> findBooksImplByAuthors(String searchStr);

	List<Books> findBookImplsByTitle(String searchStr);

	/**
	 * Find books by authors name. 
	 *
	 * @param searchStr authors name
	 * @return books
	 */
	public List<Books> findMyLibBooksByAuthors(String searchStr);

	/**
	 * Find books by title. 
	 *
	 * @param searchStr title
	 * @return books
	 */
	public List<Books> findMyLibBookByTitle(String searchStr);

	/**
	 * Save book in database. 
	 *
	 * @param books
	 */
	public void save(MyLibBooks books);

	/**
	 * Find all books in database, without condition. 
	 *
	 * @return books
	 */
	List<Books> findAllMyLibBook();

	/**
	 * Delete book in database. 
	 *
	 * @param booksId id books
	 */
	void delete(Long booksId);

	/**
	 * Update books in database. 
	 *
	 * @param books
	 */
	void changeBooks(MyLibBooks books);
}
