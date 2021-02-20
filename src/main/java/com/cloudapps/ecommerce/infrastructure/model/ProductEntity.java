package com.cloudapps.ecommerce.infrastructure.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String description;
	
	@ManyToMany(mappedBy = "products")
    private List<ShoppingCartEntity> shoppingCarts;
	
	public ProductEntity() {}
	
	public ProductEntity(String name, String description) {
		this(null, name, description);
	}

	public ProductEntity(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format("Product [id=%s, name=%s, description=%s, quantity=%d]", id, name, description);
	}
	
}
