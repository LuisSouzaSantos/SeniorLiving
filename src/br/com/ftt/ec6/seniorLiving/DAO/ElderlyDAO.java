package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface ElderlyDAO {
	Elderly save(Elderly elderly);
	
	Elderly update(Elderly elderly);
	
	String delete(Long id);
	
	List<Elderly> getAll();
	
	List<Elderly> getElderlyByRestHome(RestHome restHome);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
	
}
