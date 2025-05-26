package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findByAccountsIdOrderByOrderedOnDesc(Integer accountsId);

	List<Order> findByAccountsId(Integer accountsId);

	void deleteByAccountsId(Integer accountsId);

	List<Order> findAllByOrderByOrderedOnDesc();

}