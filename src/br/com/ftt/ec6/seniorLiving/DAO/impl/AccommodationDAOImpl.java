package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.AccommodationDAO;
import br.com.ftt.ec6.seniorLiving.entities.Accommodation;

public class AccommodationDAOImpl extends DAOImpl<Accommodation> implements AccommodationDAO {

	private static AccommodationDAOImpl instance;
	
	private AccommodationDAOImpl() {
		super.t = Accommodation.class;
	}
	
	public static AccommodationDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new AccommodationDAOImpl();
		}
		instance.setEntityManager(entityManager);
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
	public void delete(Long id) {
		try {
			super.entityManager.createQuery(removeAccommodationById(), Accommodation.class)
						.setParameter("id", id)
						.executeUpdate();
		}catch(RuntimeException e) {}
		
	}
	
	private String removeAccommodationById() {
		return "DELETE from Accommodation a where a.id = :id";
	}
	
	private String findAccommodationByNameQuery() {
		return "SELECT a from Accommodation a where a.name = :name";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		super.entityManager = entityManager;
	}

}
