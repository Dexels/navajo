package com.dexels.navajo.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class FileInputStreamReader implements InputStreamReader {

  private final String filePath;
  
private final static Logger logger = LoggerFactory
		.getLogger(FileInputStreamReader.class);


  public FileInputStreamReader() {
      filePath = System.getProperty("user.dir");
  }
    
public InputStream getResource(String name) {
    try {
    	File f = new File(name);
    	if (f.exists()) {
    		return new FileInputStream(f);
    	}
    	File dir = new File(filePath);
    	URL baseDir = dir.toURI().toURL();
    	URL res = new URL(baseDir,name);
    	logger.debug("Resolved to res url: "+res.toString()+" while resolving name: "+name);
    	return res.openStream();
    } catch (Exception ioe) {
    	
    	logger.error("Could not load resource...: " + name + "(" + ioe.getMessage() + ")");
    	return null;
    }
  }
}