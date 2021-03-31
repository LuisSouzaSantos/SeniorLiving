package br.com.ftt.ec6.seniorLiving.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.security.auth.login.LoginException;

import br.com.ftt.ec6.seniorLiving.DAO.impl.UserDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.utils.BCrypt;

public class UserServiceImpl implements UserService {
	
	//TO DO: Regras para inserir email e senha
	@Override
	public User save(String email, String password, List<Role> roleList) throws LoginException {
		if(email == null || email.trim().isEmpty()) { throw new LoginException("Email inválido"); } 
		
		if(password == null || password.trim().isEmpty()) { throw new LoginException("Senha inválido"); }
		
		RoleServiceImpl roleServiceImpl = RoleServiceImpl.getInstance();
		
		if(roleServiceImpl.checkRoleList(roleList) == false) { throw new LoginException("Lista de papéis invalida"); }
		
		EntityManager entityManager =  Database.getConnection();
		UserDAOImpl userDAO = UserDAOImpl.getInstance(entityManager);
		entityManager.getTransaction().begin();
		User userRetrived = userDAO.getUserByEmail(email);
		entityManager.close();
		
		if(userRetrived != null) { throw new LoginException("Usuário com esse email já está cadastrado"); }
		
		User user = new User();
		user.setEmail(email);
		user.setPassword(new String(BCrypt.hashpw(password, BCrypt.gensalt())));
		user.setRoleList(roleList);
		user.setActive(true);
		
		EntityManager entityManage2 =  Database.getConnection();
		
		entityManage2.getTransaction().begin();
		UserDAOImpl userDAO2 = UserDAOImpl.getInstance(entityManage2);
		userDAO2.save(user);
		entityManage2.getTransaction().commit();
		entityManage2.close();
		
		return user;
	}

	@Override
	public void delete(Long id) {
		EntityManager entityManager =  Database.getConnection();
		UserDAOImpl userDAO = UserDAOImpl.getInstance(entityManager);
		userDAO.delete(id);
		entityManager.close();
	}

	@Override
	public User getUserByEmail(String email) {
		EntityManager entityManager =  Database.getConnection();
		UserDAOImpl userDAO = UserDAOImpl.getInstance(entityManager);
		User user = userDAO.getUserByEmail(email);
		entityManager.close();
		
		return user;
	}

}
