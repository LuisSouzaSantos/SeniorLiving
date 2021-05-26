package br.com.ftt.ec6.seniorLiving.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.TypeDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.TypeDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.exception.TypeException;
import br.com.ftt.ec6.seniorLiving.service.PersonService;
import br.com.ftt.ec6.seniorLiving.service.TypeService;

public class TypeServiceImpl implements TypeService {
	
	private static TypeServiceImpl instance;
	private static TypeDAO typeDAO = TypeDAOImpl.getInstance();
	private static PersonService personService = PersonServiceImpl.getInstance();
	
	private TypeServiceImpl() {}
	
	public static TypeServiceImpl getInstance() {
		if(instance == null) {
			instance = new TypeServiceImpl();
		}
		return instance;
	}
	
	@Override
	public Type save(String name, RestHome restHome) throws TypeException {
		if(name == null || name.trim().isEmpty()) { throw new TypeException("Tipo inválido"); }
		
		Type similarType = getTypeByNameAndRestHome(name, restHome);
		
		if(similarType != null) { throw new TypeException("Tipo já existe"); }
		
		Type type = new Type();
		type.setName(name);
		type.setRestHome(restHome);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		typeDAO.startConnection(entityManager);
		
		Type newType = typeDAO.save(type);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		typeDAO.stopConnection();
		
		return newType;
	}

	@Override
	public Type update(Type typeUpdate) throws TypeException {
		if(typeUpdate == null || typeUpdate.getName().trim().isEmpty()) { throw new TypeException("Tipo inválido"); }
		
		Type similarType = getTypeByNameAndRestHome(typeUpdate.getName(), typeUpdate.getRestHome());
		
		if((similarType != null) && (similarType.getId().equals(typeUpdate.getId()) == false)) { throw new TypeException("Tipo já existe"); }
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		typeDAO.startConnection(entityManager);
		
		Type type = typeDAO.update(typeUpdate);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		typeDAO.stopConnection();
		
		return type;
	}

	@Override
	public Type getTypeByName(String name) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		typeDAO.startConnection(entityManager);
		
		Type type = typeDAO.getTypeByName(name);
		
		entityManager.close();
		typeDAO.stopConnection();
		
		return type;
	}

	@Override
	public String delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		typeDAO.startConnection(entityManager);
		
		Type type = typeDAO.getById(id);
		List<Person> persons = personService.getPersonByType(type);
		
		String message = "";
		
		if(persons == null || persons.isEmpty()) {
			message = typeDAO.delete(id);
		}else {
			message = "Há pessoas vinculadas com esse tipo";
		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
		typeDAO.stopConnection();
		
		return message;
	}
	
	@Override
	public List<Type> getTypeByRestHome(RestHome resthome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		typeDAO.startConnection(entityManager);
		
		List<Type> typeList = typeDAO.getTypeByRestHome(resthome);
		
		entityManager.close();
		typeDAO.stopConnection();
		
		return typeList;
	}
	
	private Type getTypeByNameAndRestHome(String name, RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		typeDAO.startConnection(entityManager);
		
		Type type = typeDAO.getTypeByNameAndRestHome(name, restHome);
		
		entityManager.close();
		typeDAO.stopConnection();
		
		return type;
	}

}
