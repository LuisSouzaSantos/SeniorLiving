package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.PersonDao;
import model.entities.Person;

public class PersonService {
	
	private PersonDao dao = DaoFactory.createPersonDao();
	
	public List<Person> findALL() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Person obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Person obj) {
		dao.deleteById(obj.getId());
	}
}
