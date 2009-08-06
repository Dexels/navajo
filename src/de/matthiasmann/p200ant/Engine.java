/*
 * Engine.java
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
 * Created on January 2, 2007, 11:53 PM
 *
 */

package de.matthiasmann.p200ant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author Matthias Mann
 */
public class Engine {
    
    private final Properties props;
    private File destDir;
    
    /** Creates a new instance of Engine */
    public Engine() {
        this.props = new Properties();
        
        setProperty(Pack200.Packer.KEEP_FILE_ORDER, Pack200.Packer.FALSE);
        setProperty(Pack200.Packer.MODIFICATION_TIME, Pack200.Packer.LATEST);
        setProperty(Pack200.Packer.EFFORT, "9");
        setProperty(Pack200.Packer.CODE_ATTRIBUTE_PFX+"LocalVariableTable", Pack200.Packer.STRIP);
    }
    
    public void loadProperties(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        try {
            props.load(fis);
        } finally {
            fis.close();
        }
    }
    
    public void setProperty(String key, String value) {
        props.put(key, value);
    }
    
    public void setDestDir(File dir) {
        if(dir != null && !dir.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + dir);
        }
        this.destDir = dir;
    }
    
    private void copyTo(Map<String, String> target) {
        for(Map.Entry<Object, Object> e : props.entrySet()) {
            if(e.getKey().getClass() == String.class &&
                    e.getValue().getClass() == String.class) {
                target.put((String)e.getKey(), (String)e.getValue());
            }
        }
    }
    
    private Pack200.Packer createPacker() {
        Pack200.Packer p = Pack200.newPacker();
        copyTo(p.properties());
        return p;
    }
    
    private Pack200.Unpacker createUnpacker() {
        Pack200.Unpacker p = Pack200.newUnpacker();
        copyTo(p.properties());
        return p;
    }
    
    public void repack(File jarFile) throws IOException {
        BufferStream bs = new BufferStream();
        
        createPacker().pack(new JarFile(jarFile), bs);
        
        FileOutputStream fos = new FileOutputStream(jarFile);
        try {
            JarOutputStream jos = new JarOutputStream(fos);
            try {
                createUnpacker().unpack(bs.getInputStream(), jos);
            } finally {
                jos.close();
            }
        } finally {
            fos.close();
        }
    }
    
    public void pack(File jarFile) throws IOException {
        File dir = (destDir != null) ? destDir : jarFile.getParentFile();
        pack(jarFile, new File(dir, jarFile.getName().concat(".pack.gz")));
    }
    
    public void pack(File jarFile, File pakFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(pakFile);
        try {
            GZIPOutputStream zip = new GZIPOutputStream(fos);
            try {
                BufferedOutputStream bos = new BufferedOutputStream(zip);
                createPacker().pack(new JarFile(jarFile), bos);
                bos.flush();
            } finally {
                zip.close();
            }
        } finally {
            fos.close();
        }
    }
    
}
