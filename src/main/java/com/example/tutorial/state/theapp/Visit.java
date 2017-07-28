/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.state.theapp;


import java.io.Serializable;

import com.example.tutorial.domain.security.User;


//TODO: move to other package, for example com.example.tutorial.domain.security
/**
 * Use for authentificate user, store information about user accaunt.
 * @author roma
 */
@SuppressWarnings("serial")
public class Visit implements Serializable {

    private Long myUserId = null;
	/**
	 * User name (login)
	 */
    private String myLoginId = null;
    private String role = null;
    
    public Visit(User user) {
        myUserId = user.getUserId();
        cacheUsefulStuff(user);
    }

    public void noteChanges(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        else if (user.getUserId().equals(myUserId)) {
            cacheUsefulStuff(user);
        }
    }

	/**
	 * Set parameters myLoginId and role.
	 * @param user 
	 */
    private void cacheUsefulStuff(User user) {
        myLoginId = user.getLoginId();
        role = user.getRole();
    }

    public Long getMyUserId() {
        return myUserId;
    }
	
    public String getMyRole() {
        return role;
    }

	/**
	 * Get user name (login)
	 * @return 
	 */
    public String getMyLoginId() {
        return myLoginId;
    }


}
