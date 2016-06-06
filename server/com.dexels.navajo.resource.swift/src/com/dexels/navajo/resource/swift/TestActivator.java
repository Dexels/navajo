package com.dexels.navajo.resource.swift;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.resource.binarystore.BinaryStoreFactory;

public class TestActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.err.println("Starting bundle");
		BinaryStoreFactory osf = new BinaryStoreFactory();
		System.err.println("done!");
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
