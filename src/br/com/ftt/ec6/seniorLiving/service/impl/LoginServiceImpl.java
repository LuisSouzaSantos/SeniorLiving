package br.com.ftt.ec6.seniorLiving.service.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.UserDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.UserDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.LoginException;
import br.com.ftt.ec6.seniorLiving.service.LoginService;
import br.com.ftt.ec6.seniorLiving.utils.BCrypt;

public class LoginServiceImpl implements LoginService {
	
	private static LoginServiceImpl instance;
	private final static UserDAO userDAO =  UserDAOImpl.getInstance();
	
	private LoginServiceImpl() {}
	
	public static LoginServiceImpl getInstance() {
		if(instance == null) {
			instance = new LoginServiceImpl();
		}
		
		return instance;
	}
	
	@Override
	public User performLogin(String email, String password) throws LoginException {
		if(email == null || email.trim().isEmpty()) { throw new LoginException("Email inválido"); }
		
		if(password == null || password.trim().isEmpty()) { throw new LoginException("Senha inválida"); }
		
		EntityManager entityManager =  Database.getConnection();
		entityManager.getTransaction().begin();
		userDAO.startConnection(entityManager);
		User user = userDAO.getUserByEmail(email);
		entityManager.close();
		userDAO.stopConnection();
		
		if(user == null) { throw new LoginException("Usuário não registrado"); }
		
		if(user.isActive() == false) { throw new LoginException("Usuário desabilitado"); }
		
		if(BCrypt.checkpw(password, user.getPassword()) == false) { throw new LoginException("Credenciais inválidas"); }
		
		return user;
	}
	
}
