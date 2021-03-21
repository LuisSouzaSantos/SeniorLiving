package br.com.SeniorLiving.model.dao;

import java.util.List;

import br.com.SeniorLiving.model.entities.Department;

public interface DepartmentDao {

	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}