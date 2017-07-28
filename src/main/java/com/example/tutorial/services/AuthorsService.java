/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.services;

import com.example.tutorial.entities.MyLibAuthors;

/**
 * Service for table MyLibAuthors in database. It might be use in case several
 * tables of authors in database, after some correct.
 * @author roma
 */
public interface AuthorsService {

	/**
	 * Save authors in database. For table MyLibAuthors.
	 *
	 * @param authors
	 */
	void addAuthors(MyLibAuthors authors);

	/**
	 * Delete authors in database. For table MyLibAuthors.
	 *
	 * @param authorsId
	 */
	void deleteMyLibAuthors(Long authorsId);

	/**
	 * Update authors in database. For table MyLibAuthors.
	 *
	 * @param authors
	 */
	void updateMyLibAuthors(MyLibAuthors authors);
}
