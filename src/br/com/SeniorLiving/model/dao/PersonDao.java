package br.com.SeniorLiving.model.dao;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.model.entities.Department;
import br.com.ftt.ec6.seniorLiving.model.entities.Person;

public interface PersonDao {

	void insert(Person obj);
	void update(Person obj);
	void deleteById(Integer id);
	Person findById(Integer id);
	List<Person> findAll();
	List<Person> findByDepartment(Department department);
}
