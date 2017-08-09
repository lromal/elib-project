/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.rest;

import com.example.tutorial.entities.Books;
import com.example.tutorial.services.BooksService;
import com.example.tutorial.services.View;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author kirito
 */
public class BooksResourceImpl implements BooksResource {

	@Inject
	@Autowired
	private BooksService booksService;

	private List<Books> books;
	private Books book;

	public List<Books> getAllBooks() {
		books = booksService.getAllMyLibBooks();
		return books;
	}

	public List<Books> getBooksByAuthors(String searchStr) {
		books = booksService.getMyLibBooksByAuthors(searchStr);
		return books;
	}

	public Response getFile() {
		InputStream is = getClass().getResourceAsStream("/download/books/" + "mylib/HellowWorld2.txt");
//		File file = new File("/home/kirito/Project/elib-project/target/classes/download/books/mylib/HellowWorld2.txt");
//		ResponseBuilder response = Response.ok((Object) file);
		ResponseBuilder response = Response.ok((Object) is);
//		response.header("Content-Disposition", "attachment; filename=" + file.getName());
		response.header("Content-Disposition", "attachment; filename=" + "HellowWorld2.txt");
		return response.build();

	}

	public List<Books> getBooksByTitle(String searchStr) {
		books = booksService.getMyLibBooksByTitle(searchStr);
		return books;
	}

	public Books getBooksById(Long id) {
		book = booksService.getMyLibBooksById(id);
		return book;
	}
//	public String getAllBooks() {
//		JSONObject json = new JSONObject();
//		books = booksService.getAllMyLibBooks();
//		json.put("Title", books.get(0).getTitle());
//		json.put("Title", books.get(1).getTitle());
//		JSONArray jArray;
//		
////		json.put("result", "Hello " + "asd");
////        json.put("version", "asd");
////        json.put("timestamp", "asd");
//        return json.toString();
//	}
}
