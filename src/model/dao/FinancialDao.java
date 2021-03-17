package model.dao;

import java.util.List;

import model.entities.Financial;

public interface FinancialDao {

	void insert(Financial obj);
	void update(Financial obj);
	void deleteById(Integer id);
	Financial findById(Integer id);
	List<Financial> findAll();
}
