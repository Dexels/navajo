/*
 * %W% %E% 
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.tools.apache.ant.pack200;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.*;
import org.apache.tools.ant.taskdefs.*;
import java.util.*;
import java.io.*;
import java.util.zip.*;
import java.util.jar.*;
import java.util.jar.Pack200.*;


/**
 * An optional ant Task which emulates the Command Line Interface unpack200(1).
 * @version %W% %E%
 * @author Kumar Srinivasan
 */
public class Unpack200Task extends Unpack {

    enum FileType { unknown, gzip, pack200, zip };

    private SortedMap <String, String> propMap;
    private Pack200.Unpacker unpkr;

    public Unpack200Task() {
	unpkr = Pack200.newUnpacker();
	propMap = unpkr.properties();
    }

    // Needed by the super class
    protected String getDefaultExtension() {
	return ".jar";
    }

    public void setVerbose(String value) {
	propMap.put(Pack200Task.COM_PREFIX + "verbose",value);
    }

    private FileType getMagic(File in) throws IOException {
	DataInputStream is = new DataInputStream(new FileInputStream(in));
	int i = is.readInt();
	is.close();
	if ( (i & 0xffffff00) == 0x1f8b0800) {
	    return FileType.gzip;
	} else if ( i == 0xcafed00d) {
	    return FileType.pack200;
	} else if ( i == 0x504b0304) {
	    return FileType.zip;
	} else {
	    return FileType.unknown; 
	}
    }
	
    protected void extract() {
	System.out.println("Unpacking with Unpack200");
	System.out.println("Source File :" + source);
	System.out.println("Dest.  File :" + dest);

	try { 
	    FileInputStream fis = new FileInputStream(source);

	    InputStream is = (FileType.gzip == getMagic(source))
		? new BufferedInputStream(new GZIPInputStream(fis))
		: new BufferedInputStream(fis);

	    FileOutputStream fos = new FileOutputStream(dest);
	    JarOutputStream jout = new JarOutputStream(
					new BufferedOutputStream(fos));
	    
	    unpkr.unpack(is, jout);
            is.close();
  	    jout.close();

	} catch (IOException ioe) {
	    throw new BuildException("Error in unpack200");	
        }

    }

}
