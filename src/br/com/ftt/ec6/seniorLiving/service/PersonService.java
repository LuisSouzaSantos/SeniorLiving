package br.com.ftt.ec6.seniorLiving.service;

import java.time.LocalDate;
import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.exception.PersonException;
import br.com.ftt.ec6.seniorLiving.utils.MaritalStatus;

public interface PersonService {
	Person save(String name, MaritalStatus maritalStatus, String nationality, String job, String rg, String cpf, LocalDate birthDate, 
			String phone, String email, String addressStreet, String addressNumber, String addressState, String addressCep, String addressNeighborhood, RestHome restHome,List<Type> typeList) throws PersonException;
	
	Person update(Person person) throws PersonException;
	
	List<Person> getPersonByRestHome(RestHome restHome);
	
	List<Person> getPersonByRestHomeAndType(RestHome restHome, Type type);
	
	List<Person> getPersonByType(Type type);
	
	Person getById(Long id);
	
	String delete(Long id);
}
