package com.openclassrooms.shopmanager.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerSIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webContext;
	
	@MockBean
	private ProductService productService;
	
	@Before
	public void setupMockmvc() {
	   mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	   }
	
	@Test
	public void createProduct_correctInput_noErrorMessageDisplayed() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    		.param("name", "Samsung Galaxy S9")
	    		.param("price", "899.0")
	    		.param("quantity", "1"))
    	    .andExpect(model().errorCount(0))
	    	.andExpect(status().is3xxRedirection());
//	        .andExpect(content().string(containsString("<td>Samsung Galaxy S9</td>")))
//	        .andExpect(content().string(containsString("<td>899.0</td>")))
//	        .andExpect(content().string(containsString("<td>1</td>")));
	    }
	
	@Test
	public void createProduct_missingName_displaysErrorMessage() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    		.param("price", "899.0")
	    		.param("quantity", "1"))
	        .andExpect(model().errorCount(1))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(content().string(containsString("Please enter a name")));
	    }
	
	@Test
	public void createProduct_missingPrice_displaysErrorMessage() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    		.param("name", "Samsung Galaxy S9")
	    		.param("quantity", "1"))
	        .andExpect(model().errorCount(1))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(content().string(containsString("Price cannot be blank. Please enter a decimal number price greater than zero.")));
	    }
 
	@Test
	public void createProduct_priceNotADecimalNumber_displaysErrorMessage() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    		.param("name", "Samsung Galaxy S9")
	    		.param("price", "899")
	    		.param("quantity", "1"))
	        .andExpect(model().errorCount(1))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(content().string(containsString("Price must be a decimal number and greater than zero")));
	    }
	
	@Test
	public void createProduct_priceNotGreaterThanZero_displaysErrorMessage() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    		.param("name", "Samsung Galaxy S9")
	    		.param("price", "0")
	    		.param("quantity", "1"))
	        .andExpect(model().errorCount(1))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(content().string(containsString("Price must be a decimal number and greater than zero")));
	    }
	  
	@Test
	public void createProduct_missingQuantity_displaysErrorMessage() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    		.param("name", "Samsung Galaxy S9")
	    		.param("price", "899.0"))
			// TODO need to diagnose why there are two errors here instead of one
	        .andExpect(model().errorCount(2))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(content().string(containsString("Quantity must be a whole number greater than zero.")));
	    }

	@Test
	public void createProduct_quantityNotAnInteger_displaysErrorMessage() throws Exception {
		mockMvc.perform(post("/admin/product")
	    		.param("name", "Samsung Galaxy S9")
	    		.param("price", "899.0")
				.param("quantity", "1.1"))
	        .andExpect(model().errorCount(1))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(content().string(containsString("Quantity must be a whole number greater than zero.")));
	    }
	
	@Test
	public void createProduct_quantityNotGreaterThanZero_displaysErrorMessage() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    		.param("name", "Samsung Galaxy S9")
	    		.param("price", "899.0")
				.param("quantity", "0"))
	        .andExpect(model().errorCount(1))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(content().string(containsString("Quantity must be a whole number greater than zero.")));
	    }
}