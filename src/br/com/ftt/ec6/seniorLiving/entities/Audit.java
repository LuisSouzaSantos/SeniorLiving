package br.com.ftt.ec6.seniorLiving.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "audit")
public class Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "timestamp")
	private Long timestamp;
	
	@Column(name = "method_used", nullable = true)
	private String methodUsed;                            
	
	@Column(name = "action", nullable = true)
	@Enumerated(EnumType.STRING)
	private Action action;
	
	@Column(name = "user", nullable = true)
	private String user;
	
	@Column(name = "role", nullable = true)
	private String roleUsed;
	
	public Audit() {}
	
	public Audit(Long timestamp, String methodUsed, Action action, String user, String roleUsed) {
		this.timestamp = timestamp;
		this.methodUsed = methodUsed;
		this.action = action;
		this.user = user;
		this.roleUsed = roleUsed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMethodUsed() {
		return methodUsed;
	}

	public void setMethodUsed(String methodUsed) {
		this.methodUsed = methodUsed;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRoleUsed() {
		return roleUsed;
	}

	public void setRoleUsed(String roleUsed) {
		this.roleUsed = roleUsed;
	}
	
	
		
}
