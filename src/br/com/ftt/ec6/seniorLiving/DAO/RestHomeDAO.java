package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.User;

public interface RestHomeDAO {
	
	RestHome save(RestHome restHome);
	
	RestHome update(RestHome restHome);
	
	String delete(Long id);
	
	RestHome getRestHomeByCNPJ(String cnpj);
	
	RestHome getRestHomeBySocialReason(String socialReason);
	
	List<RestHome> getAll();
	
	List<RestHome> getRestHomeByAdmin(User user);
	
	List<RestHome> getRestHomeByFilter(String socialReason, String cnpj, String uf, String cep);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();

}
