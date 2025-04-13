package com.example.gestionproduit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionproduit.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
}