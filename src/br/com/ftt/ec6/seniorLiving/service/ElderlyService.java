package br.com.ftt.ec6.seniorLiving.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface ElderlyService {
	Elderly save(String name, String maritalStatus, String nationality, String rg, String cpf, LocalDate birthDate, 
			BigDecimal monthlyPayment, Person curator, Person sympathetic, RestHome restHome, List<Billing> billingList);
	
	void delete(Long id);
}
