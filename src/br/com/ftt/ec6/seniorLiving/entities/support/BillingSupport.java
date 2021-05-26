package br.com.ftt.ec6.seniorLiving.entities.support;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;

public class BillingSupport {

	private Long id;
	private LocalDate month;
	private Elderly elderly;
	private BigDecimal amount;
	private Billing billing;
	
	public BillingSupport() {
		
	}
		
	public BillingSupport(Long id, LocalDate month, Elderly elderly, BigDecimal amount, Billing billing) {
		this.id = id;
		this.month = month;
		this.elderly = elderly;
		this.amount = amount;
		this.billing = billing;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getMonth() {
		return month;
	}
	public void setMonth(LocalDate month) {
		this.month = month;
	}
	public Elderly getElderly() {
		return elderly;
	}
	public void setElderly(Elderly elderly) {
		this.elderly = elderly;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Billing getBilling() {
		return billing;
	}
	public void setBilling(Billing billing) {
		this.billing = billing;
	}
	
}
