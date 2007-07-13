package com.dexels.navajo.tipi.components.echoimpl;

import java.io.*;
import java.net.*;

import javax.servlet.*;

import com.dexels.navajo.tipi.internal.*;

public class ServletContextResourceLoader extends ClassPathResourceLoader {

	private final ServletContext servletContext;
	private final String prefix;
	public ServletContextResourceLoader(ServletContext myContext,String prefix) {
		this.servletContext = myContext;
		this.prefix = prefix;
	}

	public InputStream getResourceStream(String location) throws IOException {
		String prefixPath = servletContext.getRealPath(prefix);
		System.err.println("Prefixpath: "+prefixPath);
		File prefixDir = new File(prefixPath);
		if(!prefixDir.exists()) {
			System.err.println("whooooops");
		}
		File ff = new File(prefixDir,location);
		System.err.println("File: "+ff.getAbsolutePath());

		if (ff.exists()) {
			System.err.println("Exists");
			FileInputStream fis = new FileInputStream(ff);
			return fis;
		}
		
		return super.getResourceStream(location);
	}
}
