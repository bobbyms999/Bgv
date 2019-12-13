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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACCRLNK_EMPLOYEE")
@EntityListeners(AuditingEntityListener.class)
public class Employee implements Serializable {

	private int employeeId;
	@Column(name = "FIRST_NAME", length = 15)
	private String firstName;

	@Column(name = "LAST_NAME", length = 20)
	private String lastName;

	@Column(name = "MIDDLE_NAME", length = 15)
	private String middleName;

	@Column(name = "DATE_OF_BIRTH")
	private LocalDate dateOfBirth;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "SSN_NUMBER", length = 11)
	private String ssnNumber;

	private Discipline discipline;

	private Address address;

	@Column(name = "ALIAS_SPECIFIC", length = 15)
	private String aliasSpecific;

	@Column(name = "MAIDEN_NAME", length = 15)
	private String maidenName;

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
	private String active;

	@Id
	@GeneratedValue
	@Column(name = "EMPLOYEE_ID", nullable = false)
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeAgencyPk.employee", cascade = CascadeType.ALL)
	@JsonIgnore
	public Set<EmployeeAgency> getEmployeeAgency() {
		return employeeAgency;
	}

	public void setEmployeeAgency(Set<EmployeeAgency> employeeAgency) {
		this.employeeAgency = employeeAgency;
	}

	@OneToOne(cascade = CascadeType.MERGE, targetEntity = Discipline.class)
	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	@OneToOne(cascade = CascadeType.ALL, targetEntity = Address.class)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getAliasSpecific() {
		return aliasSpecific;
	}

	public void setAliasSpecific(String aliasSpecific) {
		this.aliasSpecific = aliasSpecific;
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

	public void addEmployeeAgency(EmployeeAgency employeeAgency) {
		this.employeeAgency.add(employeeAgency);
	}
}
