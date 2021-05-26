package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface BillingProductDAO {

	BillingProduct save(BillingProduct billingProduct);
	
	BillingProduct update(BillingProduct billingProduct);
		
	String delete(Long id);
	
	List<BillingProduct> getAll();
	
	List<BillingProduct> getBillingProductByRestHome(RestHome restHome);
	
	List<BillingProduct> getBillingProductByBilling(Billing billing);
	
	List<BillingProduct> getBillingProductByProduct(Product product);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
	
}
