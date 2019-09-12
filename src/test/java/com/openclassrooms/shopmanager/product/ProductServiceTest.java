package com.openclassrooms.shopmanager.product;
import com.openclassrooms.shopmanager.order.Cart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

/**
 * Take this test method as a template to write your test methods for ProductService and OrderService.
 * A test method must check if a definite method does its job:
 *
 * Naming follows this popular convention : http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
 */


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;
    
    // Adding setup() method to make sure productRepository is injected into productService
    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
    }
    
//    @Before
//    public void ProductServiceTest() {
//    	Product product1 = new Product();
//        product1.setId(1L);
//        Product product2 = new Product();
//        product2.setId(2L);	
//    }

    @Test
    public void getAllProducts_DbHasData_allDataReturned(){
        // arrange
    	Product product1 = new Product();
        product1.setId(1L);

        Product product2 = new Product();
        product2.setId(2L);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // act
        List<Product> products = productService.getAllProducts();

        // assert
        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
    }
    
    @Test
    public void getAllAdminProducts_DbHasData_allProductsReturnedByIdDesc() {
        // arrange
    	Product product1 = new Product();
        product1.setId(1L);

        Product product2 = new Product();
        product2.setId(2L);

        when(productRepository.findAllByOrderByIdDesc()).thenReturn(Arrays.asList(product1, product2));

        // act
        List<Product> products = productService.getAllAdminProducts();

        // assert
        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
    }
    
    @Test
    public void getByProductId_addingOneProduct_returnsProductById() {
    	// arrange
    	Product product = new Product();
    	product.setId(1L);
    	
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        
        // act
        Product foundProduct = productService.getByProductId(product.getId());
    	
    	// assert
        assertEquals(1L, foundProduct.getId(), 0); 
    }
    
//    //TODO: How to set up testing bad case input/parameters?
//    @Test(expected = NullPointerException.class)
//    public void getByProductId_addingNullProduct_throwsException() {
//    	// arrange
//    	Product product = null;
//        when(productRepository.findById(null)).thenThrow(new NullPointerException("Error: Product does not exist."));
//        
//        // act
//        productService.getByProductId(product.getId());
//    }
    
    //OR
    
//    //TODO: How to set up testing bad case input/parameters?
//    @Test(expected = Exception.class)
//    public void getByProductId_addingNullProductId_throwsException() {
//    	// arrange
//    	when(productRepository.findById(null)).thenThrow(new Exception("Error: Product does not exist."));
//	}
    
    @Test
    public void createProduct_addingOneProduct_savesNewProductToDb() {	
    	// arrange
    	ProductModel productModel = new ProductModel();
    	productModel.setId(1L);
    	productModel.setQuantity("2");
    	productModel.setPrice("10.0");
    	
    	Product product = new Product();
    	product.setId(productModel.getId());
    	product.setQuantity(Integer.parseInt(productModel.getQuantity()));
    	product.setPrice(Double.parseDouble(productModel.getPrice()));
        
        when(productService.createProduct(productModel)).thenReturn(product);
    	
    	// act
        Product productCreated = productService.createProduct(productModel);        
    	
    	// assert
        assertEquals(1L, productCreated.getId(), 0);
        assertEquals(10.0, productCreated.getPrice(), 0);
    }
    
    @Test
    public void deleteProduct_addingProductToRemove_removesProductById() {
    	// arrange
    	//ProductService productService = mock(ProductService.class);
    	Product product = new Product();
    	product.setId(1L);
    	ArgumentCaptor<Long> productIdCaptor = ArgumentCaptor.forClass(Long.class);
    	
        doNothing().when(productRepository).deleteById(productIdCaptor.capture());
    	
    	// act
    	productService.deleteProduct(product.getId());
    	
    	// assert: verifying that ProductRepository's deleteById() method was called once, capturing the argument with ArgumentCaptor
    	verify(productRepository, times(1)).deleteById(productIdCaptor.capture());
    	// checking that the productId and the argument captor value are equal (with no acceptable delta)
    	assertEquals(1L, productIdCaptor.getValue(), 0);
    }
    
    @Test
    public void updateProductQuantities_addingOneProduct_updatesExistingProductQuantity() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        product.setQuantity(5);
        
        Cart cart = new Cart();
        cart.addItem(product, 1);
        
        ArgumentCaptor<Product> productArgument = ArgumentCaptor.forClass(Product.class);
        // Stub: search for product by productId, then assign it to an Optional type and return it
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    	
    	// act
        productService.updateProductQuantities(cart);
    	
    	// assert: verifying that the value of the argument captor was saved (method called once)
        verify(productRepository, times(1)).save(productArgument.capture());
        //TODO add remaining three tests
    }
}
