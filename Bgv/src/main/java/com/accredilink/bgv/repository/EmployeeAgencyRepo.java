package com.accredilink.bgv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.key.EmployeeAgencyKey;

public interface EmployeeAgencyRepo extends JpaRepository<EmployeeAgency, EmployeeAgencyKey> {

	public List<EmployeeAgency> findAllByBgStatus(String status);

	@Query(name = "select * from accrlnk_employee_agency where employee_id=:id", nativeQuery = true)
	public EmployeeAgency findByEmployeeId(@Param("id") int employeeId);

}
