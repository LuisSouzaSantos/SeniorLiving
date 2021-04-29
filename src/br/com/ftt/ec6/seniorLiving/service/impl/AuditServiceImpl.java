package br.com.ftt.ec6.seniorLiving.service.impl;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Audit;
import br.com.ftt.ec6.seniorLiving.service.AuditService;

public class AuditServiceImpl extends Thread implements AuditService {
	
	private static AuditServiceImpl instance;
	private static EntityManager entityManager = null;
	private static ConcurrentLinkedQueue<Audit> waitingAuditList = new ConcurrentLinkedQueue<Audit>();
	
	private AuditServiceImpl() {}
	
	public static AuditServiceImpl getInstance() {
		if(instance == null) {
			instance = new AuditServiceImpl();
			instance.start();
		}
		return instance;
	}
	
	@Override
	public void addToWaitingAuditList(Audit audit) {
		waitingAuditList.add(audit);
	}
	
	private void checkWaitingAuditList() throws InterruptedException {
		if(waitingAuditList.isEmpty()) {
			if((entityManager != null) && (entityManager.isOpen())) {
				entityManager.close();
			}
			Thread.sleep(5000);
		}else {
			performSaveAudit();
			Thread.sleep(200);
		}	
	}
	
	private void performSaveAudit() {
		Audit lastAuditObject = waitingAuditList.poll();
		
		if(lastAuditObject == null) { return; }
		
		if((entityManager == null) || (entityManager.getTransaction() == null) || (entityManager.isOpen() == false) || (entityManager.getTransaction().isActive() == false)) { 
			entityManager = Database.getConnection(); 
			entityManager.getTransaction().begin(); 
		}
		
		entityManager.persist(lastAuditObject);
		entityManager.getTransaction().commit();
	}

	@Override
	public void run() {
		while(true) {
			try {
				checkWaitingAuditList();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
			
	}
	
}
