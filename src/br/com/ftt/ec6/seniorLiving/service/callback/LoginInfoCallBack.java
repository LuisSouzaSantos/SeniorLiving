package br.com.ftt.ec6.seniorLiving.service.callback;

import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.LoginException;

public interface LoginInfoCallBack {

	public void onSucess(User userLogged);
	
	public void onFail(LoginException loginException);
}
