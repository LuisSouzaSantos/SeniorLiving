package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.AccommodationDAO;
import br.com.ftt.ec6.seniorLiving.entities.Accommodation;

public class AccommodationDAOImpl implements AccommodationDAO {

	private static AccommodationDAOImpl instance;
	private EntityManager entityManager;
	
	private AccommodationDAOImpl() {}
	
	public static AccommodationDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new AccommodationDAOImpl();
		}
		instance.setEntityManager(entityManager);
		return instance;
	}
	
	@Override
	public Accommodation save(Accommodation accommodation) {
		entityManager.persist(accommodation);
		return accommodation;
	}

	@Override
	public Accommodation update(Accommodation accommodation) {
		if(accommodation.getId() == null) { return null; }
		
		entityManager.persist(accommodation);
		return accommodation;
	}

	@Override
	public Accommodation getAccommodationByName(String name) {
		try {
			return this.entityManager.createQuery(findAccommodationByNameQuery(), Accommodation.class)
						.setParameter("name", name)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}

	@Override
	public void delete(Long id) {
		try {
			this.entityManager.createQuery(removeAccommodationById(), Accommodation.class)
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
		this.entityManager = entityManager;
	}

}
