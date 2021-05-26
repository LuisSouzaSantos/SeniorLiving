package br.com.ftt.ec6.seniorLiving.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.BillingDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.BillingDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.support.BillingSupport;
import br.com.ftt.ec6.seniorLiving.exception.BillingException;
import br.com.ftt.ec6.seniorLiving.service.BillingService;

public class BillingServiceImpl implements BillingService {

	private static BillingServiceImpl instance;
	private static BillingDAO billingDAO = BillingDAOImpl.getInstance();
	
	private BillingServiceImpl() {}
	
	public static BillingServiceImpl getInstance() {
		if(instance == null) {
			instance = new BillingServiceImpl();
		}
		return instance;
	}
	
	@Override
	public Billing save(Elderly elderly, RestHome restHome, LocalDate month,
			List<BillingProduct> billingProductList) throws BillingException {
		
		if(elderly == null || restHome == null || month == null) { return null; }
		
		Billing billing = new Billing();
		billing.setElderly(elderly);
		billing.setRestHome(restHome);
		billing.setMonth(month);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		billingDAO.startConnection(entityManager);
		
		Billing newBilling = billingDAO.save(billing);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		billingDAO.stopConnection();
		
		
		return newBilling;
	}
	
	
	@Override
	public Billing update(Billing billing) {
		
		if(billing.getElderly() == null || billing.getRestHome() == null || billing.getMonth() == null) { return null; }
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		billingDAO.startConnection(entityManager);
		
		Billing updateBilling = billingDAO.update(billing);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		billingDAO.stopConnection();
		
		return updateBilling;
	}

	@Override
	public String delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		billingDAO.startConnection(entityManager);
		
		String message = billingDAO.delete(id);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		billingDAO.stopConnection();
		
		return message;
	}

	@Override
	public List<Billing> getBillingByRestHome(RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		billingDAO.startConnection(entityManager);
		
		List<Billing> billingList = billingDAO.getBillingByRestHome(restHome);
		
		entityManager.close();
		billingDAO.stopConnection();
		
		return billingList;
	}

	@Override
	public List<BillingSupport> getBillingSupportByRestHome(RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		billingDAO.startConnection(entityManager);
		
		List<BillingSupport> billingList = billingDAO.getBillingSupportByRestHome(restHome);
		
		entityManager.close();
		billingDAO.stopConnection();
		
		return billingList;
	}
	
	public List<BillingSupport> billingFilter(String elderlyName, RestHome restHome, LocalDate minDate, LocalDate maxDate, Double maxValue){
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		billingDAO.startConnection(entityManager);
		
		List<BillingSupport> billingList = billingDAO.filter(elderlyName, restHome, minDate, maxDate, new BigDecimal(maxValue));
		
		entityManager.close();
		billingDAO.stopConnection();
		
		return billingList;
	}

}
