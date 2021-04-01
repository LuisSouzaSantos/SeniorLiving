package br.com.ftt.ec6.seniorLiving.service.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.ProductDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.ProductDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.exception.ProductException;
import br.com.ftt.ec6.seniorLiving.service.ProductService;

public class ProductServiceImpl implements ProductService {
	
	private static ProductServiceImpl instance;
	
	private ProductServiceImpl() {}
	
	public static ProductServiceImpl getInstance() {
		if(instance == null) {
			instance = new ProductServiceImpl();
		}
		return instance;
	}

	@Override
	public Product save(String name, String description) throws ProductException {
		if(name == null || name.trim().isEmpty()) { throw new ProductException("Nome do produto inválido"); }
		
		if(description == null || description.trim().isEmpty()) { throw new ProductException("Descrição do produto inválida"); }
		
		Product similarProduct = getProductByName(name);
		
		if(similarProduct != null) { throw new ProductException("Produto já existe"); }
		
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		ProductDAO productDAO = ProductDAOImpl.getInstance(entityManager);
		Product newProduct = productDAO.save(product);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return newProduct;
	}

	@Override
	public void delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		ProductDAO productDAO = ProductDAOImpl.getInstance(entityManager);
		productDAO.delete(id);
		entityManager.close();
	}

	@Override
	public Product getProductByName(String name) {
		EntityManager entityManager = Database.getConnection();
		ProductDAO productDAO = ProductDAOImpl.getInstance(entityManager);
		Product product = productDAO.getProductByName(name);
		entityManager.close();
		return product;
	}

}
