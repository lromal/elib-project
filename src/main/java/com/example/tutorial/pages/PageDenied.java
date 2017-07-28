/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.pages;

import javax.servlet.http.HttpServletResponse;
import org.apache.tapestry5.annotations.Import;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;


/**
 * Intended for use with PageProtectionFilter, this displays the error message of 
 * the page to which you are not authorised.
 * 
 */
@Import(stylesheet = {"context:pageDenied.css"})
public class PageDenied {
	// Activation context

    /**
	 * Url denied page
	 */
	@Property
    private String urlDenied;

    // Other useful bits and pieces

    @Inject
    private Response response;

    // The code

	/**
	 * Transfer urlDenied between page reload
	 * @param urlDenied 
	 */
    void onActivate(String urlDenied) {
        this.urlDenied = urlDenied;
    }

	/**
	 * Transfer urlDenied between page reload
	 * 
	 * @return 
	 */
    String onPassivate() {
        return urlDenied;
    }

    public void setupRender() {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
