package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.TypeDAO;
import br.com.ftt.ec6.seniorLiving.entities.Type;

public class TypeDAOImpl implements TypeDAO {

	private static TypeDAOImpl instance;
	private EntityManager entityManager;
	
	private TypeDAOImpl() {}
	
	public static TypeDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new TypeDAOImpl();
		}
		instance.setEntityManager(entityManager);
		return instance;
	}
	
	
	@Override
	public Type save(Type type) {
		entityManager.persist(type);
		return type;
	}

	@Override
	public Type update(Type type) {
		if(type.getId() == null) { return null; }
		
		entityManager.persist(type);
		return type;
	}

	@Override
	public Type getTypeByName(String name) {
		try {
			return this.entityManager.createQuery(findTypeByNameQuery(), Type.class)
						.setParameter("name", name)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}

	@Override
	public void delete(Long id) {
		try {
			this.entityManager.createQuery(removeTypeById(), Type.class)
						.setParameter("id", id)
						.executeUpdate();
		}catch(RuntimeException e) {}
		
	}
	
	private String removeTypeById() {
		return "DELETE from Type t where t.id = :id";
	}
	
	private String findTypeByNameQuery() {
		return "SELECT t from Type t where t.name = :name";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
