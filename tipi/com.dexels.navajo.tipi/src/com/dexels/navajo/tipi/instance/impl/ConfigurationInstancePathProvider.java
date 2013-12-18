package com.dexels.navajo.tipi.instance.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.instance.InstancePathProvider;

public class ConfigurationInstancePathProvider implements
		InstancePathProvider {

	private File instancePath;

	@Override
	public File getInstancePath() {
		return instancePath;
	}

	public void activate(Map<String,Object> parameters, BundleContext bundleContext) throws FileNotFoundException {
		instancePath = new File((String) parameters.get("path"));
	}

	public void deactivate() {
		instancePath = null;
	}
}
