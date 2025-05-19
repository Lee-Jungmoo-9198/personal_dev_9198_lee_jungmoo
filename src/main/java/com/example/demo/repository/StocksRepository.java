package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Stocks;

public interface StocksRepository extends JpaRepository<Stocks, Integer> {

}
