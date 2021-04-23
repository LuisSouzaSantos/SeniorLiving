package br.com.ftt.ec6.seniorLiving.service;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.UserException;
import br.com.ftt.ec6.seniorLiving.exception.UserFormException;

public interface UserService {

	User save(String email, String nickname, String password, String passwordConfirmation, List<Role> roleList) throws UserException;
	
	void delete(Long id);
	
	User getUserByEmail(String email);
	
	List<User> getAll();
}
