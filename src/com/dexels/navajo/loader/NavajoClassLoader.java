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

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.zip.ZipException;

import org.dexels.utils.JarResources;
import org.dexels.utils.MultiClassLoader;

import com.dexels.navajo.server.NavajoConfig;

import sun.misc.CompoundEnumeration;

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
    private HashSet<JarResources> jarResources = null;
    private HashSet<JarResources> betaJarResources = null;
    
    private boolean noCaching = false;
    
    private static int instances = 0;
    
    @SuppressWarnings("unchecked")
	private volatile Class myScriptClass = null;
    
    /**
     * beta flag denotes whether beta versions of jar files should be used (if present).
     */
    private boolean beta;

    public NavajoClassLoader(ClassLoader parent) {
    	super(parent);
    }
    
    public NavajoClassLoader(String adapterPath, String compiledScriptPath, boolean beta, ClassLoader parent) {
    	super(parent);
        this.adapterPath = adapterPath;
        this.beta = beta;
        this.compiledScriptPath = compiledScriptPath;
        instances++;
        initializeJarResources();
    }

    public static int getInstances() {
    	return instances;
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
    
    public boolean isNoCaching() {
    	return noCaching;
    }
    
    public synchronized void clearCache(String className) {

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
    @SuppressWarnings("unchecked")
	public final Class getCompiledNavaScript(String script) throws ClassNotFoundException {

      if ( myScriptClass != null ) {
    	  return myScriptClass;
      }
      
      if ( compiledScriptPath == null ) {
    	  throw new ClassNotFoundException("Script path not set!");
      }
      File compiledFolder = new File(compiledScriptPath);
      if(!compiledFolder.exists()) {
      	compiledFolder.mkdirs();
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
    		  //e.printStackTrace();
    		  // Try normal classloader...
    		  try {
    			  Class c = getClass(className);
    			  return c;
    		  } catch (Exception e2) {
    			  throw new ClassNotFoundException("Could not find script: " + script,e2);
    		  }
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
    @SuppressWarnings("unchecked")
	public Class getClass(String className) throws ClassNotFoundException {
    	try {
    		return Class.forName(className, false, this);
    	} catch (ClassNotFoundException cnfe) {
    		System.err.println("ERROR: COULD NOT FIND CLASS: " + className);
    		throw cnfe;
    	}
    }

    public final File [] getJarFiles(String path, boolean beta) {
    	
    	 if ( adapterPath == null ) {
    		 // Try my parent.
    		 if ( getParent() instanceof NavajoClassLoader ) {
    			 return  ( (NavajoClassLoader) getParent() ).getJarFiles(path, beta);
    		 } else {
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
    		System.err.println("No adapters found!");
    		return;
    	}
    	
    	synchronized (mutex2) {
    	
    		if (jarResources == null) {

    			File[] files = getJarFiles(adapterPath, false);
    			if (files == null) {
    				jarResources = null;
    				return;
    			}


    			if (jarResources == null) {
    				jarResources = new HashSet<JarResources>();
    				for (int i = 0; i < files.length; i++) {
    					try {
    						System.err.println("Checking file: "+files[i].getAbsolutePath());
							JarResources d = new JarResources(files[i]);
							jarResources.add(d);
						} catch (Throwable e) {
							System.err.println("Error opening zip file in adapters. Continuing with others");
							e.printStackTrace();
						}
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
    				betaJarResources = new HashSet<JarResources>();
    				for (int i = 0; i < files.length; i++) {
    					try { 
							JarResources d = new JarResources(files[i]);
	    					betaJarResources.add(d);
						} catch (Throwable e) {
							System.err.println("Error opening zip file in adapters. Continuing with others");
							e.printStackTrace();
						}
    				}
    			}
    		}

    	}

    }

    
        
    public InputStream getResourceAsStream(String name) {

      if ( jarResources == null || betaJarResources == null ) {
    	initializeJarResources();
      }
    	 	
      if (jarResources == null) {
        return getParent().getResourceAsStream(name);
      }

      // If beta classloader first try betaJarResources.
      if ( beta ) {
    	  Iterator<JarResources> allResources = betaJarResources.iterator();
          while (allResources.hasNext()) {

        	  JarResources d = allResources.next();

        	  try {

        		  byte [] resource = d.getResource(name);
        		  if (resource != null) {
        			  return new java.io.ByteArrayInputStream(resource);
        		  }
        	  }
        	  catch (Exception e) {
        	  }
          }

          return this.getClass().getClassLoader().getResourceAsStream(name);
      }
      
      Iterator<JarResources> allResources = jarResources.iterator();
      while (allResources.hasNext()) {

    	  JarResources d = allResources.next();

    	  try {

    		  byte [] resource = d.getResource(name);
    		  if (resource != null) {
    			  return new java.io.ByteArrayInputStream(resource);
    		  }
    	  }
    	  catch (Exception e) {
    	  }
      }

      return this.getClass().getClassLoader().getResourceAsStream(name);
    }


    /**
     * This method loads the class from a jar file.
     * Beta jars are supported if the beta flag is on.
     */
    protected byte[] loadClassBytes(String className) {

        // Support the MultiClassLoader's class name munging facility.
        className = formatClassName(className);
        byte[] resource = null;
        
        if (jarResources == null) {
        	return null;
        }

        // If beta flag is on first check beta versions of jar files before other jars.
        if ( beta && betaJarResources != null ) {

        	   Iterator<JarResources> allResources = betaJarResources.iterator();
               
        	   while (allResources.hasNext()) {

                  JarResources d = allResources.next();

                    try {
                        
                        resource = d.getResource(className);

                        if (resource != null) {
                           return resource;
                        }
                    } catch (Exception e) {
                      
                    }
                }
               
        }


        if (resource == null) {

           Iterator<JarResources> allResources = jarResources.iterator();
         
           while (allResources.hasNext()) {

              JarResources d = allResources.next();

                try {
                   
                    resource = d.getResource(className);

                    if (resource != null) {
                        break;
                    }
                } catch (Exception e) {
                  
                }
            }
        }
    
        return resource;

    }

    protected void finalize() {
        instances--;
    }

    public static int getIntances() {
    	return instances;
    }
    
    @Override 
    public URL getResource(String name) {

    	if ( jarResources == null || betaJarResources == null ) {
    		initializeJarResources();
    	}

    	if (jarResources == null) {
    		return getParent().getResource(name);
    	}

    	// If beta classloader first try betaJarResources.
    	if ( beta ) {
    		Iterator<JarResources> allResources = betaJarResources.iterator();
    		while (allResources.hasNext()) {

    			JarResources d = allResources.next();

    			try {

    				URL resource = d.getPathURL(name);
    				if (resource != null && d.hasResource(name)) {
    					return resource;
    				}
    			}
    			catch (Exception e) {
    			}
    		}

    		return this.getClass().getClassLoader().getResource(name);
    	}

    	Iterator<JarResources> allResources = jarResources.iterator();
    	while (allResources.hasNext()) {

    		JarResources d = allResources.next();

    		try {

    			URL resource = d.getPathURL(name);    	      		  
    			if (resource != null && d.hasResource(name)) {
    				return resource;
    			}
    		}
    		catch (Exception e) {
    		}
    	}

    	return this.getClass().getClassLoader().getResource(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {


    	HashSet<URL> s = new HashSet();

    	if ( jarResources == null || betaJarResources == null ) {
    		initializeJarResources();
    	}

    	if (jarResources == null) {
    		return getParent().getResources(name);
    	}

    	// If beta classloader first try betaJarResources.
    	if ( beta ) {
    		Iterator<JarResources> allResources = betaJarResources.iterator();
    		while (allResources.hasNext()) {

    			JarResources d = allResources.next();

    			try {

    				URL resource = d.getPathURL(name);
    				if (resource != null && d.hasResource(name)) {
    					s.add(resource);
    				}
    			}
    			catch (Exception e) {
    			}
    		}

    		return this.getClass().getClassLoader().getResources(name);
    	} else {

    		Iterator<JarResources> allResources = jarResources.iterator();
    		while (allResources.hasNext()) {

    			JarResources d = allResources.next();

    			try {

    				URL resource = d.getPathURL(name);
    				if (resource != null && d.hasResource(name)) {
    					s.add(resource);
    				}
    			}
    			catch (Exception e) {
    			}
    		}
    	}

    	return new CompoundEnumeration(new Enumeration[]{Collections.enumeration(s), 
    			                       getClass().getClassLoader().getResources(name)});

    }

}
