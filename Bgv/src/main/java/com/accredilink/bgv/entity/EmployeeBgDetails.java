package com.accredilink.bgv.entity;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "ACCRLNK_EMPLOYEE_BG_DETAILS")
public class EmployeeBgDetails {

	private Employee employee;
	private Agency agency;
	
	
	
	private Date bgDateTime;
	
	private BgDataBase bgDateBase;
	
	@Column(name = "bg_result")
	private String bgResult;
	
	@Column(name = "bg_result_desc")
	private String bgResultDesc;
	
	@Column(name = "bg_result_proof")
	private Blob bgResultProof;

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

	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = Employee.class)
	@JoinColumn(name = "EMPLOYEE_ID")
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = Agency.class)
	@JoinColumn(name = "AGENCY_ID")
	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	@Id
	@Column(name = "bg_date_time")
	public Date getBgDateTime() {
		return bgDateTime;
	}

	public void setBgDateTime(Date bgDateTime) {
		this.bgDateTime = bgDateTime;
	}

	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = BgDataBase.class)
	@JoinColumn(name="BG_DB_ID")
	public BgDataBase getBgDateBase() {
		return bgDateBase;
	}

	public void setBgDateBase(BgDataBase bgDateBase) {
		this.bgDateBase = bgDateBase;
	}

	public String getBgResult() {
		return bgResult;
	}

	public void setBgResult(String bgResult) {
		this.bgResult = bgResult;
	}

	public String getBgResultDesc() {
		return bgResultDesc;
	}

	public void setBgResultDesc(String bgResultDesc) {
		this.bgResultDesc = bgResultDesc;
	}

	public Blob getBgResultProof() {
		return bgResultProof;
	}

	public void setBgResultProof(Blob bgResultProof) {
		this.bgResultProof = bgResultProof;
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

}
