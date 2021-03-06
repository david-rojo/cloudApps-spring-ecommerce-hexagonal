package com.cloudapps.ecommerce.infrastructure.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="shopping_cart")
public class ShoppingCartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private boolean completed;
	
	@OneToMany(mappedBy = "shoppingCart")
	private  List<CartItemEntity> cartItems;
	
	public ShoppingCartEntity() {}

	public ShoppingCartEntity(Long id, boolean completed) {
		this(id, completed, new ArrayList<>());
	}
	
	public ShoppingCartEntity(boolean completed) {
		this(null, completed, new ArrayList<>());
	}
	
	public ShoppingCartEntity(Long id, boolean completed, List<CartItemEntity> cartItems) {
		this.id = id;
		this.completed = completed;
		this.cartItems = cartItems;
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

	public List<CartItemEntity> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemEntity> cartItems) {
		this.cartItems = cartItems;
	}
	
	public void addCartItem(CartItemEntity cartItem) {
		cartItems.add(cartItem);
	}
	
	public void removeCartItem(CartItemEntity cartItem) {
		boolean removed = cartItems.remove(cartItem);
		if(!removed) {
			throw new NoSuchElementException();
		}
	}
	
}
