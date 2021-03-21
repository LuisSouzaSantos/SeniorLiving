package br.com.SeniorLiving.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Agenda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String fullname;
	private String notes;
	private Date initiation;
	private Date termination;
	
	public Agenda(){		
	}
	
	public Agenda(Integer id, String fullname, String notes, Date initiation, Date termination) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.notes = notes;
		this.initiation = initiation;
		this.termination = termination;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getInitiation() {
		return initiation;
	}

	public void setInitiation(Date initiation) {
		this.initiation = initiation;
	}

	public Date getTermination() {
		return termination;
	}

	public void setTermination(Date termination) {
		this.termination = termination;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((initiation == null) ? 0 : initiation.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((termination == null) ? 0 : termination.hashCode());
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
		Agenda other = (Agenda) obj;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (initiation == null) {
			if (other.initiation != null)
				return false;
		} else if (!initiation.equals(other.initiation))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (termination == null) {
			if (other.termination != null)
				return false;
		} else if (!termination.equals(other.termination))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Agenda [id=" + id + ", fullname=" + fullname + ", notes=" + notes + ", initiation=" + initiation
				+ ", termination=" + termination + "]";
	}
}
