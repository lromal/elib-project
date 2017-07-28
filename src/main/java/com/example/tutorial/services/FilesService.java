/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.services;

import com.example.tutorial.entities.Files;
import com.example.tutorial.entities.MyLibFiles;
import java.util.List;

/**
 * Service for interface Files which implement by MyLibFiles. MyLibFiles is entyty
 * table in database. It might be use in case several tables of files in
 * database, after some correct.
 * 
 * @author roma
 */
public interface FilesService {

	/**
	 * Find files by book id.
	 *
	 * @param searchStr
	 * @return
	 */
	public List<Files> getSeveralMyLibFilesById(Long searchStr);

	/**
	 * Find files by book id.
	 *
	 * @param searchStr
	 * @return
	 */
	public List<Files> getSeveralFilesImplById(Long searchStr);

	/**
	 * Save file in database.
	 *
	 * @param files
	 */
	void addFiles(MyLibFiles files);

	/**
	 * Delete file in database.
	 *
	 * @param booksId
	 */
	void deleteMyLibFiles(Long booksId);

}
