package com.openclassrooms.shopmanager.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassrooms.shopmanager.order.Cart;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

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

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // act
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

        when(productRepository.findAllByOrderByIdDesc()).thenReturn(Arrays.asList(product1, product2));

        // act
        List<Product> products = productService.getAllAdminProducts();

        // assert
        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
        assertEquals(2L, products.get(1).getId() , 0);
    }
    
    @Test
    public void getByProductId_singleProduct_returnsProductById() {
    	// arrange
    	Product product = new Product();
    	product.setId(1L);
        product.setName("Test Product");
    	
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        
        // act
        Product foundProduct = productService.getByProductId(product.getId());
    	
    	// assert
        assertEquals(1L, foundProduct.getId(), 0);
        assertEquals("Test Product", foundProduct.getName()); 
    }
    
    @Test
    public void createProduct_addingNewProduct_savesNewProductToDb() {	
    	// arrange
    	ProductModel productModel = new ProductModel();
    	productModel.setId(1L);
    	productModel.setName("Test Product");
    	productModel.setQuantity("2");
    	productModel.setPrice("10.0");
    	
    	Product product = new Product();
    	product.setId(productModel.getId());
    	product.setName(productModel.getName());
    	product.setQuantity(Integer.parseInt(productModel.getQuantity()));
    	product.setPrice(Double.parseDouble(productModel.getPrice()));
        
        when(productService.createProduct(productModel)).thenReturn(product);
    	
    	// act
        Product productCreated = productService.createProduct(productModel);        
    	
    	// assert
        assertEquals("Test Product", productCreated.getName());
        assertEquals(10.0, productCreated.getPrice(), 0);
    }
    
    @Test
    public void deleteProduct_addingProductToRemove_removesProductById() {
    	// arrange: creating a productId and argument captor for that id
    	Long productId = 1L;
    	ArgumentCaptor<Long> productIdCaptor =  ArgumentCaptor.forClass(Long.class);
    	
    	// act: deleting product by productId
    	productService.deleteProduct(productId);
    	
    	// assert: verifying that the ProductRepository's deleteById() method was called once with the argument captor value
    	verify(productRepository, times(1)).deleteById(productIdCaptor.capture());
    	// checking that the productId and the argument captor value are equal
    	assertEquals(1L, productIdCaptor.getValue(), 0);
    }
    
    @Test
    public void updateProductQuantities_singleProduct_updatesExistingProductQuantity() {
    	// arrange: creating a product, creating a cart, and adding that product to the cart
    	Product product1 = new Product();
        product1.setId(1L);
        product1.setQuantity(5);
        
        Cart cart = new Cart();
        cart.addItem(product1, 1);
        
        // creating an argument captor of type Product for the updateProductQuantities() method
        ArgumentCaptor<Product> productArgument = ArgumentCaptor.forClass(Product.class);
        // searching for product by productId, then assigning it to an Optional type and returning it
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
    	
    	// act: passing the test cart with the product in it to the updateProductQuantities() method 
        productService.updateProductQuantities(cart);
    	
    	// assert: verifying that the value of the argument captor was saved (once)
        verify(productRepository, times(1)).save(productArgument.capture());
    }
}
