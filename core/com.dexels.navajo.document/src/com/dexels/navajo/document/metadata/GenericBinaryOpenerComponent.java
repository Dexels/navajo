package com.dexels.navajo.document.metadata;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.document.BinaryOpenerFactory;

public class GenericBinaryOpenerComponent extends GenericBinaryOpener {

	public GenericBinaryOpenerComponent() {
		super();
	}
	
	public void activate(BundleContext bc) {
		BinaryOpenerFactory.setInstance(this);

	}
	
	public void deactivate() {
		BinaryOpenerFactory.setInstance(null);
	}
}
