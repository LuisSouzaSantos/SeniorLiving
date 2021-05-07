package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

public abstract class DAOImpl<T> {
	
	protected EntityManager entityManager;
	protected Class<T> t;

	public T save(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	public String delete(Long id) {
		try {
			T entity = entityManager.find(t, id);
//			entityManager.createQuery(removeByIdQuery())
//						.setParameter("id", id)
//						.executeUpdate();,
			entityManager.remove(entity);
			return "SUCESS";
		}catch(RuntimeException e) {
			return "ERROR";
		}
	}
	
	public List<T> getAll(){
		try {
			return entityManager.createQuery(getAllQuery(), t).getResultList();
		}catch(RuntimeException e) {
			return new ArrayList<T>();
		}
	}

	public T update(T entity) {
		T entityUpdated = entityManager.merge(entity);
		return entityUpdated;
	}

	public T getById(Long id) {
		return entityManager.createQuery(getByIdQuery(), t).getSingleResult();
	}
	
	private String getAllQuery() {
		return "SELECT t FROM "+t.getName()+" t";
	}
	
	private String getByIdQuery() {
		return "SELECT t FROM "+t.getName()+" t where t.id = :id";
	}
	
	private String removeByIdQuery() {
		return "DELETE FROM "+t.getName()+" t where t.id = :id";
	}

}
