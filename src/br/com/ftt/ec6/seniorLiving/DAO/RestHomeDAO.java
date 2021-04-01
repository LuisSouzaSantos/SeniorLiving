package br.com.ftt.ec6.seniorLiving.DAO;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface RestHomeDAO {
	
	RestHome save(RestHome restHome);
	
	void delete(Long id);

}
