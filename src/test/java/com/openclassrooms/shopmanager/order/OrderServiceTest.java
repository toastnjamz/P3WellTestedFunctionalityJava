package com.openclassrooms.shopmanager.order;
import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertThat;

/**
 * A test method must check if a definite method does its job:
 *
 * Naming follows this popular convention : http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
 */


@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
	
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;
    
    @Mock
    private ProductService productService;

    @Test
    public void addToCart_addingOneProduct_returnsTrueIfProductAddedToCart() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        
        when(productService.getByProductId(1L)).thenReturn(product);
    	
    	// act
        Boolean result = orderService.addToCart(product.getId());
    	
    	// assert
        assertEquals(true, result);
	}
    
    // TODO: Not sure if this is an appropriate unit test for a bad case scenario...
    @Test(expected = Exception.class)
    public void addToCart_addingNullProduct_throwsException() {
    	// arrange
        when(productService.getByProductId(null)).thenThrow(new Exception("Error: Product does not exist."));
	}
    
    @Test
    public void saveOrder_newOrder_savesOrderToOrderRepository() {
    	// arrange
        Order order = new Order();
        //order.setLines(cartLineList);
        
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        
        //TODO: add a when() statement
        //when(orderService.saveOrder(order)).then();

    	// act
        orderService.saveOrder(order);
    	
    	// assert
        verify(orderRepository, times(1)).save(orderCaptor.capture());
        assertEquals(order, orderCaptor.getValue());
    }
    
    @Test
    public void getCart_currentCart_returnsCart() {
    	// arrange
    	OrderService mockOfOrderService = mock(OrderService.class);

    	Product product = new Product();
        product.setId(1L);
        
        Cart cart = new Cart();
        cart.addItem(product, 2);
    	
        when(mockOfOrderService.getCart()).thenReturn(cart);
        
    	// act
        Cart foundCart = mockOfOrderService.getCart();
    	
    	// assert
        assertEquals(1L, foundCart.getCartLineList().get(0).getProduct().getId(), 0);
        assertEquals(2, foundCart.getCartLineList().get(0).getQuantity());
    }
    
    @Test
    public void removeFromCart_addingProductToRemove_removesProductByIdFromCart() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        product.setPrice(12.0);
        
        when(productService.getByProductId(1L)).thenReturn(product);
    	
    	// act
        orderService.addToCart(product.getId());
        orderService.removeFromCart(product.getId());
        Cart updatedCart = orderService.getCart();
    	
    	// assert
        //TODO: Find out if I need to add another assert to cover adding the item to the cart?
        assertEquals(true, updatedCart.getCartLineList().isEmpty());
    }
    
    @Test
    public void isCartEmpty_checkingForEmptyCart_returnsTrue() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        
        orderService.addToCart(1L);
        orderService.removeFromCart(1L);
    	
    	Cart foundCart = orderService.getCart();
    	
    	//TODO: Write when() statement
    	//when(orderService.getCart().getCartLineList().isEmpty()).doAnswer(?);
    	
    	// act
    	boolean emptyCart = foundCart.getCartLineList().isEmpty();
    	
    	// assert
    	assertEquals(true, emptyCart);
    }
    
    @Test
    public void createOrder_savingNewOrder_savesOrderAndClearsCart() {
    	// arrange
    	Order order = new Order();
    	
    	// act
    	
    	// assert
    }

}
