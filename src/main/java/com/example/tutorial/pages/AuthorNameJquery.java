/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.pages;

import com.example.tutorial.annotation.ProtectedPage;
import com.example.tutorial.entities.Books;
import com.example.tutorial.services.BooksService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.components.DataTable;
import org.got5.tapestry5.jquery.internal.TableInformation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Page of search books by author full name.
 *
 * @author roma
 */
@Import(stylesheet = {"jquerydatatables.css", "radiogroup.css"})
@ProtectedPage
public class AuthorNameJquery {

	private final Random random = new Random(System.nanoTime());

	@Inject
	@Autowired
	private BooksService booksService;

	/**
	 * Display search string as messege on page
	 */
	@Property
	private String searchStr;

	private Books fl;

	/**
	 * Store many books data for table t:type="loop"
	 */
	@SessionState
	@Property
	private List<Books> books;

	/**
	 * Store one book data for table t:type="loop"
	 */
	@Property
	private Books book;

	@Property
	private BeanModel<Books> myModel;

	@Inject
	private BeanModelSource beanModelSource;

	@Inject
	private Messages messages;

	@Inject
	private JavaScriptSupport js;



	/**
	 * Run javascript functions for change icon authorLink (on Layout component)
	 * style, add jQuery DataTable.
	 */
	private void afterRender() {
		js.require("addDataTable").with(1, "#loopjq");
		js.require("styleIcon").with("authorLink");

	}

	/**
	 * Search string with author full name
	 */
	@Property
	private String fullName;

	@InjectComponent("names")
	private Form form;

	@InjectComponent("fullName")
	private TextField fullNameField;

	/**
	 * Check non empty fullName field
	 */
	void onValidateFromNames() {

		// Note, this method is triggered even if server-side validation has already found error(s).
		if (fullName == null) {
			form.recordError(fullNameField, "Поле поиска не должно быть пустым.");
		}

	}

	/**
	 * Function for transfer data in this page when redirect to it
	 *
	 * @param searchStr
	 */
	public void set(String searchStr) {
		this.searchStr = searchStr;
	}

	@InjectPage
	private AuthorNameJquery pageAuthorNameJquery;

	/**
	 * Transfer fullName value between page reload
	 *
	 * @return
	 */
	Object onSuccess() {
		pageAuthorNameJquery.set(fullName);
		return pageAuthorNameJquery;
	}

	/**
	 * Transfer fullName value between page reload
	 *
	 * @param searchStr
	 */
	void onActivate(String searchStr) {
		if (searchStr == null) {
			this.searchStr = "";
		} else {
			this.searchStr = searchStr;
		}
		this.fullName = searchStr;
	}

	/**
	 * Transfer fullName value between page reload
	 *
	 * @return searchStr
	 */
	Object[] onPassivate() {
		return new String[]{searchStr};
	}

	@InjectPage
	private DownloadBook downloadBook;

	/**
	 * Redirect to page DownloadBook for download chosen book
	 *
	 * @param bookId id chosen book
	 * @return DownloadBook
	 */
	Object onUseDownloadBook(Long bookId) {
		downloadBook.set(bookId, libNamme);
		return downloadBook;
	}

	/**
	 * Load data from database to tables on page.
	 */
	void setupRender() {

		if (book == null) {
			books = new ArrayList<Books>();
		}

		if (libNamme == null) {
			libNamme = "MYLIB";
		}

		myModel = beanModelSource.createDisplayModel(Books.class, messages);
		myModel.add("action", null);
		myModel.include("year", "title", "authors_full_name");
		myModel.get("year").label("Год");
		myModel.get("title").label("Название");
		myModel.get("authors_full_name").label("Авторы");

		if (!searchStr.isEmpty()) {
			if (libNamme.equals("MYLIB")) {
				books = booksService.getMyLibBooksByAuthors(searchStr);
			} 
//                        else {
//				books = booksService.getBooksImplByAuthors(searchStr);
//			}

		} else if (books.isEmpty()) {
			books.clear();

		}

	}



	/**
	 * For t:radiogroup
	 */
	@Property
	@Persist
	private String libNamme;

	/**
	 * Get value for t:radiogroup
	 *
	 * @return name of library
	 */
//	public String getKolhoz() {
//		return "Kolhoz";
//	}

	/**
	 * Get value for t:radiogroup
	 *
	 * @return name of library
	 */
	public String getMyLib() {
		return "MYLIB";
	}

}
