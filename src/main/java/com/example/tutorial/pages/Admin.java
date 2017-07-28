package com.example.tutorial.pages;

import com.example.tutorial.annotation.ProtectedPage;
import com.example.tutorial.domain.security.User;
import com.example.tutorial.domain.security.sevice.UserService;
import com.example.tutorial.entities.Books;
import com.example.tutorial.entities.Files;
import com.example.tutorial.entities.MyLibAuthors;
import com.example.tutorial.entities.MyLibBooks;
import com.example.tutorial.entities.MyLibFiles;
import com.example.tutorial.services.AuthorsService;
import com.example.tutorial.services.BooksService;
import com.example.tutorial.services.Encryption;
import com.example.tutorial.services.FilesService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.PersistenceConstants;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Admin page of application. Can view only user with role "admin". This page
 * provide two function, first - CRUD operations on library tables (MyLibBooks,
 * MyLibAuthors, MyLibFiles), second - CRUD operations on user table (User).
 * Class should annotated by Secure, ProtectedPage and RolesAllowed.
 *
 * @author roma
 */
@Import(stylesheet = {"jquerydatatables.css", "js.css", "pageAdmin.css"})
@Secure
@ProtectedPage
@RolesAllowed({"admin"})
public class Admin {

	/**
	 * Store many users data for table t:type="loop"
	 */
	@Property
	private List<User> users;

	/**
	 * Store one user data for table t:type="loop"
	 */
	@Property
	private User user;

	/**
	 * Store many books data for table t:type="loop"
	 */
	@Property
	private List<Books> books;

	/**
	 * Store one book data for table t:type="loop"
	 */
	@Property
	private Books book;

	@Inject
	private JavaScriptSupport js;

	@Property
	private String[] userRoles;

	@Property
	@NotNull
	private String userRole;

	@Property
	private String loginId;

	@Property
	private String userDescription;

	@Property
	private String password;
	@Property
	private String rpassword;

	/**
	 * User roles
	 */
	static final private String[] ALL_USER_ROLES = new String[]{"user", "admin"};

	@InjectComponent("login")
	private Form form;

	@InjectComponent("loginId")
	private TextField loginIdField;

	/**
	 * Repeat user password
	 */
	@InjectComponent("rpassword")
	private PasswordField rpasswordField;

	@Inject
	private Logger logger;

	@Inject
	@Autowired
	private UserService userService;

	/**
	 * For encryption of password
	 */
	@Inject
	@Autowired
	private Encryption encr;

	@Property
	private BeanModel<User> userModel;

	@Property
	private BeanModel<Books> booksModel;

	@Inject
	private BeanModelSource beanModelSource;

	@Inject
	private Messages messages;

	@Inject
	private Request request;

	@InjectComponent
	private Zone time2Zone;
	@InjectComponent
	private Zone booksZone;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	/**
	 * Run javascript functions for change icon adminLink (on Layout component) 
	 * style, add jQuery DataTable.
	 */
	private void afterRender() {
		js.require("styleIcon").with("adminLink");
		js.require("addDataTable").with(0, "#loopjq");
		js.require("addDataTable").with(1, "#loopjqbooks");
		js.require("bootstrap/tab");

	}

	@Inject
	@Autowired
	private BooksService booksService;

	@Inject
	@Autowired
	private AuthorsService authorsService;

	@Inject
	@Autowired
	private FilesService filesService;

	@Persist(PersistenceConstants.FLASH)
	@Property
	private String errorUploadMessage;

	@InjectComponent("uploadBooks")
	private Form uploadBooks;

	//TODO: Do more detail exception processing (only on file size)
	/**
	 * Show error message if upload file size over 100 Mb
	 *
	 * @param ex
	 * @return Admin class
	 */
	Object onUploadException(FileUploadException ex) {
		errorUploadMessage = "Максимальный размер загружаемого файла 100 Мб";
		return this;
	}

	@Property
	String valAuthorsDescription;
	@Property
	String valAuthorsFull_name;
	@Persist
	Long valAuthors_id;
	@Property
	String valBooksTitle;
	@Persist
	Long valBooksId;
	@Persist
	String valFilesFile_name;
	@Property
	String valBooksYear;
	@Property
	String valBooksDescription;

