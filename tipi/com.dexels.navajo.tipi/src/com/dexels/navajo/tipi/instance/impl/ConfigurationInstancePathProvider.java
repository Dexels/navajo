/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
