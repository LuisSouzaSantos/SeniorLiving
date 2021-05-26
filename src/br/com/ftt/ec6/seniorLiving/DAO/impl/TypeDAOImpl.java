package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.TypeDAO;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;

public class TypeDAOImpl extends DAOImpl<Type> implements TypeDAO {

	private static TypeDAOImpl instance;
	
	private TypeDAOImpl() {
		super.t = Type.class;
	}
	
	public static TypeDAOImpl getInstance() {
		if(instance == null) {
			instance = new TypeDAOImpl();
		}
		return instance;
	}

	@Override
	public Type getTypeByName(String name) {
		try {
			return super.entityManager.createQuery(findTypeByNameQuery(), Type.class)
						.setParameter("name", name)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public Type getTypeByNameAndRestHome(String name, RestHome restHome) {
		try {
			return super.entityManager.createQuery(findTypeByNameAndRestHomeQuery(), Type.class)
						.setParameter("name", name)
						.setParameter("restHome", restHome)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}

	
	@Override
	public List<Type> getTypeByRestHome(RestHome restHome) {
		try {
			return super.entityManager.createQuery(getTypeByRestHomeQuery(), Type.class)
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
	
	private String getTypeByRestHomeQuery() {
		return "SELECT t from Type t where t.restHome = :restHome";
	}
	
	private String findTypeByNameQuery() {
		return "SELECT t from Type t where t.name = :name";
	}
	
	private String findTypeByNameAndRestHomeQuery() {
		return "SELECT t from Type t where t.name = :name AND t.restHome = :restHome";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
