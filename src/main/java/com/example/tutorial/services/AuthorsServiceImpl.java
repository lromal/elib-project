package com.example.tutorial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tutorial.entities.MyLibAuthors;
import org.springframework.transaction.annotation.Propagation;
import com.example.tutorial.dao.AuthorsDao;

/**
 * 
 * @author roma
 * 
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AuthorsServiceImpl implements AuthorsService {


	@Autowired
	AuthorsDao authorsDao;


	
	@Override
	public void addAuthors(MyLibAuthors authors) {
		authorsDao.save(authors);
	}
	
	@Override
	public void deleteMyLibAuthors(Long authorsId) {
		authorsDao.delete(authorsId);
	}
	
	@Override
	public void updateMyLibAuthors(MyLibAuthors authors) {
		authorsDao.changeMyLibAuthors(authors);
	}
}
