package com.accredilink.bgv.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ACCRLNK_ALIAS")
@EntityListeners(AuditingEntityListener.class)
public class Alias {

	
	private int aliasId;
	@Column(name = "ALIAS_NAME_FOR",length = 15)
	private String aliasNameFor;
	
	@Column(name = "ALIAS_NAMES_GENERAL")
	private String aliasNamesGeneral;

	@CreatedDate
	@Column(name = "CREATED_DATE")
	private LocalDate createdDate;

	@LastModifiedDate
	@Column(name = "MODIFIED_DATE")
	private LocalDate modifiedDate;

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@LastModifiedBy
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "ACTIVE")
	private boolean active;

	@Id
	@Column(name = "ALIAS_ID", nullable = false)
	@GeneratedValue
	public int getAliasId() {
		return aliasId;
	}

	public void setAliasId(int aliasId) {
		this.aliasId = aliasId;
	}

	public String getAliasNameFor() {
		return aliasNameFor;
	}

	public void setAliasNameFor(String aliasNameFor) {
		this.aliasNameFor = aliasNameFor;
	}

	public String getAliasNamesGeneral() {
		return aliasNamesGeneral;
	}

	public void setAliasNamesGeneral(String aliasNamesGeneral) {
		this.aliasNamesGeneral = aliasNamesGeneral;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + aliasId;
		result = prime * result + ((aliasNameFor == null) ? 0 : aliasNameFor.hashCode());
		result = prime * result + ((aliasNamesGeneral == null) ? 0 : aliasNamesGeneral.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
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
		Alias other = (Alias) obj;
		if (active != other.active)
			return false;
		if (aliasId != other.aliasId)
			return false;
		if (aliasNameFor == null) {
			if (other.aliasNameFor != null)
				return false;
		} else if (!aliasNameFor.equals(other.aliasNameFor))
			return false;
		if (aliasNamesGeneral == null) {
			if (other.aliasNamesGeneral != null)
				return false;
		} else if (!aliasNamesGeneral.equals(other.aliasNamesGeneral))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Alias [aliasId=" + aliasId + ", aliasNameFor=" + aliasNameFor + ", aliasNamesGeneral="
				+ aliasNamesGeneral + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + ", active=" + active + "]";
	}

	
}
