package br.com.ftt.ec6.seniorLiving.DAO;

import br.com.ftt.ec6.seniorLiving.entities.Person;

public interface PersonDAO {
	Person save(Person person);
	
	void delete(Long id);
}
