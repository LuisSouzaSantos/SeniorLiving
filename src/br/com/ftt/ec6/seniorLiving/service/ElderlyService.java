package br.com.ftt.ec6.seniorLiving.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.utils.MaritalStatus;

public interface ElderlyService {
	Elderly save(String name, MaritalStatus maritalStatus, String nationality, String rg, String cpf, LocalDate birthDate, 
			BigDecimal monthlyPayment, Person curator, Person sympathetic, RestHome restHome, Accommodation accommodation, List<Billing> billingList);
	
	Elderly update(Elderly elderly);
	
	List<Elderly> getElderlyByRestHome(RestHome restHome);
	
	String delete(Long id);
}
