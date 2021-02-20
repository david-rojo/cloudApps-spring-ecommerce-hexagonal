package com.cloudapps.ecommerce.controller.dto.shoppingcart;

import java.util.List;

import com.cloudapps.ecommerce.controller.dto.product.ProductResponseDto;

public class ShoppingCartResponseDto {
	
	private Long id;
	
	private boolean completed;
	
	private List<ProductResponseDto> products;
	
	public ShoppingCartResponseDto() {}

	public ShoppingCartResponseDto(Long id, boolean completed, List<ProductResponseDto> products) {
		this.id = id;
		this.completed = completed;
		this.products = products;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public List<ProductResponseDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductResponseDto> products) {
		this.products = products;
	}

}
