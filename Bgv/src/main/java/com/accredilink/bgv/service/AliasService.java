package com.accredilink.bgv.service;

import java.util.List;

import com.accredilink.bgv.entity.Alias;

public interface AliasService {

	public List<Alias> getAllAliasDetails();
	
	public Alias findAllByAliasNameFor(String firstName);
	
	public Alias findAllByAliasNamesGeneral(String aliasName);


}
