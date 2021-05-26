package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.BillingProductDAO;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public class BillingProductDAOImpl extends DAOImpl<BillingProduct> implements BillingProductDAO {

	
	private static BillingProductDAOImpl instance;
	
	private BillingProductDAOImpl() {
		super.t = BillingProduct.class;
	}
	
	public static BillingProductDAOImpl getInstance() {
		if(instance == null) {
			instance = new BillingProductDAOImpl();
		}
		return instance;
	}
	
	@Override
	public List<BillingProduct> getBillingProductByProduct(Product product) {
		try {
			return super.entityManager.createQuery(findBillingProductByProductQuery(), BillingProduct.class)
						.setParameter("product", product)
						.getResultList();
		}catch(RuntimeException e) {return null;}
	}

	@Override
	public List<BillingProduct> getBillingProductByRestHome(RestHome restHome) {
		try {
			return super.entityManager.createQuery(findBillingProductByRestHomeQuery(), BillingProduct.class)
						.setParameter("restHome", restHome)
						.getResultList();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public List<BillingProduct> getBillingProductByBilling(Billing billing) {
		try {
			return super.entityManager.createQuery(findBillingProductByBillingQuery(), BillingProduct.class)
						.setParameter("billing", billing)
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
	
	private String findBillingProductByRestHomeQuery () {
		return "SELECT bp from BillingProduct bp where bp.restHome = :restHome";
	}
	
	private String findBillingProductByBillingQuery () {
		return "SELECT bp from BillingProduct bp where bp.billing = :billing";
	}
	
	private String findBillingProductByProductQuery() {
		return "SELECT bp from BillingProduct bp where bp.product = :product";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		super.entityManager = entityManager;
	}

}
