package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.UserDAO;
import br.com.ftt.ec6.seniorLiving.entities.User;

public class UserDAOImpl implements UserDAO{
	
	private static UserDAOImpl instance;
	private EntityManager entityManager;
	
	private UserDAOImpl() {}
	
	public static UserDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new UserDAOImpl();
		}
		instance.setEntityManager(entityManager);
		return instance;
	}
	
	@Override
	public User getUserByEmail(String email) {
		try {
			return this.entityManager.createQuery(findUserByEmailQuery(), User.class)
			  		 .setParameter("email", email)
			         .getSingleResult();
		}catch(RuntimeException e) {
			return null;
		}
		
	}
	
	@Override
	public User save(User user) {
		this.entityManager.persist(user);
		return user;
	}

	private String findUserByEmailQuery() {
		return "SELECT u from User u where u.email = :email";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
