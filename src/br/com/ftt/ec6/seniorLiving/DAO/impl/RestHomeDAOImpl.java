package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.RestHomeDAO;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.User;

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

	@Override
	public List<RestHome> getRestHomeByAdmin(User user) {
		try {
			return super.entityManager.createQuery(getRestHomeByAdminQuery(), RestHome.class)
					.setParameter("admin", user)
					.getResultList();
		}catch(RuntimeException e) {
			return null;
		}
	}
	
	private String getRestHomeByAdminQuery() {
		return "SELECT rh from RestHome rh where rh.admin = :admin";
	}
	
}
