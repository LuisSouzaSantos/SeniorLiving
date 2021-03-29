package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Department;
import br.com.ftt.ec6.seniorLiving.entities.Person;

public interface PersonDao {

	void insert(Person obj);
	void update(Person obj);
	void deleteById(Integer id);
	Person findById(Integer id);
	List<Person> findAll();
	List<Person> findByDepartment(Department department);
}
