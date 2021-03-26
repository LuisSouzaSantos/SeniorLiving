package br.com.SeniorLiving.model.services;

import java.util.List;

import br.com.SeniorLiving.model.dao.DaoFactory;
import br.com.SeniorLiving.model.dao.DepartmentDao;
import br.com.ftt.ec6.seniorLiving.model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public List<Department> findALL() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Department obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Department obj) {
		dao.deleteById(obj.getId());
	}
}
