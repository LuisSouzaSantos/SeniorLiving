package br.com.SeniorLiving.model.services;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.DAO.DaoFactory;
import br.com.ftt.ec6.seniorLiving.DAO.impl.FinancialDaoJDBC;
import br.com.ftt.ec6.seniorLiving.entities.Financial;

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