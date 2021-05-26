package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.DAO.ProductDAO;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;

public class ProductDAOImpl extends DAOImpl<Product> implements ProductDAO {

	private static ProductDAOImpl instance;
	
	private ProductDAOImpl() {
		super.t = Product.class;
	}
	
	public static ProductDAOImpl getInstance() {
		if(instance == null) {
			instance = new ProductDAOImpl();
		}
		return instance;
	}
	
	@Override
	public Product getProductByName(String name) {
		try {
			return super.entityManager.createQuery(findProductByNameQuery(), Product.class)
						.setParameter("name", name)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public Product getProductByNameAndRestHome(String name, RestHome restHome) {
		try {
			return super.entityManager.createQuery(findProductByNameAndRestHomeQuery(), Product.class)
						.setParameter("name", name)
						.getSingleResult();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public List<Product> getProductByRestHome(RestHome restHome) {
		try {
			return super.entityManager.createQuery(findProductByRestHomeQuery(), Product.class)
						.setParameter("restHome", restHome)
						.getResultList();
		}catch(RuntimeException e) {return null;}
	}
	
	@Override
	public void startConnection(EntityManager entityManager) {
		instance.setEntityManager(entityManager);
	}

	@Override
	public void stopConnection() {
		super.entityManager = null;
	}
	
	private String findProductByRestHomeQuery() {
		return "SELECT p from Product p where p.restHome = :restHome";
	}
	
	private String findProductByNameQuery() {
		return "SELECT p from Product p where p.name = :name";
	}
	
	private String findProductByNameAndRestHomeQuery() {
		return "SELECT p from Product p where p.name = :name AND p.restHome = :restHome";
	}
	
	private void setEntityManager(EntityManager entityManager) {
		super.entityManager = entityManager;
	}
	
}
