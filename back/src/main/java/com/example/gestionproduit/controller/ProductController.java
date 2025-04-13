package com.example.gestionproduit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionproduit.entity.Product;
import com.example.gestionproduit.service.ProductService;
import com.example.gestionproduit.util.InventoryStatusEnum;

@RestController
@RequestMapping("/products")
public class ProductController {
	private final ProductService productService;
    private final ApplicationContext applicationContext;
	
	public ProductController(ProductService productService, ApplicationContext applicationContext) {
		this.productService = productService;
		this.applicationContext = applicationContext;
	}

	@PostMapping
	public ResponseEntity<Object> createProduct(@RequestBody Product product) {
	    if (InventoryStatusEnum.isValidStatus(product.getInventoryStatus())) {
	        Product savedProduct = productService.createProduct(product);
	        return ResponseEntity.ok(new AuthResponse("Product created successfully : " + savedProduct.getId()));
	    } else {
	        return ResponseEntity.badRequest().body(new ErrorResponse("Invalid inventory status: " + product.getInventoryStatus()));
	    }
	}

	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
	    if (InventoryStatusEnum.isValidStatus(productDetails.getInventoryStatus())) {
	        return productService.getProductById(id)
	                .map(product -> {
	                    productService.updateProduct(id, productDetails);
	                    return ResponseEntity.ok((Object) new AuthResponse("Product updated successfully!"));
	                })
	                .orElse(ResponseEntity.notFound().build());
	    } else {
	        return ResponseEntity.badRequest().body((Object) new ErrorResponse("Invalid inventory status: " + productDetails.getInventoryStatus()));
	    }
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
	
    @RequestMapping("/endpoints")
    public Map<String, String> getEndpoints() {
        Map<String, String> endpoints = new HashMap<>();
        applicationContext.getBeansWithAnnotation(RestController.class).forEach((name, bean) -> {
            RequestMapping requestMapping = bean.getClass().getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                for (String path : requestMapping.value()) {
                    endpoints.put(name, path);
                }
            }
        });
        return endpoints;
    }
    
    public record AuthResponse(String ok) {}
    public record ErrorResponse(String error) {}
	
}