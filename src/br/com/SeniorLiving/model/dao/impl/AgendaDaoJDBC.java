package br.com.SeniorLiving.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Statement;

import br.com.SeniorLiving.db.DB;
import br.com.SeniorLiving.db.DbException;
import br.com.SeniorLiving.model.dao.AgendaDao;
import br.com.ftt.ec6.seniorLiving.model.entities.Agenda;
import br.com.ftt.ec6.seniorLiving.model.entities.User;

public class AgendaDaoJDBC implements AgendaDao {

	private Connection conn;
	
	public AgendaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Agenda obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO agenda "
					+ "(fullname, notes, initiation, termination) "
					+ "VALUES "
					+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getFullname());
			st.setString(2, obj.getNotes());
			st.setDate(3, new java.sql.Date(obj.getInitiation().getTime()));
			st.setDate(4, new java.sql.Date(obj.getTermination().getTime()));
			
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

	@Override
	public void update(Agenda obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE agenda "
					+ "SET fullname = ?, notes = ?, initiation = ?, termination = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getFullname());
			st.setString(2, obj.getNotes());
			st.setDate(3, new java.sql.Date(obj.getInitiation().getTime()));
			st.setDate(4, new java.sql.Date(obj.getTermination().getTime()));
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM agenda WHERE Id = ?");
			
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

	public Agenda findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM agenda WHERE id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Agenda obj = new Agenda();
				obj.setId(rs.getInt("id"));
				obj.setFullname(rs.getString("fullname"));
				obj.setNotes(rs.getString("notes"));
				obj.setInitiation(rs.getDate("initiation"));
				obj.setTermination(rs.getDate("termination"));

				
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

	private Agenda instantiateAgenda(ResultSet rs) throws SQLException {
		Agenda obj = new Agenda();
		obj.setId(rs.getInt("id"));
		obj.setFullname(rs.getString("fullname"));
		obj.setNotes(rs.getString("notes"));
		obj.setInitiation(new java.util.Date(rs.getTimestamp("initiation").getTime()));
		obj.setTermination(new java.util.Date(rs.getTimestamp("termination").getTime()));

		return obj;
	}


	@Override
	public List<Agenda> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM agenda ORDER BY Id");
			rs = st.executeQuery();

			List<Agenda> list = new ArrayList<>();

			while (rs.next()) {
				Agenda obj = new Agenda();
				obj.setId(rs.getInt("Id"));
				obj.setFullname(rs.getString("fullname"));
				obj.setNotes(rs.getString("notes"));
				obj.setInitiation(new java.util.Date(rs.getTimestamp("initiation").getTime()));
				obj.setTermination(new java.util.Date(rs.getTimestamp("termination").getTime()));;
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
