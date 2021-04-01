package br.com.ftt.ec6.seniorLiving.service.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.AccommodationDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.AccommodationDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.exception.AccommodationException;
import br.com.ftt.ec6.seniorLiving.service.AccommodationService;

public class AccommodationServiceImpl implements AccommodationService {

	private static AccommodationServiceImpl instance;
	
	private AccommodationServiceImpl() {}
	
	public static AccommodationServiceImpl getInstance() {
		if(instance == null) {
			instance = new AccommodationServiceImpl();
		}
		return instance;
	}

	@Override
	public Accommodation save(String name, String description) throws AccommodationException {
		if(name == null || name.trim().isEmpty()) { throw new AccommodationException("Acomoda��o inv�lida"); }
		
		if(description == null || description.trim().isEmpty()) { throw new AccommodationException("Descri��o inv�lida"); }
		
		Accommodation similarAccommodation = getAccommodationByName(name);
		
		if(similarAccommodation != null) { throw new AccommodationException("Acomoda��o j� existe"); }
		
		Accommodation accommodation = new Accommodation();
		accommodation.setName(name);
		accommodation.setDescription(description);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		AccommodationDAO AccommodationDAO = AccommodationDAOImpl.getInstance(entityManager);
		Accommodation newAccommodation = AccommodationDAO.save(accommodation);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return newAccommodation;
	}

	@Override
	public Accommodation update(Accommodation accommodationUpdate) throws AccommodationException {
		if(accommodationUpdate == null || accommodationUpdate.getName().trim().isEmpty()) { throw new AccommodationException("Acomoda��o inv�lidA"); }
		
		if(accommodationUpdate.getDescription().trim().isEmpty()) { throw new AccommodationException("Descri��o inv�lida"); }
		
		Accommodation similarAccommodation = getAccommodationByName(accommodationUpdate.getLinkedToDatabase());
		
		if(similarAccommodation != null && similarAccommodation.getId().equals(accommodationUpdate.getId())) { throw new AccommodationException("Role j� existe"); }
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		AccommodationDAO AccommodationDAO = AccommodationDAOImpl.getInstance(entityManager);
		Accommodation accommodation = AccommodationDAO.update(accommodationUpdate);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return accommodation;
	}

	@Override
	public Accommodation getAccommodationByName(String name) {
		EntityManager entityManager = Database.getConnection();
		AccommodationDAO AccommodationDAO = AccommodationDAOImpl.getInstance(entityManager);
		Accommodation Accommodation = AccommodationDAO.getAccommodationByName(name);
		entityManager.close();
		return Accommodation;
	}

	@Override
	public void delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		AccommodationDAO AccommodationDAO = AccommodationDAOImpl.getInstance(entityManager);
		AccommodationDAO.delete(id);
		entityManager.close();
	}

}
