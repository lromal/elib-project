/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.rest;

import com.example.tutorial.entities.Books;
import com.example.tutorial.services.View;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.apache.tapestry5.json.JSONObject;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 *
 * @author kirito
 */
@Path("/books")
//TODO move to rest package
public interface BooksResource {
	@GET
    @Produces({ "application/json" })
    @JsonView(View.REST.class)
	public List<Books> getAllBooks();
//    public String getAllBooks();
	
}
