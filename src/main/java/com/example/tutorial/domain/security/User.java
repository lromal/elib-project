/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.domain.security;

import com.example.tutorial.services.View;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.codehaus.jackson.map.annotate.JsonView;

//TODO: move to other package, for example com.example.tutorial.domain.security.entities
/**
 * Entity of table users in database. Use for login user in application and
 * other security function.
 *
 * @author roma
 */
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private Long userId = null;

	@Column(name = "loginId")
	private String loginId = null;

	@Column(name = "password")
	@JsonView(View.Private.class)
	private String password = null;

	@Column(name = "role")
	private String role = null;

	@Column(name = "description")
	private String description = null;

	/**
	 * Get id user
	 *
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Set id user
	 *
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Get user name
	 *
	 * @return
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * Set user name
	 *
	 * @param loginId
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * Get password.
	 *
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password. Befor set - password encrypt.
	 *
	 * @see com.example.tutorial.services.Encryption
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get user role, now use two - "user" and "admin". "user" can view all
	 * pages besides Admin page
	 *
	 * @return user role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Set user role, now use two - "user" and "admin". "user" can view all
	 * pages besides Admin page
	 *
	 * @param role user role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Get user description.
	 *
	 * @return user role
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set user description.
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
