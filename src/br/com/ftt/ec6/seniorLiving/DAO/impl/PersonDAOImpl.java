package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.PersonDAO;
import br.com.ftt.ec6.seniorLiving.entities.Person;

public class PersonDAOImpl implements PersonDAO {
	
	private static PersonDAOImpl instance;
	private EntityManager entityManager;
	
	private PersonDAOImpl() {}
	
	public static PersonDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new PersonDAOImpl();
		}
		instance.setEntityManager(entityManager);
		return instance;
	}

	@Override
	public Person save(Person person) {
		entityManager.persist(person);
		return person;
	}

	@Override
	public void delete(Long id) {
		try {
			this.entityManager.createQuery(removePersonById(), Person.class)
						.setParameter("id", id)
						.executeUpdate();
		}catch(RuntimeException e) {}
		
	}
	
	private String removePersonById() {
		return "DELETE from Person p where p.id = :id";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
