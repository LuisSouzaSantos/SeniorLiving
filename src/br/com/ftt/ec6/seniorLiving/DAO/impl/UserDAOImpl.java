package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
	public User getUserByEmail(String email) {
		try {
			return super.entityManager.createQuery(findUserByEmailQuery(), User.class)
			  		 .setParameter("email", email)
			         .getSingleResult();
		}catch(RuntimeException e) {
			return null;
		}
		
	}
	
	@Override
	public User getUserByNickname(String nickname) {
		try {
			return super.entityManager.createQuery(findUserByNicknameQuery(), User.class)
			  		 .setParameter("nickname", nickname)
			         .getSingleResult();
		}catch(RuntimeException e) {
			return null;
		}
	}
	
	@Override
	public List<User> getUsersByRole(String roleName) {
		try {
			return super.entityManager.createQuery(findUsersByRoleNameQuery(), User.class)
			  		 .setParameter("name", roleName)
			         .getResultList();
		}catch(RuntimeException e) {
			return null;
		}
	}
	
	@Override
	public List<User> getUserByFilter(String email, String nickname, String active) {
		String userFilter = "SELECT u from User u WHERE 1=1 ";
		
		if(email != null && email.trim().isEmpty() == false) {
			userFilter+=" AND u.email LIKE CONCAT('%',:email,'%') ";
		}
		
		if(nickname != null && nickname.trim().isEmpty() == false) {
			userFilter+=" AND u.nickname LIKE CONCAT('%',:nickname,'%') ";
		}
		
		if(active != null && active.trim().isEmpty() == false) {
			userFilter+=" AND u.active = :active ";
		}
		
		TypedQuery<User> userFilterQuery = super.entityManager.createQuery(userFilter, User.class);
		
		if(email != null && email.trim().isEmpty() == false) {
			userFilterQuery.setParameter("email", email);
		}
		
		if(nickname != null && nickname.trim().isEmpty() == false) {
			userFilterQuery.setParameter("nickname", nickname);
		}
		
		if(active != null && active.trim().isEmpty() == false) {
			if(active.trim().toLowerCase().equals("true")) {
				userFilterQuery.setParameter("active", "1");
			}
				
			if(active.trim().toLowerCase().equals("false")) {
				userFilterQuery.setParameter("active", "0");
			}
				
		}
		
		return userFilterQuery.getResultList();
	}
	
	@Override
	public void startConnection(EntityManager entityManager) {
		instance.setEntityManager(entityManager);
	}
	
	@Override
	public void stopConnection() {
		clearEntityManager();
	}

	private String findUserByEmailQuery() {
		return "SELECT u from User u where u.email = :email";
	}
	
	private String findUserByNicknameQuery() {
		return "SELECT u from User u where u.nickname = :nickname";
	}
	
	private String findUsersByRoleNameQuery() {
		return "SELECT u from User u JOIN u.roleList rl where rl.name = :name";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		super.entityManager = entityManager;
	}
	
	private void clearEntityManager() {
		super.entityManager = null;
	}
	
}
