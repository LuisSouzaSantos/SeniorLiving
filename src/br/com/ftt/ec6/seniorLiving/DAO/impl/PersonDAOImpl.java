package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.PersonDAO;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;

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
	public List<Person> getPersonByRestHomeAndType(RestHome restHome, Type type) {
		try {
			return super.entityManager.createQuery(getPersonByRestHomeAndTypeQuery(), Person.class)
						.setParameter("restHome", restHome)
						.setParameter("type", type)
						.getResultList();
		}catch(RuntimeException e) {
			e.getStackTrace();
			return null;
		}
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
	
	private String getPersonByRestHomeAndTypeQuery() {
		return "SELECT p from Person p JOIN p.typeList t where p.restHome = :restHome and t = :type";
	}


	

}
