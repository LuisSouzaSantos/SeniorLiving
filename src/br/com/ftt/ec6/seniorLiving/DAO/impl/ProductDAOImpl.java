package br.com.ftt.ec6.seniorLiving.DAO.impl;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.ProductDAO;
import br.com.ftt.ec6.seniorLiving.entities.Product;

public class ProductDAOImpl implements ProductDAO {

	private static ProductDAOImpl instance;
	private EntityManager entityManager;
	
	private ProductDAOImpl() {}
	
	public static ProductDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new ProductDAOImpl();
		}
		instance.setEntityManager(entityManager);
		return instance;
	}
	
	@Override
	public Product save(Product product) {
		entityManager.persist(product);
		return product;
	}
	
	@Override
	public Product getProductByName(String name) {
		try {
			return this.entityManager.createQuery(findProductByNameQuery(), Product.class)
						.setParameter("name", name)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}

	@Override
	public void delete(Long id) {
		try {
			this.entityManager.createQuery(removeProductById(), Product.class)
						.setParameter("id", id)
						.executeUpdate();
		}catch(RuntimeException e) {}
		
	}
	
	private String findProductByNameQuery() {
		return "SELECT p from Product p where p.name = :name";
	}
	
	private String removeProductById() {
		return "DELETE from Product p where p.id = :id";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	
}
