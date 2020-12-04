/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class FileInputLocator implements ScriptInputLocator {

	private final File baseDir;
	public FileInputLocator(File baseDir) {
		this.baseDir = baseDir;
	}

	@Override
	public Navajo getInput(String scriptName) throws IOException {
		System.err.println("Getting: "+scriptName);
		File inputFile = new File(baseDir,scriptName+".xml");
		System.err.println("Resolved: "+inputFile);
		FileInputStream is = new FileInputStream(inputFile);
		Navajo result = NavajoFactory.getInstance().createNavajo(is);
		is.close();
		return result;
	}	

}
