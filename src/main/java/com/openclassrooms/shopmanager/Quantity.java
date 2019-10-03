package com.openclassrooms.shopmanager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target ({ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy= QuantityValidator.class)
public @interface Quantity {
	
	String message();
	
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}