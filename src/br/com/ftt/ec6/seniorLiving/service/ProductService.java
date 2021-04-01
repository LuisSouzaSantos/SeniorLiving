package br.com.ftt.ec6.seniorLiving.service;

import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.exception.ProductException;

public interface ProductService {
	
	Product save(String name, String description) throws ProductException;
	
	Product getProductByName(String name);
	
	void delete(Long id);

}
