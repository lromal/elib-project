/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.pages;

import com.example.tutorial.annotation.ProtectedPage;
import com.example.tutorial.entities.Books;
import com.example.tutorial.services.FilesService;
import com.example.tutorial.services.OutputStreamResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * Page for download book, also have description of book.
 *
 * @author roma
 */
@Import(stylesheet = {"jquerydatatables.css"})
@ProtectedPage
@Service
public class DownloadBook {

	@Inject
	private FilesService filesService;

	/**
	 * Id book, by this value select path of book file
	 */
	@Persist(PersistenceConstants.FLASH)
	private Long bookId;

	/**
	 * List with books get from page BooksNameJquery or AuthorNameJquery.
	 */
	@SessionState
	@Property
	private List<Books> books;

	/**
	 * Index of chosen book in list of book. Book choose on page BooksNameJquery
	 * or AuthorNameJquery.
	 */
	@Persist
	private int idListBooks;

	/**
	 * Store many links to book file on server for table t:type="loop"
	 */
	@Property
	@Qualifier("FilesImpl")
	List<com.example.tutorial.entities.Files> downloadFiles;

	/**
	 * Store one link to book file on server for table t:type="loop"
	 */
	@Property
	@Qualifier("FilesImpl")
	com.example.tutorial.entities.Files downloadFile;

	/**
	 * Property for show/hide download link
	 */
	@Property
	String hideLinck = "";

	/**
	 * Property for show/hide error message of download link
	 */
	@Property
	String hideErr = "display: none;";

	/**
	 *
	 * @return book title
	 */
	public String getTitle() {
		return books.get(idListBooks).getTitle();
	}

	/**
	 *
	 * @return authors full name
	 */
	public String getAuthors() {
		return books.get(idListBooks).getAuthors_full_name();
	}

	/**
	 *
	 * @return book description
	 */
	public String getDescription() {
		return books.get(idListBooks).getDescription();
	}

	/**
	 * Set download link on page
	 */
	@Component(id = "downloadLink")
	private ActionLink downloadLink;

	/**
	 * Download book from server
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	@OnEvent(component = "downloadLink")
	private Object handleDownload(final String filePath) throws IOException {

		InputStream is = getClass().getResourceAsStream("/download/books/" + filePath);
		if (is == null) {
			return null;
		}
		is.close();

		final OutputStreamResponse response = new OutputStreamResponse() {

			@Override
			public String getContentType() {
				int index = filePath.lastIndexOf('.');
				String type;

				type = index == -1 ? null : filePath.substring(index + 1);
				return "application/" + type; // or whatever content type your file is
			}

			@Override
			public void prepareResponse(Response response) {
				int index = filePath.lastIndexOf('/');
				String fileName;
				fileName = index == -1 ? null : filePath.substring(index + 1);
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			}

			@Override
			public void writeToStream(OutputStream out) throws IOException {
				try {

					InputStream is = getClass().getResourceAsStream("/download/books/" + filePath);

					IOUtils.copy(is, out);
					is.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		};

		return response;
	}

	/**
	 * Function for transfer data in this page when redirect to it
	 *
	 * @param bookId
	 * @param libNamme
	 */
	public void set(Long bookId, String libNamme) {
		this.bookId = bookId;
		if (libNamme != null) {
			this.libNamme = libNamme;
		}
	}

	/**
	 * Library namme chosen on page BooksNameJquery or AuthorNameJquery.
	 */
	@Persist
	private String libNamme;

	/**
	 * Transfer data of book between page reload
	 *
	 * @param bookId
	 * @param libNamme
	 */
	void onActivate() {
	}

	/**
	 * Transfer data of book between page reload and identify idListBooks
	 *
	 * @return
	 */
	Long onPassivate() {
		idListBooks = 0;
		for (Books s : books) {
			if (s.getId().equals(bookId)) {
				break;
			}
			idListBooks++;
		}

		if (libNamme != null) {
			this.libNamme = libNamme;
		}
		return bookId;
	}

	@Inject
	private Logger logger;

	

	/**
	 * Set book download links on page. Path for links get from table myLibFiles
	 * by book id. If file non exist in server show error message.
	 *
	 * @throws IOException
	 */
	void setupRender() throws IOException {
		/**
		 * write downloadFiles
		 */
		if (this.libNamme != null) {
//			if (libNamme.equals("Kolhoz")) {
//				downloadFiles = filesService.getSeveralFilesImplById(books.get(idListBooks).getId());
//			}
			if (libNamme.equals("MYLIB")) {
				downloadFiles = filesService.getSeveralMyLibFilesById(books.get(idListBooks).getId());
			}
		} else {

			downloadFiles = filesService.getSeveralFilesImplById(books.get(idListBooks).getId());
		}

		int countNonExFile = 0;
		for (com.example.tutorial.entities.Files f : downloadFiles) {

			InputStream is = getClass().getResourceAsStream("/download/books/" + f.getFile_name());
			if (is == null) {
				countNonExFile++;
			} else {
				is.close();
			}

		}
		if (countNonExFile == downloadFiles.size()) {
			hideLinck = "display:none";
			hideErr = "";
		}
	}
}
