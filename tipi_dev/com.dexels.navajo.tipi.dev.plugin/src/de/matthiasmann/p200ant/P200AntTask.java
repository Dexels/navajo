/*
 * P200AntTask.java
 *
 * (BSD license)
 *
 * Copyright (c) 2007, Matthias Mann (www.matthiasmann.de)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *   * Neither the name of the Matthias Mann nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on January 3, 2007, 12:55 AM
 *
 */

package de.matthiasmann.p200ant;

import static java.util.jar.Pack200.Packer.FALSE;
import static java.util.jar.Pack200.Packer.KEEP;
import static java.util.jar.Pack200.Packer.KEEP_FILE_ORDER;
import static java.util.jar.Pack200.Packer.LATEST;
import static java.util.jar.Pack200.Packer.MODIFICATION_TIME;
import static java.util.jar.Pack200.Packer.SEGMENT_LIMIT;
import static java.util.jar.Pack200.Packer.TRUE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matthias Mann
 */
public class P200AntTask extends Task {
    
	private final static Logger logger = LoggerFactory
			.getLogger(P200AntTask.class);
	
    private final Engine e;
    private final ArrayList<FileSet> filesets;
    private boolean repack;
    private File file;
    
    /** Creates a new instance of P200AntTask */
    public P200AntTask() {
        this.e = new Engine();
        this.filesets = new ArrayList<FileSet>();
    }
    
    public void setKeepOrder(boolean enabled) {
        e.setProperty(KEEP_FILE_ORDER, enabled ? TRUE : FALSE);
    }
    
    public void setKeepModificationTime(boolean enabled) {
        e.setProperty(MODIFICATION_TIME, enabled ? KEEP : LATEST);
    }
    
    public void setSingleSegment(boolean enabled) {
        e.setProperty(SEGMENT_LIMIT, enabled ? "-1" : "1000000");
    }
    
    public void setSegmentLimit(int size) {
        e.setProperty(SEGMENT_LIMIT, Integer.toString(size));
    }
    
    public void setConfigFile(File file) throws IOException {
        e.loadProperties(file);
    }
    
    public void setRepack(boolean repack) {
        this.repack = repack;
    }
    
    public void setSrcfile(File file) {
        this.file = file;
    }
    
    public void setDestdir(File dir) {
        e.setDestDir(dir);
    }
    
    /**
     * Adds a set of files to copy.
     * @param set a set of files to copy
     */
    public void addFileset(FileSet set) {
        filesets.add(set);
    }

    @SuppressWarnings("unused")
	public void execute() throws BuildException {
        validate();
        
        ArrayList<File> files = new ArrayList<File>();
        if(file != null) {
            files.add(file);
        } else {
            for(FileSet fs : filesets) {
            	if(true) {
               	throw new BuildException("f: "+fs.getDir());
            		
            	}
                DirectoryScanner ds = fs.getDirectoryScanner(getProject());
                
                File dir = fs.getDir(getProject());
                for(String fileName : ds.getIncludedFiles()) {
                    files.add(new File(dir, fileName));
                }
            }
        }
        
        for(File f : files) {
            if(!f.canRead() || !f.isFile()) {
                throw new BuildException("File does not exist or can't be read: " + f);
            }
        }
        
        for(File f : files) {
            try {
                if(repack) {
                    logger.info("Repacking JAR: " + f);
                    e.repack(f);
                } else {
                    logger.info("Packing JAR: " + f);
                    e.pack(f);
                }
            } catch (IOException ex) {
                throw new BuildException("Error while processing file: " + f, ex);
            }
        }
    }
    
    private void validate() throws BuildException {
        if(file == null && filesets.size() == 0) {
            throw new BuildException("need to specify either file or fileset");
        }
        if(file != null && filesets.size() > 0) {
            throw new BuildException("can't specify both file and fileset");
        }
    }

}
