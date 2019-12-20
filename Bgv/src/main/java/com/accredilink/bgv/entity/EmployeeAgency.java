package com.accredilink.bgv.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.accredilink.bgv.key.EmployeeAgencyKey;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACCRLNK_EMPLOYEE_AGENCY")
@AssociationOverrides({
		@AssociationOverride(name = "employeeAgencyPk.employee", joinColumns = @JoinColumn(name = "EMPLOYEE_ID")),
		@AssociationOverride(name = "employeeAgencyPk.agency", joinColumns = @JoinColumn(name = "AGENCY_ID")) })
public class EmployeeAgency implements Serializable {

	private EmployeeAgencyKey employeeAgencyPk = new EmployeeAgencyKey();
	private String bgStatus;
	private Date bgvDate;

	@EmbeddedId
	public EmployeeAgencyKey getEmployeeAgencyPk() {
		return employeeAgencyPk;
	}

	public void setEmployeeAgencyPk(EmployeeAgencyKey employeeAgencyPk) {
		this.employeeAgencyPk = employeeAgencyPk;
	}

	public String getBgStatus() {
		return bgStatus;
	}

	@Transient
	@JsonIgnore
	public Employee getEmployee() {
		return getEmployeeAgencyPk().getEmployee();
	}

	public void setEmployee(Employee employee) {
		getEmployeeAgencyPk().setEmployee(employee);
	}

	@Transient
	@JsonIgnore
	public Agency getAgency() {
		return getEmployeeAgencyPk().getAgency();
	}

	public void setAgency(Agency agency) {
		getEmployeeAgencyPk().setAgency(agency);
	}

	@Column(name = "BG_STATUS", length = 15)
	public void setBgStatus(String bgStatus) {
		this.bgStatus = bgStatus;
	}
	

	public Date getBgvDate() {
		return bgvDate;
	}

	public void setBgvDate(Date bgvDate) {
		this.bgvDate = bgvDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeAgencyPk == null) ? 0 : employeeAgencyPk.hashCode());
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
		EmployeeAgency other = (EmployeeAgency) obj;
		if (employeeAgencyPk == null) {
			if (other.employeeAgencyPk != null)
				return false;
		} else if (!employeeAgencyPk.equals(other.employeeAgencyPk))
			return false;
		return true;
	}

}
