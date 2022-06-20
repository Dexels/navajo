/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Class that supports Navajo classloaders (classloaders that support hot-pluggable jar-files)
 * 
 * @author arjen
 *
 */
public final class NavajoObjectInputStream extends ObjectInputStream {

	private ClassLoader classLoader;

	public NavajoObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
		super(in);
		this.classLoader = classLoader;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Class resolveClass(ObjectStreamClass desc) throws ClassNotFoundException {
		return Class.forName(desc.getName(), false, classLoader);
	}
}

