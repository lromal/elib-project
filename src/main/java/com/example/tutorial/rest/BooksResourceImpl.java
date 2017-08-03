/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.rest;

import com.example.tutorial.entities.Books;
import com.example.tutorial.services.BooksService;
import com.example.tutorial.services.View;
import java.util.List;
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
	
	public List<Books> getAllBooks() {
		books = booksService.getAllMyLibBooks();
		return books;
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
