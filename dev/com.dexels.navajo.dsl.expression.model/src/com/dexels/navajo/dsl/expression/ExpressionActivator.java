package com.dexels.navajo.dsl.expression;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.dsl.model.expression.ExpressionPackage;

public class ExpressionActivator implements BundleActivator {

	@Override
	public void start(BundleContext b) throws Exception {
		ExpressionPackage.eINSTANCE.getClass();
	}

	@Override
	public void stop(BundleContext b) throws Exception {

	}

}
