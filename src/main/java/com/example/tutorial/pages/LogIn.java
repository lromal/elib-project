/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.pages;

import com.example.tutorial.domain.security.User;
import com.example.tutorial.commons.IIntermediatePage;
import com.example.tutorial.domain.security.sevice.UserService;
import com.example.tutorial.services.EncryptionInterf;
import com.example.tutorial.state.theapp.Visit;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Page for login user in application.
 * 
 * @author roma
 */
// To make this page accessible only by HTTPS, annotate it with @Secure and ensure your web server can deliver HTTPS.
// See http://tapestry.apache.org/secure.html .
@Secure
@Import(stylesheet = "login.css")
public class LogIn implements IIntermediatePage {
	// The activation context

	@Inject
	private UserService userService;

	@Property
	private String loginId;

	@Property
	private String password;

	// Generally useful bits and pieces
	/**
	 * Link to next page
	 */
	@Persist
	private Link nextPageLink;

	@InjectComponent("login")
	private Form form;

	@InjectComponent("loginId")
	private TextField loginIdField;

	@Inject
	private Logger logger;

	//TODO Write help about this 
	@Inject
	private ComponentResources componentResources;

	@InjectPage
	private Index userViewPage;

	/**
	 * For password encrypt
	 */
	@Inject
	@Autowired
	private EncryptionInterf encr;

	// The code
	/**
	 * Set next page link. For redirect to chosen page.
	 * 
	 * @param nextPageLink 
	 */
	@Override
	public void setNextPageLink(Link nextPageLink) {
		this.nextPageLink = nextPageLink;
	}

	/**
	 * Transfer data of user login between page reload. 
	 * @param loginId 
	 */
	void onActivate(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * Transfer data of user login between page reload. 
	 * @return 
	 */
	String onPassivate() {
		

		return loginId;
	}

	@Inject
	private JavaScriptSupport js;

	/**
	 * Authenticate user, if not succes show errow message.
	 */
	void onValidateFromLogin() {

		if (form.getHasErrors()) {
			return;
		}

		// Authenticate User
		User testUser = userService.authenticateUser(loginId, encr.encrypt(password));

		if (testUser != null) {

			setVisit(new Visit(testUser));

		} else {
			form.recordError(loginIdField, "error login or password");
		}

	}

	/**
	 * Redirect to chosen application page or index page (userViewPage).
	 * 
	 * @return 
	 */
	Object onSuccess() {

		if (nextPageLink == null) {
			return userViewPage;
		} else {
			componentResources.discardPersistentFieldChanges();
			return nextPageLink;
		}

	}


	@SessionState
	private Visit visit;

	protected void setVisit(Visit visit) {

		this.visit = visit;
	}

	public Visit getVisit() {
		return visit;
	}
}
