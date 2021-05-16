package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface PersonDAO{
	Person save(Person person);
	
	Person update(Person person);
	
	List<Person> getAll();
	
	List<Person> getPersonByRestHome(RestHome restHome);
	
	String delete(Long id);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
}
