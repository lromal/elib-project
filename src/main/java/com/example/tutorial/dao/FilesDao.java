package com.example.tutorial.dao;

import com.example.tutorial.entities.Files;
import com.example.tutorial.entities.MyLibFiles;
import java.util.List;

/**
 * Dao for interface Files which implement by MyLibFiles. MyLibFiles is entyty
 * table in database. It might be use in case several tables of files in
 * database, after some correct.
 *
 * @author roma
 */
public interface FilesDao {

	/**
	 * Find files by book id.
	 *
	 * @param id_book book id
	 * @return files
	 */
	List<Files> findSeveralMyLibFilesById(Long id_book);

	/**
	 * 
	 * @param id_book book id
	 * @return
	 */
	List<Files> findSeveralFilesImplById(Long id_book);

	/**
	 * Save file in database.
	 * @param files
	 */
	void save(MyLibFiles files);

	/**
	 * Delete file in database. 
	 * @param booksId book id
	 */
	void delete(Long booksId);

}
