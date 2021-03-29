package br.com.ftt.ec6.seniorLiving.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.security.auth.login.LoginException;

import br.com.ftt.ec6.seniorLiving.DAO.impl.UserDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.service.LoginService;
import br.com.ftt.ec6.seniorLiving.utils.BCrypt;

public class LoginServiceImpl implements LoginService {
	
	@Override
	public void performLogin(String email, String password) throws LoginException {
		if(email == null || email.trim().isEmpty()) { throw new LoginException("Email inválido"); }
		
		if(password == null || password.trim().isBlank()) { throw new LoginException("Senha inválida"); }
		
		EntityManager entityManager =  Database.getConnection();
		entityManager.getTransaction().begin();
		UserDAOImpl userDAO = UserDAOImpl.getInstance(entityManager);
		User user = userDAO.getUserByEmail(email);
		entityManager.close();
		
		if(BCrypt.checkpw(password, user.getPassword()) == false) { throw new LoginException("Email ou Senha inválida"); }
	}

	//TO DO: Fazer a validação de role
	@Override
	public User registerUser(String email, String password, List<Role> roleList) throws LoginException {
		if(email == null || email.trim().isEmpty()) { throw new LoginException("Email inválido"); } 
		
		if(password == null || password.trim().isEmpty()) { throw new LoginException("Senha inválido"); }
		
		EntityManager entityManager =  Database.getConnection();
		UserDAOImpl userDAO = UserDAOImpl.getInstance(entityManager);
		entityManager.getTransaction().begin();
		User userRetrived = userDAO.getUserByEmail(email);
		entityManager.close();
		
		if(userRetrived != null) { throw new LoginException("Outro usuário com esse email já está cadastrado"); }
		
		User user = new User();
		user.setEmail(email);
		user.setPassword(new String(BCrypt.hashpw(password, BCrypt.gensalt())));
		user.setRoleList(roleList);
		
		EntityManager entityManage2 =  Database.getConnection();
		
		entityManage2.getTransaction().begin();
		UserDAOImpl userDAO2 = UserDAOImpl.getInstance(entityManage2);
		userDAO2.save(user);
		entityManage2.getTransaction().commit();
		entityManage2.close();
		
		return user;
	}
	
}
