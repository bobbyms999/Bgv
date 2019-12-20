package com.accredilink.bgv.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ACCRLNK_DATA_FEED_EMPLOYEE")
@EntityListeners(AuditingEntityListener.class)
public class DataFeedEmployee {
	
	
	private int feed_emp_id;
	
	@Column(name = "FIRST_NAME", length = 15)
	private String firstName;

	@Column(name = "LAST_NAME", length = 20)
	private String lastName;

	@Column(name = "MIDDLE_NAME", length = 15)
	private String middleName;
	
	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "SSN")
	private int ssn;
	
	@Column(name = "DOB")
	private LocalDate dob;
	
	
	private BgDataBase bgDateBase;
	
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
	@Column(name = "FEED_EMP_ID")
	public int getFeed_emp_id() {
		return feed_emp_id;
	}

	public void setFeed_emp_id(int feed_emp_id) {
		this.feed_emp_id = feed_emp_id;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getSsn() {
		return ssn;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
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
	
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = BgDataBase.class)
	@JoinColumn(name="BG_DB_ID")
	public BgDataBase getBgDateBase() {
		return bgDateBase;
	}

	public void setBgDateBase(BgDataBase bgDateBase) {
		this.bgDateBase = bgDateBase;
	}

	@Override
	public String toString() {
		return "DataFeedEmployee [feed_emp_id=" + feed_emp_id + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", middleName=" + middleName + ", emailId=" + emailId + ", ssn=" + ssn + ", dob=" + dob
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", active=" + active + "]";
	}
	
	
	
}
