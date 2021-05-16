package br.com.ftt.ec6.seniorLiving.service;

import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.exception.ProductException;

public interface ProductService {
	
	Product save(String name, String description, RestHome restHome) throws ProductException;
	
	Product update (Product product) throws ProductException;
	
	Product getProductByName(String name);
	
	List<Product> getProductByRestHome(RestHome restHome);
	
	String delete(Long id);

}
