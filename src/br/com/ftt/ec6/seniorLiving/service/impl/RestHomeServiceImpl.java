package br.com.ftt.ec6.seniorLiving.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.RestHomeDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.RestHomeDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.RestHomeException;
import br.com.ftt.ec6.seniorLiving.service.RestHomeService;

public class RestHomeServiceImpl implements RestHomeService {

	private static RestHomeServiceImpl instance;
	private static final RestHomeDAO restHomeDAO = RestHomeDAOImpl.getInstance(null);
	
	private RestHomeServiceImpl() {}
	
	public static RestHomeServiceImpl getInstance() {
		if(instance == null) {
			instance = new RestHomeServiceImpl();
		}
		
		return instance;
	}
	
	@Override
	public List<RestHome> getAll() {
		EntityManager entityManagerConnection = Database.getConnection();
		entityManagerConnection.getTransaction().begin();
		restHomeDAO.startConnection(entityManagerConnection);
		List<RestHome> restHomeList = restHomeDAO.getAll();
		entityManagerConnection.close();
		restHomeDAO.stopConnection();
		
		return restHomeList;
	}
	
	@Override
	public RestHome save(String socialReason, String cnpj, String addressStreet, String addressNumber, String addressState,
			String addressCep, String addressNeighborhood, User admin, List<Accommodation> accommodationList, List<User> userList, List<Type> typeList) throws RestHomeException {
		
		validateFields(socialReason, cnpj, addressStreet, addressNumber, addressState, addressCep, addressNeighborhood, admin, accommodationList, userList, typeList);
		
		RestHome restHome = new RestHome();
		restHome.setSocialReason(socialReason);
		restHome.setCnpj(cnpj);
		restHome.setAddressStreet(addressStreet);
		restHome.setAddressNumber(addressNumber);
		restHome.setAddressState(addressState);
		restHome.setAddressCep(addressCep);
		restHome.setAddressNeighborhood(addressNeighborhood);
		restHome.setAdmin(admin);
		
		EntityManager entityManagerConnection = Database.getConnection();
		entityManagerConnection.getTransaction().begin();
		restHomeDAO.startConnection(entityManagerConnection);
		RestHome newRestHome = restHomeDAO.save(restHome);
		entityManagerConnection.getTransaction().commit();
		entityManagerConnection.close();
		restHomeDAO.stopConnection();
		
		return newRestHome;
	}

	@Override
	public String delete(Long id) {
		EntityManager entityManagerConnection = Database.getConnection();
		restHomeDAO.startConnection(entityManagerConnection);
		entityManagerConnection.getTransaction().begin();
		String messageInfo = restHomeDAO.delete(id);
		entityManagerConnection.getTransaction().commit();
		entityManagerConnection.close();
		restHomeDAO.stopConnection();
		
		return messageInfo;
	}

	@Override
	public RestHome update(RestHome restHome) throws RestHomeException {
		if(restHome == null) { throw new RestHomeException("Casa de Repouso n�o pode ser nulo"); }
		
		validateFields(restHome.getSocialReason(), restHome.getCnpj(), restHome.getAddressStreet(), restHome.getAddressNumber(), restHome.getAddressState(), restHome.getAddressCep(), restHome.getAddressNeighborhood(), restHome.getAdmin(), null, null, null);
		
		EntityManager entityManagerConnection = Database.getConnection();
		restHomeDAO.startConnection(entityManagerConnection);
		entityManagerConnection.getTransaction().begin();
		
		RestHome restHomeUpdated = restHomeDAO.update(restHome);
		entityManagerConnection.getTransaction().commit();
		restHomeDAO.stopConnection();
		
		return restHomeUpdated;
	}
	
	
	@Override
	public List<RestHome> getRestHomeByAdmin(User user) {
		EntityManager entityManagerConnection = Database.getConnection();
		restHomeDAO.startConnection(entityManagerConnection);
		entityManagerConnection.getTransaction().begin();
		
		List<RestHome> restHomeList = restHomeDAO.getRestHomeByAdmin(user);
		entityManagerConnection.getTransaction().commit();
		restHomeDAO.stopConnection();
		
		return restHomeList;
	}
		
	private void validateFields(String socialReason, String cnpj, String addressStreet, String addressNumber, String addressState,
			String addressCep, String addressNeighborhood, User admin, List<Accommodation> accommodationList, List<User> userList, List<Type> typeList) throws RestHomeException {
		
		if(socialReason == null || socialReason.trim().isEmpty()) { throw new RestHomeException("Raz�o social inv�lida"); }
		
		if(cnpj == null || cnpj.trim().isEmpty()) { throw new RestHomeException("CNPJ inv�lido"); }
		
		if(addressStreet == null || addressStreet.trim().isEmpty()) { throw new RestHomeException("Nome da rua inv�lida"); }
		
		if(addressNumber == null || addressNumber.trim().isEmpty()) { throw new RestHomeException("N�mero da rua inv�lido"); }
		
		if(addressState == null || addressState.trim().isEmpty()) { throw new RestHomeException("Estado inv�lido"); }
		
		if(addressCep == null || addressCep.trim().isEmpty()) { throw new RestHomeException("CEP inv�lido"); }
		
		if(addressNeighborhood == null || addressNeighborhood.trim().isEmpty()) { throw new RestHomeException("Bairro inv�lido"); }
		
		if(admin == null) { throw new RestHomeException("Admin inv�lido"); }
	}


	
}
