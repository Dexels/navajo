package com.dexels.navajo.tipi.validation;

public interface TipiValidationDecorator {
	public void setValidation(boolean state, String expression,
			TipiValidatableComponent component);
}
