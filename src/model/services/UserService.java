package model.services;

import model.dao.DaoFactory;
import model.dao.impl.UserJDBC;
import model.entities.User;

public class UserService {
	
private UserJDBC dao = DaoFactory.createUserDao();

public void save(User obj) {
	if (obj.getId() == null) {
		dao.insert(obj);
	}
	else {
		
	}
}

}
