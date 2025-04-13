package com.example.gestionproduit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionproduit.entity.Cart;
import com.example.gestionproduit.entity.Product;
import com.example.gestionproduit.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    
    

    public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    public void addProductToCart(@PathVariable Long userId, @RequestBody Product product) {
        cartService.addProductToCart(userId, product);
    }

}