package br.com.ftt.ec6.seniorLiving.DAO;

import br.com.SeniorLiving.db.DB;
import br.com.ftt.ec6.seniorLiving.DAO.impl.AgendaDaoJDBC;
import br.com.ftt.ec6.seniorLiving.DAO.impl.DepartmentDaoJDBC;
import br.com.ftt.ec6.seniorLiving.DAO.impl.FinancialDaoJDBC;
import br.com.ftt.ec6.seniorLiving.DAO.impl.PersonDaoJDBC;
import br.com.ftt.ec6.seniorLiving.DAO.impl.UserDAOImpl;

public class DaoFactory {

	public static PersonDao createPersonDao() {
		return new PersonDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	
	public static UserDAOImpl createUserDao() {
		return new UserDAOImpl(DB.getConnection());
	}

	public static AgendaDao createAgendaDao() {
		return new AgendaDaoJDBC(DB.getConnection());
	}
	
	public static FinancialDaoJDBC createFinancialDao() {
		return new FinancialDaoJDBC(DB.getConnection());
	}
}
