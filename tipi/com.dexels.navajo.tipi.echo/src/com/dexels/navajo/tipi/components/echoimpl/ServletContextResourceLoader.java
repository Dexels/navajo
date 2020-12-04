/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import java.io.File;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.FileResourceLoader;

public class ServletContextResourceLoader extends FileResourceLoader {

	private static final long serialVersionUID = -2895275193644899925L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ServletContextResourceLoader.class);
	public ServletContextResourceLoader(ServletContext myContext,String prefix) {
		super(null);
		String prefixPath = myContext.getRealPath(prefix);
		logger.info("Prefixpath: "+prefixPath);
		File prefixDir = new File(prefixPath);
		if(!prefixDir.exists()) {
			logger.info("whooooops");
		}
		setBaseFile(prefixDir);
	}

}
