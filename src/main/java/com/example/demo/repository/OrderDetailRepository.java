package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Order_details;

public interface OrderDetailRepository extends JpaRepository<Order_details, Integer> {

}
