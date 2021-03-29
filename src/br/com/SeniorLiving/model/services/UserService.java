package br.com.SeniorLiving.model.services;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.DAO.DaoFactory;
import br.com.ftt.ec6.seniorLiving.DAO.impl.UserDAOImpl;
import br.com.ftt.ec6.seniorLiving.entities.User;

public class UserService {
	
private UserDAOImpl dao = DaoFactory.createUserDao();

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
