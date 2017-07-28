/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity of table myLibAuthors in database. Use for save information about 
 * authors of book.
 *
 * @author roma
 */
@Entity
@Table(name = "myLibAuthors")
public class MyLibAuthors {

	private Long id;
	private String full_name;
	private String description;

	/**
	 * Get author id
	 * @return 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	/**
	 * Set author id
	 * @param id author id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get full name all authors of some book
	 * @return author id
	 */
	@Column(name = "full_name")
	public String getFull_name() {
		return full_name;
	}

	/**
	 * Set full name all authors of some book
	 * @param full_name 
	 */
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	/**
	 * Get authors description
	 * @return 
	 */
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	/**
	 * Set authors description
	 * @param description 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
