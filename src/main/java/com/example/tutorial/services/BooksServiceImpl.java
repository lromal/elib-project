package com.example.tutorial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tutorial.dao.BooksDao;
import com.example.tutorial.entities.Books;
import com.example.tutorial.entities.MyLibBooks;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;

/**
 * 
 * @author roma
 * 
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BooksServiceImpl implements BooksService {


	@Autowired
	BooksDao booksDao;


	@Override
	public List<Books> getBooksImplByAuthors(String searchStr) {
		return booksDao.findBooksImplByAuthors(searchStr);
	}
	@Override
	public List<Books> getMyLibBooksByAuthors(String searchStr) {
		return booksDao.findMyLibBooksByAuthors(searchStr);
	}
	@Override
	public List<Books> getBooksImplByTitle(String searchStr) {
		return booksDao.findBookImplsByTitle(searchStr);
	}
	@Override
	public List<Books> getMyLibBooksByTitle(String searchStr) {
		return booksDao.findMyLibBookByTitle(searchStr);
	}
	/**
	 * Add new employee
	 * 
	 * @param employee:
	 *            Employee to add
	 */
	@Override
	public void addBooks(MyLibBooks books) {
		booksDao.save(books);
	}
	
	@Override
	public List<Books> getAllMyLibBooks() {
		return booksDao.findAllMyLibBook();
	}
	
	@Override
	public void deleteMyLibBooks(Long booksId) {
		booksDao.delete(booksId);
	}
	
	@Override
	public void updateBooks(MyLibBooks books) {
		booksDao.changeBooks(books);
	}
}
