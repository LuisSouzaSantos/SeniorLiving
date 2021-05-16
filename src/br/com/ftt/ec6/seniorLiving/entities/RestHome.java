package br.com.ftt.ec6.seniorLiving.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "restHome", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Type> typeList;
	
	@OneToMany(mappedBy = "restHome", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Accommodation> accommodationList;
	
	@OneToMany(mappedBy = "restHome", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Product> productList;
	
	@OneToMany(mappedBy = "restHome", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Person> personList;
	
	@OneToMany(mappedBy = "restHome", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Elderly> elderlyList;
	
	
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
	
	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

	public List<Elderly> getElderlyList() {
		return elderlyList;
	}

	public void setElderlyList(List<Elderly> elderlyList) {
		this.elderlyList = elderlyList;
	}

	@Override
	public String getLinkedToDatabase() {
		return LINKED_DATABASE;
	}
	
	@Override
	public String toString() {
		return this.socialReason+"("+this.cnpj+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accommodationList == null) ? 0 : accommodationList.hashCode());
		result = prime * result + ((addressCep == null) ? 0 : addressCep.hashCode());
		result = prime * result + ((addressNeighborhood == null) ? 0 : addressNeighborhood.hashCode());
		result = prime * result + ((addressNumber == null) ? 0 : addressNumber.hashCode());
		result = prime * result + ((addressState == null) ? 0 : addressState.hashCode());
		result = prime * result + ((addressStreet == null) ? 0 : addressStreet.hashCode());
		result = prime * result + ((admin == null) ? 0 : admin.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((elderlyList == null) ? 0 : elderlyList.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((personList == null) ? 0 : personList.hashCode());
		result = prime * result + ((productList == null) ? 0 : productList.hashCode());
		result = prime * result + ((socialReason == null) ? 0 : socialReason.hashCode());
		result = prime * result + ((typeList == null) ? 0 : typeList.hashCode());
		result = prime * result + ((userList == null) ? 0 : userList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestHome other = (RestHome) obj;
		if (accommodationList == null) {
			if (other.accommodationList != null)
				return false;
		} else if (!accommodationList.equals(other.accommodationList))
			return false;
		if (addressCep == null) {
			if (other.addressCep != null)
				return false;
		} else if (!addressCep.equals(other.addressCep))
			return false;
		if (addressNeighborhood == null) {
			if (other.addressNeighborhood != null)
				return false;
		} else if (!addressNeighborhood.equals(other.addressNeighborhood))
			return false;
		if (addressNumber == null) {
			if (other.addressNumber != null)
				return false;
		} else if (!addressNumber.equals(other.addressNumber))
			return false;
		if (addressState == null) {
			if (other.addressState != null)
				return false;
		} else if (!addressState.equals(other.addressState))
			return false;
		if (addressStreet == null) {
			if (other.addressStreet != null)
				return false;
		} else if (!addressStreet.equals(other.addressStreet))
			return false;
		if (admin == null) {
			if (other.admin != null)
				return false;
		} else if (!admin.equals(other.admin))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (elderlyList == null) {
			if (other.elderlyList != null)
				return false;
		} else if (!elderlyList.equals(other.elderlyList))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (personList == null) {
			if (other.personList != null)
				return false;
		} else if (!personList.equals(other.personList))
			return false;
		if (productList == null) {
			if (other.productList != null)
				return false;
		} else if (!productList.equals(other.productList))
			return false;
		if (socialReason == null) {
			if (other.socialReason != null)
				return false;
		} else if (!socialReason.equals(other.socialReason))
			return false;
		if (typeList == null) {
			if (other.typeList != null)
				return false;
		} else if (!typeList.equals(other.typeList))
			return false;
		if (userList == null) {
			if (other.userList != null)
				return false;
		} else if (!userList.equals(other.userList))
			return false;
		return true;
	}
	
	
}
