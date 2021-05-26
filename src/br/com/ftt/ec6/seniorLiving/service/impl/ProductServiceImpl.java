package br.com.ftt.ec6.seniorLiving.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.ProductDAO;
import br.com.ftt.ec6.seniorLiving.DAO.impl.ProductDAOImpl;
import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.exception.ProductException;
import br.com.ftt.ec6.seniorLiving.service.BillingProductService;
import br.com.ftt.ec6.seniorLiving.service.ProductService;

public class ProductServiceImpl implements ProductService {
	
	private static ProductServiceImpl instance;
	private static ProductDAO productDAO = ProductDAOImpl.getInstance();
	private static BillingProductService billingProductService = BillingProductServiceImpl.getInstance();
	
	private ProductServiceImpl() {}
	
	public static ProductServiceImpl getInstance() {
		if(instance == null) {
			instance = new ProductServiceImpl();
		}
		return instance;
	}

	@Override
	public Product save(String name, String description, RestHome restHome) throws ProductException {
		if(name == null || name.trim().isEmpty()) { throw new ProductException("Nome do produto inválido"); }
		
		if(description == null || description.trim().isEmpty()) { throw new ProductException("Descrição do produto inválida"); }
		
		Product similarProduct = getProductByNameAndRestHome(name, restHome);
		
		if(similarProduct != null) { throw new ProductException("Produto já existe"); }
		
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setRestHome(restHome);
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		productDAO.startConnection(entityManager);
		
		Product newProduct = productDAO.save(product);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		productDAO.stopConnection();
		
		return newProduct;
	}
	
	@Override
	public Product update(Product product) throws ProductException {
		if(product == null) { throw new ProductException("Produto não pode ser nulo"); }
		
		if(product.getName() == null || product.getName().trim().isEmpty()) { throw new ProductException("Nome do produto inválido"); }
		
		if(product.getDescription() == null || product.getDescription().trim().isEmpty()) { throw new ProductException("Descrição do produto inválida"); }
		
		Product similarProduct = getProductByNameAndRestHome(product.getName(), product.getRestHome());
		
		if((similarProduct != null) && (similarProduct.getId().equals(product.getId()))) { throw new ProductException("Produto já existe"); }
		
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		productDAO.startConnection(entityManager);
		
		Product productUpdated = productDAO.update(product);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		productDAO.stopConnection();
		
		return productUpdated;
	}

	@Override
	public String delete(Long id) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		productDAO.startConnection(entityManager);
		
		Product product = productDAO.getById(id);
		List<BillingProduct> productBillingList = billingProductService.getBillingProductByProduct(product);
		
		String message = "";
		
		if(productBillingList == null || productBillingList.isEmpty()) {
			message = productDAO.delete(id);
		}else {
			message = "Esse produto está vinculado a um faturamento";
		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
		productDAO.stopConnection();
		
		return message;
	}

	@Override
	public Product getProductByName(String name) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		productDAO.startConnection(entityManager);
		
		Product product = productDAO.getProductByName(name);
		
		entityManager.close();
		productDAO.stopConnection();
		return product;
	}

	@Override
	public List<Product> getProductByRestHome(RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		productDAO.startConnection(entityManager);
		
		List<Product> productList = productDAO.getProductByRestHome(restHome);
		
		entityManager.close();
		productDAO.stopConnection();
		return productList;
	}
	
	private Product getProductByNameAndRestHome(String name, RestHome restHome) {
		EntityManager entityManager = Database.getConnection();
		entityManager.getTransaction().begin();
		productDAO.startConnection(entityManager);
		
		Product product = productDAO.getProductByNameAndRestHome(name, restHome);
		
		entityManager.close();
		productDAO.stopConnection();
		return product;
	}


}
