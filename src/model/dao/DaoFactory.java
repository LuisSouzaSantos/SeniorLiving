package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.PersonDaoJDBC;
import model.dao.impl.UserJDBC;

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
}
