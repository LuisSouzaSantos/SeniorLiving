package br.com.ftt.ec6.seniorLiving.service.impl;

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
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		billingDAO.startConnection(entityManager);
		
		
		entityManager.getTransaction().commit();
		entityManager.close();
		billingDAO.stopConnection();
		
		
		return null;
	}
	
	
	@Override
	public Billing update(Billing billing) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		billingDAO.startConnection(entityManager);
		
		
		entityManager.getTransaction().commit();
		entityManager.close();
		billingDAO.stopConnection();
		return null;
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

	

}