	/**
	 * Load data from database to tables on page.
	 */
	void setupRender() {
		isBooksAddMode = true;
		isLoginAddMode = true;

		if (userRoles == null) {
			userRoles = ALL_USER_ROLES;

		}

		userModel = beanModelSource.createDisplayModel(User.class, messages);
		userModel.add("action", null);
		userModel.include("loginId", "role", "description");
		userModel.get("loginId").label("Логин");
		userModel.get("role").label("Роль");
		userModel.get("description").label("Описание");

		users = userService.getAllUser();

		booksModel = beanModelSource.createDisplayModel(Books.class, messages);
		booksModel.add("action", null);
		booksModel.include("year", "title", "authors_full_name");
		booksModel.get("year").label("Год");
		booksModel.get("title").label("Название");
		booksModel.get("authors_full_name").label("Авторы");

		books = booksService.getAllMyLibBooks();

	}

	/**
	 * Override standard context return in t:eventlink element, for return
	 * several values.
	 *
	 * @return all parameters of user
	 */
	public Object[] getUserContext() {
		return new Object[]{user.getUserId(), user.getLoginId(), user.getRole(), user.getDescription()};
	}

	/**
	 * Override standard context return in t:eventlink element, for return
	 * several values.
	 *
	 * @return book and author id
	 */
	public Object[] getDeleteBooksContext() {
		return new Object[]{book.getId(), book.getId_author()};
	}

	/**
	 * Override standard context return in t:eventlink element, for return
	 * several values.
	 *
	 * @return all parameters of book and author
	 */
	public Object[] getUpdateBooksContext() {
		return new Object[]{book.getId(), book.getId_author(), book.getTitle(),
			book.getDescription(), book.getYear(), book.getAuthors_full_name(),
			book.getAuthors_Description()};
	}

