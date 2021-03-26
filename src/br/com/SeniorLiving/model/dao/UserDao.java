package br.com.SeniorLiving.model.dao;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.model.entities.User;

public interface UserDao {
	
	void insert(User obj);
	void update(User obj);
	void deleteById(Integer id);
	User findById(Integer id);
	List<User> findAll();
	boolean findLogin(String email, String password);
}
