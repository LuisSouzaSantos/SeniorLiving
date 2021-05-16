package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface AccommodationDAO {
	Accommodation save(Accommodation accommodation);
	
	Accommodation update(Accommodation accommodation);
	
	Accommodation getAccommodationByName(String name);
		
	String delete(Long id);
	
	List<Accommodation> getAll();
	
	List<Accommodation> getAccommodationByRestHome(RestHome restHome);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
}
