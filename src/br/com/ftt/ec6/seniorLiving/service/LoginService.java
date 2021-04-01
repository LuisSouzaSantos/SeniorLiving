package br.com.ftt.ec6.seniorLiving.service;

import javax.security.auth.login.LoginException;

import br.com.ftt.ec6.seniorLiving.entities.User;

public interface LoginService {

	User performLogin(String email, String password) throws LoginException;
	
}
