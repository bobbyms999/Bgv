package com.accredilink.bgv.pojo;

import java.time.LocalDate;
import java.util.Date;

public class BgDetails {

	private int employeeId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String emailId;
	private String ssnNumber;
	private String agencyName;
	private Date bgvDate;
	private String bgvResult;

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

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Date getBgvDate() {
		return bgvDate;
	}

	public void setBgvDate(Date bgvDate) {
		this.bgvDate = bgvDate;
	}

	public String getBgvResult() {
		return bgvResult;
	}

	public void setBgvResult(String bgvResult) {
		this.bgvResult = bgvResult;
	}

}
