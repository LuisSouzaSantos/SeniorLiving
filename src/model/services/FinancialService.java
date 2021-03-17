package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.impl.FinancialDaoJDBC;
import model.entities.Financial;

public class FinancialService {

private FinancialDaoJDBC dao = DaoFactory.createFinancialDao();
	
	public List<Financial> findALL() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Financial obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Financial obj) {
		dao.deleteById(obj.getId());
	}
}