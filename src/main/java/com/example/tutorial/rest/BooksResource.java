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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 *
 * @author kirito
 */
@Path("/books")
//TODO move to rest package
public interface BooksResource {

	@GET
	@Produces({"application/json"})
	@JsonView(View.REST.class)
	public List<Books> getAllBooks();

	@GET
	@Path("{id : \\d+}") //support digit only
	@Produces({"application/json"})
	@JsonView(View.REST.class)
	public Books getBooksById(@PathParam("id") Long id);
//    public String getAllBooks();

	@GET
	@Path("/authors/{searchStr}")
	@Produces({"application/json"})
	@JsonView(View.REST.class)
	public List<Books> getBooksByAuthors(@PathParam("searchStr") String searchStr);

	@GET
	@Path("/title/{searchStr}")
	@Produces({"application/json"})
	@JsonView(View.REST.class)
	public List<Books> getBooksByTitle(@PathParam("searchStr") String searchStr);

	@GET
	@Path("/download")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFile();
}
