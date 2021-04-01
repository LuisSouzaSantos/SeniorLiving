package br.com.ftt.ec6.seniorLiving.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Billing extends BaseConfig {
	
	@Transient
	private final static String LINKED_DATABASE = "SeniorLiving";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String registrationCode;
	private LocalDate month;
	
	@ManyToOne
	@JoinColumn(name = "rest_home_id")
	private RestHome restHome;
	
	@OneToMany(mappedBy = "billing")
	private List<BillingProduct> billingProductList;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRegistrationCode() {
		return registrationCode;
	}
	
	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}
	
	public LocalDate getMonth() {
		return month;
	}

	public void setMonth(LocalDate month) {
		this.month = month;
	}

	public List<BillingProduct> getBillingProductList() {
		return billingProductList;
	}
	
	public void setBillingProductList(List<BillingProduct> billingProductList) {
		this.billingProductList = billingProductList;
	}
	
	public RestHome getRestHome() {
		return restHome;
	}

	public void setRestHome(RestHome restHome) {
		this.restHome = restHome;
	}

	@Override
	public String getLinkedToDatabase() {
		return LINKED_DATABASE;
	}

}
