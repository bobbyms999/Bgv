package com.accredilink.bgv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accredilink.bgv.entity.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Integer>{

	public Optional<Agency> findByAgencyName(String agencyName);

}
