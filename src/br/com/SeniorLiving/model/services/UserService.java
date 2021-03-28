package br.com.SeniorLiving.model.services;

import java.util.List;

import br.com.SeniorLiving.model.dao.DaoFactory;
import br.com.SeniorLiving.model.dao.impl.UserJDBC;
import br.com.ftt.ec6.seniorLiving.model.entities.User;

public class UserService {
	
private UserJDBC dao = DaoFactory.createUserDao();

public List<User> findALL() {
	return dao.findAll();
}

public void saveOrUpdate(User obj) {
	if (obj.getId() == null) {
		dao.insert(obj);
	}
	else {
		dao.update(obj);
	}
}

public void remove(User obj) {
	dao.deleteById(obj.getId());
}

public boolean logar(String email, String password) {
	return dao.findLogin(email, password);
}
}
