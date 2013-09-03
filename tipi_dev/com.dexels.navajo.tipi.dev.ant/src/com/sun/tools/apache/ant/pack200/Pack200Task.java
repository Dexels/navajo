/*
 * %W% %E% 
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.tools.apache.ant.pack200;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.jar.Pack200.Packer;
import java.util.zip.GZIPOutputStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Pack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An optional ant Task which emulates the Command Line Interface pack200(1).
 * Note, only the most commonly used command line options are implemented
 * here, users are encouraged to use the configuration file described below
 * to set other Pack200 options. 
 * @version %W% %E%
 * @author Kumar Srinivasan
 */
public class Pack200Task extends Pack {

    private   static final String ERRMSG_ZF="zipfile attribute must end";
    protected static final String COM_PREFIX="com.sun.java.util.jar.pack.";

    private boolean doRepack = false;
    private boolean doGZIP = false;
    private File p200ConfigFile = null;

    
	private final static Logger logger = LoggerFactory
			.getLogger(Pack200Task.class);
    // Storage for the properties used by the setters.
    private HashMap<String, String> propMap;

    public Pack200Task() {
	// Initialize our fields
	doRepack = false;
	doGZIP   = false;
	propMap  = new HashMap<String, String>();
    }

    // We validate our own stuff
    private void validate() throws BuildException {
	if (p200ConfigFile != null && 
		(!p200ConfigFile.exists() || p200ConfigFile.isDirectory())) {
            throw new BuildException("Pack200 property file attribute must "
				+ "exist and not represent a directory!", 
				getLocation()); 
	}
	if (doGZIP) { 
	   if (!zipFile.toString().toLowerCase().endsWith(".gz")) {
		throw new BuildException(ERRMSG_ZF + " with .gz extension", 
				getLocation()); 
	   }
	} else if (doRepack) {
	   if (!zipFile.toString().toLowerCase().endsWith(".jar")) {
		throw new BuildException(ERRMSG_ZF + " with .jar extension", 
				getLocation()); 
	   }
	} else {
	   if (!zipFile.toString().toLowerCase().endsWith(".pack") && 
			!zipFile.toString().toLowerCase().endsWith(".pac")) {
	       throw new BuildException(ERRMSG_ZF + 
					"with .pack or .pac extension", 
					getLocation()); 
	   }
	}
    }

    /**
     * Sets the repack option, ie the jar will be packed and repacked.
     */

    public void setRepack(boolean value) {
	doRepack = value;	
    }

    /**
     * Sets whether the pack archive is additionally  deflated with gzip.
     */
    public void setGZIPOutput(boolean value) {
	doGZIP = value;
    }

    /**
     * Sets whether the java debug attributes should be stripped
     */
    public void setStripDebug(String value) {
	propMap.put(COM_PREFIX+"strip.debug",value);
    } 
    
    /**
     * Sets the modification time for the archive
     */
    public void setModificationTime(String value) {
	propMap.put(Packer.MODIFICATION_TIME, value);
    }

    /**
     * Sets the deflate hint for the archive
     */
    public void setDeflateHint(String value) {
	propMap.put(Packer.DEFLATE_HINT, value);
    }

    /**
     * Sets the file ordering.
     */
    public void setKeepFileOrder(String value) {
	propMap.put(Packer.KEEP_FILE_ORDER,value);
    }

    /**
     *  Sets the segment limit.
     */
    public void setSegmentLimit(String value) {
	propMap.put(Packer.SEGMENT_LIMIT, value);
    }

    /**
     *  Sets the effort.
     */
    public void setEffort(String value) {
	propMap.put(Packer.EFFORT, value);
    }

    /**
     *  Sets the action to be taken if an unknown attribute is encountered.
     */
    public void setUnknownAttribute(String value) {
	propMap.put(Packer.UNKNOWN_ATTRIBUTE, value);
    }

    /**
     * Useful to set those Pack200 attributes which are not
     * commonly used.
     */
    public void setConfigFile(File packConfig) {
        p200ConfigFile = packConfig;
    }
    /**
     * Set the verbosity level.
     */
    public void setVerbose(String value) {
	propMap.put(COM_PREFIX + "verbose",value);
    }

    @Override
	protected void pack() {
	String statusStr = doRepack 
	    ? "Repack with Pack200" 
	    : "Packing with Pack200";

	logger.info(statusStr);
	logger.info("Source File :" + source);
	logger.info("Dest.  File :" + zipFile);
	if (p200ConfigFile != null)
           logger.info("Config file :" + p200ConfigFile);

	this.validate();

	File packFile = zipFile;
	FileOutputStream fos = null;
	try { 
	    Pack200.Packer pkr = Pack200.newPacker();
	    pkr.properties().putAll(propMap);
	    // The config file overrides all.
	    if (p200ConfigFile != null) {
		InputStream is = new BufferedInputStream(
				new FileInputStream(p200ConfigFile));
		Properties pFile = new Properties();
		pFile.load(is);
		is.close();
		for (Map.Entry<Object,Object> me : pFile.entrySet()) 
		   pkr.properties().put((String) me.getKey(), 
				(String) me.getValue());
	    }

	    if (doRepack) {
		doGZIP = false;
		packFile = new File(zipFile.toString() + ".tmp");
	    }

	    JarFile jarFile = new JarFile(source);
   	    fos = new FileOutputStream(packFile);

            OutputStream os = (doGZIP) 
		? new BufferedOutputStream(new GZIPOutputStream(fos)) 
		: new BufferedOutputStream(fos);

	    pkr.pack(jarFile, os);
            os.close();
  	    jarFile.close();

	    if (doRepack) {
		InputStream is = new BufferedInputStream(
				    new FileInputStream(packFile));

		JarOutputStream jout = new JarOutputStream(
				          new BufferedOutputStream(
					  new FileOutputStream(zipFile)));

		Pack200.Unpacker unpkr = Pack200.newUnpacker();
	        unpkr.properties().putAll(propMap);
		// The config file overrides all.
		if (p200ConfigFile != null) {
		    InputStream pc_is = new BufferedInputStream(
				        new FileInputStream(p200ConfigFile));
		    Properties pFile = new Properties();
		    pFile.load(pc_is);
		    pc_is.close();	
		    for (Map.Entry<Object,Object> me : pFile.entrySet()) {
		        unpkr.properties().put((String)me.getKey(), (String)me.getValue());
		    }
		}

		unpkr.unpack(is, jout);
	        is.close();
	        jout.close();
	    }

	} catch (IOException ioe) {
	    logger.error("Error: ",ioe);
	    throw new BuildException("Error in pack200: ",ioe);	
    } finally {
	    if (doRepack) packFile.delete();
		if(fos!=null) {
			try {
				fos.close();
			} catch (IOException e) {
				logger.warn("Error closing stream: ",e);
			}
		}
	}
    }
}
