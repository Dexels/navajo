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
