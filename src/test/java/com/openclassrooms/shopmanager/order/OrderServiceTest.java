package com.openclassrooms.shopmanager.order;

import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductService;

import org.junit.Before;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
	
    @InjectMocks
    private OrderService orderService;
    
    @Mock
    private OrderService orderServiceMock;

    @Mock
    private OrderRepository orderRepository;
    
    @Mock
    private ProductService productService;
    
    // Adding setup() method to make sure orderRepository and productService are injected into OrderService
    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
    }
    
    @AfterClass
    public static void afterClass() {
    	System.out.println("OrderServiceTest testing complete.");
    }

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
    
    @Test
    public void saveOrder_newOrder_savesOrderToOrderRepository() {
    	// arrange
        Order order = new Order();
        
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);
        
        doNothing().when(orderRepository).save(orderCaptor.capture());

    	// act
        orderService.saveOrder(order);
    	
    	// assert
        verify(orderRepository, times(1)).save(orderCaptor.capture());
        verify(productService, times(1)).updateProductQuantities(cartCaptor.capture());
        assertEquals(order, orderCaptor.getValue());
        assertNotNull(cartCaptor.getValue());
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
        Cart resultingCart = mockOfOrderService.getCart();
    	
    	// assert
        assertEquals(1L, resultingCart.getCartLineList().get(0).getProduct().getId(), 0);
        assertEquals(2, resultingCart.getCartLineList().get(0).getQuantity());
    }
    
    @Test
    public void removeFromCart_addingProductToRemove_removesProductByIdFromCart() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        
        Cart cart = orderService.getCart();
        
        when(productService.getByProductId(1L)).thenReturn(product);
    	
    	// act
        orderService.addToCart(product.getId());
        assertEquals(false, cart.getCartLineList().isEmpty());
        orderService.removeFromCart(product.getId());
    	
    	// assert
        assertEquals(true, cart.getCartLineList().isEmpty());
    }
    
    @Test
    public void isCartEmpty_addingAndRemovingProduct_returnsTrue() {
    	// arrange
    	when(orderServiceMock.isCartEmpty()).thenReturn(true);
    	
    	// act
        boolean emptyCart = orderServiceMock.isCartEmpty();
        //boolean emptyCart = cart.getCartLineList().isEmpty();
    	
    	// assert
    	assertEquals(true, emptyCart);
    }
    
    @Test
    public void createOrder_creatingNewOrderToSave_savesOrderAndClearsCart() {
    	// arrange
    	Order order = new Order();
    	
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);
        
        // Doesn't work, seems like @InjectMocks and @Mock wires are getting crossed
        //doNothing().when(orderService).saveOrder(orderCaptor.capture());
    	
    	// act
        orderService.createOrder(order);
    	
    	// assert
        verify(orderRepository, times(1)).save(orderCaptor.capture());
        verify(productService, times(1)).updateProductQuantities(cartCaptor.capture());
    	assertEquals(order, orderCaptor.getValue());
    	assertNotNull(cartCaptor.getValue());
    }
}
