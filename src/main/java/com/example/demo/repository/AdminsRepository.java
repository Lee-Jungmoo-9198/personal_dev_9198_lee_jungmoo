package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Admins;

public interface AdminsRepository extends JpaRepository<Admins, Integer> {

}
