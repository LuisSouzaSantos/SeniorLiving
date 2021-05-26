package br.com.ftt.ec6.seniorLiving.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public interface ProductDAO {
	Product save(Product product);
	
	Product update(Product product);
	
	Product getProductByName(String name);
	
	Product getProductByNameAndRestHome(String name, RestHome restHome);
	
	Product getById(Long id);
	
	List<Product> getProductByRestHome(RestHome restHome);
	
	String delete(Long id);
	
	void startConnection(EntityManager entityManager);
	
	void stopConnection();
}
