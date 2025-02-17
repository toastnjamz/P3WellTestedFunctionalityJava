package com.openclassrooms.shopmanager.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.openclassrooms.shopmanager.Price;
import com.openclassrooms.shopmanager.Quantity;

public class ProductModel {

    private Long id;
    
    @NotBlank(message = "Name cannot be blank. Please enter a name.")
    private String name;            // Required
    
    private String description;
    private String details;
    
    @NotBlank(message = "Quantity cannot be blank. Please enter a quantity greater than zero.")
    @Quantity(message = "Quantity must be a whole number greater than zero.")
    private String quantity;       // Required, Integer, Greater than zero
    
    @NotBlank(message = "Price cannot be blank. Please enter a decimal number price greater than zero.")
    @Pattern(regexp = "(1|[1-9]\\d*)?(\\.\\d+)", message = "Price must be a decimal number and greater than zero")
    //@Price(message = "Price must be a decimal number and greater than zero.")
    private String price;          // Required, Numeric, Greater than zero

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
