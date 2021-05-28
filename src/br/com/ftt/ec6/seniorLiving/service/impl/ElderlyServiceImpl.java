package br.com.ftt.ec6.seniorLiving.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.ElderlyDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.ElderlyDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.exception.ElderlyException;
import br.com.ftt.ec6.seniorLiving.service.ElderlyService;
import br.com.ftt.ec6.seniorLiving.utils.MaritalStatus;
import br.com.ftt.ec6.seniorLiving.utils.Utils;

public class ElderlyServiceImpl implements ElderlyService {
	
	private static ElderlyServiceImpl instance;
	private static ElderlyDAO elderlyDAO = ElderlyDAOImpl.getInstance();
	
	private ElderlyServiceImpl() {}
	
	public static ElderlyServiceImpl getInstance() {
		if(instance == null) {
			instance = new ElderlyServiceImpl();
		}
		return instance;
	}

	@Override
	public Elderly save(String name, MaritalStatus maritalStatus, String nationality, String rg, String cpf,
			LocalDate birthDate, BigDecimal monthlyPayment, Person curator, Person sympathetic, RestHome restHome, Accommodation accommodation, List<Billing> billingList) throws ElderlyException {
		
		validateFields(name, maritalStatus, nationality, rg, cpf, birthDate, monthlyPayment, curator, sympathetic, restHome, accommodation);
		
		if(Utils.isCPF(cpf) == false) { throw new ElderlyException("CPF inválido"); }
		
		if(rg.length() < 1 || rg.length() > 9)  { throw new ElderlyException("RG inválido"); }
		
		Elderly elderlyRetrievedByRg = getElderlyByRGAndRestHome(rg, restHome);
		
		if(elderlyRetrievedByRg != null) { throw new ElderlyException("Já existe um idoso com esse RG cadastrado"); }
		
		Elderly elderlyRetrievedByCPF = getElderlyByCPFAndRestHome(cpf, restHome);
		
		if(elderlyRetrievedByCPF != null) { throw new ElderlyException("Já existe um idoso com esse CPF cadastrado"); }
		
		Elderly elderly = new Elderly();
		elderly.setName(name);
		elderly.setMaritalStatus(maritalStatus);
		elderly.setNationality(nationality);
		elderly.setRg(rg);
		elderly.setCpf(cpf);
		elderly.setBirthDate(birthDate);
		elderly.setMonthlyPayment(monthlyPayment);
		elderly.setCurator(curator);
		elderly.setSympathetic(sympathetic);
		elderly.setRestHome(restHome);
		elderly.setAccommodation(accommodation);
		elderly.setBillingList(billingList);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		Elderly newElderly = elderlyDAO.save(elderly);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		elderlyDAO.stopConnection();
		
		return newElderly;
	}
	
	@Override
	public Elderly update(Elderly elderly) throws ElderlyException {
		validateFields(elderly.getName(), elderly.getMaritalStatus(), elderly.getNationality(), elderly.getRg(), elderly.getCpf(), elderly.getBirthDate(), 
				elderly.getMonthlyPayment(), elderly.getCurator(), elderly.getSympathetic(), elderly.getRestHome(), elderly.getAccommodation());
		
		if(Utils.isCPF(elderly.getCpf()) == false) { throw new ElderlyException("CPF inválido"); }
		
		if(elderly.getRg().length() < 1 || elderly.getRg().length() > 9)  { throw new ElderlyException("RG inválido"); }
		
		Elderly elderlyRetrievedByRg = getElderlyByRGAndRestHome(elderly.getRg(), elderly.getRestHome());
		
		if((elderlyRetrievedByRg != null) && (elderlyRetrievedByRg.getId().equals(elderly.getId()) == false)) { throw new ElderlyException("Já existe um idoso com esse RG cadastrado"); }
		
		Elderly elderlyRetrievedByCPF = getElderlyByCPFAndRestHome(elderly.getCpf(), elderly.getRestHome());
		
		if((elderlyRetrievedByCPF != null) && (elderlyRetrievedByCPF.getId().equals(elderly.getId()) == false)) { throw new ElderlyException("Já existe um idoso com esse CPF cadastrado"); }
	
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		Elderly elderlyUpdated = elderlyDAO.update(elderly);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		elderlyDAO.stopConnection();
		
		return elderlyUpdated;
	}

	@Override
	public List<Elderly> getElderlyByRestHome(RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		List<Elderly> elderlyList = elderlyDAO.getElderlyByRestHome(restHome);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		elderlyDAO.stopConnection();
		
		return elderlyList;
	}
	
	@Override
	public List<Elderly> getElderlyByAccommodation(Accommodation accommodation) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		List<Elderly> elderlyList = elderlyDAO.getElderlyByAccommodation(accommodation);
		
		entityManager.close();
		elderlyDAO.stopConnection();
		
		return elderlyList;
	}

	@Override
	public String delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		String message = elderlyDAO.delete(id);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		elderlyDAO.stopConnection();
		
		return message;
	}

	@Override
	public List<Elderly> getElderlyByPerson(Person person) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		List<Elderly> elderlyList = elderlyDAO.getElderlyByPerson(person);
		
		entityManager.close();
		elderlyDAO.stopConnection();
		return elderlyList;
	}
	
	private Elderly getElderlyByRGAndRestHome(String rg, RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		Elderly elderly = elderlyDAO.getElderlyByRgAndRestHome(rg, restHome);
		
		entityManager.close();
		elderlyDAO.stopConnection();
		return elderly;
	}
	
	private Elderly getElderlyByCPFAndRestHome(String cpf, RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		elderlyDAO.startConnection(entityManager);
		
		Elderly elderly = elderlyDAO.getElderlyCPFAndRestHome(cpf, restHome);
		
		entityManager.close();
		elderlyDAO.stopConnection();
		return elderly;
	}
	
	private void validateFields(String name, MaritalStatus maritalStatus, String nationality, String rg, String cpf,
			LocalDate birthDate, BigDecimal monthlyPayment, Person curator, Person sympathetic, RestHome restHome, Accommodation accommodation) throws ElderlyException {
		
		if(name == null || name.trim().isEmpty()) { throw new ElderlyException("O nome é mandatório"); }
		
		if(maritalStatus == null) { throw new ElderlyException("O estado civil é mandatório");  }
		
		if(nationality == null || nationality.trim().isEmpty()) { throw new ElderlyException("O nacionalidade é mandatório");  }
		
		if(rg == null || rg.trim().isEmpty()) { throw new ElderlyException("O rg é mandatório");  }
		
		if(cpf == null || cpf.trim().isEmpty()) { throw new ElderlyException("O cpf é mandatório");  }
		
		if(birthDate == null) { throw new ElderlyException("A data de nascimento é mandatório");  }
		
		if(monthlyPayment == null) { throw new ElderlyException("O valor mensal é mandatório");  }
		
		if(curator == null) { throw new ElderlyException("O curador é mandatório");  }
		
		if(sympathetic == null) { throw new ElderlyException("O solidário é mandatório");  }
		
		if(restHome == null) { throw new ElderlyException("A casa de repouso é mandatório");  }
		
		if(accommodation == null) { throw new ElderlyException("A acomodação é mandatório");  }
	}

}
