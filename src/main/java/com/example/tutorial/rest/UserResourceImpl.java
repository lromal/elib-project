/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.rest;

import com.example.tutorial.domain.security.User;
import com.example.tutorial.domain.security.sevice.UserService;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author kirito
 */
public class UserResourceImpl implements UserResource{
	@Inject
	@Autowired
	private UserService userService;

	
	private List<User> users;

	
	@Override
	public List<User> getAllUsers() {
		users = userService.getAllUser();
		return users;
	}
}
