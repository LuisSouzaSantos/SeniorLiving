package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.RoleDAO;
import br.com.ftt.ec6.seniorLiving.entities.Role;

public class RoleDAOImpl implements RoleDAO {

	
	private static RoleDAOImpl instance;
	private EntityManager entityManager;
	
	private RoleDAOImpl() {}
	
	public static RoleDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new RoleDAOImpl();
		}
		instance.setEntityManager(entityManager);
		return instance;
	}
	
	@Override
	public Role save(Role role) {
		entityManager.persist(role);
		return role;
	}
	
	@Override
	public Role update(Role role) {
		if(role.getId() == null) { return null; }
		
		entityManager.persist(role);
		return role;
	}

	@Override
	public Role getRoleByName(String name) {
		try {
			return this.entityManager.createQuery(findRoleByNameQuery(), Role.class)
						.setParameter("name", name)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}

	@Override
	public void delete(Long id) {
		try {
			this.entityManager.createQuery(removeRoleById(), Role.class)
						.setParameter("id", id)
						.executeUpdate();
		}catch(RuntimeException e) {}
	}
	
	private String removeRoleById() {
		return "DELETE from Role r where r.id = :id";
	}
	
	private String findRoleByNameQuery() {
		return "SELECT r from Role r where r.name = :name";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
