/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.dao;

import com.example.tutorial.entities.MyLibAuthors;

/**
 * Dao for table MyLibAuthors in database. It might be use in case several
 * tables of authors in database, after some correct.
 *
 * @author roma
 */
public interface AuthorsDao {

	/**
	 * Save authors in database. For table MyLibAuthors.
	 *
	 * @param authors object of MyLibAuthors
	 */
	void save(MyLibAuthors authors);

	/**
	 * Delete authors in database. For table MyLibAuthors.
	 *
	 * @param authorsId id authors
	 */
	void delete(Long authorsId);

	/**
	 * Update authors in database. For table MyLibAuthors.
	 *
	 * @param authors object of MyLibAuthors
	 */
	void changeMyLibAuthors(MyLibAuthors authors);
}
