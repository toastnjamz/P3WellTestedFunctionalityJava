package com.openclassrooms.shopmanager;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuantityValidator implements ConstraintValidator<Quantity, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		Double number;
		try {
			number = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if (number <= 0) {
			return false;
		}
		return true;
	}
}