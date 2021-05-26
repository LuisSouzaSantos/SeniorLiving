package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.User;

public interface UserDAO {
	
	User save(User user);
	
	User update(User user);
	
	String delete(Long id);
	
	User getUserByEmail(String email);
	
	User getUserByNickname(String nickname);
	
	User getById(Long id);
	
	List<User> getAll();
	
	List<User> getUsersByRole(String roleName);
	
	List<User> getUserByFilter(String email, String nickname, String active);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
}
