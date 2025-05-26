package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	List<Address> findByAccountId(Integer accountId);

	void deleteByAccountId(Integer accountId);

}
