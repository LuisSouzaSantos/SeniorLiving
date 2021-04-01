package br.com.ftt.ec6.seniorLiving.DAO;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;

public interface AccommodationDAO {
	Accommodation save(Accommodation accommodation);
	
	Accommodation update(Accommodation accommodation);
	
	Accommodation getAccommodationByName(String name);
	
	void delete(Long id);
}
