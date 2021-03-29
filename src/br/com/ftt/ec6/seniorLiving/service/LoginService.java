package br.com.ftt.ec6.seniorLiving.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.entities.User;

public interface LoginService {

	void performLogin(String email, String password) throws LoginException;
	
	User registerUser(String email, String password, List<Role> roleList) throws LoginException;
	
}
