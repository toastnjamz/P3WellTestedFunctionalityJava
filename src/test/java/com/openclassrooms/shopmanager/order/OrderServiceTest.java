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
    
    // TODO: Not sure if this is an appropriate unit test...
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
        
        CartLine cartLine = new CartLine();
        cartLine.setProduct(product);
        cartLine.setQuantity(2);
        
        List<CartLine> cartLineList = new ArrayList<CartLine>();
        cartLineList.add(cartLine);
        
        Order order = new Order();
        order.setLines(cartLineList);
        
        //TODO: add a when() statement
        
        ArgumentCaptor<Order> orderCaptor =  ArgumentCaptor.forClass(Order.class);

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
        //product.setName("Test Product");
        
        Cart cart = new Cart();
        cart.addItem(product, 2);
    	
        when(mockOfOrderService.getCart()).thenReturn(cart);
        
    	// act
        Cart foundCart = mockOfOrderService.getCart();
    	
    	// assert
        assertEquals(1L, foundCart.getCartLineList().get(0).getProduct().getId(), 0);
        assertEquals(2, foundCart.getCartLineList().get(0).getQuantity());
    }
    
//    @Test
//    public void removeFromCart_addingProductToRemove_removesProductByIdFromCart() {
//    	//TODO: Fix so the return type of the original method is void
//    	
//    	// arrange
//    	Product product = new Product();
//        product.setId(1L);
//        product.setPrice(12.0);
//        
//        Cart cart = new Cart();
//        cart.addItem(product, 1);
//        
//        when(productService.getByProductId(1L)).thenReturn(product);
//    	
//    	// act
//        orderService.removeFromCart(product.getId());
//    	
//    	// assert
//        //assertEquals(0.0, cart.getTotalValue());
//        assertEquals(0.0, cart.getTotalValue());
//    }
    
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
        //TODO: Do I need to add another assert to cover adding the item to the cart?
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
    	
    	when(orderService.getCart().getCartLineList().isEmpty()).thenReturn(true);
    	
    	// act
    	boolean emptyCart = foundCart.getCartLineList().isEmpty();
    	
    	// assert
    	assertEquals(emptyCart, orderService.isCartEmpty());
    }
    
    @Test
    public void createOrder_stateUnderTest_savesOrderAndClearsCart() {
    	//TODO
    	
    	// arrange
    	
    	// act
    	
    	// assert
    }

}
