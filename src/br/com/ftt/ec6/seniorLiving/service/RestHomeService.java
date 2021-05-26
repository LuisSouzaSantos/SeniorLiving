package br.com.ftt.ec6.seniorLiving.service;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.RestHomeException;

public interface RestHomeService {

	RestHome save(String socialReason, String cnpj, String addressStreet, String addressNumber, String addressState, String addressCep, 
			String addressNeighborhood, User admin, List<Accommodation> accommodationList, List<User> userList, List<Type> typeList) throws RestHomeException;
	
	RestHome update(RestHome restHome) throws RestHomeException;
	
	List<RestHome> getAll();
	
	List<RestHome> getRestHomeByAdmin(User user);
	
	List<RestHome> getRestHomeByFilter(String socialReason, String cnpj, String uf, String cep);
	
	String delete(Long id);
}
