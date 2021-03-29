package br.com.ftt.ec6.seniorLiving.DAO;

import br.com.ftt.ec6.seniorLiving.entities.User;

public interface UserDAO {
	
	User save(User user);
	User getUserByEmail(String email);
}
