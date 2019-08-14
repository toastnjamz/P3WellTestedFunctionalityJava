package com.openclassrooms.shopmanager.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Take this test method as a template to write your test methods for ProductService and OrderService.
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

    @Test
    public void addToCart_stateUnderTest_returnsTrueIfProductAddedToCart() {
    	//TODO
    	
    	// arrange
    	
    	// act
    	
    	// assert
	}
    
    @Test
    public void saveOrder_stateUnderTest_savesOrderToOrderRepository() {
    	//TODO
    	
    	// arrange
    	
    	// act
    	
    	// assert
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
