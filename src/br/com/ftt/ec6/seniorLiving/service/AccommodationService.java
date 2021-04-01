package br.com.ftt.ec6.seniorLiving.service;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.exception.AccommodationException;

public interface AccommodationService {
	Accommodation save(String name, String description) throws AccommodationException;
	
	Accommodation update(Accommodation accommodation) throws AccommodationException;
	
	Accommodation getAccommodationByName(String name);
	
	void delete(Long id);
}
