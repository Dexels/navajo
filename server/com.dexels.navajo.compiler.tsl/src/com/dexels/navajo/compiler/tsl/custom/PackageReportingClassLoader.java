/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.compiler.tsl.custom;

import java.util.HashSet;
import java.util.Set;

public class PackageReportingClassLoader extends ClassLoader {
	private Set<PackageListener> packageListeners = new HashSet<>();

	public PackageReportingClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String packageName = getPackageName(name);
		reportPackageName(packageName);
		return super.findClass(name);
	}

	private void reportPackageName(String packageName) {
		for (PackageListener p : packageListeners) {
			p.packageFound(packageName);
		}
	}

	private String getPackageName(String name) {
		String packageName = null;
		if (name.indexOf('.') >= 0) {
			packageName = name.substring(0, name.lastIndexOf('.'));
		} else {
			packageName = "";
		}
		return packageName;
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean res)
			throws ClassNotFoundException {
		reportPackageName(getPackageName(name));
		return super.loadClass(name, res);
	}

	public void addPackageListener(PackageListener p) {
		packageListeners.add(p);
	}

	public void removePackageListener(PackageListener p) {
		packageListeners.remove(p);
	}

}
