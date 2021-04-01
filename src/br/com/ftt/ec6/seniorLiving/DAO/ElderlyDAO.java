package br.com.ftt.ec6.seniorLiving.DAO;

import br.com.ftt.ec6.seniorLiving.entities.Elderly;

public interface ElderlyDAO {
	Elderly save(Elderly elderly);
	
	void delete(Long id);
}
