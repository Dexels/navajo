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
 *st
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


public final class NavajoClassLoader extends MultiClassLoader {

    private String adapterPath = "";
    private String compiledScriptPath = "";
    private static Object mutex1 = new Object();

    /**
     * beta flag denotes whether beta versions of jar files should be used (if present).
     */
    private boolean beta;

    public NavajoClassLoader(String adapterPath, String compiledScriptPath, boolean beta) {
        this.adapterPath = adapterPath;
        this.beta = beta;
        this.compiledScriptPath = compiledScriptPath;
    }

    public NavajoClassLoader(String adapterPath, String compiledScriptPath) {
        this.adapterPath = adapterPath;
        this.beta = false;
        this.compiledScriptPath = compiledScriptPath;
    }

    public synchronized void clearCache(String className) {
      Class c = (Class) classes.get(className);
      if (c != null) {
        classes.remove(className);
      }
    }

    /**
     * Use clearCache() to clear the Class cache, allowing a re-load of new jar files.
     */
    public final void clearCache() {
        super.clearCache();
    }

    /**
     * Get the class definition for a compiled NavaScript.
     * Method is run in synchronized mode to prevent multiple definitions of the same class in case
     * of multiple threads.
     *
     * 1. Try to fetch class from caching HashMap.
     * 2. Try to load class via classloader.
     * 3. Try to read class bytes from .class file, load it and store it in caching HashMap.
     *
     * @param script
     * @return
     * @throws ClassNotFoundException
     */
    public final Class getCompiledNavaScript(String script) throws ClassNotFoundException {

      String className = script;

      Class c = null;
      try {
        c = Class.forName(className, false, this);
        return c;
      }
      catch (Exception cnfe) {
        // Class not found using classloader, try compiled scripts directory.
        c = null;
      }

      synchronized (mutex1) {

          try {
            script = script.replaceAll("\\.", "/");
            String classFileName = this.compiledScriptPath + "/" + script +
                ".class";
            File fi = new File(classFileName);
            FileInputStream fis = new FileInputStream(fi);
            int size = (int) fi.length();
            byte[] b = new byte[ (int) size];
            int rb = 0;
            int chunk = 0;

            while ( ( (int) size - rb) > 0) {
              chunk = fis.read(b, rb, (int) size - rb);
              if (chunk == -1) {
                break;
              }
              rb += chunk;
            }

            c = loadClass(b, className, true, false);

            return c;
          }
          catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException(script);
          }
      }
    }

    /**
     * Always use this method to load a class. It uses the cache first before retrieving the class from a jar resource.
     */
    public final Class getClass(String className) throws ClassNotFoundException {
            return Class.forName(className, false, this);
    }

    public File [] getJarFiles(String path, boolean beta) {
         File f = new File(adapterPath);
         File [] files = null;
         if (beta)
           files = f.listFiles(new BetaJarFilter());
         else
           files = f.listFiles(new JarFilter());
        return files;
    }

    /**
     * This method loads the class from a jar file.
     * Beta jars are supported if the beta flag is on.
     */
    protected final byte[] loadClassBytes(String className) {

        //System.out.println("NavajoClassLoader: in loadClassBytes(), className = " + className);
        // Support the MultiClassLoader's class name munging facility.
        className = formatClassName(className);
        byte[] resource = null;

        File [] files = getJarFiles(adapterPath, beta);

        if (files == null)
              return null;

        // If beta flag is on first check beta versions of jar files before other jars.
        if (beta) {


            for (int i = 0; i < files.length; i++) {
                try {
                    //System.out.println("NavajoClassLoader: Locating " + className + " in jar file: " + files[i].getName());
                    JarResources d = new JarResources(files[i]);

                    resource = d.getResource(className);
                    if (resource != null) {
                        break;
                    }
                } catch (Exception e) {
                    //System.out.println("ERROR: " + e.getMessage());
                }
            }
        }

        if (resource == null) {

            for (int i = 0; i < files.length; i++) {
                try {
                    //System.out.println("NavajoClassLoader: Locating " + className + " in jar file: " + files[i].getName());
                    JarResources d = new JarResources(files[i]);
                    resource = d.getResource(className);

                    if (resource != null) {
                        break;
                    }
                } catch (Exception e) {
                    //System.out.println("ERROR: " + e.getMessage());
                }
            }
        }

        //System.out.println("NavajoClassLoader: resource = " + resource);

        return resource;

    }

    public void finalize() {
        //System.out.println("In NavajoClassLoader finalize(): Killing class loader");
    }

}
