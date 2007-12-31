package com.dexels.navajo.tipi.components.echoimpl;

import java.io.*;
import java.net.*;

import javax.servlet.*;

import com.dexels.navajo.tipi.internal.*;

public class ServletContextResourceLoader extends FileResourceLoader {

	private final ServletContext servletContext;
	private final String prefix;
	public ServletContextResourceLoader(ServletContext myContext,String prefix) {
		super(null);
		String prefixPath = myContext.getRealPath(prefix);
		System.err.println("Prefixpath: "+prefixPath);
		File prefixDir = new File(prefixPath);
		if(!prefixDir.exists()) {
			System.err.println("whooooops");
		}
		setBaseFile(prefixDir);
		this.servletContext = myContext;
		this.prefix = prefix;
	}

}
