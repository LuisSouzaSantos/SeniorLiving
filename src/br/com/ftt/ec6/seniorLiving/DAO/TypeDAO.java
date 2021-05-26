package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;

public interface TypeDAO {
	Type save(Type type);
	
	Type update(Type type);
	
	Type getTypeByName(String name);
	
	Type getTypeByNameAndRestHome(String name, RestHome restHome);
	
	Type getById(Long Id);
	
	String delete(Long id);
	
	List<Type> getAll();
	
	List<Type> getTypeByRestHome(RestHome restHome);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
}
