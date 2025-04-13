package com.example.gestionproduit.service;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.gestionproduit.entity.Product;
import com.example.gestionproduit.entity.Wish;
import com.example.gestionproduit.repository.UserRepository;
import com.example.gestionproduit.repository.WishListRepository;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;

    public WishListService(WishListRepository wishListRepository, UserRepository userRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
    }

    public Wish getWishlistByUserId(Long userId) {
        return wishListRepository.findByUserId(userId);
    }

    public void addProductToWishlist(Long userId, Product product) {
        Wish wishList = wishListRepository.findByUserId(userId);
        if (wishList == null) {
        	wishList = new Wish();
        	wishList.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        	wishList.setProducts(new ArrayList<>());
        }
        wishList.getProducts().add(product);
        wishListRepository.save(wishList);
    }
}
