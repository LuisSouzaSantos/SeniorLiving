package br.com.ftt.ec6.seniorLiving.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "accommodation", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Accommodation extends BaseConfig {
	
	@Transient
	private final static String LINKED_DATABASE = "SeniorLiving";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 50, nullable = false)
	private String name;
	
	@Column(unique = false, length = 100, nullable = false)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rest_home_id")
	private RestHome restHome;
	
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public RestHome getRestHome() {
		return restHome;
	}

	public void setRestHome(RestHome restHome) {
		this.restHome = restHome;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public String getLinkedToDatabase() {
		return LINKED_DATABASE;
	}
	
}
