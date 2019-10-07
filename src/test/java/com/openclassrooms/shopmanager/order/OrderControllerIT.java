package com.openclassrooms.shopmanager.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.openclassrooms.shopmanager.order.Cart;
import com.openclassrooms.shopmanager.order.OrderRepository;
import com.openclassrooms.shopmanager.order.OrderService;
import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductModel;
import com.openclassrooms.shopmanager.product.ProductRepository;
import com.openclassrooms.shopmanager.product.ProductService;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderControllerIT {
	
	@Autowired
	private ProductRepository productRepository;
	private OrderRepository orderRepository;
	private Product product;
	
	@Before
	public void createProduct() {
		ProductService productService = new ProductService(productRepository);
		ProductModel productModel = new ProductModel();
		productModel.setName("Samsung Galaxy S9");
		productModel.setDescription("Midnight Black");
		productModel.setDetails("128GB");
		productModel.setQuantity("1");
		productModel.setPrice("899.0");
		
		product = productService.createProduct(productModel);
	}
	
	@After
	public void deleteProduct() {
		productRepository.delete(product);
	}
	
	@Test
	public void createProductAndAddToCart_addingOneProduct_productAddedToCart() {
		// arrange
		ProductService productService = new ProductService(productRepository);
		OrderService orderService = new OrderService(orderRepository, productService);
		
		// act
		boolean result = orderService.addToCart(product.getId());
		
		// assert
		assertEquals(true, result);
	}
	
	@Test
	public void checkIfProductInCart_addingOneProduct_productInCart() {
		// arrange
		ProductService productService = new ProductService(productRepository);
		OrderService orderService = new OrderService(orderRepository, productService);
		
		orderService.addToCart(product.getId());
		
		// act
		Cart cart = orderService.getCart();
		Product productInCart = cart.findProductInCartLines(product.getId());
		
		// assert
		assertEquals(product.getId(), productInCart.getId());
		
	}
	
	@Test
	public void removeProductFromCart_addingAndRemovingOneProduct_productRemoved() {
		// arrange
		ProductService productService = new ProductService(productRepository);
		OrderService orderService = new OrderService(orderRepository, productService);
		orderService.addToCart(product.getId());
		
		// act
		orderService.removeFromCart(product.getId());
		
		Cart cart = orderService.getCart();
		
		// assert
		assertEquals(0, cart.getCartLineList().size());
	}

}
