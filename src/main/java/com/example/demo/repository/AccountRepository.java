package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	boolean existsByEmail(String trim);

	Optional<Account> findByEmail(String email);

	String findEmailByEmail(String email);
	
	
	
}
