package org.dexels.utils;


import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * JarResources: JarResources maps all resources included in a
 * Zip or Jar file. Additionaly, it provides a method to extract one
 * as a blob.
 */
public final class JarResources {

    // external debug flag
    public boolean debugOn = false;

    // jar resource mapping tables
    private Hashtable htSizes = new Hashtable();
    private Hashtable htJarContents = new Hashtable();

    // a jar file
    private File jarFile;

    private static Logger logger = Logger.getLogger( JarResources.class );

    /**
     * creates a JarResources. It extracts all resources from a Jar
     * into an internal hashtable, keyed by resource names.
     * @param jarFileName a jar or zip file
     */
    public JarResources(File jarFile) {
        this.jarFile = jarFile;
        init();
    }

    /**
     * Extracts a jar resource as a blob.
     * @param name a resource name.
     */
    public byte[] getResource(String name) throws Exception {
        byte[] resource = (byte[]) htJarContents.get(name);

        if (resource == null)
            throw new Exception("Resource not found");
        else
            return resource;
    }

    /**
     * Path "/" means, get first level files/directories.
     */
    public Iterator getResources(String path) {
        HashSet inPath = new HashSet();
        Iterator iter = getResources();

        while (iter.hasNext()) {
            String orig = (String) iter.next();
            String name = orig;

            name = "/" + orig;
            if (name.startsWith(path)) {
                name = name.substring(path.length(), name.length());
                // int end = name.lastIndexOf("/");
                // Strip path from orig.
                if (name != null) {
                    int first = name.indexOf("/");

                    if (first != -1) {  // Directory
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

    public Iterator getResources() {
        return htJarContents.keySet().iterator();
    }

    public Iterator getDirectories() {
        return null;
    }

    /**
     * initializes internal hash tables with Jar file resources.
     */
    private void init() {
        try {
            // extracts just sizes only.
            ZipFile zf = new ZipFile(jarFile);
            Enumeration e = zf.entries();

            while (e.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) e.nextElement();

                if (debugOn) {
                    logger.log(Priority.DEBUG, dumpZipEntry(ze));
                }
                // System.out.println(ze.getName());
                htSizes.put(ze.getName(), new Integer((int) ze.getSize()));
            }
            zf.close();

            // extract resources and put them into the hashtable.
            FileInputStream fis = new FileInputStream(jarFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ZipInputStream zis = new ZipInputStream(bis);
            ZipEntry ze = null;

            while ((ze = zis.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    ze.getName();
                }
                if (debugOn) {
                    logger.log(Priority.DEBUG,
                            "ze.getName()=" + ze.getName() + "," + "getSize()=" + ze.getSize()
                            );
                }
                int size = (int) ze.getSize();

                // -1 means unknown size.
                if (size == -1) {
                    size = ((Integer) htSizes.get(ze.getName())).intValue();
                }
                byte[] b = new byte[(int) size];
                int rb = 0;
                int chunk = 0;

                while (((int) size - rb) > 0) {
                    chunk = zis.read(b, rb, (int) size - rb);
                    if (chunk == -1) {
                        break;
                    }
                    rb += chunk;
                }
                // add to internal resource hashtable
                htJarContents.put(ze.getName(), b);
                if (debugOn) {
                    logger.log(Priority.DEBUG,
                            ze.getName() + "  rb=" + rb + ",size=" + size + ",csize=" + ze.getCompressedSize()
                            );
                }
            }
        } catch (NullPointerException e) {
            System.out.println("done.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dumps a zip entry into a string.
     * @param ze a ZipEntry
     */
    private String dumpZipEntry(ZipEntry ze) {
        StringBuffer sb = new StringBuffer();

        if (ze.isDirectory()) {
            sb.append("d ");
        } else {
            sb.append("f ");
        }
        if (ze.getMethod() == ZipEntry.STORED) {
            sb.append("stored   ");
        } else {
            sb.append("defalted ");
        }
        sb.append(ze.getName());
        sb.append("\t");
        sb.append("" + ze.getSize());
        if (ze.getMethod() == ZipEntry.DEFLATED) {
            sb.append("/" + ze.getCompressedSize());
        }
        return (sb.toString());
    }

    /**
     * Is a test driver. Given a jar file and a resource name, it trys to
     * extract the resource and then tells us whether it could or not.
     *
     * <strong>Example</strong>
     * Let's say you have a JAR file which jarred up a bunch of gif image
     * files. Now, by using JarResources, you could extract, create, and display
     * those images on-the-fly.
     * <pre>
     *     ...
     *     JarResources JR=new JarResources("GifBundle.jar");
     *     Image image=Toolkit.createImage(JR.getResource("logo.gif");
     *     Image logo=Toolkit.getDefaultToolkit().createImage(
     *                   JR.getResources("logo.gif")
     *                   );
     *     ...
     * </pre>
     */
    public static void main(String[] args) throws IOException {

        JarResources jr = new JarResources(new File("/home/arjen/projecten/Navajo/tml.dtd"));
        Iterator iter = jr.getResources("/com/dexels/navajo/document/");

        while (iter.hasNext()) {
            String name = (String) iter.next();

            System.out.println(name);
        }
        // byte [] file = jr.getResource("org/dexels/toolbox/studio/BPFLMethodPanel.class");

    }

}	// End of JarResources class.
