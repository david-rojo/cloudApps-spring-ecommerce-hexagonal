package com.cloudapps.ecommerce.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudapps.ecommerce.controller.dto.shoppingcart.ShoppingCartPostResponseDto;
import com.cloudapps.ecommerce.controller.dto.shoppingcart.ShoppingCartResponseDto;
import com.cloudapps.ecommerce.domain.exception.NotValidatedShoppingCartException;
import com.cloudapps.ecommerce.domain.shoppingcart.dto.FullShoppingCartDto;
import com.cloudapps.ecommerce.domain.shoppingcart.dto.NewShoppingCartDto;
import com.cloudapps.ecommerce.service.ShoppingCartService;

@RestController
@RequestMapping (value="/api/shoppingcarts")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCarts;
	
	@Autowired
	private ControllerObjectMapper mapper;
	
	@PostMapping(value="")
	public ResponseEntity<ShoppingCartPostResponseDto> postShoppingCart() {
		
		NewShoppingCartDto newShoppingCart = shoppingCarts.create();
		
		ShoppingCartPostResponseDto shoppingCartPostResponseDto = new ShoppingCartPostResponseDto(
				newShoppingCart.getId(),
				newShoppingCart.isCompleted()); 

		URI location = fromCurrentRequest().path("/{id}")
				.buildAndExpand(newShoppingCart.getId()).toUri();

		return ResponseEntity.created(location).body(shoppingCartPostResponseDto);
	}
	
	@PatchMapping(path = "/{id}")
	public ResponseEntity<ShoppingCartResponseDto> completeShoppingCart(@PathVariable Long id) {
		
		try {
			FullShoppingCartDto shoppingCart = shoppingCarts.complete(id).orElseThrow();	
			return new ResponseEntity<>(mapper.toShoppingCartResponseDto(shoppingCart), HttpStatus.OK);
		} catch (NotValidatedShoppingCartException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(value="/{id}")
	public ShoppingCartResponseDto getShoppingCart(@PathVariable(value="id") Long id) {
		FullShoppingCartDto shoppingCartDto = shoppingCarts.findById(id).orElseThrow();
		return mapper.toShoppingCartResponseDto(shoppingCartDto);
	}
	
	@DeleteMapping(value="/{id}")
	public ShoppingCartResponseDto deleteShoppingCart(@PathVariable(value="id") Long id) {
		FullShoppingCartDto fullShoppingCartDto = shoppingCarts.findById(id).orElseThrow();
		shoppingCarts.delete(id);
		return mapper.toShoppingCartResponseDto(fullShoppingCartDto);
	}
	
	@PostMapping(value="/{cartId}/product/{productId}/quantity/{prodQuantity}")
	public ShoppingCartResponseDto postProductToShoppingCart(
			@PathVariable(value="cartId") Long cartId,
			@PathVariable(value="productId") Long productId,
			@PathVariable(value="prodQuantity") int prodQuantity) {
		FullShoppingCartDto shoppingCartDto = shoppingCarts
				.addProduct(cartId, productId, prodQuantity).orElseThrow();
		return mapper.toShoppingCartResponseDto(shoppingCartDto);
	}
	
	@DeleteMapping(value="/{cartId}/product/{productId}")
	public ShoppingCartResponseDto deleteProductFromShoppingCart(
			@PathVariable(value="cartId") Long cartId,
			@PathVariable(value="productId") Long productId) {
		FullShoppingCartDto shoppingCartDto = shoppingCarts
				.deleteProduct(cartId, productId).orElseThrow();
		return mapper.toShoppingCartResponseDto(shoppingCartDto);
	}
}
