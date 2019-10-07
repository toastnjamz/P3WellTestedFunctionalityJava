package com.openclassrooms.shopmanager.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

//@WithMockUser(username="admin", password="password",roles = "ADMIN")
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
	public void setup() {
	   mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	
	    }
	
	@Test
	public void createProduct_correctInput_noErrorMessageDisplayed() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    		.param("name", "Samsung Galaxy S9")
	    		.param("price", "899.0")
	    		.param("quantity", "1"))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(content().string(containsString("<td>Samsung Galaxy S9</td>")))
	        .andExpect(content().string(containsString("<td>899.0</td>")))
	        .andExpect(content().string(containsString("<td>1</td>")));
	    }
	
//	@Test
//	public void testCreateProductWithCorrectValues() throws Exception {
//	
//	    mockMvc.perform(post("/admin/product")
//	    		.param("name", "Nokia")
//	    		.param("price", "2.0")
//	    		.param("quantity", "10"))
//	    	.andExpect(status().is3xxRedirection())
//	    	.andExpect(redirectedUrl("/admin/products"))
//	    	;
//	    }

//	@Test
//	public void createProduct_missingName_displaysErrorMessage() throws Exception {
//		mockMvc.perform(post("/admin/product")
//				.param("price", "10")
//				.param("quantity", "2"))
//		.andExpect(view().name("product"))
//		.andExpect(model().attributeExists("product"))
//		.andExpect(model().errorCount(1))
//		.andExpect(status().is2xxSuccessful());
//	}
	
//	//@Test
//	public void createProduct_missingPrice_displaysErrorMessage() throws Exception {
//		//TODO
//	}
//	
//	//@Test
//	public void createProduct_priceNotANumber_displaysErrorMessage() throws Exception {
//		//TODO
//	}
//	
//	//@Test
//	public void createProduct_priceNotGreaterThanZero_displaysErrorMessage() throws Exception {
//		//TODO
//	}
//	
//	//@Test
//	public void createProduct_MissingQuantity_displaysErrorMessage() throws Exception {
//		//TODO
//	}
//
//	//@Test
//	public void createProduct_QuantityNotAnInteger_displaysErrorMessage() throws Exception {
//		//TODO
//	}
//	
//	//@Test
//	public void createProduct_QuantityNotGreaterThanZero_displaysErrorMessage() throws Exception {
//		//TODO
//	}

}
