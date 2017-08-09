package com.example.tutorial.domain.security.dao;

import com.example.tutorial.domain.security.User;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bytesTree
 * @see <a href="http://www.bytestree.com/">BytesTree</a>
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public User findUser(String loginId, String password) {
		User user = (User) em.createQuery("select u from User u where loginId=:loginId and "
				+ "password=:password").setParameter("loginId", loginId)
				.setParameter("password", password).getSingleResult();
		return user;
	}

	@Override
	public List<User> findAllUser() {
		List<User> retUser = em.createQuery("select u from User u").getResultList();
		return retUser;
	}
	
	@Override
	public User findUserById(Long id) {
		User retUser = em.find(User.class, id);
		return retUser;
	}

	@Transactional
	@Override
	public void save(User user) {
		em.persist(user);
	}

	@Transactional
	@Override
	public void changeUser(User user) {
		em.merge(user);
	}

	@Transactional
	@Override
	public void delete(Long userId) {
		User user;
		user = em.find(User.class, userId);

		em.remove(user);
	}
}
