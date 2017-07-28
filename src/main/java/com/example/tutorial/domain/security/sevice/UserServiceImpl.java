/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.domain.security.sevice;

import com.example.tutorial.domain.security.User;
import com.example.tutorial.domain.security.dao.UserDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author roma
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public User authenticateUser(String loginId, String password) {
		return userDao.findUser(loginId, password);
	}

	@Override
	public List<User> getAllUser() {
		return userDao.findAllUser();
	}

	@Override
	public void addUsers(User users) {
		userDao.save(users);
	}

	@Override
	public void updateUsers(User users) {
		userDao.changeUser(users);
	}

	@Override
	public void deleteUser(Long userId) {
		userDao.delete(userId);
	}
}
