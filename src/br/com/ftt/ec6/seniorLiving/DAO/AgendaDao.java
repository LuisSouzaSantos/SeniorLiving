package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Agenda;

public interface AgendaDao {
	
	void insert(Agenda obj);
	void update(Agenda obj);
	void deleteById(Integer id);
	Agenda findById(Integer id);
	List<Agenda> findAll();
}
