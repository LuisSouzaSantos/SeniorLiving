package br.com.ftt.ec6.seniorLiving.service;

import java.time.LocalDate;
import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.Type;

public interface PersonService {
	Person save(String name, String maritalStatus, String nationality, String job, String rg, String cpf, LocalDate birthDate, 
			String phone, String email, String addressStreet, String addressNumber, String addressState, String addressCep, String addressNeighborhood, List<Type> typeList);
	
	void delete(Long id);
}
