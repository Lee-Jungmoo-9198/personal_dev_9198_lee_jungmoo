package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Admins;

public interface AdminsRepository extends JpaRepository<Admins, Integer> {

	Optional<Admins> findByEmail(String email);

}
