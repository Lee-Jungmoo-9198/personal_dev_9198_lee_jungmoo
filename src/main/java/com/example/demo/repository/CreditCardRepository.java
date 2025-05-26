package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    @Query("SELECT c FROM CreditCard c WHERE c.accountId = :accountId AND (c.deleteFlg IS NULL OR c.deleteFlg <> '1')")
    List<CreditCard> findByAccountId(@Param("accountId") Integer accountId);

	void deleteByAccountId(Integer accountId);
}
