package com.accredilink.bgv.entity;

import java.sql.Blob;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.accredilink.bgv.key.EmployeeBgDetailsKey;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACCRLNK_EMPLOYEE_BG_DETAILS")
public class EmployeeBgDetails {

	private EmployeeBgDetailsKey employeeBgDetailsKey;

	@Column(name = "bgresult")
	private String bgresult;

	@Column(name = "bgresultdesc")
	private String bgresultdesc;

	@Lob
	@Column(name = "bgresultproof", columnDefinition = "BLOB")
	@JsonIgnore
	private Blob bgresultproof;

	@CreatedDate
	@Column(name = "CREATEDDATE")
	private LocalDate createdDate;

	@LastModifiedDate
	@Column(name = "MODIFIEDDATE")
	private LocalDate modifiedDate;

	@CreatedBy
	@Column(name = "CREATEDBY")
	private String createdBy;

	@LastModifiedBy
	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "ACTIVE")
	private boolean active;

	@EmbeddedId
	public EmployeeBgDetailsKey getEmployeeBgDetailsPk() {
		return employeeBgDetailsKey;
	}

	public void setEmployeeBgDetailsPk(EmployeeBgDetailsKey employeeBgDetailsKey) {
		this.employeeBgDetailsKey = employeeBgDetailsKey;
	}

	public String getBgresult() {
		return bgresult;
	}

	public void setBgresult(String bgresult) {
		this.bgresult = bgresult;
	}

	public String getBgresultdesc() {
		return bgresultdesc;
	}

	public void setBgresultdesc(String bgresultdesc) {
		this.bgresultdesc = bgresultdesc;
	}

	public Blob getBgresultproof() {
		return bgresultproof;
	}

	public void setBgresultproof(Blob bgresultproof) {
		this.bgresultproof = bgresultproof;
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

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
