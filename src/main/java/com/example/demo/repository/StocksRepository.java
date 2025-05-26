package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Stocks;

public interface StocksRepository extends JpaRepository<Stocks, Integer> {

	Optional<Stocks> findByItemsId(Integer itemsId);

}
