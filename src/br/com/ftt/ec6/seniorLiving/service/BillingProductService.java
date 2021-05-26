package br.com.ftt.ec6.seniorLiving.service;

import java.math.BigDecimal;
import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface BillingProductService {
	
	BillingProduct save(Billing billing, Product product, Integer quantity, BigDecimal unitaryValue, BigDecimal amount);
	
	BillingProduct update(BillingProduct billingProduct);
	
	List<BillingProduct> getBillingProductByRestHome(RestHome restHome);
	
	List<BillingProduct> getBillingProductByBilling(Billing billing);
	
	List<BillingProduct> getBillingProductByProduct(Product product);

	String delete(Long id);

}
