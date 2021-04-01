package br.com.ftt.ec6.seniorLiving.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.BillingDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.BillingDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.exception.BillingException;
import br.com.ftt.ec6.seniorLiving.service.BillingService;

public class BillingServiceImpl implements BillingService {

	private static BillingServiceImpl instance;
	
	private BillingServiceImpl() {}
	
	public static BillingServiceImpl getInstance() {
		if(instance == null) {
			instance = new BillingServiceImpl();
		}
		return instance;
	}
	
	@Override
	public Billing save(String registrationCode, RestHome restHome, LocalDate month,
			List<BillingProduct> billingProductList) throws BillingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		BillingDAO billingDAO = BillingDAOImpl.getInstance(entityManager);
		billingDAO.delete(id);
		entityManager.close();
	}

}
