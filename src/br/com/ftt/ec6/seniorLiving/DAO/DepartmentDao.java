package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Department;

public interface DepartmentDao {

	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
