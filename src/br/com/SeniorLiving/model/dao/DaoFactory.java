package br.com.SeniorLiving.model.dao;

import br.com.SeniorLiving.db.DB;
import br.com.SeniorLiving.model.dao.impl.AgendaDaoJDBC;
import br.com.SeniorLiving.model.dao.impl.DepartmentDaoJDBC;
import br.com.SeniorLiving.model.dao.impl.FinancialDaoJDBC;
import br.com.SeniorLiving.model.dao.impl.PersonDaoJDBC;
import br.com.SeniorLiving.model.dao.impl.UserJDBC;

public class DaoFactory {

	public static PersonDao createPersonDao() {
		return new PersonDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	
	public static UserJDBC createUserDao() {
		return new UserJDBC(DB.getConnection());
	}

	public static AgendaDao createAgendaDao() {
		return new AgendaDaoJDBC(DB.getConnection());
	}
	
	public static FinancialDaoJDBC createFinancialDao() {
		return new FinancialDaoJDBC(DB.getConnection());
	}
}
