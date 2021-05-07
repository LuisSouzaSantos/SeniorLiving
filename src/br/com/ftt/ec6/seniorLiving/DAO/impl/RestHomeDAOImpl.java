package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.RestHomeDAO;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public class RestHomeDAOImpl extends DAOImpl<RestHome> implements RestHomeDAO {

	private static RestHomeDAOImpl instance;
	
	private RestHomeDAOImpl() {
		super.t = RestHome.class;
	}
	
	public static RestHomeDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new RestHomeDAOImpl();
		}
		return instance;
	}
	
	@Override
	public void startConnection(EntityManager entityManager) {
		super.entityManager = entityManager;
	}
	
	@Override
	public void stopConnection() {
		super.entityManager = null;
	}

}
