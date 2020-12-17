/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.osgicompiler;

import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.script.api.CompilationException;

public interface OSGiJavaCompiler {
	public byte[] compile(String className, InputStream source)
			throws IOException, CompilationException;

}
