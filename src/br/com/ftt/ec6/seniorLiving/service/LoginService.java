package br.com.ftt.ec6.seniorLiving.service;

import javax.security.auth.login.LoginException;

public interface LoginService {

	void performLogin(String email, String password) throws LoginException;
	
}
