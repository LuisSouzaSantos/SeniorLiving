package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.UserDAO;
import br.com.ftt.ec6.seniorLiving.entities.User;

public class UserDAOImpl extends DAOImpl<User> implements UserDAO{
	
	private static UserDAOImpl instance;
	
	private UserDAOImpl() {
		super.t = User.class;
	}
	
	public static UserDAOImpl getInstance() {
		if(instance == null) {
			instance = new UserDAOImpl();
		}
		return instance;
	}
	
	@Override
	public void startConnection(EntityManager entityManager) {
		instance.setEntityManager(entityManager);
	}
	
	@Override
	public void stopConnection() {
		clearEntityManager();
	}
	
	@Override
	public User getUserByEmail(String email) {
		try {
			return super.entityManager.createQuery(findUserByEmailQuery(), User.class)
			  		 .setParameter("email", email)
			         .getSingleResult();
		}catch(RuntimeException e) {
			return null;
		}
		
	}

	private String findUserByEmailQuery() {
		return "SELECT u from User u where u.email = :email";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		super.entityManager = entityManager;
	}
	
	private void clearEntityManager() {
		super.entityManager = null;
	}
	
}
