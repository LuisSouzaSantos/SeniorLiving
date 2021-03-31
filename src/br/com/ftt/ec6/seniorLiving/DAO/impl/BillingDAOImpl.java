package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.BillingDAO;
import br.com.ftt.ec6.seniorLiving.entities.Billing;

public class BillingDAOImpl implements BillingDAO {

	private static BillingDAOImpl instance;
	private EntityManager entityManager;
	
	private BillingDAOImpl() {}
	
	public static BillingDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new BillingDAOImpl();
		}
		instance.setEntityManager(entityManager);
		return instance;
	}
	
	@Override
	public Billing save(Billing billing) {
		entityManager.persist(billing);
		return billing;
	}

	@Override
	public void delete(Long id) {
		try {
			this.entityManager.createQuery(removeBillingtById(), Billing.class)
						.setParameter("id", id)
						.executeUpdate();
		}catch(RuntimeException e) {}	
	}
	
	private String removeBillingtById() {
		return "DELETE from Billing b where b.id = :id";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
