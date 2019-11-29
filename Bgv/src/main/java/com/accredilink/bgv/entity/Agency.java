package com.accredilink.bgv.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ACCRLNK_AGENCY")
@EntityListeners(AuditingEntityListener.class)
public class Agency {

	@Id
	@Column(name = "AGENCY_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int agencyId;
	@Column(name = "AGENCY_NAME")
	private String agencyName;
	@Column(name = "EIN")
	private Long ein;

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

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Long getEin() {
		return ein;
	}

	public void setEin(Long ein) {
		this.ein = ein;
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
		result = prime * result + agencyId;
		result = prime * result + ((agencyName == null) ? 0 : agencyName.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((ein == null) ? 0 : ein.hashCode());
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
		Agency other = (Agency) obj;
		if (active != other.active)
			return false;
		if (agencyId != other.agencyId)
			return false;
		if (agencyName == null) {
			if (other.agencyName != null)
				return false;
		} else if (!agencyName.equals(other.agencyName))
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
		if (ein == null) {
			if (other.ein != null)
				return false;
		} else if (!ein.equals(other.ein))
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
		return "Agency [agencyId=" + agencyId + ", agencyName=" + agencyName + ", ein=" + ein + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", active=" + active + "]";
	}

}
