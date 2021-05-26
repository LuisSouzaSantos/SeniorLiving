package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.AccommodationDAO;
import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public class AccommodationDAOImpl extends DAOImpl<Accommodation> implements AccommodationDAO {

	private static AccommodationDAOImpl instance;
	
	private AccommodationDAOImpl() {
		super.t = Accommodation.class;
	}
	
	public static AccommodationDAOImpl getInstance() {
		if(instance == null) {
			instance = new AccommodationDAOImpl();
		}
		return instance;
	}

	@Override
	public Accommodation getAccommodationByName(String name) {
		try {
			return super.entityManager.createQuery(findAccommodationByNameQuery(), Accommodation.class)
						.setParameter("name", name)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public Accommodation getAccommodationByNameAndRestHome(String name, RestHome restHome) {
		try {
			return super.entityManager.createQuery(findAccommodationByNameAndRestHomeQuery(), Accommodation.class)
						.setParameter("name", name)
						.setParameter("restHome", restHome)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public List<Accommodation> getAccommodationByRestHome(RestHome restHome) {
		try {
			return super.entityManager.createQuery(findAccommodationByRestHomeQuery(), Accommodation.class)
						.setParameter("restHome", restHome)
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
	
	private String findAccommodationByNameQuery() {
		return "SELECT a from Accommodation a where a.name = :name";
	}
	
	private String findAccommodationByRestHomeQuery () {
		return "SELECT a from Accommodation a where a.restHome = :restHome";
	}
	
	private String findAccommodationByNameAndRestHomeQuery() {
		return "SELECT a from Accommodation a where a.name = :name AND a.restHome = :restHome";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		super.entityManager = entityManager;
	}
}
