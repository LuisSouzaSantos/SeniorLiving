package br.com.ftt.ec6.seniorLiving.service;

import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.LoginException;

public interface LoginService {

	User performLogin(String email, String password) throws LoginException;
	
}
