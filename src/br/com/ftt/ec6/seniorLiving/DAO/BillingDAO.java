package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface BillingDAO {
	Billing save(Billing billing);
	
	Billing update(Billing billing);
	
	List<Billing> getBillingByRestHome(RestHome restHome);
	
	String delete(Long id);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
}
