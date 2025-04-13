package com.example.gestionproduit.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionproduit.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}