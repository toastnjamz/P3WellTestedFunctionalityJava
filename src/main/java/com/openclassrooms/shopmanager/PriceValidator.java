package com.openclassrooms.shopmanager;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<Price, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		Double number;
		try {
			number = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return false;
		}
		
		//TODO figure out how to check if there's a decimal place in Double number, maybe check in the String first before conversion.
		if (number % 1 == 0) {
			return false;
		}
		return true;
	}
}
