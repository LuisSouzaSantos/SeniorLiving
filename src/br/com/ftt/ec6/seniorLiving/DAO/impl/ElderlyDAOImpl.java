package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.ElderlyDAO;
import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public class ElderlyDAOImpl extends DAOImpl<Elderly> implements ElderlyDAO {
	
	private static ElderlyDAOImpl instance;
	
	private ElderlyDAOImpl() {
		super.t = Elderly.class;
	}
	
	public static ElderlyDAOImpl getInstance() {
		if(instance == null) {
			instance = new ElderlyDAOImpl();
		}
		return instance;
	}
	
	@Override
	public Elderly getElderlyByRgAndRestHome(String rg, RestHome resthome) {
		try {
			return super.entityManager.createQuery(findElderlyByRGAndRestHomeQuery(), Elderly.class)
						.setParameter("rg", rg)
						.setParameter("resthome", resthome)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}

	@Override
	public Elderly getElderlyCPFAndRestHome(String cpf, RestHome resthome) {
		try {
			return super.entityManager.createQuery(findElderlyByCPFAndRestHomeQuery(), Elderly.class)
						.setParameter("cpf", cpf)
						.setParameter("resthome", resthome)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public List<Elderly> getElderlyByPerson(Person person) {
		try {
			return super.entityManager.createQuery(findEldelyByPersonQuery(), Elderly.class)
						.setParameter("person", person)
						.getResultList();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public List<Elderly> getElderlyByRestHome(RestHome restHome) {
		try {
			return super.entityManager.createQuery(findElderlyByRestHomeQuery(), Elderly.class)
						.setParameter("restHome", restHome)
						.getResultList();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public List<Elderly> getElderlyByAccommodation(Accommodation accommodation) {
		try {
			return super.entityManager.createQuery(findEldelyByAccommodationQuery(), Elderly.class)
						.setParameter("accommodation", accommodation)
						.getResultList();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public void startConnection(EntityManager entityManager) {
		instance.setEntityManager(entityManager);
	}

	@Override
	public void stopConnection() {
		super.entityManager = null;
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private String findElderlyByRestHomeQuery() {
		return "SELECT e from Elderly e where e.restHome = :restHome";
	}
	
	private String findEldelyByAccommodationQuery() {
		return "SELECT e from Elderly e where e.accommodation = :accommodation";
	}
	
	private String findEldelyByPersonQuery() {
		return "SELECT e from Elderly e where e.person = :person";
	}
	
	private String findElderlyByRGAndRestHomeQuery() {
		return "SELECT e from Elderly e WHERE e.rg = :rg AND e.restHome = :restHome";
	}
	
	private String findElderlyByCPFAndRestHomeQuery() {
		return "SELECT e from Elderly e WHERE e.cpf = :cpf AND e.restHome = :restHome";
	}

}
