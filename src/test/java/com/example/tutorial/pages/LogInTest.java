package com.example.tutorial.pages;

import static org.junit.Assert.*;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.test.PageTester;
import org.junit.Test;

/**
 * LogIn page test. Port 8080 should be free.
 *
 * @author roma
 */
public class LogInTest {

	private final String APP_PACKAGE = "com.example.tutorial";
	private final String APP_NAME = "test";
	private final String CONTEXT = "/src/main/webapp/";

	/**
	 * Test render page LogIn
	 */
	@Test
	public void testRenderPage() {
		PageTester tester = new PageTester(APP_PACKAGE, APP_NAME, CONTEXT);

		Document doc = tester.renderPage("LogIn");

		String markup = doc.toString();
		String expectedWelcome = "Авторизация";
		assertTrue(markup.contains(expectedWelcome));
	}

}
