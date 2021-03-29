package br.com.SeniorLiving.model.services;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.DAO.DaoFactory;
import br.com.ftt.ec6.seniorLiving.DAO.DepartmentDao;
import br.com.ftt.ec6.seniorLiving.entities.Department;

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
