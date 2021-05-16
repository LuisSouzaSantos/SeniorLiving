package br.com.ftt.ec6.seniorLiving.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.ftt.ec6.seniorLiving.utils.MaritalStatus;

@Entity
@Table(name = "person")
public class Person extends BaseConfig {

	@Transient
	private final static String LINKED_DATABASE = "SeniorLiving";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, length = 30)
	private String nationality;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "marital_status")
	private MaritalStatus maritalStatus;
	
	@Column(nullable = false, length = 30)
	private String job;
	
	@Column(nullable = false, unique = true, length = 30)
	private String rg;
	
	@Column(nullable = false, unique = true, length = 11)
	private String cpf;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false, name = "address_street", length = 100)
	private String addressStreet;
	
	@Column(nullable = false, name = "address_number", length = 10)
	private String addressNumber;
	
	@Column(nullable = false, name = "address_state", length = 20)
	private String addressState;
	
	@Column(nullable = false, name = "address_cep", length = 8)
	private String addressCep;
	
	@Column(nullable = false, name = "address_neighborhood", length = 100)
	private String addressNeighborhood;
	
	@Column(nullable = false, name = "birth_date")
	private LocalDate birthDate;
	
	@ManyToOne
	@JoinColumn(name = "rest_home_id", nullable = false)
	private RestHome restHome;
	
	@ManyToMany
	@JoinTable(
			name = "person_type",
			joinColumns = @JoinColumn(name="person_id"),
			inverseJoinColumns = @JoinColumn(name="type_id"))
	private List<Type> typeList;
	
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
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	public String getAddressNumber() {
		return addressNumber;
	}
	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}
	public String getAddressState() {
		return addressState;
	}
	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}
	public String getAddressCep() {
		return addressCep;
	}
	public void setAddressCep(String addressCep) {
		this.addressCep = addressCep;
	}
	public String getAddressNeighborhood() {
		return addressNeighborhood;
	}
	public void setAddressNeighborhood(String addressNeighborhood) {
		this.addressNeighborhood = addressNeighborhood;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public RestHome getRestHome() {
		return restHome;
	}
	public void setRestHome(RestHome restHome) {
		this.restHome = restHome;
	}
	public List<Type> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}
	
	@Override
	public String getLinkedToDatabase() {
		return LINKED_DATABASE;
	}
	
}
