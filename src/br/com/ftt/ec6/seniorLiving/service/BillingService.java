package br.com.ftt.ec6.seniorLiving.service;

import java.time.LocalDate;
import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.support.BillingSupport;
import br.com.ftt.ec6.seniorLiving.exception.BillingException;

public interface BillingService {
	
	Billing save(Elderly elderly, RestHome restHome, LocalDate month, List<BillingProduct> billingProductList) throws BillingException;
	
	Billing update(Billing billing);
	
	List<Billing> getBillingByRestHome(RestHome restHome);
	
	List<BillingSupport> getBillingSupportByRestHome(RestHome restHome);
	
	List<BillingSupport> billingFilter(String elderlyName, RestHome restHome, LocalDate minDate, LocalDate maxDate, Double maxValue);
	
	String delete(Long id);
}
