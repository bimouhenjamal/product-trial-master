package com.example.gestionproduit.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Wish {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wish_id_seq")
	@SequenceGenerator(name = "wish_id_seq", sequenceName = "WISH_ID_SEQ", allocationSize = 1)
	@Column(name = "id", nullable = false)
    private Long id;

    @OneToMany
    private List<Product> products;

    @ManyToOne
    private Users user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}
