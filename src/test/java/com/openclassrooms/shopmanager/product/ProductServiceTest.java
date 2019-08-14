package com.openclassrooms.shopmanager.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Take this test method as a template to write your test methods for ProductService and OrderService.
 * A test method must check if a definite method does its job:
 *
 * Naming follows this popular convention : http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
 */


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void getAllProducts_DbHasData_allDataReturned(){

        // arrange
    	Product product1 = new Product();
        product1.setId(1L);
        product1.setName("First product");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("First product");

        // act
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        // assert
        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
        assertEquals(2L, products.get(1).getId() , 0);
    }
    
    @Test
    public void getAllAdminProducts_DbHasData_allProductsReturnedByIdDesc() {
        // arrange
    	Product product1 = new Product();
        product1.setId(1L);
        product1.setName("First product");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("First product");

        // act
        when(productRepository.findAllByOrderByIdDesc()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllAdminProducts();

        // assert
        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
        assertEquals(2L, products.get(1).getId() , 0);
    }
    
    @Test
    public void getByProductId_product_returnsProductById() {
    	// arrange
    	Product product = new Product();
    	product.setId(1L);
        product.setName("Test Product");
    	
    	// act
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        
        Product foundProduct = productService.getByProductId(product.getId());
    	
    	// assert
        assertEquals(1L, foundProduct.getId(), 0);
        assertEquals("Test Product", foundProduct.getName()); 
    }
    
    @Test
    public void createProduct_stateUnderTest_savesNewProduct() {
    	//TODO
    	
    	// arrange
    	
    	// act
    	
    	// assert
    }
    
    @Test
    public void deleteProduct_stateUnderTest_removesProductById() {
    	//TODO
    	
    	// arrange
    	
    	// act
    	
    	// assert
    }
    
    @Test
    public void updateProductQuantities_stateUnderTest_updatesExistingProductQuantityOrRemovesProduct() {
    	//TODO
    	
    	// arrange
    	
    	// act
    	
    	// assert
    }
}
