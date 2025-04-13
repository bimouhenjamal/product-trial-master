package com.example.gestionproduit.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.gestionproduit.entity.Product;
import com.example.gestionproduit.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}


	public Product updateProduct(Long id, Product productDetails) {
	    if (!productRepository.existsById(id)) {
	        throw new NoSuchElementException("Product not found with id " + id);
	    }
	    productDetails.setId(id);
	    productDetails.setUpdatedAt(LocalDateTime.now());
	    return productRepository.save(productDetails);
	}


	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}