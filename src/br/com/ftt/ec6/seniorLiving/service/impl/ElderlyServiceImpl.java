package br.com.ftt.ec6.seniorLiving.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.ElderlyDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.ElderlyDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.service.ElderlyService;

public class ElderlyServiceImpl implements ElderlyService {
	
	private static ElderlyServiceImpl instance;
	
	private ElderlyServiceImpl() {}
	
	public static ElderlyServiceImpl getInstance() {
		if(instance == null) {
			instance = new ElderlyServiceImpl();
		}
		return instance;
	}

	@Override
	public Elderly save(String name, String maritalStatus, String nationality, String rg, String cpf,
			LocalDate birthDate, BigDecimal monthlyPayment, Person curator, Person sympathetic, RestHome restHome, List<Billing> billingList) {
		
		Elderly elderly = new Elderly();
		elderly.setName(name);
		elderly.setMaritalStatus(maritalStatus);
		elderly.setNationality(nationality);
		elderly.setRg(rg);
		elderly.setCpf(cpf);
		elderly.setBirthDate(birthDate);
		elderly.setMonthlyPayment(monthlyPayment);
		elderly.setCurator(curator);
		elderly.setSympathetic(sympathetic);
		elderly.setRestHome(restHome);
		elderly.setBillingList(billingList);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		ElderlyDAO elderlyDAO = ElderlyDAOImpl.getInstance(entityManager);
		Elderly newElderly = elderlyDAO.save(elderly);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return newElderly;
	}

	@Override
	public void delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		ElderlyDAO elderlyDAO = ElderlyDAOImpl.getInstance(entityManager);
		elderlyDAO.delete(id);
		entityManager.close();
	}

}
