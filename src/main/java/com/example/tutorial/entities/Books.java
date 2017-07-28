/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.entities;

/**
 * Interface implement by MyLibBooks. MyLibBooks is entity of table myLibBooks in
 * database. Use for save information about book.
 *
 * @author roma
 */
public interface Books {

	public Long getYear();

	public void setYear(Long year);

	/**
	 * Get book id
	 *
	 * @return book id
	 */
	public Long getId();

	/**
	 * Set book id
	 *
	 * @param id book id
	 */
	public void setId(Long id);

	/**
	 * Get author id
	 *
	 * @return author id
	 */
	public Long getId_author();

	/**
	 * Set author id
	 *
	 * @param id_author author id
	 */
	public void setId_author(Long id_author);

	public String getTitle();

	public void setTitle(String title);

	/**
	 * Get book description.
	 *
	 * @return
	 */
	public String getDescription();

	/**
	 * Set book description. Can use html tag in text of description.
	 *
	 * @param description
	 */
	public void setDescription(String description);

	/**
	 * Get authors full name. This function should annotate as Transient. Use
	 * only in Tapestry page.
	 *
	 * @return
	 */
	public String getAuthors_full_name();

	/**
	 * Get authors description. This function should annotate as Transient. Use
	 * only in Tapestry page.
	 *
	 * @return
	 */
	public String getAuthors_Description();

	/**
	 * Get authors id. This function should annotate as Transient. Use only in
	 * Tapestry page.
	 *
	 * @return
	 */
	public Long getAuthors_id();

}
