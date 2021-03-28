package br.com.SeniorLiving.model.dao;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.model.entities.Agenda;

public interface AgendaDao {
	
	void insert(Agenda obj);
	void update(Agenda obj);
	void deleteById(Integer id);
	Agenda findById(Integer id);
	List<Agenda> findAll();
}
