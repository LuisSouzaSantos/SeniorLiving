package br.com.ftt.ec6.seniorLiving.service;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.exception.RoleException;

public interface RoleService {
	
	Role save(String name) throws RoleException;
	
	Role update(Role role) throws RoleException;
	
	Role getRoleByName(String name);
	
	List<Role> findRoleListByRoleNameArray(String[] roleNameArray);
	
	boolean checkRoleList(List<Role> roleLroleListNameist);
	
	void delete(Long id);

}
