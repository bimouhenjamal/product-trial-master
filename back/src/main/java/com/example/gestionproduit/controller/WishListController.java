package com.example.gestionproduit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionproduit.entity.Product;
import com.example.gestionproduit.entity.Wish;
import com.example.gestionproduit.service.WishListService;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    private final WishListService wishListService;
    
    public WishListController(WishListService wishListService) {
		this.wishListService = wishListService;
	}

	@GetMapping("/{userId}")
    public Wish getWishList(@PathVariable Long userId) {
        return wishListService.getWishlistByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    public void addProductToWishlist(@PathVariable Long userId, @RequestBody Product product) {
    	wishListService.addProductToWishlist(userId, product);
    }

}
