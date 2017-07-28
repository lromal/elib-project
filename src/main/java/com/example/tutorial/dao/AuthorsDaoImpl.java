package com.example.tutorial.dao;

import org.springframework.stereotype.Repository;

import com.example.tutorial.entities.MyLibAuthors;
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
public class AuthorsDaoImpl implements AuthorsDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void save(MyLibAuthors authors) {
		em.persist(authors);
	}

	@Transactional
	@Override
	public void delete(Long authorsId) {
		MyLibAuthors authors;
		authors = em.find(MyLibAuthors.class, authorsId);

		em.remove(authors);
	}

	@Transactional
	@Override
	public void changeMyLibAuthors(MyLibAuthors authors) {
		em.merge(authors);
	}

}
