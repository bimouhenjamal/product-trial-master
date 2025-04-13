package com.example.gestionproduit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gestionproduit.entity.Wish;

public interface WishListRepository extends JpaRepository<Wish, Long> {
    Wish findByUserId(Long userId);
}