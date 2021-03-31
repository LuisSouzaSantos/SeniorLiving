package br.com.ftt.ec6.seniorLiving.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.impl.RoleDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.exception.RoleException;
import br.com.ftt.ec6.seniorLiving.service.RoleService;

public class RoleServiceImpl implements RoleService {
	
	private static RoleServiceImpl instance;
	
	private RoleServiceImpl() {}
	
	public static RoleServiceImpl getInstance() {
		if(instance == null) {
			instance = new RoleServiceImpl();
		}
		return instance;
	}

	@Override
	public Role save(String name) throws RoleException {
		if(name == null || name.trim().isEmpty()) { throw new RoleException("Papel inválido"); }
		
		Role similarRole = getRoleByName(name);
		
		if(similarRole != null) { throw new RoleException("Role já existe"); }
		
		Role role = new Role();
		role.setName(name);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		RoleDAOImpl roleDAOImpl = RoleDAOImpl.getInstance(entityManager);
		Role newRole = roleDAOImpl.save(role);
		entityManager.getTransaction().commit();
		entityManager.close();

		return newRole;
	}
	
	@Override
	public Role update(Role roleUpdate) throws RoleException {
		if(roleUpdate == null || roleUpdate.getName().trim().isEmpty()) { throw new RoleException("Papel inválido"); }
		
		Role similarRole = getRoleByName(roleUpdate.getName());
		
		if(similarRole != null && similarRole.getId().equals(roleUpdate.getId())) { throw new RoleException("Role já existe"); }
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		RoleDAOImpl roleDAOImpl = RoleDAOImpl.getInstance(entityManager);
		Role role = roleDAOImpl.update(roleUpdate);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return role;
	}

	@Override
	public Role getRoleByName(String name) {
		EntityManager entityManager = Database.getConnection();
		RoleDAOImpl roleDAOImpl = RoleDAOImpl.getInstance(entityManager);
		Role role = roleDAOImpl.getRoleByName(name);
		entityManager.close();
		return role;
	}

	@Override
	public void delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		RoleDAOImpl roleDAOImpl = RoleDAOImpl.getInstance(entityManager);
		roleDAOImpl.delete(id);
		entityManager.close();
	}

	@Override
	public boolean checkRoleList(List<Role> roleList) {
		boolean validRoleList = true;
		
		for (Role role : roleList) {
			Role roleToBeCheck = getRoleByName(role.getName());
			
			if(roleToBeCheck == null) {
				validRoleList = false;
			}
			
		}
		return validRoleList;
	}
	
}
