package br.com.ftt.ec6.seniorLiving.DAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.support.BillingSupport;

public interface BillingDAO {
	Billing save(Billing billing);
	
	Billing update(Billing billing);
	
	List<Billing> getBillingByRestHome(RestHome restHome);
	
	List<BillingSupport> getBillingSupportByRestHome(RestHome restHome);
	
	String delete(Long id);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
	
	List<BillingSupport> filter(String elderlyName, RestHome restHome, LocalDate minDate, LocalDate maxDate, BigDecimal maxValue);
}
