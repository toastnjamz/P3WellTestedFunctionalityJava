package com.openclassrooms.shopmanager;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuantityValidator implements ConstraintValidator<Quantity, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		Integer number;
		try {
			number = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if (number <= 0) {
			return false;
		}
		return true;
	}
}