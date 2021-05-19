package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.ElderlyDAO;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public class ElderlyDAOImpl extends DAOImpl<Elderly> implements ElderlyDAO {
	
	private static ElderlyDAOImpl instance;
	
	private ElderlyDAOImpl() {
		super.t = Elderly.class;
	}
	
	public static ElderlyDAOImpl getInstance() {
		if(instance == null) {
			instance = new ElderlyDAOImpl();
		}
		return instance;
	}
	
	@Override
	public List<Elderly> getElderlyByRestHome(RestHome restHome) {
		try {
			return super.entityManager.createQuery(getElderlyByRestHomeQuery(), Elderly.class)
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
	
	private String getElderlyByRestHomeQuery() {
		return "SELECT e from Elderly e where e.restHome = :restHome";
	}

}
