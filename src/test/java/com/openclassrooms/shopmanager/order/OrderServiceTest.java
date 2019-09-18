package com.openclassrooms.shopmanager.order;

import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
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
    private OrderRepository orderRepository;
    
    @Mock
    private ProductService productService;
    
    // Adding setup() method to make sure orderRepository and productService are injected into OrderService
    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
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
        
        doNothing().when(orderRepository).save(orderCaptor.capture());
        // TODO: Figure out how to test the second method in saveOrder(), productService.updateProductQuantities(this.cart)

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
        
        when(productService.getByProductId(1L)).thenReturn(product);
    	
    	// act
        //TODO: Do I need to add another assert to verify I added the item to the cart?
        orderService.addToCart(product.getId());
        orderService.removeFromCart(product.getId());
        Cart updatedCart = orderService.getCart();
    	
    	// assert
        assertEquals(true, updatedCart.getCartLineList().isEmpty());
    }
    
    @Test
    public void isCartEmpty_addingAndRemovingProduct_returnsTrue() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        
        orderService.addToCart(1L);
        orderService.removeFromCart(1L);
    	
    	Cart resultingCart = orderService.getCart();
    	
    	//TODO: create a stub that works
    	//when(orderService.getCart().getCartLineList().isEmpty()).thenReturn(true);
    	//when(orderService.isCartEmpty()).thenReturn(true);
    	
    	// act
    	boolean emptyCart = resultingCart.getCartLineList().isEmpty();
    	
    	// assert
    	assertEquals(true, emptyCart);
    }
    
    @Test
    public void createOrder_creatingNewOrderToSave_savesOrderAndClearsCart() {
    	// arrange
    	Order order = new Order();
    	
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        
        //TODO: create a stub that works
        //doNothing().when(orderService).createOrder(orderCaptor.capture());
    	
    	// act
    	orderService.createOrder(order);
    	
    	// assert
        verify(orderRepository, times(1)).save(orderCaptor.capture());
    	assertEquals(order, orderCaptor.getValue());
    }

}
