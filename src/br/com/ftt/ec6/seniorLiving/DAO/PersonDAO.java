package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;

public interface PersonDAO{
	Person save(Person person);
	
	Person update(Person person);
	
	Person getById(Long id);
	
	List<Person> getAll();
	
	List<Person> getPersonByRestHome(RestHome restHome);
	
	List<Person> getPersonByRestHomeAndType(RestHome restHome, Type type);
	
	String delete(Long id);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
}
