package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.ftt.ec6.seniorLiving.DAO.BillingDAO;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.support.BillingSupport;

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
	public List<BillingSupport> getBillingSupportByRestHome(RestHome restHome) {
		try {
			return super.entityManager.createQuery(getBillingSupportByRestHomeQuery(), BillingSupport.class)
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
	
	private String getBillingSupportByRestHomeQuery() {
		return "SELECT new br.com.ftt.ec6.seniorLiving.entities.support.BillingSupport(b.id, b.month, b.elderly, SUM(bp.amount), b) FROM Billing b JOIN b.billingProductList bp where b.restHome = :restHome group by b.id";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Override
	public List<BillingSupport> filter(String elderlyName, RestHome restHome, LocalDate minDate, LocalDate maxDate, BigDecimal maxValue) {
		String billingFilter = "SELECT new br.com.ftt.ec6.seniorLiving.entities.support.BillingSupport(b.id, b.month, b.elderly, SUM(bp.amount), b) FROM Billing b JOIN b.billingProductList bp WHERE 1=1 AND b.restHome = :restHome";
		
		if(elderlyName != null && elderlyName.trim().isEmpty() == false) {
			billingFilter+=" AND b.elderly.name LIKE CONCAT('%',:elderly,'%') ";
		}
		
		billingFilter+= " AND b.month BETWEEN :minDate AND :maxDate ";
		billingFilter+= " GROUP BY b.id ";
		billingFilter+= " HAVING SUM(bp.amount) <= :maxValue ";
		
		TypedQuery<BillingSupport> billingFilterQuery = super.entityManager.createQuery(billingFilter, BillingSupport.class);
		
		if(elderlyName != null && elderlyName.trim().isEmpty() == false) {
			billingFilterQuery.setParameter("elderly", elderlyName);
		}
		
		billingFilterQuery.setParameter("restHome", restHome);
		billingFilterQuery.setParameter("minDate", minDate);
		billingFilterQuery.setParameter("maxDate", maxDate);
		billingFilterQuery.setParameter("maxValue", maxValue);
		
				
		return billingFilterQuery.getResultList();
	}

}
