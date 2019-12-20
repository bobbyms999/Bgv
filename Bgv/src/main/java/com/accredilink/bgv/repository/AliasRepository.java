package com.accredilink.bgv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accredilink.bgv.entity.Alias;

public interface AliasRepository extends JpaRepository<Alias, Integer> {
	
	public Alias findAllByAliasNameFor(String firstName);
	public Alias findAllByAliasNamesGeneral(String aliasName);
	
	Alias findAllByAliasNameForAndAliasNamesGeneral(String firstName,String aliasName);

	
}
