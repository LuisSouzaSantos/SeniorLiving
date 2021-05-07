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
		if(socialReason == null || socialReason.trim().isEmpty()) { throw new RestHomeException("Razão social inválida"); }
		
		if(cnpj == null || cnpj.trim().isEmpty()) { throw new RestHomeException("CNPJ inválido"); }
		
		if(addressStreet == null || addressStreet.trim().isEmpty()) { throw new RestHomeException("Nome da rua inválida"); }
		
		if(addressNumber == null || addressNumber.trim().isEmpty()) { throw new RestHomeException("Número da rua inválido"); }
		
		if(addressState == null || addressState.trim().isEmpty()) { throw new RestHomeException("Estado inválido"); }
		
		if(addressCep == null || addressCep.trim().isEmpty()) { throw new RestHomeException("CEP inválido"); }
		
		if(addressNeighborhood == null || addressNeighborhood.trim().isEmpty()) { throw new RestHomeException("Bairro inválido"); }
		
		if(admin == null) { throw new RestHomeException("Admin inválido"); }
		
		RestHome restHome = new RestHome();
		restHome.setSocialReason(socialReason);
		restHome.setCnpj(cnpj);
		restHome.setAddressStreet(addressStreet);
		restHome.setAddressNumber(addressNumber);
		restHome.setAddressState(addressState);
		restHome.setAddressCep(addressCep);
		restHome.setAddressNeighborhood(addressNeighborhood);
		restHome.setAdmin(admin);
//		restHome.setAccommodationList(accommodationList);
//		restHome.setUserList(userList);
//		restHome.setTypeList(typeList);
		
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
	public void delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		RestHomeDAO restHomeDAO = RestHomeDAOImpl.getInstance(entityManager);
		restHomeDAO.delete(id);
		entityManager.close();
	}

}
