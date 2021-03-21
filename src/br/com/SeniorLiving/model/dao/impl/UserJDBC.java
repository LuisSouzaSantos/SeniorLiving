package br.com.SeniorLiving.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.SeniorLiving.db.DB;
import br.com.SeniorLiving.db.DbException;
import br.com.SeniorLiving.db.DbIntegrityException;
import br.com.SeniorLiving.model.entities.User;

public class UserJDBC {

	private Connection conn;
	
	public UserJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public User findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM registration WHERE id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				User obj = new User();
				obj.setId(rs.getInt("id"));
				obj.setFull_name(rs.getString("full_name"));
				obj.setEmail_id(rs.getString("email_id"));
				obj.setPassword(rs.getString("password"));
				
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

	public boolean findLogin(String email, String password) {
					
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM registration WHERE email = ? and password = ?");
			st.setString(1, email);
			st.setString(2, password);

			rs = st.executeQuery();
						
			if (rs.next()) {
				
				return true;
			}
			return false;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	
	public List<User> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM registration ORDER BY id");
			rs = st.executeQuery();

			List<User> list = new ArrayList<>();

			while (rs.next()) {
				User obj = new User();
				obj.setId(rs.getInt("id"));
				obj.setFull_name(rs.getString("full_name"));
				obj.setEmail_id(rs.getString("email_id"));
				obj.setPassword(rs.getString("password"));
				
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
	
	public void insert(User obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO registration "
							+ "(full_name, email_id, password) "
							+ "VALUES "
							+ "(?, ?, ?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getFull_name());
			st.setString(2, obj.getEmail_id());
			st.setString(3, obj.getPassword());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
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
	
	public void update(User obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE registration " +
				"SET full_name = ?, email_id = ?, password = ? " +
				"WHERE Id = ?");

			st.setString(1, obj.getFull_name());
			st.setInt(2, obj.getId());

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
			st = conn.prepareStatement(
				"DELETE FROM registration WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}
}
