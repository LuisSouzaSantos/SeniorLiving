package br.com.ftt.ec6.seniorLiving.service;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.exception.TypeException;

public interface TypeService {
	Type save(String name, RestHome restHome) throws TypeException;
	
	Type update(Type type) throws TypeException;
	
	Type getTypeByName(String name);
	
	List<Type> getTypeByRestHome(RestHome resthome);
	
	String delete(Long id);
}
