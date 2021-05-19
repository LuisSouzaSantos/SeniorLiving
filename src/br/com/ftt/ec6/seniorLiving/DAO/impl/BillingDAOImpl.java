package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.BillingDAO;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public class BillingDAOImpl extends DAOImpl<Billing> implements BillingDAO {

	private static BillingDAOImpl instance;
	
	private BillingDAOImpl() {
		super.t = Billing.class;
	}
	
	public static BillingDAOImpl getInstance() {
		if(instance == null) {
			instance = new BillingDAOImpl();
		}
		return instance;
	}
	

	@Override
	public List<Billing> getBillingByRestHome(RestHome restHome) {
		try {
			return super.entityManager.createQuery(getBillingByRestHomeQuery(), Billing.class)
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
	
	private String getBillingByRestHomeQuery() {
		return "SELECT b from Billing b where b.restHome = :restHome";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


}
