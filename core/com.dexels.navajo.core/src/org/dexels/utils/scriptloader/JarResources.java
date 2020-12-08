/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.dexels.utils.scriptloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JarResources: JarResources maps all resources included in a Zip or Jar file.
 * Additionaly, it provides a method to extract one as a blob.
 */
public final class JarResources {

    // external debug flag
    // jar resource mapping tables
    private Hashtable<String,Integer> htSizes = new Hashtable<>();

    private Hashtable<String,byte[]> htJarContents = new Hashtable<>();

    // a jar file
    private File jarFile;
    
    
	private static final Logger logger = LoggerFactory
			.getLogger(JarResources.class);
	
	
    /**
     * creates a JarResources. It extracts all resources from a Jar into an
     * internal hashtable, keyed by resource names.
     * 
     * @param jarFileName
     *            a jar or zip file
    * @throws IOException 
    * @throws ZipException 
     */
    public JarResources(File jarFile) throws IOException {
        this.jarFile = jarFile;
        init();
    }

    public URL getJarURL() throws MalformedURLException {
        return jarFile.toURI().toURL();
    }

    public URL getPathURL(String path) throws MalformedURLException {
        if (path.length() == 0 || path.charAt(0) != '/') {
            path = "/" + path;
        }
        String contents = "jar:" + getJarURL().toString() + "!" + path;
        return new URL(contents);
    }

    /**
     * Extracts a jar resource as a blob.
     * 
     * @param name
     *            a resource name.
     */
    public final byte[] getResource(String name) throws Exception {
        byte[] resource = htJarContents.get(name);

        if (resource == null)
            throw new Exception("Resource not found");
        else
            return resource;
    }

    /**
     * Path "/" means, get first level files/directories.
     */
    public final Iterator<String> getResources(String path) {
        Set<String> inPath = new HashSet<String>();
        Iterator<String> iter = getResources();

        while (iter.hasNext()) {
            String orig =  iter.next();
            String name = orig;

            name = "/" + orig;
            if (name.startsWith(path)) {
                name = name.substring(path.length(), name.length());
                // int end = name.lastIndexOf("/");
                // Strip path from orig.
                if (name != null) {
                    int first = name.indexOf("/");

                    if (first != -1) { // Directory
                        name = name.substring(0, first);
                        if (!inPath.contains(name))
                            inPath.add(name + "/");
                    } else { // File
                        inPath.add(name);
                    }
                }
            }

        }
        return inPath.iterator();
    }

    public final Iterator<String> getResources() {
        return htJarContents.keySet().iterator();
    }

    public final Iterator<String> getDirectories() {
        return null;
    }

    public final boolean hasResource(String resourcePath) {
        return htJarContents.containsKey(resourcePath);
    }

    /**
     * initializes internal hash tables with Jar file resources.
    * @throws IOException 
    * @throws ZipException 
     */
    private final void init() throws ZipException, IOException {
        // extracts just sizes only.
        ZipFile zf = null;
        try {
            zf = new ZipFile(jarFile);
            Enumeration<? extends ZipEntry> e = zf.entries();

            while (e.hasMoreElements()) {
                ZipEntry ze = e.nextElement();

                // System.out.println(ze.getName());
                htSizes.put(ze.getName(), new Integer((int) ze.getSize()));
            }

        } finally {
            if (zf != null) {
                try {
                    zf.close();
                } catch (IOException e) {
                	logger.error("Error: ", e);
                }
            }
        }

        // extract resources and put them into the hashtable.
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        ZipInputStream zis = null;
        try {
            fis = new FileInputStream(jarFile);
            bis = new BufferedInputStream(fis);
            zis = new ZipInputStream(bis);
            ZipEntry ze = null;

            while ((ze = zis.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    ze.getName();
                }
                int size = (int) ze.getSize();

                // -1 means unknown size.
                if (size == -1) {
                    size = htSizes.get(ze.getName()).intValue();
                }
                byte[] b = new byte[size];
                
                int rb = 0;
                int chunk = 0;

                while ((size - rb) > 0) {
                    chunk = zis.read(b, rb, size - rb);
                    if (chunk == -1) {
                        break;
                    }
                    rb += chunk;
                }
                // add to internal resource hashtable
                htJarContents.put(ze.getName(), b);

            }
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                	logger.error("Error: ", e);
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                	logger.error("Error: ", e);
                }
            }
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                	logger.error("Error: ", e);
                }
            }
        }
    }

    /**
     * Is a test driver. Given a jar file and a resource name, it trys to
     * extract the resource and then tells us whether it could or not.
     * 
     * <strong>Example</strong> Let's say you have a JAR file which jarred up a
     * bunch of gif image files. Now, by using JarResources, you could extract,
     * create, and display those images on-the-fly.
     * 
     * <pre>
     *       ...
     *       JarResources JR=new JarResources(&quot;GifBundle.jar&quot;);
     *       Image image=Toolkit.createImage(JR.getResource(&quot;logo.gif&quot;);
     *       Image logo=Toolkit.getDefaultToolkit().createImage(
     *                     JR.getResources(&quot;logo.gif&quot;)
     *                     );
     *       ...
     * </pre>
     */
    public static void main(String[] args) {

//        JarResources jr = new JarResources(new File("/home/arjen/projecten/Navajo/tml.dtd"));
//        Iterator iter = jr.getResources("/com/dexels/navajo/document/");
//
//        while (iter.hasNext()) {
//            String name = (String) iter.next();
//
//            System.out.println(name);
//        }
        // byte [] file =
        // jr.getResource("org/dexels/toolbox/studio/BPFLMethodPanel.class");

    }

} // End of JarResources class.
