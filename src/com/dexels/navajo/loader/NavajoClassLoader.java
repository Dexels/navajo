package com.dexels.navajo.loader;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 *
 * $Id$
 *
 */

import org.dexels.utils.*;
import java.io.*;
import java.util.*;


/**
 * This class implements the Navajo Class Loader. It is used to implement re-loadable and hot-loadable Navajo adapter classes
 * and Navajo Expression Language functions.
 * It uses a cache for fast multiple instantiation of loaded Classes.
 */

class JarFilter implements FilenameFilter {
    public boolean accept(File f, String name) {
        if (name.endsWith("jar"))
            return true;
        else
            return false;
    }
}


class BetaJarFilter implements FilenameFilter {
    public boolean accept(File f, String name) {
        if (name.endsWith("jar_beta"))
            return true;
        else
            return false;
    }
}


public class NavajoClassLoader extends MultiClassLoader {

    private String adapterPath = "";
    private Hashtable pooledObjects = new Hashtable();

    /**
     * beta flag denotes whether beta versions of jar files should be used (if present).
     */
    private boolean beta;

    public NavajoClassLoader(String adapterPath, boolean beta) {
        // System.out.println("Initializing NavajoClassLoader: adapterPath = " + adapterPath);
        this.adapterPath = adapterPath;
        this.beta = beta;
    }

    public NavajoClassLoader(String adapterPath) {
        // System.out.println("Initializing NavajoClassLoader: adapterPath = " + adapterPath);
        this.adapterPath = adapterPath;
        this.beta = false;
    }

    /**
     * Use clearCache() to clear the Class cache, allowing a re-load of new jar files.
     */
    public void clearCache() {
        super.clearCache();
    }

    /**
     * Always use this method to load a class. It uses the cache first before retrieving the class from a jar resource.
     */
    public Class getClass(String className) throws ClassNotFoundException {
        // System.out.println("Using classLoader: " + this);
        // System.out.println("Cache: " + classes);

        Class c = (Class) classes.get(className);

        if (c == null) {
            return Class.forName(className, false, this);
        } else {
            // System.out.println("Found class in cache");
            return c;
        }
    }

    /**
     * Method for pooled objects. Beware of thread-safety of your object!
     * Object instance is returned from pool.
     */
    public Object getPooledObject(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (pooledObjects.containsKey(className)) {
            return pooledObjects.get(className);
        } else {
            Class c = getClass(className);
            Object o = c.newInstance();

            pooledObjects.put(className, o);
            return o;
        }
    }

    /**
     * This method loads the class from a jar file.
     * Beta jars are supported if the beta flag is on.
     */
    protected byte[] loadClassBytes(String className) {
        // Support the MultiClassLoader's class name munging facility.
        className = formatClassName(className);
        byte[] resource = null;
        File f = new File(adapterPath);

        // If beta flag is on first check beta versions of jar files before other jars.
        if (beta) {
            File[] files = f.listFiles(new BetaJarFilter());

            for (int i = 0; i < files.length; i++) {
                try {
                    // System.out.println("Locating " + className + " in jar file: " + files[i].getName());
                    JarResources d = new JarResources(files[i]);

                    resource = d.getResource(className);
                    if (resource != null) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
        }

        if (resource == null) {
            File[] files = f.listFiles(new JarFilter());

            for (int i = 0; i < files.length; i++) {
                try {
                    // System.out.println("Locating " + className + " in jar file: " + files[i].getName());
                    JarResources d = new JarResources(files[i]);

                    resource = d.getResource(className);
                    if (resource != null) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
        }

        return resource;

    }

    public void finalize() {
        System.out.println("In NavajoClassLoader finalize(): Killing class loader");
    }

    public static void main(String args[]) throws Exception {
        NavajoClassLoader loader = new NavajoClassLoader("/home/arjen/projecten/ThisToolbox/deploy/ThispasServlets/auxilary/adapters");
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            Object o = loader.getPooledObject("com.dexels.navajo.functions.Max");
            // Object o = loader.getClass("com.dexels.navajo.functions.Max").newInstance();
        }
        long end = System.currentTimeMillis();

        System.out.println("total = " + (end - start) / 1000.0);
    }
}
