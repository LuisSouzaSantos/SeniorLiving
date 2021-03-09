package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Person;

public interface PersonDao {

	void insert(Person obj);
	void update(Person obj);
	void deleteById(Integer id);
	Person findById(Integer id);
	List<Person> findAll();
	List<Person> findByDepartment(Department department);
}
