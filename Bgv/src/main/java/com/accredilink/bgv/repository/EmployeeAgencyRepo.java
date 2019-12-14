package com.accredilink.bgv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.key.EmployeeAgencyKey;

public interface EmployeeAgencyRepo extends JpaRepository<EmployeeAgency, EmployeeAgencyKey>{

}
