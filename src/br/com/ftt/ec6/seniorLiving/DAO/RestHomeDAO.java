package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface RestHomeDAO {
	
	RestHome save(RestHome restHome);
	
	String delete(Long id);
	
	List<RestHome> getAll();
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();

}
