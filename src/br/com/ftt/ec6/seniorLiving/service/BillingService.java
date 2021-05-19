package br.com.ftt.ec6.seniorLiving.service;

import java.time.LocalDate;
import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.exception.BillingException;

public interface BillingService {
	
	Billing save(Elderly elderly, RestHome restHome, LocalDate month, List<BillingProduct> billingProductList) throws BillingException;
	
	Billing update(Billing billing);
	
	String delete(Long id);

}
