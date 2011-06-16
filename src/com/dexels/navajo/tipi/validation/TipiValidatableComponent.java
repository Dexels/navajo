package com.dexels.navajo.tipi.validation;

import com.dexels.navajo.tipi.TipiDataComponent;

public interface TipiValidatableComponent extends TipiDataComponent {
	public void flash();

	public Object getValidatingContainer();

	public void setValidatingContainer();
}
