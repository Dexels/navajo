package com.dexels.navajo.tipi.validation;

import com.dexels.navajo.tipi.*;

public interface TipiValidatableComponent extends TipiDataComponent {
	public void flash();
	public Object getValidatingContainer();
	public void setValidatingContainer();
}
