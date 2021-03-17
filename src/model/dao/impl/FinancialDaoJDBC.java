package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.entities.Financial;

public class FinancialDaoJDBC {

private Connection conn;
	
	public FinancialDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public void insert(Financial obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO outgoing "
					+ "(description, amount) "
					+ "VALUES "
					+ "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getDescription());
			st.setDouble(2, obj.getAmount());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	public void update(Financial obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE outgoing "
					+ "SET description = ?, amount = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getDescription());
			st.setDouble(2, obj.getAmount());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM outgoing WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	public Financial findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM outgoing WHERE id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Financial obj = new Financial();
				obj.setId(rs.getInt("id"));
				obj.setDescription(rs.getString("description"));
				obj.setAmount(rs.getDouble("amount"));

				
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Financial instantiateFinancial(ResultSet rs) throws SQLException {
		Financial obj = new Financial();
		obj.setId(rs.getInt("id"));
		obj.setDescription(rs.getString("description"));
		obj.setAmount(rs.getDouble("amount"));

		return obj;
	}


	public List<Financial> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM outgoing ORDER BY Id");
			rs = st.executeQuery();

			List<Financial> list = new ArrayList<>();

			while (rs.next()) {
				Financial obj = new Financial();
				obj.setId(rs.getInt("Id"));
				obj.setDescription(rs.getString("description"));
				obj.setAmount(rs.getDouble("amount"));

				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}	
}
