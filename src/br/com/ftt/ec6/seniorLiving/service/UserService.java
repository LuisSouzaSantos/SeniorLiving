package br.com.ftt.ec6.seniorLiving.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.entities.User;

public interface UserService {

	User save(String email, String password, String nickname, List<Role> roleList) throws LoginException;
	
	void delete(Long id);
	
	User getUserByEmail(String email);
	
	List<User> getAll();
}
