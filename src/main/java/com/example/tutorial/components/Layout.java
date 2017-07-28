package com.example.tutorial.components;

import com.example.tutorial.pages.Index;
import com.example.tutorial.state.theapp.Visit;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Layout component for pages of application.
 */
//@Import(stylesheet = {"context:resources/META-INF/assets/headerbackground.css"}, module = "bootstrap/collapse")
@Import(stylesheet = {"context:headerbackground.css"}, module = "bootstrap/collapse")

public class Layout {


	/**
	 * The page title, for the <title> element and the <h1> element.
	 */
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Property
	private String pageName;

	@Inject
	private JavaScriptSupport js;

	@Inject
	private ApplicationStateManager sessionStateManager;

	@Inject
	private Logger logger;

	/**
	 * show admin icon (link) if user role is admin
	 */
	private void afterRender() {
		if (sessionStateManager.get(Visit.class).getMyRole().equals("admin")) {
			js.require("showAdmin");

		}
	}

	@Inject
	private RequestGlobals requestGlobals;

	/**
	 * Action when click by icon (link) Logout. Logout from current user end 
	 * redirect to Index page
	 * @return redirect to Index page
	 */
	public Object onActionFromLogout() {
		requestGlobals.getRequest().getSession(false).invalidate();
		return Index.class;
	}

}
