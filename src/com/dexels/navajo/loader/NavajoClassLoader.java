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


public class NavajoClassLoader extends MultiClassLoader {

    private String adapterPath = "";
    private String compiledScriptPath = "";
    private Hashtable pooledObjects = new Hashtable();

    /**
     * beta flag denotes whether beta versions of jar files should be used (if present).
     */
    private boolean beta;

    public NavajoClassLoader(String adapterPath, String compiledScriptPath, boolean beta) {
        //System.out.println("Initializing BETA NavajoClassLoader: adapterPath = " + adapterPath + "(" + this + ")");
        this.adapterPath = adapterPath;
        this.beta = beta;
        this.compiledScriptPath = compiledScriptPath;
    }

    public NavajoClassLoader(String adapterPath, String compiledScriptPath) {
        //System.out.println("Initializing NavajoClassLoader: adapterPath = " + adapterPath+ "(" + this + ")");
        this.adapterPath = adapterPath;
        this.beta = false;
        this.compiledScriptPath = compiledScriptPath;
    }

    public void clearCache(String className) {
      Class c = (Class) classes.get(className);
      if (c != null) {
        System.out.println("REMOVING CLASS " + className + " FROM CACHE");
        classes.remove(className);
      }
    }

    /**
     * Use clearCache() to clear the Class cache, allowing a re-load of new jar files.
     */
    public void clearCache() {
        pooledObjects.clear();
        super.clearCache();
        System.out.println("Clear cache called, classes = " + classes);
    }

    public Class getCompiledNavaScript(String script) throws ClassNotFoundException {

      System.out.println("in getCompiledNavaScript(), script = " + script);
      String className = script;

      Class c = (Class) classes.get(className);

      //if (c != null)
      //  System.out.println("FOUND CLASS " + c.getName() + " IN CACHE");

      if (c == null) {
        try {
          c = Class.forName(className, false, this);
          //System.out.println("FOUND CLASS " + c.getName() + " USING CLASS.FORNAME().....");
          return c;
        } catch (Exception cnfe) {
          //System.out.println("Class not found using classloader...trying compiled script working directory...");
        }
        try {
          script = script.replaceAll("\\.", "/");
          String classFileName = this.compiledScriptPath + "/" + script + ".class";
          //System.out.println("TRYING TO READ CLASS FILE: " + classFileName);
          File fi = new File(classFileName);
          FileInputStream fis = new FileInputStream(fi);
          int size = (int) fi.length();
          byte[] b = new byte[(int) size];
          int rb = 0;
          int chunk = 0;

          while (((int) size - rb) > 0) {
            chunk = fis.read(b, rb, (int) size - rb);
            if (chunk == -1) {
              break;
            }
            rb += chunk;
          }

          c = loadClass(b, className, true, false);
          classes.put(className, c);
          //System.out.println("FOUND CLASS " + c.getName() + " IN NAVAJO ADAPTERS WORKING DIRECTORY");
          return c;
        } catch (Exception e) {
          e.printStackTrace();
          throw new ClassNotFoundException(script);
        }

      } else {
        return c;
      }
    }

    /**
     * Always use this method to load a class. It uses the cache first before retrieving the class from a jar resource.
     */
    public Class getClass(String className) throws ClassNotFoundException {

        Class c = (Class) classes.get(className);

        if (c == null) {
            return Class.forName(className, false, this);
        } else {
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
    protected byte[] loadClassBytes(String className) {


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
                    //System.out.println("Locating " + className + " in jar file: " + files[i].getName());
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
                    //System.out.println("Locating " + className + " in jar file: " + files[i].getName());
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

        return resource;

    }

    public void finalize() {
        //System.out.println("In NavajoClassLoader finalize(): Killing class loader");
    }

    public static void main(String args[]) throws Exception {
        NavajoClassLoader loader = new NavajoClassLoader("/home/arjen/projecten/ThisToolbox/deploy/ThispasServlets/auxilary/adapters",
                                                         "/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/navajo/adapters/work");
        Class c = loader.getCompiledNavaScript("InitKueryAllMembersPerClub");
        com.dexels.navajo.mapping.CompiledScript cs = (com.dexels.navajo.mapping.CompiledScript) c.newInstance();
        System.out.println("CREATED INSTANCE: " + cs);
    }
}
