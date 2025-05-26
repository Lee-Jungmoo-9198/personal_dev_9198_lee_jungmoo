package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.OrderDetails;

public interface OrderDetailRepository extends JpaRepository<OrderDetails, Integer> {

	void deleteByOrdersId(Integer ordersId);

	List<OrderDetails> findByOrdersId(Integer ordersId);

}