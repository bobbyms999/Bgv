package com.accredilink.bgv.key;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.accredilink.bgv.entity.Agency;
import com.accredilink.bgv.entity.BgDataBase;
import com.accredilink.bgv.entity.Employee;

@Embeddable
public class EmployeeBgDetailsKey implements Serializable {

	private Employee employee;
	private Agency agency;
	private Date bg_date_time;
	private BgDataBase bgDateBase;

	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Agency.class)
	@JoinColumn(name = "AGENCY_ID")
	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Employee.class)
	@JoinColumn(name = "EMPLOYEE_ID")
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	
	@Column(name = "bg_date_time")
	public Date getBg_date_time() {
		return bg_date_time;
	}

	public void setBg_date_time(Date bg_date_time) {
		this.bg_date_time = bg_date_time;
	}

	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = BgDataBase.class)
	@JoinColumn(name = "BG_DB_ID")
	public BgDataBase getBgDateBase() {
		return bgDateBase;
	}

	public void setBgDateBase(BgDataBase bgDateBase) {
		this.bgDateBase = bgDateBase;
	}

}
