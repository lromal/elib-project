/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.entities;

/**
 * Interface implement by MyLibFiles. MyLibFiles is entity of table myLibFiles
 * in database. Use for save information of book files store on server.
 *
 * @author roma
 */
public interface Files {

	/**
	 * Get book id
	 *
	 * @return book id
	 */
	public Long getId_book();

	/**
	 * Set book id
	 *
	 * @param id_book book id
	 */
	public void setId_book(Long id_book);

	/**
	 * Get file name. File name is string with relative path to file and file
	 * name.
	 *
	 * @return
	 */
	public String getFile_name();

	/**
	 * Set file name. File name is string with relative path to file and file
	 * name.
	 *
	 * @param file_name
	 */
	public void setFile_name(String file_name);

}
