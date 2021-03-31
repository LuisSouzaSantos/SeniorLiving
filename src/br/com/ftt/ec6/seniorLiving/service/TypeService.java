package br.com.ftt.ec6.seniorLiving.service;

import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.exception.TypeException;

public interface TypeService {
	Type save(String name) throws TypeException;
	
	Type update(Type type) throws TypeException;
	
	Type getTypeByName(String name);
	
	void delete(Long id);
}
