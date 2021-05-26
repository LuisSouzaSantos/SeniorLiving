package br.com.ftt.ec6.seniorLiving.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.BillingProductDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.BillingProductDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.service.BillingProductService;

public class BillingProductServiceImpl implements BillingProductService {

	private static BillingProductServiceImpl instance;
	private BillingProductDAO billingProductDAO = BillingProductDAOImpl.getInstance();
	
	private BillingProductServiceImpl() {}
	
	public static BillingProductServiceImpl getInstance() {
		if(instance == null) {
			instance = new BillingProductServiceImpl();
		}
		return instance;
	}
	
	@Override
	public BillingProduct save(Billing billing, Product product, Integer quantity, BigDecimal unitaryValue, BigDecimal amount) {
		BillingProduct billingProduct = new BillingProduct();
		billingProduct.setBilling(billing);
		billingProduct.setProduct(product);
		billingProduct.setQuantity(quantity);
		billingProduct.setUnitaryValue(unitaryValue);
		billingProduct.setAmount(amount);
		
		EntityManager entityManager = Database.getConnection();
		billingProductDAO.startConnection(entityManager);
		entityManager.getTransaction().begin();
		
		BillingProduct newBillingProduct = billingProductDAO.save(billingProduct);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		billingProductDAO.stopConnection();
		
		
		return newBillingProduct;
	}

	@Override
	public BillingProduct update(BillingProduct billingProduct) {
		EntityManager entityManager = Database.getConnection();
		billingProductDAO.startConnection(entityManager);
		entityManager.getTransaction().begin();
		
		BillingProduct billingProductUpdated = billingProductDAO.update(billingProduct);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		billingProductDAO.stopConnection();
		
		
		return billingProductUpdated;
	}

	@Override
	public List<BillingProduct> getBillingProductByRestHome(RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		billingProductDAO.startConnection(entityManager);
		entityManager.getTransaction().begin();
		
		List<BillingProduct> billingProductList = billingProductDAO.getBillingProductByRestHome(restHome);
		
		entityManager.close();
		billingProductDAO.stopConnection();
		
		return billingProductList;
	}

	@Override
	public List<BillingProduct> getBillingProductByBilling(Billing billing) {
		EntityManager entityManager = Database.getConnection();
		billingProductDAO.startConnection(entityManager);
		entityManager.getTransaction().begin();
		
		List<BillingProduct> billingProductList = billingProductDAO.getBillingProductByBilling(billing);
		
		entityManager.close();
		billingProductDAO.stopConnection();
		
		return billingProductList;
	}
	
	@Override
	public List<BillingProduct> getBillingProductByProduct(Product product) {
		EntityManager entityManager = Database.getConnection();
		billingProductDAO.startConnection(entityManager);
		entityManager.getTransaction().begin();
		
		List<BillingProduct> billingProductList = billingProductDAO.getBillingProductByProduct(product);
		
		entityManager.close();
		billingProductDAO.stopConnection();
		
		return billingProductList;
	}

	@Override
	public String delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		billingProductDAO.startConnection(entityManager);
		entityManager.getTransaction().begin();
		
		String message = billingProductDAO.delete(id);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		billingProductDAO.stopConnection();
		
		return message;
	}


}
