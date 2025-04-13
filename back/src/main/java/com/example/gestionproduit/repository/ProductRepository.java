package com.example.gestionproduit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestionproduit.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}