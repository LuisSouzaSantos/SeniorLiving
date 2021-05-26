package br.com.ftt.ec6.seniorLiving.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.ftt.ec6.seniorLiving.utils.MaritalStatus;

@Entity
@Table(name = "elderly")
public class Elderly extends BaseConfig {
	
	@Transient
	private final static String LINKED_DATABASE = "SeniorLiving";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String name;
	
	@Column(name = "marital_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;
	
	@Column(length = 30, nullable = false)
	private String nationality;
	
	@Column(length = 30, unique = false, nullable = false)
	private String rg;
	
	@Column(length = 11, unique = false, nullable = false)
	private String cpf;
	
	@Column(nullable = false, name = "birth_date")
	private LocalDate birthDate;
	
	@Column(nullable = false, name = "monthly_payment")
	private BigDecimal monthlyPayment;
	
	@ManyToOne
	@JoinColumn(name = "curator_id", nullable = false)
	private Person curator;
	
	@ManyToOne
	@JoinColumn(name = "sympathetic_id", nullable = false)
	private Person sympathetic;
	
	@ManyToOne
	@JoinColumn(name = "accommodation_id", nullable = false)
	private Accommodation accommodation;
	
	@ManyToOne
	@JoinColumn(name = "rest_home_id", nullable = false)
	private RestHome restHome;
	
	@OneToMany(mappedBy = "elderly", orphanRemoval = true, cascade = CascadeType.ALL)
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
	
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
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
	
	public Person getSympathetic() {
		return sympathetic;
	}
	
	public void setSympathetic(Person sympathetic) {
		this.sympathetic = sympathetic;
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
	public String toString() {
		// TODO Auto-generated method stub
		return this.name+"("+this.cpf+")";
	}

	@Override
	public String getLinkedToDatabase() {
		return LINKED_DATABASE;
	}
	
}
