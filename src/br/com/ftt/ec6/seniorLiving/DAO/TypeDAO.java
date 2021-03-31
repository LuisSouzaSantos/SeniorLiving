package br.com.ftt.ec6.seniorLiving.DAO;

import br.com.ftt.ec6.seniorLiving.entities.Type;

public interface TypeDAO {
	Type save(Type type);
	
	Type update(Type type);
	
	Type getTypeByName(String name);
	
	void delete(Long id);
}
