package br.com.ftt.ec6.seniorLiving.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.PersonDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.PersonDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.service.PersonService;

public class PersonServiceImpl implements PersonService {

	private static PersonServiceImpl instance;
	
	private PersonServiceImpl() {}
	
	public static PersonServiceImpl getInstance() {
		if(instance == null) {
			instance = new PersonServiceImpl();
		}
		return instance;
	}
	
	@Override
	public Person save(String name, String maritalStatus, String nationality, String job, String rg, String cpf,
			LocalDate birthDate, String phone, String email, String addressStreet, String addressNumber,
			String addressState, String addressCep, String addressNeighborhood,List<Type> typeList) {
		
		Person person = new Person();
		person.setName(name);
		person.setMaritalStatus(maritalStatus);
		person.setNationality(nationality);
		person.setJob(job);
		person.setRg(rg);
		person.setCpf(cpf);
		person.setBirthDate(birthDate);
		person.setPhone(phone);
		person.setPhone(phone);
		person.setEmail(email);
		person.setAddressStreet(addressStreet);
		person.setAddressNumber(addressNumber);
		person.setAddressState(addressState);
		person.setAddressCep(addressCep);
		person.setAddressNeighborhood(addressNeighborhood);
		person.setTypeList(typeList);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		PersonDAO personDAO = PersonDAOImpl.getInstance(entityManager);
		Person newPerson = personDAO.save(person);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return newPerson;
	}

	@Override
	public void delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		PersonDAO personDAO = PersonDAOImpl.getInstance(entityManager);
		personDAO.delete(id);
		entityManager.close();
	}

}
