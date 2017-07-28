/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.example.tutorial.annotation.ProtectedPage;
import com.example.tutorial.commons.IIntermediatePage;
import com.example.tutorial.pages.LogIn;
import com.example.tutorial.pages.PageDenied;
import com.example.tutorial.state.theapp.Visit;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.EmptyEventContext;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A service that protects pages annotated with
 * {@link jumpstart.web.annotation.ProtectedPage}. It examines each
 * {@link org.apache.tapestry5.services.Request} and redirects it to the login
 * page if the request is for a ProtectedPage and the user is not logged in. If
 * the page also has the {@link javax.annotation.security.RolesAllowed}
 * annotation then the user must belong to one of the listed roles.
 * <p>
 * To use this filter, contribute it to Tapestry's ComponentRequestHandler
 * service as we do in AppModule.
 *
 */
public class PageProtectionFilter implements ComponentRequestFilter {

	private static final String COMPONENT_PARAM_PREFIX = "t:";

	private final String autoLogInStr = System.getProperty("jumpstart.auto-login");

	private enum AuthCheckResult {
		AUTHENTICATE, AUTHORISED, DENY;
	}

	private final PageRenderLinkSource pageRenderLinkSource;
	private final ComponentSource componentSource;
	private final Request request;
	private final Response response;
	private ApplicationStateManager sessionStateManager;
	private final Logger logger;

	/**
	 * Receive all the services needed as constructor arguments. When we bind
	 * this service, T5 IoC will provide all the services.
	 */
	public PageProtectionFilter(PageRenderLinkSource pageRenderLinkSource, ComponentSource componentSource,
			Request request, Response response, ApplicationStateManager asm, Logger logger) {
		this.pageRenderLinkSource = pageRenderLinkSource;
		this.request = request;
		this.response = response;
		this.componentSource = componentSource;
		this.sessionStateManager = asm;
		this.logger = logger;
	}

	/**
	 * Use when page activate or rendering.
	 * @param parameters page name and activate parameters
	 * @param handler link to next page
	 * @throws IOException 
	 */
	@Override
	public void handlePageRender(PageRenderRequestParameters parameters, ComponentRequestHandler handler)
			throws IOException {

		AuthCheckResult result = checkAuthorityToPage(parameters.getLogicalPageName());

		if (result == AuthCheckResult.AUTHORISED) {
			handler.handlePageRender(parameters);
		} else if (result == AuthCheckResult.DENY) {

			// Redirect to the Denied page.
			Link pageProtectedLink = pageRenderLinkSource.createPageRenderLinkWithContext(PageDenied.class,
					parameters.getLogicalPageName());
			response.sendRedirect(pageProtectedLink);
		} else if (result == AuthCheckResult.AUTHENTICATE) {

			// Redirect to the LogIn page, with memory of the request.
			Link requestedPageLink = createLinkToRequestedPage(parameters.getLogicalPageName(),
					parameters.getActivationContext());
			Link logInPageLink = createLogInPageLinkWithMemory(requestedPageLink);

			response.sendRedirect(logInPageLink);
		} else {
			throw new IllegalStateException(result.toString());
		}

	}

	/**
	 * Use when component activate
	 * @param parameters
	 * @param handler
	 * @throws IOException 
	 */
	@Override
	public void handleComponentEvent(ComponentEventRequestParameters parameters, ComponentRequestHandler handler)
			throws IOException {

		AuthCheckResult result = checkAuthorityToPage(parameters.getActivePageName());

		if (result == AuthCheckResult.AUTHORISED) {
			handler.handleComponentEvent(parameters);
		} else if (result == AuthCheckResult.DENY) {

			// Redirect to the Denied page.
			Link pageProtectedLink = pageRenderLinkSource.createPageRenderLinkWithContext(PageDenied.class,
					parameters.getActivePageName());
			response.sendRedirect(pageProtectedLink);
		} else if (result == AuthCheckResult.AUTHENTICATE) {

			// If AJAX request, return an AJAX response that reloads the page.
			if (request.isXHR()) {
				Link requestedPageLink = createLinkToRequestedPage(parameters.getActivePageName(),
						parameters.getPageActivationContext());
				OutputStream os = response.getOutputStream("application/json;charset=UTF-8");
				// TODO - Using new, longer JSON string until this is fixed:
				// http://apache-tapestry-mailing-list-archives.1045711.n5.nabble.com/T5-4-cannot-redirect-on-session-timeout-tt5724697.html.
				// os.write(("{\"redirectURL\":\"" + requestedPageLink.toAbsoluteURI() + "\"}").getBytes());
				os.write(("{\"_tapestry\" : " + "{\"redirectURL\" : \"" + requestedPageLink.toAbsoluteURI() + "\"}" + " }")
						.getBytes());
				os.close();
				return;
			} // Else, redirect to the LogIn page, with memory of the request.
			else {
				Link requestedPageLink = createLinkToRequestedPage(parameters.getActivePageName(),
						parameters.getPageActivationContext());
				Link logInPageLink = createLogInPageLinkWithMemory(requestedPageLink);

				response.sendRedirect(logInPageLink);
			}
		} else {
			throw new IllegalStateException(result.toString());
		}

	}

