package com.accredilink.bgv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accredilink.bgv.entity.Alias;

public interface AliasRepository extends JpaRepository<Alias, Integer>{

	Alias findByAliasName(String aliasName);

	
}
