package br.com.ftt.ec6.seniorLiving.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.ftt.ec6.seniorLiving.DAO.RestHomeDAO;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.User;

public class RestHomeDAOImpl extends DAOImpl<RestHome> implements RestHomeDAO {

	private static RestHomeDAOImpl instance;
	
	private RestHomeDAOImpl() {
		super.t = RestHome.class;
	}
	
	public static RestHomeDAOImpl getInstance(EntityManager entityManager) {
		if(instance == null) {
			instance = new RestHomeDAOImpl();
		}
		return instance;
	}
	
	@Override
	public void startConnection(EntityManager entityManager) {
		super.entityManager = entityManager;
	}
	
	@Override
	public void stopConnection() {
		super.entityManager = null;
	}

	@Override
	public List<RestHome> getRestHomeByAdmin(User user) {
		try {
			return super.entityManager.createQuery(getRestHomeByAdminQuery(), RestHome.class)
					.setParameter("admin", user)
					.getResultList();
		}catch(RuntimeException e) {
			return null;
		}
	}
	
	@Override
	public RestHome getRestHomeByCNPJ(String cnpj) {
		try {
			return super.entityManager.createQuery(getRestHomeByCNPJQuery(), RestHome.class)
					.setParameter("cnpj", cnpj)
					.getSingleResult();
		}catch(RuntimeException e) {
			return null;
		}
	}

	@Override
	public RestHome getRestHomeBySocialReason(String socialReason) {
		try {
			return super.entityManager.createQuery(getRestHomeBySocialReasonQuery(), RestHome.class)
					.setParameter("socialReason", socialReason)
					.getSingleResult();
		}catch(RuntimeException e) {
			return null;
		}
	}
	
	@Override
	public List<RestHome> getRestHomeByFilter(String socialReason, String cnpj, String uf, String cep) {
		String restHomeFilter = "SELECT rh from RestHome rh WHERE 1=1 ";
		
		if(socialReason != null && socialReason.trim().isEmpty() == false) {
			restHomeFilter+=" AND rh.socialReason LIKE CONCAT('%',:socialReason,'%') ";
		}
		
		if(cnpj != null && cnpj.trim().isEmpty() == false) {
			restHomeFilter+=" AND rh.cnpj LIKE CONCAT('%',:cnpj,'%') ";
		}
		
		if(uf != null && uf.trim().isEmpty() == false) {
			restHomeFilter+=" AND rh.addressState LIKE CONCAT('%',:addressState,'%') ";
		}
		
		if(cep != null &&  cep.trim().isEmpty() == false) {
			restHomeFilter+=" AND rh.addressCep LIKE CONCAT('%', :addressCep,'%') ";
		}
		
		TypedQuery<RestHome> restHomeFilterQuery = super.entityManager.createQuery(restHomeFilter, RestHome.class);
		
		if(socialReason != null && socialReason.trim().isEmpty() == false) {
			restHomeFilterQuery.setParameter("socialReason", socialReason);
		}
		
		if(cnpj != null && cnpj.trim().isEmpty() == false) {
			restHomeFilterQuery.setParameter("cnpj", cnpj);
		}
		
		if(uf != null && uf.trim().isEmpty() == false) {
			restHomeFilterQuery.setParameter("addressState", uf);
		}
		
		if(cep != null &&  cep.trim().isEmpty() == false) {
			restHomeFilterQuery.setParameter("addressCep", cep);
		}
		
		return restHomeFilterQuery.getResultList();
	}
	
	private String getRestHomeByAdminQuery() {
		return "SELECT rh from RestHome rh where rh.admin = :admin";
	}
	
	private String getRestHomeByCNPJQuery() {
		return "SELECT rh from RestHome rh where rh.cnpj = :cnpj";
	}
	
	private String getRestHomeBySocialReasonQuery() {
		return "SELECT rh from RestHome rh where rh.socialReason = :socialReason";
	}


	
}
