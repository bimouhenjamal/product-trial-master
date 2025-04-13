package com.example.gestionproduit.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.gestionproduit.entity.Cart;
import com.example.gestionproduit.entity.Product;
import com.example.gestionproduit.repository.CartRepository;
import com.example.gestionproduit.repository.UserRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    
    public CartService(CartRepository cartRepository, UserRepository userRepository) {
    	this.cartRepository = cartRepository;
    	this.userRepository = userRepository;
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void addProductToCart(Long userId, Product product) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            cart.setProducts(new ArrayList<>());
        }
        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

}