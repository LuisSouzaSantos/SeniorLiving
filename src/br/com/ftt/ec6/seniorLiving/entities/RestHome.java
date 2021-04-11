package br.com.ftt.ec6.seniorLiving.entities;

import java.util.List;

import javax.persistence.Column;
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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "rest_home", uniqueConstraints = {@UniqueConstraint(columnNames = {"cnpj"})})
public class RestHome extends BaseConfig {
	
	@Transient
	private final static String LINKED_DATABASE = "SeniorLiving";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 14, nullable = false)
	private String cnpj;
	
	@Column(name = "social_reason", length = 100, nullable = false)
	private String socialReason;
	
	@Column(name = "address_street", length = 100, nullable = false)
	private String addressStreet;
	
	@Column(name = "address_number", length = 10, nullable = false)
	private String addressNumber;
	
	@Column(name = "address_state", length = 20, nullable = false)
	private String addressState;
	
	@Column(name = "address_cep", length = 8, nullable = false)
	private String addressCep;
	
	@Column(name = "address_neighborhood", length = 100, nullable = false)
	private String addressNeighborhood;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User admin;
	
	@ManyToMany
	@JoinTable(
			name = "rest_home_user",
			joinColumns = @JoinColumn(name="rest_home_id"),
			inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> userList;
	
	@ManyToMany
	@JoinTable(
			name = "rest_home_type",
			joinColumns = @JoinColumn(name="rest_home_id"),
			inverseJoinColumns = @JoinColumn(name="type_id"))
	private List<Type> typeList;
	
	@ManyToMany
	@JoinTable(
			name = "rest_home_accommodation",
			joinColumns = @JoinColumn(name="rest_home_id"),
			inverseJoinColumns = @JoinColumn(name="accommodation_id"))
	private List<Accommodation> accommodationList;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSocialReason() {
		return socialReason;
	}

	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
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

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Type> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}

	public List<Accommodation> getAccommodationList() {
		return accommodationList;
	}

	public void setAccommodationList(List<Accommodation> accommodationList) {
		this.accommodationList = accommodationList;
	}

	@Override
	public String getLinkedToDatabase() {
		return LINKED_DATABASE;
	}
	
}
