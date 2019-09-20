package com.openclassrooms.shopmanager.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ProductController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerIT {
	
    @Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	//@Test
	public void createProduct_missingName_displaysErrorMessage() throws Exception {
		mockMvc.perform(post("/admin/product")
				.param("price", "10")
				.param("quantity", "2"));
		//TODO
	}
	
	//@Test
	public void createProduct_missingPrice_displaysErrorMessage() throws Exception {
		//TODO
	}
	
	//@Test
	public void createProduct_priceNotANumber_displaysErrorMessage() throws Exception {
		//TODO
	}
	
	//@Test
	public void createProduct_priceNotGreaterThanZero_displaysErrorMessage() throws Exception {
		//TODO
	}
	
	//@Test
	public void createProduct_MissingQuantity_displaysErrorMessage() throws Exception {
		//TODO
	}

	//@Test
	public void createProduct_QuantityNotAnInteger_displaysErrorMessage() throws Exception {
		//TODO
	}
	
	//@Test
	public void createProduct_QuantityNotGreaterThanZero_displaysErrorMessage() throws Exception {
		//TODO
	}

}
