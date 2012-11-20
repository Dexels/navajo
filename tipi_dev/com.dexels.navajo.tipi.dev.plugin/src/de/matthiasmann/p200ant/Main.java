/*
 * Main.java
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
 * Created on January 2, 2007, 11:47 PM
 *
 */

package de.matthiasmann.p200ant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Pack200;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matthias Mann
 */
public class Main {
    
	
	private final static Logger logger = LoggerFactory.getLogger(Main.class);
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ArrayList<File> files = new ArrayList<File>();
            boolean repack = false;
            
            Engine e = new Engine();
            for(int i=0 ; i<args.length ;) {
                String arg = args[i++];
                if("--repack".equals(arg)) {
                    repack = true;
                } else if("--config".equals(arg)) {
                    needsArg(args, i);
                    e.loadProperties(new File(args[i++]));
                } else if("--keep-order".equals(arg)) {
                    e.setProperty(Pack200.Packer.KEEP_FILE_ORDER, Pack200.Packer.TRUE);
                } else if("--keep-modification-time".equals(arg)) {
                    e.setProperty(Pack200.Packer.MODIFICATION_TIME, Pack200.Packer.KEEP);
                } else if("--single-segment".equals(arg)) {
                    e.setProperty(Pack200.Packer.SEGMENT_LIMIT, "-1");
                } else if("--segment-limit".equals(arg)) {
                    needsArg(args, i);
                    e.setProperty(Pack200.Packer.SEGMENT_LIMIT, args[i++]);
                } else if(arg.startsWith("--")) {
                    help();
                } else {
                    File f = new File(arg);
                    if(!f.canRead() || !f.isFile()) {
                        logger.info("File does not exist or can't be read: " + arg);
                        System.exit(1);
                    }
                    files.add(f);
                }
            }
            
            if(files.size() == 0) {
                help();
            }
            
            for(File f : files) {
                if(repack) {
                    e.repack(f);
                } else {
                    e.pack(f);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void needsArg(String[] args, int i) {
        if(i == args.length) {
            logger.info(args[i-1] + " needs a parameter");
            System.exit(1);
        }
    }
    
    private static void help() {
        logger.info("Usage: java -jar P200Ant [<option> ...] <file> [<file> ... ]");
        logger.info("Possible options:");
        logger.info("  --repack                  do repacking");
        logger.info("  --config config.file      read properties from config.file");
        logger.info("  --keep-order              keep file order");
        logger.info("  --keep-modification-time  keep class modification time");
        logger.info("  --single-segment          create only one big segment");
        logger.info("  --segment-limit nnn       sets the segment limit to nnn bytes");
        System.exit(1);
    }
}
