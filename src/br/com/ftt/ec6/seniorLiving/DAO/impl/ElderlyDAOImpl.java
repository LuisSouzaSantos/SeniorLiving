package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.ElderlyDAO;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;

public class ElderlyDAOImpl implements ElderlyDAO {
	
	private static ElderlyDAOImpl instance;
	private EntityManager entityManager;
	
	private ElderlyDAOImpl() {}
	
	public static ElderlyDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new ElderlyDAOImpl();
		}
		instance.setEntityManager(entityManager);
		return instance;
	}

	@Override
	public Elderly save(Elderly elderly) {
		entityManager.persist(elderly);
		return elderly;
	}

	@Override
	public void delete(Long id) {
		try {
			this.entityManager.createQuery(removeElderlyById(), Elderly.class)
						.setParameter("id", id)
						.executeUpdate();
		}catch(RuntimeException e) {}
	}
	
	private String removeElderlyById() {
		return "DELETE from Elderly e where e.id = :id";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
