package br.com.ftt.ec6.seniorLiving.DAO;

import br.com.ftt.ec6.seniorLiving.entities.Billing;

public interface BillingDAO {
	Billing save(Billing billing);
	
	void delete(Long id);
}
