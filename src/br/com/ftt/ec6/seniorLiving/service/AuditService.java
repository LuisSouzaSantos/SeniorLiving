package br.com.ftt.ec6.seniorLiving.service;

import br.com.ftt.ec6.seniorLiving.entities.Audit;

public interface AuditService {

	void addToWaitingAuditList(Audit audit);
}
