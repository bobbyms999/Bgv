package com.accredilink.bgv.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ACCRLNK_AGENCY")
@EntityListeners(AuditingEntityListener.class)
public class Agency implements Serializable{

	private int agencyId;
	@Column(name = "AGENCY_NAME", length = 15)
	private String agencyName;
	@Column(name = "EIN")
	private Long ein;

	private Set<EmployeeAgency> employeeAgency = new HashSet<EmployeeAgency>();

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
	@Column(name = "AGENCY_ID", nullable = false)
	@GeneratedValue
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeAgencyPk.agency", cascade=CascadeType.ALL)
	public Set<EmployeeAgency> getEmployeeAgency() {
		return employeeAgency;
	}

	public void setEmployeeAgency(Set<EmployeeAgency> employeeAgency) {
		this.employeeAgency = employeeAgency;
	}

	public void addEmployeeAgency(EmployeeAgency employeeAgency) {
		this.employeeAgency.add(employeeAgency);
	}

}
