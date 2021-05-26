package br.com.ftt.ec6.seniorLiving.service.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.PersonDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.PersonDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.exception.PersonException;
import br.com.ftt.ec6.seniorLiving.service.ElderlyService;
import br.com.ftt.ec6.seniorLiving.service.PersonService;
import br.com.ftt.ec6.seniorLiving.utils.MaritalStatus;
import br.com.ftt.ec6.seniorLiving.utils.Utils;

public class PersonServiceImpl implements PersonService {

	private static PersonServiceImpl instance;
	private static PersonDAO personDAO = PersonDAOImpl.getInstance();
	private static ElderlyService elderlyService = ElderlyServiceImpl.getInstance();
	
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
			String addressState, String addressCep, String addressNeighborhood, RestHome restHome, List<Type> typeList) throws PersonException {
		
		validateFields(name, maritalStatus, nationality, job, rg, cpf, birthDate, phone, email, addressStreet, addressNumber, addressState, addressCep, addressNeighborhood, restHome, typeList);
		
		if(Utils.isCPF(cpf) == false) { throw new PersonException("CPF inválido"); }
		
		if(rg.length() < 1 || rg.length() > 9)  { throw new PersonException("RG inválido"); }
		
		Person personRetrievedByRg = getPersonByRgAndRestHome(rg, restHome);
		
		if(personRetrievedByRg != null) { throw new PersonException("Já existe uma pessoa com esse RG cadastrado"); }
		
		Person personRetrievedByCPF = getPersonCPFAndRestHome(cpf, restHome);
		
		if(personRetrievedByCPF != null) { throw new PersonException("Já existe uma pessoa com esse CPF cadastrado"); }
		
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
		person.setTypeList(distincTypes(typeList));
		
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
	public Person update(Person person) throws PersonException {
		
		validateFields(person.getName(), person.getMaritalStatus(), person.getNationality(), person.getJob(), 
				person.getRg(), person.getCpf(), person.getBirthDate(), person.getPhone(), person.getEmail(), person.getAddressStreet(), 
				person.getAddressNumber(), person.getAddressState(), person.getAddressCep(), person.getAddressNeighborhood(), person.getRestHome(), person.getTypeList());
		
		if(Utils.isCPF(person.getCpf()) == false) { throw new PersonException("CPF inválido"); }
		
		if(person.getRg().length() < 1 || person.getRg().length() > 9)  { throw new PersonException("RG inválido"); }
		
		Person personRetrievedByRg = getPersonByRgAndRestHome(person.getRg(), person.getRestHome());
		
		if((personRetrievedByRg != null) && (personRetrievedByRg.getId().equals(person.getId()) == false)) { throw new PersonException("Já existe uma pessoa com esse RG cadastrado"); }
		
		Person personRetrievedByCPF = getPersonCPFAndRestHome(person.getCpf(), person.getRestHome());
		
		if((personRetrievedByCPF != null) && (personRetrievedByCPF.getId().equals(person.getId()) == false)) { throw new PersonException("Já existe uma pessoa com esse CPF cadastrado"); }
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		person.setTypeList(distincTypes(person.getTypeList()));
		
		Person newPerson = personDAO.update(person);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		personDAO.stopConnection();
		
		return newPerson;
	}

	@Override
	public String delete(Long id) {
		Person person = getById(id);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		List<Elderly> elderlyList = elderlyService.getElderlyByPerson(person);
		
		String message = "";
		
		if(elderlyList == null || elderlyList.isEmpty()) {
			message = personDAO.delete(id);
		}else {
			message = "Há idosos vinculados a essa pessoa";
		}
		
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

	@Override
	public Person getById(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		Person person = personDAO.getById(id);
		
		entityManager.close();
		personDAO.stopConnection();
		
		return person;
	}

	@Override
	public List<Person> getPersonByRestHomeAndType(RestHome restHome, Type type) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		List<Person> personList = personDAO.getPersonByRestHomeAndType(restHome, type);
		
		entityManager.close();
		personDAO.stopConnection();
		
		return personList;
	}
	
	private Person getPersonByRgAndRestHome(String rg, RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		Person person = personDAO.getPersonByRgAndRestHome(rg, restHome);
		
		entityManager.close();
		personDAO.stopConnection();
		
		return person;
	}
	
	private Person getPersonCPFAndRestHome(String cpf, RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		Person person = personDAO.getPersonByCPFAndRestHome(cpf, restHome);
		
		entityManager.close();
		personDAO.stopConnection();
		
		return person;
	}
	
	@Override
	public List<Person> getPersonByType(Type type) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		personDAO.startConnection(entityManager);
		
		List<Person> personList = personDAO.getPersonByType(type);
		
		entityManager.close();
		personDAO.stopConnection();
		
		return personList;
	}
	
	private List<Type> distincTypes(List<Type> typeList) {
		Set<Type> typeSet = new HashSet<>(typeList);
		typeList.clear();
		typeList.addAll(typeSet);
		return typeList;
	}
	
	private void validateFields(String name, MaritalStatus maritalStatus, String nationality, String job, String rg, String cpf,
			LocalDate birthDate, String phone, String email, String addressStreet, String addressNumber,
			String addressState, String addressCep, String addressNeighborhood, RestHome restHome, List<Type> typeList) throws PersonException {
		
		if(name == null || name.trim().isEmpty()) { throw new PersonException("O nome é mandatório"); }
		
		if(maritalStatus == null) { throw new PersonException("O estado civil é mandatório"); }
		
		if(nationality == null || nationality.trim().isEmpty()) { throw new PersonException("O nacionalidade é mandatório"); }
		
		if(job == null || job.trim().isEmpty()) { throw new PersonException("O posto é mandatório"); }
		
		if(rg == null || rg.trim().isEmpty()) { throw new PersonException("O rg é mandatório"); }
		
		if(cpf == null || cpf.trim().isEmpty()) { throw new PersonException("O cpf é mandatório"); }
		
		if(birthDate == null) { throw new PersonException("O data de nascimento é mandatório"); }
		
		if(phone == null || phone.trim().isEmpty()) { throw new PersonException("O telefone é mandatório"); }
		
		if(email == null || email.trim().isEmpty()) { throw new PersonException("O email é mandatório"); }
		
		if(addressStreet == null || addressStreet.trim().isEmpty()) { throw new PersonException("O logradouro é mandatório"); }
		
		if(addressNumber == null || addressNumber.trim().isEmpty()) { throw new PersonException("O número é mandatório"); }
		
		if(addressState == null || addressState.trim().isEmpty()) { throw new PersonException("O estado é mandatório"); }
		
		if(addressCep == null || addressCep.trim().isEmpty()) { throw new PersonException("O estado é mandatório"); }
		
		if(addressNeighborhood == null || addressNeighborhood.trim().isEmpty()) { throw new PersonException("O bairro é mandatório"); }
		
		if(restHome == null) { throw new PersonException("O casa de repouso é mandatório"); }
		
		if(typeList == null || typeList.isEmpty()) { throw new PersonException("O tipo é mandatório"); }
	}

}
