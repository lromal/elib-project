/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.services;

import com.example.tutorial.dao.FilesDao;
import com.example.tutorial.entities.Files;
import com.example.tutorial.entities.MyLibFiles;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author roma
 */

@Repository
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class FilesServiceImpl implements FilesService{
	@Autowired
	FilesDao filesDao;
	@Override
	public List<Files> getSeveralMyLibFilesById(Long id_book) {
        return filesDao.findSeveralMyLibFilesById(id_book);
    }
	@Override
	public List<Files> getSeveralFilesImplById(Long id_book) {
        return filesDao.findSeveralFilesImplById(id_book);
    }
	@Override
	public void addFiles(MyLibFiles files) {
		filesDao.save(files);
	}
	
	@Override
	public void deleteMyLibFiles(Long booksId) {
		filesDao.delete(booksId);
	}
	

}
