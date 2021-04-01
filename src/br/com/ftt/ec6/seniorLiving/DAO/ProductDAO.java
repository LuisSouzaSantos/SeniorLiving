package br.com.ftt.ec6.seniorLiving.DAO;

import br.com.ftt.ec6.seniorLiving.entities.Product;

public interface ProductDAO {
	Product save(Product product);
	
	Product getProductByName(String name);
	
	void delete(Long id);
}
