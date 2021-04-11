package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

public abstract class DAOImpl<T> {
	
	protected static EntityManager entityManager;

	public T save(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	public void delete(Class<T> classInUse, Long id) {
		try {
			DAOImpl.entityManager.createQuery(removeById(classInUse), classInUse)
						.setParameter("id", id)
						.executeUpdate();
		}catch(RuntimeException e) {}
	}

	public T update(T entity) {
		T entityUpdated = entityManager.merge(entity);
		return entityUpdated;
	}

	public T getById(Class<T> classInUse, Long id) {
		return entityManager.find(classInUse, id);
	}
	
	private String removeById(Class<T> classInUse) {
		return "DELETE from "+ classInUse.getName() + "e where e.id = :id";
	}

}
