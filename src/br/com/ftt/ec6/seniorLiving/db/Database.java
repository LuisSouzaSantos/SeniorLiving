package br.com.ftt.ec6.seniorLiving.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.ftt.ec6.seniorLiving.entities.BaseConfig;

public class Database {
	
	public static EntityManager getConnection() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SeniorLiving");
		return entityManagerFactory.createEntityManager();
	}
	
	public static EntityManager getConnection(BaseConfig baseConfig) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(baseConfig.getLinkedToDatabase());
		return entityManagerFactory.createEntityManager();
	}
	
}
