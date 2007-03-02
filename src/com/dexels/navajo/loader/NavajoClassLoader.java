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
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

import org.dexels.utils.*;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.util.AuditLog;

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
    private static Object mutex1 = new Object();
    private static Object mutex2 = new Object();
    private HashSet jarResources = null;
    private HashSet betaJarResources = null;
    
    private boolean noCaching = false;
    
    public static int instances = 0;
    
    private volatile Class myScriptClass = null;
    
    /**
     * beta flag denotes whether beta versions of jar files should be used (if present).
     */
    private boolean beta;

    public NavajoClassLoader(String adapterPath, String compiledScriptPath, boolean beta, ClassLoader parent) {
    	super(parent);
        this.adapterPath = adapterPath;
        this.beta = beta;
        this.compiledScriptPath = compiledScriptPath;
        instances++;
        initializeJarResources();
    }

    public NavajoClassLoader(String adapterPath, String compiledScriptPath, ClassLoader parent) {
    	super(parent);
        this.adapterPath = adapterPath;
        this.beta = false;
        this.compiledScriptPath = compiledScriptPath;
        instances++;
        initializeJarResources();
    }

    public void setNoCaching() {
    	noCaching = true;
    }
    
    public synchronized void clearCache(String className) {
//      Class c = (Class) classes.get(className);
//      if (c != null) {
//        classes.remove(className);
//      }
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

      if ( myScriptClass != null ) {
    	  return myScriptClass;
      }
      
      if ( compiledScriptPath == null ) {
    	  throw new ClassNotFoundException("Script path not set!");
      }
      
      synchronized (mutex1) {
    	
    	  if ( myScriptClass != null ) {
    		  return myScriptClass;
    	  }
    	  
    	  // What if class has been defined in the mean time??????? and second thread waiting comes in????
    	  FileInputStream fis = null;
    	  String className = script;
    	  
    	  try {
    		  script = script.replaceAll("\\.", "/");
    		  String classFileName = this.compiledScriptPath + "/" + script + ".class";
    		  File fi = new File(classFileName);
    		  fis = new FileInputStream(fi);
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
    		  
    		  Class c = loadClass(b, className, true, false);
			 
    		  myScriptClass = c;
    		  
    		  return myScriptClass;
    	  }
    	  catch (Exception e) {
    		  e.printStackTrace();
    		  throw new ClassNotFoundException(script);
    	  } finally {
    		  if ( fis != null ) {
    			  try {
    				  fis.close();
    			  } catch (IOException e) { }
    		  }
    	  }
      }
    }

    /**
     * Always use this method to load a class. It uses the cache first before retrieving the class from a jar resource.
     */
    public Class getClass(String className) throws ClassNotFoundException {
    	//System.err.println("in getClass("+className+")");
        return Class.forName(className, false, this);
    }

    public final File [] getJarFiles(String path, boolean beta) {
    	
    	 if ( adapterPath == null ) {
    		 // Try my parent.
    		 if ( getParent() instanceof NavajoClassLoader ) {
    			 return  ( (NavajoClassLoader) getParent() ).getJarFiles(path, beta);
    		 } else {
    			 //System.err.println("Cound not find Jars ");
    			 return null;
    		 }
    	 }
         File f = new File(adapterPath);
         File [] files = null;
         if (beta)
           files = f.listFiles(new BetaJarFilter());
         else
           files = f.listFiles(new JarFilter());
        return files;
    }

    private final void initializeJarResources() {

    	if ( adapterPath == null || !new File(adapterPath).exists() ) {
    		return;
    	}
    	
    	synchronized (mutex2) {

    		// Do nothing if there is no dispatcher available.
    		if ( NavajoConfig.getInstance() == null ) {
    			return;
    		}
    		
    	
    		if (jarResources == null) {

    			AuditLog.log(AuditLog.AUDIT_MESSAGE_DISPATCHER, "Initializing adapter resources");
    			
    			File[] files = getJarFiles(adapterPath, false);
    			if (files == null) {
    				jarResources = null;
    				return;
    			}


    			if (jarResources == null) {
    				jarResources = new HashSet();
    				for (int i = 0; i < files.length; i++) {
    					JarResources d = new JarResources(files[i]);
    					jarResources.add(d);
    				}
    			}
    			
    		}
    		
    		if ( betaJarResources == null ) {
    			
    			File[] files = getJarFiles(adapterPath, true);
    			if (files == null) {
    				betaJarResources = null;
    				return;
    			}


    			if (betaJarResources == null) {
    				betaJarResources = new HashSet();
    				for (int i = 0; i < files.length; i++) {
    					JarResources d = new JarResources(files[i]);
    					betaJarResources.add(d);
    				}
    			}
    		}

    	}

    }

    public InputStream getResourceAsStream(String name) {

      //System.err.println("in NavajoClassLoader (v2). getResourceAsStream(" + name + ")");
      if ( jarResources == null || betaJarResources == null ) {
    	initializeJarResources();
      }
    	 	
      if (jarResources == null) {
        return getParent().getResourceAsStream(name);
      }

      // If beta classloader first try betaJarResources.
      if ( beta ) {
    	  Iterator allResources = betaJarResources.iterator();
          /// for (int i = 0; i < files.length; i++) {
          //System.err.println("NavajoClassLoader: Locating " + name + " in beta jar file");
          while (allResources.hasNext()) {

        	  JarResources d = (JarResources) allResources.next();

        	  try {

        		  //JarResources d = new JarResources(files[i]);
        		  byte [] resource = d.getResource(name);
        		  if (resource != null) {
        			  return new java.io.ByteArrayInputStream(resource);
        		  }
        	  }
        	  catch (Exception e) {
        	  }
          }

          //System.err.println("Did not find resource, trying parent classloader....: " + this.getClass().getClassLoader() );
          return this.getClass().getClassLoader().getResourceAsStream(name);
      }
      
      Iterator allResources = jarResources.iterator();
      /// for (int i = 0; i < files.length; i++) {
      //System.err.println("NavajoClassLoader: Locating " + name + " in normal jar file");
      while (allResources.hasNext()) {

    	  JarResources d = (JarResources) allResources.next();

    	  try {

    		  //JarResources d = new JarResources(files[i]);
    		  byte [] resource = d.getResource(name);
    		  if (resource != null) {
    			  return new java.io.ByteArrayInputStream(resource);
    		  }
    	  }
    	  catch (Exception e) {
    	  }
      }

      //System.err.println("Did not find resource, trying parent classloader....: " + this.getClass().getClassLoader() );
      return this.getClass().getClassLoader().getResourceAsStream(name);
    }


    /**
     * This method loads the class from a jar file.
     * Beta jars are supported if the beta flag is on.
     */
    protected byte[] loadClassBytes(String className) {

        //System.err.print("NavajoClassLoader " + ( this.hashCode() ) + ": in loadClassBytes(), className = " + className);
        
        // Support the MultiClassLoader's class name munging facility.
        className = formatClassName(className);
        byte[] resource = null;

        //initializeJarResources();

        if ( NavajoConfig.getInstance() == null ) {
        	//System.err.println("... NavajoConfig.getInstance() is null, not found!");
        	return null;
        }
        
        //HashSet jarResources = NavajoConfig.getInstance().getJarResources();
        
        if (jarResources == null) {
        	//System.err.println("... jarResources is null, not found!");
        	return null;
        }

        // If beta flag is on first check beta versions of jar files before other jars.
        if ( beta && betaJarResources != null ) {

        	   Iterator allResources = betaJarResources.iterator();
               /// for (int i = 0; i < files.length; i++) {
               
               //System.err.println("Message: NavajoClassLoader: Locating " + className + " in beta jar file");
               while (allResources.hasNext()) {

                  JarResources d = (JarResources) allResources.next();

                    try {
                        
                        //JarResources d = new JarResources(files[i]);
                        resource = d.getResource(className);

                        if (resource != null) {
                           return resource;
                        }
                    } catch (Exception e) {
                        //System.err.println("ERROR: " + e.getMessage());
                    }
                }
               
        }


        if (resource == null) {

           Iterator allResources = jarResources.iterator();
           /// for (int i = 0; i < files.length; i++) {
           
           //System.err.println("Message: NavajoClassLoader: Locating " + className + " in normal jar file");
           while (allResources.hasNext()) {

              JarResources d = (JarResources) allResources.next();

                try {
                    
                    //JarResources d = new JarResources(files[i]);
                    resource = d.getResource(className);

                    if (resource != null) {
                        break;
                    }
                } catch (Exception e) {
                    //System.err.println("ERROR: " + e.getMessage());
                }
            }
        }

        //System.err.println("NavajoClassLoader: resource = " + resource);

     
        return resource;

    }

    public void finalize() {
        //System.out.println("In NavajoClassLoader finalize(): Killing class loader");
    	instances--;
    }

    public static void main(String [] args) throws Exception {
    	
    	int total = 2;
    	if ( args.length > 0 ) {
    		 total = Integer.parseInt(args[0]);
    	}
    	
    	NavajoConfig nc = new NavajoConfig(null, null);
    	
    	NavajoClassLoader [] ncl = new NavajoClassLoader[total];
    	NavajoClassLoader parent = new NavajoClassLoader("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/adapters", "/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/classes", nc.getClass().getClassLoader());
    	
    	
    	for (int i = 0; i < total; i++) {
    		
    		
    		ncl[i] = new NavajoClassLoader(null, "/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/classes", parent);
    		System.err.println("ncl["+i+"] = " + ncl[i].hashCode());
    		Class c3 = Class.forName("com.dexels.navajo.functions.ParseDouble", true, ncl[i]);
    		
    		Class c = ncl[i].getCompiledNavaScript("club.InitUpdateClub");
    		Class c2 = Class.forName("java.lang.String", true, ncl[i]);
    		System.err.println(i + ": club.InitUpdateClub = " + c.hashCode() + ", java.lang.String = " + c2.hashCode() + ", ParseDouble = " + c3.hashCode());
    		Class c5 = ncl[i].getCompiledNavaScript("club.InitUpdateClub");
    		Class c6 = Class.forName("com.dexels.navajo.functions.ParseDouble", true, ncl[i]);
    	}
    	
    	
    }
}
