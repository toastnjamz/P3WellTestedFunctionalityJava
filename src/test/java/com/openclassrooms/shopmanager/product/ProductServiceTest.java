package com.openclassrooms.shopmanager.product;

import com.openclassrooms.shopmanager.order.Cart;

import org.junit.Before;
import org.junit.AfterClass;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;
import static org.hamcrest.CoreMatchers.*;

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
    
    @AfterClass
    public static void afterClass() {
    	System.out.println("ProductServiceTest testing complete.");
    }

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

        // assert (using Hamcrest)
        assertThat(2, is(equalTo(products.size())));
        assertThat(1L, is(equalTo(products.get(0).getId())));
        
        // assert using regular JUnit
        //assertEquals(2, products.size());
        //assertEquals(1L, products.get(0).getId() , 0);
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
    
    @Test
    public void createProduct_addingOneProduct_savesNewProductToDb() {	
    	// arrange
    	ProductModel productModel = new ProductModel();
    	productModel.setId(1L);
    	productModel.setName("Samsung Galaxy S9");
    	productModel.setQuantity("2");
    	productModel.setPrice("800.0");
    	
    	Product product = new Product();
    	product.setId(productModel.getId());
    	product.setName(productModel.getName());
    	product.setQuantity(Integer.parseInt(productModel.getQuantity()));
    	product.setPrice(Double.parseDouble(productModel.getPrice()));
        
        when(productService.createProduct(productModel)).thenReturn(product);
    	
    	// act
        Product productCreated = productService.createProduct(productModel);        
    	
    	// assert
        assertEquals(1L, productCreated.getId(), 0);
        assertEquals("Samsung Galaxy S9", productCreated.getName());
    }
    
    @Test
    public void deleteProduct_addingProductToRemove_removesProductById() {
    	// arrange
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
    public void updateProductQuantities_checkingProductOptionalExists_updatesProductQuantity() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        product.setQuantity(3);
        
        Cart cart = new Cart();
        cart.addItem(product, 1);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        
    	// act
        productService.updateProductQuantities(cart);
    	
    	// assert
        assertEquals(2, product.getQuantity());
    }
    
    @Test
    public void updateProductQuantities_addingOneProduct_savesProductToRepository() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        product.setQuantity(2);
        
        Cart cart = new Cart();
        cart.addItem(product, 1);
        
        ArgumentCaptor<Product> productArgument = ArgumentCaptor.forClass(Product.class);

        // Stub: search for product by productId, then assign it to an Optional type and return it
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    	
    	// act
        productService.updateProductQuantities(cart);
    	
    	// assert: verifying that the value of the argument captor was saved in the repository (method called once)
        verify(productRepository, times(1)).save(productArgument.capture());
        assertEquals(product, productArgument.getValue());
    }
    
    @Test
    public void updateProductQuantities_addingProductWithZeroQuantity_deletesProductFromRepository() {
    	// arrange
    	Product product = new Product();
        product.setId(1L);
        product.setQuantity(0);
        
        Cart cart = new Cart();
        cart.addItem(product, 0);
        //cart.removeLine(product);
        
        ArgumentCaptor<Product> productArgument = ArgumentCaptor.forClass(Product.class);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    	
    	// act
        productService.updateProductQuantities(cart);
    	
    	// assert: verifying that the value of the argument captor was deleted from the repository (method called once)
        verify(productRepository, times(1)).delete(productArgument.capture());
        assertEquals(product, productArgument.getValue());
    }
}
