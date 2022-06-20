/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
  
private static final Logger logger = LoggerFactory
		.getLogger(FileInputStreamReader.class);


  public FileInputStreamReader() {
      filePath = System.getProperty("user.dir");
  }
    
@Override
public InputStream getResource(String name) {
    try {
    	File f = new File(name);
    	if (f.exists()) {
    		return new FileInputStream(f);
    	}
    	File dir = new File(filePath);
    	File target = new File(dir,name);
    	if(!target.exists()) {
    		logger.debug("Could not load resource...: " + name + " no such file: "+target.getAbsolutePath());
    		return null;
    	}
    	URL baseDir = dir.toURI().toURL();
    	URL res = new URL(baseDir,name);
    	logger.debug("Resolved to res url: "+res.toString()+" while resolving name: "+name);
    	return res.openStream();
    } catch (Exception ioe) {
    	
    	logger.error("Could not load resource...: " + name + "(" + ioe.getMessage() + ")",ioe);
    	return null;
    }
  }
}