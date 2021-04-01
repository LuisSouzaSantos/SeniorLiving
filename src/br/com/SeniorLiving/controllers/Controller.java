package br.com.SeniorLiving.controllers;

import br.com.ftt.ec6.seniorLiving.entities.User;

public class Controller {
	
	protected static User userLogged;
	
	protected void setUserLogged(User user) {
		this.userLogged = user;
	}
	

}
