package com.example.tutorial.dao;

import org.springframework.stereotype.Repository;

import com.example.tutorial.entities.Files;
import com.example.tutorial.entities.MyLibFiles;
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
public class FilesDaoImpl implements FilesDao {


	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Files> findSeveralMyLibFilesById(Long id_book) {
		List<Files> retFiles = em.createQuery("select f "
				+ "from MyLibFiles f where f.id_book = :id_book")
				.setParameter("id_book", id_book).getResultList();
        return retFiles;
	}
	
	@Override
	public List<Files> findSeveralFilesImplById(Long id_book) {
		List<Files> retFiles = em.createQuery("select f "
				+ "from FilesImpl f where f.id_book = :id_book")
				.setParameter("id_book", id_book).getResultList();
        return retFiles;
	}
	
	@Transactional
	@Override
	public void save(MyLibFiles files) {
		em.persist(files);
	}


	@Transactional
	@Override
	public void delete(Long booksId) {
		MyLibFiles files;
		List<Files> retFiles = findSeveralMyLibFilesById(booksId);

		files = (MyLibFiles)retFiles.get(0);

		em.remove(files);
	}	
	
}