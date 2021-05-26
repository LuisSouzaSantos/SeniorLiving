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
	
	private static final String EMAIL_PATTERN = 
	        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
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
		
		if(email.matches(EMAIL_PATTERN) == false) { throw new UserException("O Email informado n�o est� nos padr�es"); }
		
		if(roleService.checkRoleList(roleList) == false) { throw new UserException("Lista de pap�is inv�lida"); }
		
		EntityManager entityManagerConnection = Database.getConnection();
		userDAO.startConnection(entityManagerConnection);
		entityManagerConnection.getTransaction().begin();
		
		User userRetrieved = userDAO.getUserByEmail(email);
		
		if(userRetrieved != null) { throw new UserException("J� existe um usu�rio com esse email cadastrado na aplica��o"); }
		
		User userRetrievedByNickname = userDAO.getUserByNickname(nickname);
		
		if(userRetrievedByNickname != null) { throw new UserException("J� existe um usu�rio com esse apelido cadastrado na aplica��o"); }
		
		if(checkPassword(password, passwordConfirmation) == false) { throw new UserException("Senha de confirma��o est� divergente da senha "); }
		
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
	public User update(User user) throws UserException {
		if(user == null) { throw new UserException("Usu�rio = n�o pode ser nulo"); }
		
		validateUserUpdateParameters(user.getEmail(), user.getNickname(), user.getPassword(), user.getPasswordConfirmation(), user.getRoleList());
		
		if(user.getEmail().matches(EMAIL_PATTERN) == false) { throw new UserException("O Email informado n�o est� nos padr�es"); }
		
		if(roleService.checkRoleList(user.getRoleList()) == false) { throw new UserException("Lista de pap�is invalida"); }
		
		EntityManager entityManagerConnection = Database.getConnection();
		userDAO.startConnection(entityManagerConnection);
		entityManagerConnection.getTransaction().begin();
		
		User userRetrieved = userDAO.getUserByEmail(user.getEmail());
		
		if((userRetrieved != null) && (userRetrieved.getId().equals(user.getId()) == false)) { throw new UserException("J� existe um usu�rio com esse email cadastrado na aplica��o"); }
		
		User userRetrievedByNickname = userDAO.getUserByNickname(user.getNickname());
		
		if((userRetrievedByNickname != null) && (userRetrievedByNickname.getId().equals(user.getId()) == false)) { throw new UserException("J� existe um usu�rio com esse apelido cadastrado na aplica��o"); }
		
		userDAO.update(user);
		
		entityManagerConnection.getTransaction().commit();
		entityManagerConnection.close();
		userDAO.stopConnection();
		
		return user;
	}

	@Override
	public String delete(Long id) {
		EntityManager entityManager =  Database.getConnection();
		userDAO.startConnection(entityManager);
		entityManager.getTransaction().begin();
		
		String messageInfo = userDAO.delete(id);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		userDAO.stopConnection();
		
		return messageInfo;
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
	
	@Override
	public List<User> getUsersByRole(String roleName) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		userDAO.startConnection(entityManager);
		
		List<User> users = userDAO.getUsersByRole(roleName);
		
		entityManager.close();
		userDAO.stopConnection();
		
		return users;
	}
	
	@Override
	public List<User> getUserByFilter(String email, String nickname, String active){
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		userDAO.startConnection(entityManager);
		
		List<User> users = userDAO.getUserByFilter(email, nickname, active);
		
		entityManager.close();
		userDAO.stopConnection();
		
		return users;
	}
	
	private void validateUserParameters(String email, String nickname, String password, String passwordConfirmation, List<Role> roleList) throws UserException {
		if(email == null || email.trim().isEmpty()) { throw new UserException("Email n�o pode estar em branco."); }
		
		if(nickname == null || nickname.trim().isEmpty()) { throw new UserException("Apelido n�o pode estar em branco."); }
		
		if(password == null || password.trim().isEmpty()) { throw new UserException("Senha n�o pode estar em branco."); }
		
		if(passwordConfirmation == null || passwordConfirmation.trim().isEmpty()) { throw new UserException("Confirma��o da senha n�o pode estar em branco."); }
		
		if(roleList == null || roleList.isEmpty()) { throw new UserException("Ao menos uma role deve ser selecionada."); }
	}
	
	private void validateUserUpdateParameters(String email, String nickname, String password, String passwordConfirmation, List<Role> roleList) throws UserException {
		if(email == null || email.trim().isEmpty()) { throw new UserException("Email n�o pode estar em branco."); }
		
		if(nickname == null || nickname.trim().isEmpty()) { throw new UserException("Apelido n�o pode estar em branco."); }
		
		if(roleList == null || roleList.isEmpty()) { throw new UserException("Ao menos uma role deve ser selecionada."); }
	}
	
	private boolean checkPassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}

}
