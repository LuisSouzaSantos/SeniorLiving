package br.com.ftt.ec6.seniorLiving.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.UserDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.UserDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.UserException;
import br.com.ftt.ec6.seniorLiving.service.RoleService;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.utils.BCrypt;

public class UserServiceImpl implements UserService {
	
	private static UserServiceImpl instance;
	private RoleService roleService = RoleServiceImpl.getInstance();
	private UserDAO userDAO = UserDAOImpl.getInstance();
	
	private UserServiceImpl() {}
	
	public static UserServiceImpl getInstance() {
		if(instance == null) {
			instance = new UserServiceImpl();
		}
		return instance;
	}
	
	@Override
	public User save(String email, String nickname, String password, String passwordConfirmation, List<Role> roleList) throws UserException {
		validateUserParameters(email, nickname, password, passwordConfirmation, roleList);
		
		if(roleService.checkRoleList(roleList) == false) { throw new UserException("Lista de papéis invalida"); }
		
		EntityManager entityManagerConnection = Database.getConnection();
		userDAO.startConnection(entityManagerConnection);
		
		entityManagerConnection.getTransaction().begin();
		User userRetrieved = userDAO.getUserByEmail(email);
		
		if(userRetrieved != null) { throw new UserException("Já existe um usuário com esse email cadastrado na aplicação"); }
		
		if(checkPassword(password, passwordConfirmation) == false) { throw new UserException("Senha de confirmação está divergente da senha "); }
		
		User user = new User();
		user.setEmail(email);
		user.setNickname(nickname);
		user.setPassword(new String(BCrypt.hashpw(password, BCrypt.gensalt())));
		user.setRoleList(roleList);
		user.setActive(true);
		userDAO.save(user);
		
		entityManagerConnection.getTransaction().commit();
		entityManagerConnection.close();
		userDAO.stopConnection();
		
		return user;
	}

	@Override
	public void delete(Long id) {
		EntityManager entityManager =  Database.getConnection();
		userDAO.startConnection(entityManager);
		userDAO.delete(id);
		entityManager.close();
		userDAO.stopConnection();
	}

	@Override
	public User getUserByEmail(String email) {
		EntityManager entityManager =  Database.getConnection();
		userDAO.startConnection(entityManager);
		User user = userDAO.getUserByEmail(email);
		entityManager.close();
		userDAO.stopConnection();
		
		return user;
	}

	@Override
	public List<User> getAll() {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		userDAO.startConnection(entityManager);
		List<User> users = userDAO.getAll();
		entityManager.close();
		userDAO.stopConnection();
		
		return users;
	}
	
	private void validateUserParameters(String email, String nickname, String password, String passwordConfirmation, List<Role> roleList) throws UserException {
		if(email == null || email.trim().isEmpty()) { throw new UserException("Email não pode estar em branco."); }
		
		if(nickname == null || nickname.trim().isEmpty()) { throw new UserException("Apelido não pode estar em branco."); }
		
		if(password == null || password.trim().isEmpty()) { throw new UserException("Senha não pode estar em branco."); }
		
		if(passwordConfirmation == null || passwordConfirmation.trim().isEmpty()) { throw new UserException("Confirmação da senha não pode estar em branco."); }
		
		if(roleList == null || roleList.isEmpty()) { throw new UserException("Ao menos uma role deve ser selecionada."); }
	}
	
	private boolean checkPassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}

}
