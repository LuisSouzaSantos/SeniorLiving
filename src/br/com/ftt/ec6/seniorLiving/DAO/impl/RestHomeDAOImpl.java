package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.RestHomeDAO;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public class RestHomeDAOImpl implements RestHomeDAO {

	private static RestHomeDAOImpl instance;
	private EntityManager entityManager;
	
	private RestHomeDAOImpl() {}
	
	public static RestHomeDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new RestHomeDAOImpl();
		}
		instance.setEntityManager(entityManager);
		return instance;
	}
	
	
	@Override
	public RestHome save(RestHome restHome) {
		entityManager.persist(restHome);
		return restHome;
	}

	@Override
	public void delete(Long id) {
		try {
			this.entityManager.createQuery(removeRestHomeById(), RestHome.class)
						.setParameter("id", id)
						.executeUpdate();
		}catch(RuntimeException e) {}
	}
	
	private String removeRestHomeById() {
		return "DELETE from RestHome rh where rh.id = :id";
	}
	
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
