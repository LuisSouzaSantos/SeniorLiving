package br.com.ftt.ec6.seniorLiving.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.PersonDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.PersonDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.service.PersonService;
import br.com.ftt.ec6.seniorLiving.utils.MaritalStatus;

public class PersonServiceImpl implements PersonService {

	private static PersonServiceImpl instance;
	private static PersonDAO personDAO = PersonDAOImpl.getInstance();
	
	private PersonServiceImpl() {}
	
	public static PersonServiceImpl getInstance() {
		if(instance == null) {
			instance = new PersonServiceImpl();
		}
		return instance;
	}
	
	@Override
	public Person save(String name, MaritalStatus maritalStatus, String nationality, String job, String rg, String cpf,
			LocalDate birthDate, String phone, String email, String addressStreet, String addressNumber,
			String addressState, String addressCep, String addressNeighborhood, RestHome restHome, List<Type> typeList) {
		
		//TODO: Fazer Validação
		
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
		person.setRestHome(restHome);
		person.setTypeList(typeList);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		Person newPerson = personDAO.save(person);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		personDAO.stopConnection();
		
		return newPerson;
	}
	
	@Override
	public Person update(Person person) {
		//TODO: Fazer Validação
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		Person newPerson = personDAO.update(person);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		personDAO.stopConnection();
		
		return newPerson;
	}

	@Override
	public String delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		String message = personDAO.delete(id);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		personDAO.stopConnection();
		
		return message;
	}

	

	@Override
	public List<Person> getPersonByRestHome(RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		List<Person> personList = personDAO.getPersonByRestHome(restHome);
		
		entityManager.close();
		personDAO.stopConnection();
		
		return personList;
	}

}
