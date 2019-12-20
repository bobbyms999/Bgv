package com.accredilink.bgv.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accredilink.bgv.entity.Alias;
import com.accredilink.bgv.repository.AliasRepository;

@Service
public class AliasServiceImpl implements AliasService {

	private static final Logger logger = LoggerFactory.getLogger(AliasServiceImpl.class);

	@Autowired
	AliasRepository aliasRepo;
	
	@Override
	public List<Alias> getAllAliasDetails() {
		
		return aliasRepo.findAll();
	}

	@Override
	public Alias findAllByAliasNameFor(String firstName) {
		
		return aliasRepo.findAllByAliasNameFor(firstName);
	}

	@Override
	public Alias findAllByAliasNamesGeneral(String aliasName) {
		return aliasRepo.findAllByAliasNameFor(aliasName);
	}

	


}
