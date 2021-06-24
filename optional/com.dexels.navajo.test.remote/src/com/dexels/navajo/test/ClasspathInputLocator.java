/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.test;

import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class ClasspathInputLocator implements ScriptInputLocator {

	public ClasspathInputLocator() {
	}

	@Override
	public Navajo getInput(String scriptName) throws IOException {

		InputStream is = getClass().getClassLoader().getResourceAsStream(scriptName+".xml");
		if(is!=null) {
			System.err.println("Resource found!");
		}
		Navajo result=null;
		try {
			result = NavajoFactory.getInstance().createNavajo(is);
		} catch (Exception e) {
			throw new IOException("Error loading test input: "+scriptName);
		}
		if(is!=null) {
			is.close();
		}
		return result;
	}	

}
