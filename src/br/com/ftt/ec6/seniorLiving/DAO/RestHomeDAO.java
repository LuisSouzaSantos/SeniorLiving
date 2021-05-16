package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.User;

public interface RestHomeDAO {
	
	RestHome save(RestHome restHome);
	
	RestHome update(RestHome restHome);
	
	String delete(Long id);
	
	List<RestHome> getAll();
	
	List<RestHome> getRestHomeByAdmin(User user);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();

}
