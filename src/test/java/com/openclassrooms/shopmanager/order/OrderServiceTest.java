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

/**
 * A test method must check if a definite method does its job:
 *
 * Naming follows this popular convention : http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
 */


@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
	
    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;
    
    @Mock
    ProductService productService;

    @Test
    public void addToCart_addingOneProduct_returnsTrueIfProductAddedToCart() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        
        when(productService.getByProductId(1L)).thenReturn(product);
    	
    	// act
        Boolean result = orderService.addToCart(product.getId());
    	
    	// assert
        assertEquals(true, result);
	}
    
    // Not sure if this is an appropriate unit test...
    @Test(expected = Exception.class)
    public void addToCart_addingNullProduct_throwsException() {
    	// arrange
    	Product product = null;
        
        when(productService.getByProductId(null)).thenThrow(new Exception("Product unavailable to add to cart."));
    	
    	// act
        //Boolean result = orderService.addToCart(product.getId());
    	
    	// assert
        //assertEquals(false, result);
	}
    
    @Test
    public void saveOrder_newOrder_savesOrderToOrderRepository() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        
        CartLine cartLine = new CartLine();
        cartLine.setProduct(product);
        cartLine.setQuantity(2);
        
        List<CartLine> cartLineList = new ArrayList<CartLine>();
        cartLineList.add(cartLine);
        
        Order order = new Order();
        order.setLines(cartLineList);
        
        ArgumentCaptor<Order> orderCaptor =  ArgumentCaptor.forClass(Order.class);

    	// act
        orderService.saveOrder(order);
    	
    	// assert
        verify(orderRepository, times(1)).save(orderCaptor.capture());
        assertEquals(order, orderCaptor.getValue());
    }
    
    @Test
    public void getCart_stateUnderTest_returnsCart() {
    	//TODO
    	
    	// arrange
    	
    	// act
    	
    	// assert
    }
    
    @Test
    public void removeFromCart_stateUnderTest_removesProductByIdFromCart() {
    	//TODO
    	
    	// arrange
    	
    	// act
    	
    	// assert
    }
    
    @Test
    public void isCartEmpty_stateUnderTest_returnsTrueIfCartIsEmpty() {
    	//TODO
    	
    	// arrange
    	
    	// act
    	
    	// assert
    }
    
    @Test
    public void createOrder_stateUnderTest_savesOrderAndClearsCart() {
    	//TODO
    	
    	// arrange
    	
    	// act
    	
    	// assert
    }

}
