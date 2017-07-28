/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.domain.security.sevice;

import com.example.tutorial.domain.security.User;
import java.util.List;

/**
 * Sevice for table User in database.
 *
 * @see com.example.tutorial.domain.security.dao.UserDao
 * @author roma
 */
public interface UserService {

	/**
	 * Find all users in database, without condition.
	 * 
	 * @return
	 */
	List<User> getAllUser();

	/**
	 * Find users in database, by login (user name) and password. Password 
	 * should encrypt and encrypt algorithm must be the same as in 
	 * com.example.tutorial.domain.security.User.setPassword.
	 * 
	 * @see com.example.tutorial.services.Encryption
	 * @param loginId user name
	 * @param password
	 * @return 
	 */
	User authenticateUser(String loginId, String password);

	/**
	 * Save user in database
	 * 
	 * @param user 
	 */
	void addUsers(User user);

	/**
	 * Delete user in database
	 * 
	 * @param userId 
	 */
	void deleteUser(Long userId);

	/**
	 * Update user in database
	 * 
	 * @param users 
	 */
	void updateUsers(User users);
}
