package br.com.ftt.ec6.seniorLiving.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "billing")
public class Billing extends BaseConfig {
	
	@Transient
	private final static String LINKED_DATABASE = "SeniorLiving";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDate month;
	
	@ManyToOne
	@JoinColumn(name = "rest_home_id")
	private RestHome restHome;
	
	@ManyToOne
	@JoinColumn(name =  "elderly_id")
	private Elderly elderly;
	
	@OneToMany(mappedBy = "billing")
	private List<BillingProduct> billingProductList;
	

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

	public RestHome getRestHome() {
		return restHome;
	}

	public void setRestHome(RestHome restHome) {
		this.restHome = restHome;
	}

	public Elderly getElderly() {
		return elderly;
	}

	public void setElderly(Elderly elderly) {
		this.elderly = elderly;
	}

	public List<BillingProduct> getBillingProductList() {
		return billingProductList;
	}

	public void setBillingProductList(List<BillingProduct> billingProductList) {
		this.billingProductList = billingProductList;
	}

	@Override
	public String getLinkedToDatabase() {
		return LINKED_DATABASE;
	}

}
