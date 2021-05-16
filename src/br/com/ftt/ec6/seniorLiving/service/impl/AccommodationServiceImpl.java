package br.com.ftt.ec6.seniorLiving.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.AccommodationDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.AccommodationDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.exception.AccommodationException;
import br.com.ftt.ec6.seniorLiving.service.AccommodationService;

public class AccommodationServiceImpl implements AccommodationService {

	private static AccommodationServiceImpl instance;
	private AccommodationDAO accommodationDAO = AccommodationDAOImpl.getInstance();
	
	private AccommodationServiceImpl() {}
	
	public static AccommodationServiceImpl getInstance() {
		if(instance == null) {
			instance = new AccommodationServiceImpl();
		}
		return instance;
	}

	@Override
	public Accommodation save(String name, String description, RestHome restHome) throws AccommodationException {
		validateAccommodationParameters(name, description);
		
		Accommodation similarAccommodation = getAccommodationByName(name);
		
		if(similarAccommodation != null) { throw new AccommodationException("Acomodação já existe"); }
		
		Accommodation accommodation = new Accommodation();
		accommodation.setName(name);
		accommodation.setDescription(description);
		accommodation.setRestHome(restHome);
		
		EntityManager entityManager = Database.getConnection();
		accommodationDAO.startConnection(entityManager);
		entityManager.getTransaction().begin();
		
		Accommodation newAccommodation = accommodationDAO.save(accommodation);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		accommodationDAO.stopConnection();
		
		return newAccommodation;
	}

	@Override
	public Accommodation update(Accommodation accommodationUpdate) throws AccommodationException {
		if(accommodationUpdate == null || accommodationUpdate.getName().trim().isEmpty()) { throw new AccommodationException("Acomodação inválida"); }
		
		if(accommodationUpdate.getDescription().trim().isEmpty()) { throw new AccommodationException("Descrição inválida"); }
		
		Accommodation similarAccommodation = getAccommodationByName(accommodationUpdate.getName());
		
		if(similarAccommodation != null && similarAccommodation.getId().equals(accommodationUpdate.getId()) == false) { throw new AccommodationException("Role já existe"); }
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		accommodationDAO.startConnection(entityManager);
		
		Accommodation accommodation = accommodationDAO.update(accommodationUpdate);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		accommodationDAO.stopConnection();
		
		return accommodation;
	}

	@Override
	public Accommodation getAccommodationByName(String name) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		accommodationDAO.startConnection(entityManager);
		
		Accommodation Accommodation = accommodationDAO.getAccommodationByName(name);
		
		entityManager.close();
		accommodationDAO.stopConnection();
		
		return Accommodation;
	}

	@Override
	public String delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		accommodationDAO.startConnection(entityManager);
		
		String message = accommodationDAO.delete(id);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		accommodationDAO.stopConnection();
		
		return message;
	}

	@Override
	public List<Accommodation> getAll() {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		accommodationDAO.startConnection(entityManager);
		 
		List<Accommodation> accommodationList = accommodationDAO.getAll();
		
		entityManager.close();
		accommodationDAO.stopConnection();
		
		return accommodationList;
	}
	
	@Override
	public List<Accommodation> getAccommodationByRestHome(RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		accommodationDAO.startConnection(entityManager);
		
		List<Accommodation> accommodationList = accommodationDAO.getAccommodationByRestHome(restHome);
		
		entityManager.close();
		accommodationDAO.stopConnection();
		
		return accommodationList;
	}
	
	private void validateAccommodationParameters(String name, String description) throws AccommodationException {
		if(name == null || name.trim().isEmpty()) { throw new AccommodationException("Acomodação inválida"); }
		
		if(description == null || description.trim().isEmpty()) { throw new AccommodationException("Descrição inválida"); }
	}



}
