package br.com.ftt.ec6.seniorLiving.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "elderly")
public class Elderly extends BaseConfig {
	
	@Transient
	private final static String LINKED_DATABASE = "SeniorLiving";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String maritalStatus;
	private String nationality;
	private String rg;
	private String cpf;
	private LocalDate birthDate;
	private BigDecimal monthlyPayment;
	
	@ManyToOne
	@JoinColumn(name = "curator_id")
	private Person curator;
	
	@ManyToOne
	@JoinColumn(name = "sysmpathetic_id")
	private Person sysmpathetic;
	
	@ManyToOne
	@JoinColumn(name = "accommodation_id")
	private Accommodation accommodation;
	
	@ManyToOne
	@JoinColumn(name = "rest_home_id")
	private RestHome restHome;
	
	@ManyToMany
	@JoinTable(
			name = "elderly_billing",
			joinColumns = @JoinColumn(name="elderly_id"),
			inverseJoinColumns = @JoinColumn(name="billing_id"))
	private List<Billing> billingList;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMaritalStatus() {
		return maritalStatus;
	}
	
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getRg() {
		return rg;
	}
	
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public BigDecimal getMonthlyPayment() {
		return monthlyPayment;
	}
	
	public void setMonthlyPayment(BigDecimal monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
	
	public Person getCurator() {
		return curator;
	}
	
	public void setCurator(Person curator) {
		this.curator = curator;
	}
	
	public Person getSysmpathetic() {
		return sysmpathetic;
	}
	
	public void setSysmpathetic(Person sysmpathetic) {
		this.sysmpathetic = sysmpathetic;
	}
	
	public Accommodation getAccommodation() {
		return accommodation;
	}
	
	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}
	
	public RestHome getRestHome() {
		return restHome;
	}
	
	public void setRestHome(RestHome restHome) {
		this.restHome = restHome;
	}
	
	public List<Billing> getBillingList() {
		return billingList;
	}

	public void setBillingList(List<Billing> billingList) {
		this.billingList = billingList;
	}

	@Override
	public String getLinkedToDatabase() {
		return LINKED_DATABASE;
	}
	
}
