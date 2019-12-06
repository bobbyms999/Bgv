package com.accredilink.bgv.key;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.accredilink.bgv.entity.Agency;
import com.accredilink.bgv.entity.Employee;

@Embeddable
public class EmployeeAgencyKey implements Serializable {

	private Employee employee;

	private Agency agency;

	@ManyToOne
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@ManyToOne
	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agency == null) ? 0 : agency.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
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
		EmployeeAgencyKey other = (EmployeeAgencyKey) obj;
		if (agency == null) {
			if (other.agency != null)
				return false;
		} else if (!agency.equals(other.agency))
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		return true;
	}

}
