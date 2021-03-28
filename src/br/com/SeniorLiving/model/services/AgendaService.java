package br.com.SeniorLiving.model.services;

import java.util.List;

import br.com.SeniorLiving.model.dao.AgendaDao;
import br.com.SeniorLiving.model.dao.DaoFactory;
import br.com.ftt.ec6.seniorLiving.model.entities.Agenda;

public class AgendaService {

	private AgendaDao dao = DaoFactory.createAgendaDao();
	
	public List<Agenda> findALL() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Agenda obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Agenda obj) {
		dao.deleteById(obj.getId());
	}
}