	/**
	 * Get parameters of exist user for update operation. Write parameters in
	 * textfields of page, for display use ajax zone.
	 *
	 * @param userId
	 * @param loginId
	 * @param role
	 * @param description
	 */
	void onUpdateUser(Long userId, String loginId, String role, String userDescription) {
		this.loginId = loginId;
		this.userRole = role;
		this.userDescription = userDescription;
		this.userId = userId;
		this.isLoginAddMode = false;

		userRoles = ALL_USER_ROLES;
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(time2Zone);
		}
	}

	/**
	 * Get parameters of exist books, authot and book file for update operation.
	 * Write parameters in textfields of page, for display use ajax zone
	 *
	 *
	 * @param bookId
	 * @param authorId
	 * @param valBooksTitle
	 * @param valBooksDescription
	 * @param valBooksYear
	 * @param valAuthorsFull_name
	 * @param valAuthorsDescription
	 */
	void onUpdateBooks(Long bookId, Long authorId, String valBooksTitle, String valBooksDescription,
			String valBooksYear, String valAuthorsFull_name, String valAuthorsDescription) {
		isBooksAddMode = false;
		this.valBooksTitle = valBooksTitle;
		this.valBooksDescription = valBooksDescription;
		this.valBooksId = bookId;
		this.valBooksYear = valBooksYear;
		this.valAuthorsFull_name = valAuthorsFull_name;
		this.valAuthorsDescription = valAuthorsDescription;
		this.valAuthors_id = authorId;
		List<Files> fl;
		fl = filesService.getSeveralMyLibFilesById(bookId);
		this.valFilesFile_name = fl.get(0).getFile_name();
		errorUploadMessage = "Имя файла " + fl.get(0).getFile_name()
				+ ". </br></br>ЕСЛИ БУДЕТ ВЫБРАН ДРУГОЙ ФАЙЛ, СТАРЫЙ БУДЕТ УДАЛЕН!";

		MyLibAuthors auth = new MyLibAuthors();
		auth.setDescription(this.valAuthorsDescription);
		auth.setFull_name(this.valAuthorsFull_name);
		auth.setId(this.valAuthors_id);

		MyLibBooks bk = new MyLibBooks();
		bk.setDescription(this.valBooksDescription);
		bk.setId(this.valBooksId);
		bk.setId_author(this.valAuthors_id);
		bk.setTitle(this.valBooksTitle);
		bk.setYear(Long.valueOf(this.valBooksYear).longValue());
		bk.setAuthors(auth);

		Files flUpdate;
		flUpdate = fl.get(0);
		flUpdate.setFile_name("mylib/3d.pdf");
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(booksZone);
		}
	}

	/**
	 * Delete user from database
	 *
	 * @param userId id user
	 * @return Admin class
	 */
	Object onDeleteUser(Long userId) {

		userService.deleteUser(userId);
		return Admin.class;
	}

	/**
	 * Delete chosen book, author and book file from database and server
	 *
	 * @param bookId id book
	 * @param authorId id author
	 * @return Admin class
	 */
	Object onDeleteBooks(Long bookId, Long authorId) {

		List<Files> fl;
		fl = filesService.getSeveralMyLibFilesById(bookId);
		File delFile = new File(getClass().getResource("/download/books/").getPath() + fl.get(0).getFile_name());
		delFile.delete();

		booksService.deleteMyLibBooks(bookId);
		authorsService.deleteMyLibAuthors(authorId);
		filesService.deleteMyLibFiles(bookId);

		return Admin.class;
	}

	/**
	 * Switch insert and update operations for user
	 */
	@SessionState
	boolean isLoginAddMode = true;

	/**
	 * Switch insert and update operations for book
	 */
	@SessionState
	boolean isBooksAddMode = true;

	@SessionState
	Long userId = null;

	/**
	 * Update or insert user in database. Type of operation is depends of
	 * isLoginAddMode value, if true then insert. Befor operation check password
	 * match.
	 */
	void onValidateFromLogin() {

		if (form.getHasErrors()) {
			return;
		}

//		// Authenticate User
		if (password.equals(rpassword)) {
			User testUser = new User();
			testUser.setLoginId(loginId);
			testUser.setPassword(encr.encrypt(password));
			testUser.setRole(userRole);
			testUser.setDescription(userDescription);
			if (isLoginAddMode) {
				userService.addUsers(testUser);
			} else {
				testUser.setUserId(userId);
				userService.updateUsers(testUser);
				userId = null;
				isLoginAddMode = true;
			}
		} else {
			form.recordError(rpasswordField, "Пароли не совпадают");
		}

	}

	//TODO: If file exist data of file is update anyway, is this correct?
	/**
	 * Update or insert books, authot and book file in database and server.
	 * Type of operation is depends of isBooksAddMode value, if true then
	 * insert. Befor operation check exist file.
	 *
	 * @throws IOException
	 */
	void onValidateFromUploadBooks() throws IOException {
		if (isBooksAddMode) {
			if (file == null) {
				errorUploadMessage = "Файл не выбран";
				return;
			}
			InputStream is = getClass().getResourceAsStream("/download/books/mylib/" + file.getFileName());
			if (is != null) {
				errorUploadMessage = "Файл существует";
				is.close();
			} else {
				File copied = new File(getClass().getResource("/download/books/mylib/").getPath() + file.getFileName());
				file.write(copied);

				MyLibAuthors as = new MyLibAuthors();
				as.setDescription(valAuthorsDescription);
				as.setFull_name(valAuthorsFull_name);
				authorsService.addAuthors(as);

				MyLibBooks bk = new MyLibBooks();
				bk.setTitle(valBooksTitle);
				bk.setYear(Long.valueOf(valBooksYear).longValue());
				bk.setId_author(as.getId());
				bk.setDescription(valBooksDescription);
				bk.setAuthors(as);
				booksService.addBooks(bk);

				MyLibFiles mlf = new MyLibFiles();
				mlf.setFile_name("mylib/" + file.getFileName());
				mlf.setId_book(bk.getId());
				filesService.addFiles(mlf);
			}
		} else {
			MyLibAuthors auth = new MyLibAuthors();
			auth.setDescription(this.valAuthorsDescription);
			auth.setFull_name(this.valAuthorsFull_name);
			auth.setId(this.valAuthors_id);
			authorsService.updateMyLibAuthors(auth);
			MyLibBooks bk = new MyLibBooks();
			bk.setDescription(this.valBooksDescription);
			bk.setId(this.valBooksId);
			bk.setId_author(this.valAuthors_id);
			bk.setTitle(this.valBooksTitle);
			bk.setYear(Long.valueOf(this.valBooksYear).longValue());
			bk.setAuthors(auth);
			booksService.updateBooks(bk);
			if (file != null) {
				InputStream is = getClass().getResourceAsStream("/download/books/mylib/" + file.getFileName());
				if (is != null) {
					errorUploadMessage = "Файл существует";
					is.close();
				} else {
					File delFile = new File(getClass().getResource("/download/books/").getPath() + this.valFilesFile_name);
					delFile.delete();
					File copied = new File(getClass().getResource("/download/books/mylib/").getPath() + file.getFileName());
					file.write(copied);
					MyLibFiles flUpdate = new MyLibFiles();
					flUpdate.setFile_name("mylib/" + file.getFileName());
					flUpdate.setId_book(bk.getId());
					filesService.deleteMyLibFiles(bk.getId());
					filesService.addFiles((MyLibFiles) flUpdate);
				}
			}
			isBooksAddMode = true;

		}

	}

	@Property
	private UploadedFile file;

}
