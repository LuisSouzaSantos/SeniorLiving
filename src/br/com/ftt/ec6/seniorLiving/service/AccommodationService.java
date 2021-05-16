package br.com.ftt.ec6.seniorLiving.service;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.exception.AccommodationException;

public interface AccommodationService {
	Accommodation save(String name, String description, RestHome restHome) throws AccommodationException;
	
	Accommodation update(Accommodation accommodation) throws AccommodationException;
	
	Accommodation getAccommodationByName(String name);
	
	List<Accommodation> getAll();
	
	List<Accommodation> getAccommodationByRestHome(RestHome restHome);

	String delete(Long id);
}
