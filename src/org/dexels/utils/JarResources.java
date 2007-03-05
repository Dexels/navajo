package org.dexels.utils;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

/**
 * JarResources: JarResources maps all resources included in a Zip or Jar file.
 * Additionaly, it provides a method to extract one as a blob.
 */
public final class JarResources {

    // external debug flag
    public boolean debugOn = false;

    // jar resource mapping tables
    private Hashtable htSizes = new Hashtable();

    private Hashtable htJarContents = new Hashtable();

    // a jar file
    private File jarFile;
    
    private int totalSize = 0;

    /**
     * creates a JarResources. It extracts all resources from a Jar into an
     * internal hashtable, keyed by resource names.
     * 
     * @param jarFileName
     *            a jar or zip file
     */
    public JarResources(File jarFile) {
        this.jarFile = jarFile;
        init();
    }

    public URL getJarURL() throws MalformedURLException {
        return jarFile.toURL();
    }

    public URL getPathURL(String path) throws MalformedURLException {
        if (!path.startsWith("/")) {
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
        byte[] resource = (byte[]) htJarContents.get(name);

        if (resource == null)
            throw new Exception("Resource not found");
        else
            return resource;
    }

    /**
     * Path "/" means, get first level files/directories.
     */
    public final Iterator getResources(String path) {
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

    public final Iterator getResources() {
        return htJarContents.keySet().iterator();
    }

    public final Iterator getDirectories() {
        return null;
    }

    public final boolean hasResource(String resourcePath) {
        return htJarContents.containsKey(resourcePath);
    }

    /**
     * initializes internal hash tables with Jar file resources.
     */
    private final void init() {
        // extracts just sizes only.
        ZipFile zf = null;
        try {
            zf = new ZipFile(jarFile);
            Enumeration e = zf.entries();

            while (e.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) e.nextElement();

                // System.out.println(ze.getName());
                htSizes.put(ze.getName(), new Integer((int) ze.getSize()));
            }
        } catch (IOException io) {
            io.printStackTrace();

        } finally {
            if (zf != null) {
                try {
                    zf.close();
                } catch (IOException e) {
                    e.printStackTrace();
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
                    size = ((Integer) htSizes.get(ze.getName())).intValue();
                }
                byte[] b = new byte[(int) size];
                
                totalSize += size;
                
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

            }

        } catch (NullPointerException en) {
            en.printStackTrace(System.err);
            // System.out.println("done.");
        } catch (FileNotFoundException enn) {
            enn.printStackTrace(System.err);
        } catch (IOException ennn) {
            ennn.printStackTrace(System.err);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        System.err.println("Size of JarResources " + jarFile.getAbsolutePath() + " : " + totalSize);
        
    }

    /**
     * Dumps a zip entry into a string.
     * 
     * @param ze
     *            a ZipEntry
     */
    private final String dumpZipEntry(ZipEntry ze) {
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
    public static void main(String[] args) throws IOException {

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
