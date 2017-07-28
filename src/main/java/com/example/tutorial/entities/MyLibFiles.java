/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
/**
 *
 * @author roma
 */
@Entity
@Table(name="myLibFiles")
public class MyLibFiles implements Files{
    
    @Column(name = "id_book")
    private Long id_book;
    
    @Id
    @Column(name = "name")
    private String file_name;
    

	@Override
    public Long getId_book() {
        return id_book;
    }

	@Override
    public void setId_book(Long id_book) {
        this.id_book = id_book;
    }

	@Override
    public String getFile_name() {
        return file_name;
    }

	@Override
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    
}
