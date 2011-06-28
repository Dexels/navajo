package com.dexels.navajo.tipi.vaadin.bridged;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaadinFileServlet extends HttpServlet {

	private static final long serialVersionUID = -8585205923703499272L;
	private String path = null;
	private static final Logger logger = LoggerFactory.getLogger(VaadinFileServlet.class); 
	public VaadinFileServlet(String path) {
		this.path = path;
	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		File f = new File(path);
		File vaadin = new File(f,"VAADIN");
		logger.debug("Path: "+vaadin.getAbsolutePath());
		String pathInf = req.getPathInfo();
		File resolved = new File(vaadin,pathInf);
		logger.debug("Path resolved to: "+resolved.getAbsolutePath());
		if(!resolved.exists()) {
			getFromClassPath(pathInf,resp);
		} else {
			FileInputStream fr = new FileInputStream(resolved);
			OutputStream os = resp.getOutputStream();
			copyResource(os, fr);
			
		}
//		super.doGet(req, resp);
	}

	
	  private void getFromClassPath(String pathInf, HttpServletResponse resp) throws IOException {
		  logger.debug("Getting resource from cp: "+pathInf);
		  URL u = getClass().getClassLoader().getResource(pathInf);
		  if(u==null) {
			  if(pathInf.startsWith("/VAADIN")) {
				  pathInf = pathInf.substring("/VAADIN".length());
				  u = getClass().getClassLoader().getResource(pathInf);
			  }
			  
		  }
		  if(u!=null) {
			    InputStream fr = u.openStream();
				OutputStream os = resp.getOutputStream();
				copyResource(os, fr);
		  } else {
			  resp.sendError(404,"Cant find resource: "+pathInf+" in classpath");
		  }
	}


	private final void copyResource(OutputStream out, InputStream in){
		  BufferedInputStream bin = new BufferedInputStream(in);
		  BufferedOutputStream bout = new BufferedOutputStream(out);
		  byte[] buffer = new byte[1024];
		  int read = -1;
		  boolean ready = false;
		  while (!ready) {
			  try {
				  read = bin.read(buffer);
				  if ( read > -1 ) {
					  bout.write(buffer,0,read);
				  }
			  } catch (IOException e) {
			  }
			  if ( read <= -1) {
				  ready = true;
			  }
		  }
		  try {
			  bin.close();
			  bout.flush();
			  bout.close();
		  } catch (IOException e) {

		  }
	  }
}
