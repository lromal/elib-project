package com.example.tutorial.dao;


import org.springframework.stereotype.Repository;

import com.example.tutorial.entities.Books;
import com.example.tutorial.entities.MyLibBooks;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bytesTree
 * @see <a href="http://www.bytestree.com/">BytesTree</a>
 *
 */
@Repository
public class BooksDaoImpl implements BooksDao {


	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Books> findBooksImplByAuthors(String searchStr) {
		List<Books> retBook = em.createQuery("select b "
				+ "from BooksImpl b inner join b.authors a where a.full_name like :full_name")
				.setParameter("full_name", "%" + searchStr + "%").setMaxResults(60)
				.getResultList();
		return retBook;

	}
	@Override
	public List<Books> findMyLibBooksByAuthors(String searchStr) {
		List<Books> retBook = em.createQuery("select b "
				+ "from MyLibBooks b inner join b.authors a where a.full_name like :full_name")
				.setParameter("full_name", "%" + searchStr + "%").setMaxResults(60)
				.getResultList();
		return retBook;

	}
	@Override
	public List<Books> findBookImplsByTitle(String searchStr) {
		List<Books> retBook = em.createQuery("select b "
				+ "from BooksImpl b inner join b.authors a where b.title like :title")
				.setParameter("title", "%" + searchStr + "%").setMaxResults(60)
				.getResultList();
		return retBook;

	}
	@Override
	public List<Books> findMyLibBookByTitle(String searchStr) {
		List<Books> retBook = em.createQuery("select b "
				+ "from MyLibBooks b inner join b.authors a where b.title like :title")
				.setParameter("title", "%" + searchStr + "%").setMaxResults(60)
				.getResultList();
		return retBook;

	}

	@Transactional
	@Override
	public void save(MyLibBooks books) {
		em.persist(books);
	}

	@Override
	public List<Books> findAllMyLibBook() {
		List<Books> retUser = em.createQuery("select b from MyLibBooks b inner join b.authors a").getResultList();
		return retUser;
	}

	@Transactional
	@Override
	public void delete(Long booksId) {
		MyLibBooks books;
		books = em.find(MyLibBooks.class, booksId);

		em.remove(books);
	}
	
	@Transactional
	@Override
	public void changeBooks(MyLibBooks books) {
		em.merge(books);
	}
}
