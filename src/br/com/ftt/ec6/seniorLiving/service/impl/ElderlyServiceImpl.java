package br.com.ftt.ec6.seniorLiving.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.ElderlyDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.ElderlyDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.service.ElderlyService;
import br.com.ftt.ec6.seniorLiving.utils.MaritalStatus;

public class ElderlyServiceImpl implements ElderlyService {
	
	private static ElderlyServiceImpl instance;
	private static ElderlyDAO elderlyDAO = ElderlyDAOImpl.getInstance();
	
	private ElderlyServiceImpl() {}
	
	public static ElderlyServiceImpl getInstance() {
		if(instance == null) {
			instance = new ElderlyServiceImpl();
		}
		return instance;
	}

	@Override
	public Elderly save(String name, MaritalStatus maritalStatus, String nationality, String rg, String cpf,
			LocalDate birthDate, BigDecimal monthlyPayment, Person curator, Person sympathetic, RestHome restHome, Accommodation accommodation, List<Billing> billingList) {
		
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
		elderly.setAccommodation(accommodation);
		elderly.setBillingList(billingList);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		Elderly newElderly = elderlyDAO.save(elderly);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		elderlyDAO.stopConnection();
		
		return newElderly;
	}
	
	@Override
	public Elderly update(Elderly elderly) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		Elderly elderlyUpdated = elderlyDAO.update(elderly);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		elderlyDAO.stopConnection();
		
		return elderlyUpdated;
	}

	@Override
	public List<Elderly> getElderlyByRestHome(RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		List<Elderly> elderlyList = elderlyDAO.getElderlyByRestHome(restHome);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		elderlyDAO.stopConnection();
		
		return elderlyList;
	}

	@Override
	public String delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		String message = elderlyDAO.delete(id);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		elderlyDAO.stopConnection();
		
		return message;
	}



}
