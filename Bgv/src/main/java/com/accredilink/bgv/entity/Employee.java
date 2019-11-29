package com.accredilink.bgv.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ACCRLNK_EMPLOYEE")
@EntityListeners(AuditingEntityListener.class)
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EMPLOYEE_ID", nullable = false)
	private int employeeId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "DATE_OF_BIRTH")
	private LocalDate dateOfBirth;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "SSN_NUMBER")
	private String ssnNumber;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ACCRLNK_EMPLOYEE_AGENCY", joinColumns = {
			@JoinColumn(name = "EMPLOYEE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AGENCY_ID") })
	private List<Agency> agency;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "DISCIPLINE_ID")
	private Discipline discipline;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESS_ID")
	private Address address;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ALIAS_ID")
	private Alias alias;

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
	private String active;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getSsnNumber() {
		return ssnNumber;
	}

	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}

	public List<Agency> getAgency() {
		return agency;
	}

	public void setAgency(List<Agency> agency) {
		this.agency = agency;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Alias getAlias() {
		return alias;
	}

	public void setAlias(Alias alias) {
		this.alias = alias;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((agency == null) ? 0 : agency.hashCode());
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((discipline == null) ? 0 : discipline.hashCode());
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + employeeId;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
		result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((ssnNumber == null) ? 0 : ssnNumber.hashCode());
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
		Employee other = (Employee) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (agency == null) {
			if (other.agency != null)
				return false;
		} else if (!agency.equals(other.agency))
			return false;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
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
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (discipline == null) {
			if (other.discipline != null)
				return false;
		} else if (!discipline.equals(other.discipline))
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
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
		if (ssnNumber == null) {
			if (other.ssnNumber != null)
				return false;
		} else if (!ssnNumber.equals(other.ssnNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", middleName=" + middleName + ", dateOfBirth=" + dateOfBirth + ", emailId=" + emailId
				+ ", ssnNumber=" + ssnNumber + ", agency=" + agency + ", discipline=" + discipline + ", address="
				+ address + ", alias=" + alias + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", active=" + active + "]";
	}

}
