package com.example.tutorial.pages;

import com.example.tutorial.annotation.ProtectedPage;
import com.example.tutorial.entities.Books;
import com.example.tutorial.services.BooksService;
import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Start page of application.
 * 
 * @author roma
 */
@Import(stylesheet = {"jquerydatatables.css"})
@ProtectedPage
public class Index {



	@Inject
	private JavaScriptSupport js;


	/**
	 * Run javascript functions for change icon homeLink (on Layout component)
	 * style.
	 */
	private void afterRender() {
		js.require("styleIcon").with("homeLink");

	}

}
