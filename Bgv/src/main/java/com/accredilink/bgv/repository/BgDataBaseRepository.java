package com.accredilink.bgv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accredilink.bgv.entity.BgDataBase;

public interface BgDataBaseRepository  extends JpaRepository<BgDataBase, Integer>{
	
	public BgDataBase findAllByBgDbId(int bgdbid);


}
