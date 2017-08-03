/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.entities;

import com.example.tutorial.services.View;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 * 
 * @author roma
 */
@Entity
@Table(name="myLibBooks")
public class MyLibBooks implements Books{
    /**
     * id books
     */
    private Long id;

    private Long id_author;
    
    private String title;
    
    private String description;


    
    private Long year;     
    
    
    /*join author hibernate*/
    private MyLibAuthors authors = new MyLibAuthors();

	@ManyToOne
    @JoinColumn(name = "id_author",
            foreignKey = @ForeignKey(name = "fk_id_author"))
    public MyLibAuthors getAuthors() {
        return authors;
    }

    public void setAuthors(MyLibAuthors authors) {
        this.authors = authors;
    }

    /*Only for tapestry*/
	@Override
	@Transient
    @JsonView(View.Private.class)
	public String getAuthors_full_name() {
        return authors.getFull_name();
    }
	/*Only for tapestry*/
	@Override
	@Transient
    @JsonView(View.Private.class)
	public String getAuthors_Description() {
        return authors.getDescription();
    }
	
	//TODO getId_author already exist, delete this parameter?
	/*Only for tapestry*/
	@Override
	@Transient
    @JsonView(View.Private.class)
	public Long getAuthors_id() {
        return authors.getId();
    }



	@Override
    @Column(name = "year")
    public Long getYear() {
        return year;
    }

	@Override
    public void setYear(Long year) {
        this.year = year;
    }


    
    /**
     * get id books
     * @return 
     */
	@Override
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	public Long getId() {
        return id;
    }

	@Override
    public void setId(Long id) {
        this.id = id;
    }

	
	@Override
    @Column(name = "id_author" , insertable = false, updatable = false)
    @JsonView(View.Private.class)
	public Long getId_author() {
        return id_author;
    }

	@Override
    public void setId_author(Long id_author) {
        this.id_author = id_author;
    }

	@Override
    @NotNull
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

	@Override
    public void setTitle(String title) {
        this.title = title;
    }

	@Override
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

	@Override
    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