	/**
	 * 
	 * @param requestedPageName
	 * @return authentificate result
	 * @throws IOException 
	 */
	public AuthCheckResult checkAuthorityToPage(String requestedPageName) throws IOException {

		Component page = componentSource.getPage(requestedPageName);

		// Does the page have security annotations @ProtectedPage or @RolesAllowed?
		boolean protectedPage = page.getClass().getAnnotation(ProtectedPage.class) != null;
		RolesAllowed rolesAllowed = page.getClass().getAnnotation(RolesAllowed.class);

		// If the security annotations on the page conflict in meaning, then error
		if (!protectedPage && rolesAllowed != null) {
			throw new IllegalStateException("Page \"" + requestedPageName
					+ "\" is annotated with @RolesAllowed but not @ProtectedPage.");
		} else if (protectedPage && rolesAllowed == null) {
			// throw new IllegalStateException("Page \"" + requestedPageName
			// + "\" is annotated with @ProtectedPage but not @RolesAllowed.");
		}

		// If page is public (ie. not protected), then everyone is authorised to it so allow access
		if (!protectedPage) {
			return AuthCheckResult.AUTHORISED;
		}

		// If user has not been authenticated, disallow.
		if (!isAuthenticated()) {
			return AuthCheckResult.AUTHENTICATE;
		}

		// If user is authorised to the page, then all is well.
		if (isAuthorised(rolesAllowed)) {
			return AuthCheckResult.AUTHORISED;
		}

		// Fell through, so not authorised.
		return AuthCheckResult.DENY;

	}

	
	/**
	 * Create link to page
	 * @param requestedPageName
	 * @param eventContext
	 * @return link to page
	 */
	private Link createLinkToRequestedPage(String requestedPageName, EventContext eventContext) {

		// Create a link to the page you wanted.
		Link linkToRequestedPage;

		if (eventContext instanceof EmptyEventContext) {
			linkToRequestedPage = pageRenderLinkSource.createPageRenderLink(requestedPageName);
		} else {
			Object[] args = new String[eventContext.getCount()];
			for (int i = 0; i < eventContext.getCount(); i++) {
				args[i] = eventContext.get(String.class, i);
			}
			linkToRequestedPage = pageRenderLinkSource.createPageRenderLinkWithContext(requestedPageName, args);
		}

		// Add any activation request parameters (AKA query parameters).
		List<String> parameterNames = request.getParameterNames();

		for (String parameterName : parameterNames) {
			linkToRequestedPage.removeParameter(parameterName);
			if (!parameterName.startsWith(COMPONENT_PARAM_PREFIX)) {
				linkToRequestedPage.addParameter(parameterName, request.getParameter(parameterName));
			}
		}

		return linkToRequestedPage;
	}

	private boolean isAuthenticated() throws IOException {

		// TODO - is this test unnecessary?
		if (request.getSession(false) == null) {
			return false;
		}

		// If a Visit already exists in the session then you have already been authenticated
		if (sessionStateManager.exists(Visit.class)) {
			return true;
		} // Else if "auto-login" is on, try auto-logging in.
		// - this facility is for development environment only. It avoids getting you thrown out of the
		// app every time the session clears eg. when app is restarted.
		else if (isAutoLogInOn()) {
//                autoLogIn(1L);
			return true;
		}

		return false;
	}


	private boolean isAuthorised(RolesAllowed rolesAllowed) throws IOException {
		boolean authorised = false;

		if (rolesAllowed == null) {
			authorised = true;
		} else {
			String [] rol = rolesAllowed.value();
			if(sessionStateManager.get(Visit.class).getMyRole().equals("admin")) {
				authorised = true;
				
			}

			// Here we could check whether the user's role, or perhaps roles, include one of the rolesAllowed.
			// Typically we'd cache the user's roles in the Visit.
		}

		return authorised;
	}

	/**
	 * Checks the value of system property jumpstart.auto-login. If "true" then
	 * returns true; if "false" then return false; if not set then returns
	 * false.
	 */
	private boolean isAutoLogInOn() {
		boolean autoLogIn = false;
		if (autoLogInStr == null) {
			autoLogIn = false;
		} else if (autoLogInStr.equalsIgnoreCase("true")) {
			autoLogIn = true;
		} else if (autoLogInStr.equalsIgnoreCase("false")) {
			autoLogIn = false;
		} else {
			throw new IllegalStateException(
					"System property jumpstart.auto-login has been set to \""
					+ autoLogInStr
					+ "\".  Please set it to \"true\" or \"false\".  If not specified at all then it will default to \"false\".");
		}
		return autoLogIn;
	}

	private Link createLogInPageLinkWithMemory(Link requestedPageLink) {

		IIntermediatePage logInPage = (IIntermediatePage) componentSource.getPage(LogIn.class);
		logInPage.setNextPageLink(requestedPageLink);
		Link logInPageLink = pageRenderLinkSource.createPageRenderLink(LogIn.class);

		return logInPageLink;
	}

}
