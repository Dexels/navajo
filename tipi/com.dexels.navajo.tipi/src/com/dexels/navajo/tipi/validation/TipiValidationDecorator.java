package com.dexels.navajo.tipi.validation;

import java.io.Serializable;

public interface TipiValidationDecorator extends Serializable {
	public void setValidation(boolean state, String expression,
			TipiValidatableComponent component);
}
