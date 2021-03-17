package model.dao;

import java.util.List;

import model.entities.User;

public interface UserDao {
	
	void insert(User obj);
	void update(User obj);
	void deleteById(Integer id);
	User findById(Integer id);
	List<User> findAll();
	boolean findLogin(String email, String password);
}
