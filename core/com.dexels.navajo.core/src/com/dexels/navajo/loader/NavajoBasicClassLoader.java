/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.loader;

import java.io.File;

import com.dexels.navajo.script.api.NavajoClassSupplier;

/**
 * @author Administrator
 *
 */
public class NavajoBasicClassLoader extends NavajoClassSupplier {

	public NavajoBasicClassLoader(ClassLoader parent) {
		super(parent);
	}
	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.NavajoClassSupplier#getClass(java.lang.String)
	 */
	@Override
	public Class<?> getClass(String className) throws ClassNotFoundException {
		return Class.forName(className,true,this);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.NavajoClassSupplier#getJarFiles(java.lang.String, boolean)
	 */
	@Override
	public File[] getJarFiles(String path, boolean beta) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.NavajoClassSupplier#getCompiledNavaScript(java.lang.String)
	 */
	@Override
	public Class<?> getCompiledNavaScript(String className)
			throws ClassNotFoundException {
		String conv = className.replaceAll("/",".");
		return getClass(conv);
	}

}
