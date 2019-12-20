package com.accredilink.bgv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accredilink.bgv.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByEmailIdOrUserName(String emailId,String userName);
	
	public Optional<User> findByEmailId(String emailId);
	
	public Optional<User> findByEmailIdAndTokenNumer(String emailId,String tokenNumer);
}