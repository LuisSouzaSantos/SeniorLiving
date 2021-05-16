package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.PersonDAO;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public class PersonDAOImpl extends DAOImpl<Person> implements PersonDAO {
	
	private static PersonDAOImpl instance;
	
	private PersonDAOImpl() {
		super.t = Person.class;
	}
	
	public static PersonDAOImpl getInstance() {
		if(instance == null) {
			instance = new PersonDAOImpl();
		}
		return instance;
	}
	
	@Override
	public List<Person> getPersonByRestHome(RestHome restHome) {
		try {
			return super.entityManager.createQuery(getTypeByRestHomeQuery(), Person.class)
						.setParameter("restHome", restHome)
						.getResultList();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public void startConnection(EntityManager entityManager) {
		instance.setEntityManager(entityManager);
	}

	@Override
	public void stopConnection() {
		super.entityManager = null;
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private String getTypeByRestHomeQuery() {
		return "SELECT p from Person p where p.restHome = :restHome";
	}

	

}
