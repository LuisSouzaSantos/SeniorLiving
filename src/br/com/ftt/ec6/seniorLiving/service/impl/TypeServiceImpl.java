package br.com.ftt.ec6.seniorLiving.service.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.TypeDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.TypeDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.exception.TypeException;
import br.com.ftt.ec6.seniorLiving.service.TypeService;

public class TypeServiceImpl implements TypeService {

	@Override
	public Type save(String name) throws TypeException {
		if(name == null || name.trim().isEmpty()) { throw new TypeException("Tipo inválido"); }
		
		Type similarType = getTypeByName(name);
		
		if(similarType != null) { throw new TypeException("Tipo já existe"); }
		
		Type type = new Type();
		type.setName(name);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		TypeDAO typeDAO = TypeDAOImpl.getInstance(entityManager);
		Type newType = typeDAO.save(type);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return newType;
	}

	@Override
	public Type update(Type typeUpdate) throws TypeException {
		if(typeUpdate == null || typeUpdate.getName().trim().isEmpty()) { throw new TypeException("Tipo inválido"); }
		
		Type similarType = getTypeByName(typeUpdate.getName());
		
		if(similarType != null && similarType.getId().equals(typeUpdate.getId())) { throw new TypeException("Tipo já existe"); }
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		TypeDAO typeDAO = TypeDAOImpl.getInstance(entityManager);
		Type type = typeDAO.update(typeUpdate);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return type;
	}

	@Override
	public Type getTypeByName(String name) {
		EntityManager entityManager = Database.getConnection();
		TypeDAO typeDAO = TypeDAOImpl.getInstance(entityManager);
		Type type = typeDAO.getTypeByName(name);
		entityManager.close();
		
		return type;
	}

	@Override
	public void delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		TypeDAO typeDAO = TypeDAOImpl.getInstance(entityManager);
		typeDAO.delete(id);
		entityManager.close();
	}

}
