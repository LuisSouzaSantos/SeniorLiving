package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface ElderlyDAO {
	Elderly save(Elderly elderly);
	
	Elderly update(Elderly elderly);
	
	Elderly getElderlyByRgAndRestHome(String rg, RestHome resthome);
	
	Elderly getElderlyCPFAndRestHome(String cpf, RestHome resthome);
	
	String delete(Long id);
	
	List<Elderly> getAll();
	
	List<Elderly> getElderlyByRestHome(RestHome restHome);
	
	List<Elderly> getElderlyByAccommodation(Accommodation accommodation);
	
	List<Elderly> getElderlyByPerson(Person person);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
	
}
