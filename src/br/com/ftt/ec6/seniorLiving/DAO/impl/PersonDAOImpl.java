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
	public Person getPersonByRgAndRestHome(String rg, RestHome restHome) {
		try {
			return super.entityManager.createQuery(findPersonByRGAndRestHome(), Person.class)
						.setParameter("rg", rg)
						.setParameter("restHome", restHome)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}

	@Override
	public Person getPersonByCPFAndRestHome(String cpf, RestHome restHome) {
		try {
			return super.entityManager.createQuery(findPersonByCPFAndRestHome(), Person.class)
						.setParameter("cpf", cpf)
						.setParameter("restHome", restHome)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public List<Person> getPersonByRestHome(RestHome restHome) {
		try {
			return super.entityManager.createQuery(findTypeByRestHomeQuery(), Person.class)
						.setParameter("restHome", restHome)
						.getResultList();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public List<Person> getPersonByRestHomeAndType(RestHome restHome, Type type) {
		try {
			return super.entityManager.createQuery(findPersonByRestHomeAndTypeQuery(), Person.class)
						.setParameter("restHome", restHome)
						.setParameter("type", type)
						.getResultList();
		}catch(RuntimeException e) { return null; }
	}
	
	@Override
	public List<Person> getPersonByType(Type type) {
		try {
			return super.entityManager.createQuery(findPersonByType(), Person.class)
						.setParameter("type", type)
						.getResultList();
		}catch(RuntimeException e) { return null; }
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
		super.entityManager = entityManager;
	}
	
	private String findTypeByRestHomeQuery() {
		return "SELECT p from Person p WHERE p.restHome = :restHome";
	}
	
	private String findPersonByRestHomeAndTypeQuery() {
		return "SELECT p from Person p JOIN p.typeList t where p.restHome = :restHome and t = :type";
	}
	
	private String findPersonByType() {
		return "SELECT p from Person p JOIN p.typeList t where t = :type";
	}
	
	private String findPersonByRGAndRestHome() {
		return "SELECT p from Person p WHERE p.rg = :rg AND p.restHome = :restHome";
	}
	
	private String findPersonByCPFAndRestHome() {
		return "SELECT p from Person p WHERE p.cpf = :cpf AND p.restHome = :restHome";
	}
	
}
