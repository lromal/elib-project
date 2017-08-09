package com.example.tutorial.domain.security.dao;

import com.example.tutorial.domain.security.User;
import java.util.List;

/**
 * Dao for table User in database.
 *
 * @author roma
 */
public interface UserDao {

	/**
	 * Find all users in database, without condition.
	 *
	 * @return
	 */
	List<User> findAllUser();

	/**
	 * Find users in database, by login (user name) and password. Password 
	 * should encrypt and encrypt algorithm must be the same as in 
	 * com.example.tutorial.domain.security.User.setPassword.
	 * 
	 * @see com.example.tutorial.services.Encryption
	 * @param loginId
	 * @param password
	 * @return 
	 */
	User findUser(String loginId, String password);
	
	//TODO add documentation
	public User findUserById(Long id);

	/**
	 * Save user in database
	 * @param users 
	 */
	void save(User users);

	/**
	 * Delete user in database
	 * @param userId 
	 */
	void delete(Long userId);

	/**
	 * Update user in database
	 * @param user 
	 */
	void changeUser(User user);
}
