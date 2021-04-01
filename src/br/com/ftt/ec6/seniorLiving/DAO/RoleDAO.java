package br.com.ftt.ec6.seniorLiving.DAO;

import br.com.ftt.ec6.seniorLiving.entities.Role;

public interface RoleDAO {

	Role save(Role role);
	
	Role update(Role role);
	
	Role getRoleByName(String name);
	
	void delete(Long id);
	
}
