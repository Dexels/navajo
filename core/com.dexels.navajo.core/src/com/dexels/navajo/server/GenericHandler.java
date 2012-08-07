package com.dexels.navajo.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import navajocore.Version;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoCompileScriptEvent;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.server.scriptengine.GenericScriptEngine;
import com.dexels.navajo.util.AuditLog;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001 - 2011
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 *
 * This class is used to run Navajo script web services.
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

public final class GenericHandler extends ServiceHandler {

    private static ConcurrentHashMap<String,NavajoClassSupplier> loadedClasses = null;

    private static Object mutex1 = new Object();
    private static Object mutex2 = new Object();
   
    
	private final static Logger logger = LoggerFactory
			.getLogger(GenericHandler.class);
    public static String applicationGroup = "";
    
    static {
    	try {
    		applicationGroup = DispatcherFactory.getInstance().getNavajoConfig().getInstanceGroup();
    	} catch (Throwable t) {
    		applicationGroup = "";
    	}
    }
    
    public GenericHandler() {

    	boolean finishedSync = false;
    	
    	if (loadedClasses == null)
    		synchronized ( mutex1 ) {
    			if ( !finishedSync ) {
    				loadedClasses = new ConcurrentHashMap<String, NavajoClassSupplier>();
    				finishedSync = true;
    			}
    		}
    }

	public static void doClearCache() {
    	// fix ugly shutdown npe in some cases
    	if(loadedClasses!=null) {
        	loadedClasses.clear();
    	}
       loadedClasses = null;
       loadedClasses = new ConcurrentHashMap<String, NavajoClassSupplier>();
    }

    private final static CompiledScript getCompiledScript(Access a, String className,File scriptFile,String scriptName) throws Exception {
    	NavajoClassSupplier loader = getScriptLoader(a.betaUser, className);
    	Class cs = loader.getCompiledNavaScript(className);
    	if ( cs != null ) {
    		com.dexels.navajo.mapping.CompiledScript cso = (com.dexels.navajo.mapping.CompiledScript) cs.newInstance();
    		if(cso instanceof GenericScriptEngine) {
    			GenericScriptEngine gse = (GenericScriptEngine)cso;
    			gse.setScriptFile(scriptFile);
    			gse.setScriptName(scriptName);
    			gse.setAccess(a);
    			
    		}
    		cso.setClassLoader(loader);
    		return cso;
    	}
    	return null;
    }
    
    private static final boolean hasDirtyDepedencies(Access a, String className) {
    	CompiledScript cso = null;
    	try {
    		NavajoClassLoader loader = (NavajoClassLoader) loadedClasses.get(className);
    		if ( loader == null ) { // Script does not yet exist.
    			return false;
    		}
    		Class cs = loader.getCompiledNavaScript(className);
        	if ( cs != null ) {
        		cso = (com.dexels.navajo.mapping.CompiledScript) cs.newInstance();
        		cso.setClassLoader(loader);
        	}	
    	} catch (Exception e) {
    		logger.error("Error: ", e);
    		return false;
    	}
    	if ( cso != null ) {
    		boolean result = cso.hasDirtyDependencies(a);
    		return result;
    	} else {
    		return false;
    	}

    }
    
    private static final Object[] getScriptPathServiceNameAndScriptFile(String rpcName, boolean betaUser) {
    	String scriptPath = DispatcherFactory.getInstance().getNavajoConfig().getScriptPath();
    	//System.err.println("Looking for script: "+rpcName);
    	int strip = rpcName.lastIndexOf("/");
        String pathPrefix = "";
        String serviceName = rpcName;
        if (strip != -1) {
          serviceName = rpcName.substring(strip+1);
          pathPrefix = rpcName.substring(0, strip) + "/";
        }
        
    	File scriptFile = new File(scriptPath + "/" + rpcName + "_" + applicationGroup + ".xml");
    	if (scriptFile.exists()) {
    		serviceName += "_" + applicationGroup;
    	} else {
    		scriptFile = new File(scriptPath + "/" + rpcName + ( betaUser ? "_beta" : "" ) + ".xml" );
    		if ( betaUser && !scriptFile.exists() ) {
    			// Try normal webservice.
    			scriptFile = new File(scriptPath + "/" + rpcName + ".xml" );
    		} else if ( betaUser ) {
    			serviceName += "_beta";
    		} 
    		// Check if scriptFile exists.
			if ( !scriptFile.exists() ) {
//				scriptFile = null;
			}
    	}
    	
    	if(!scriptFile.exists()) {
       	//-------------------------------
       	// Now, check the context folder:
       	//-------------------------------
       	String sp = "scripts/";
       	File scrPath = new File(DispatcherFactory.getInstance().getNavajoConfig().getContextRoot(),sp);
       	//System.err.println("Proposed script path: "+scrPath.getAbsolutePath()+" exists? "+scrPath.exists());
       	scriptFile = new File(scrPath + rpcName + "_" + applicationGroup + ".xml");
       	if (scriptFile.exists()) {
       		serviceName += "_" + applicationGroup;
       	} else {
       		scriptFile = new File(scrPath, rpcName + ( betaUser ? "_beta" : "" ) + ".xml" );
       		if ( betaUser && !scriptFile.exists() ) {
       			// Try normal webservice.
       			scriptFile = new File(scrPath, rpcName + ".xml" );
       		} else if ( betaUser ) {
       			serviceName += "_beta";
       		} 
       		// Check if scriptFile exists.
   			if ( !scriptFile.exists() ) {
//   				scriptFile = null;
   			}
       	}

    	}
    	
    	
    	
    	
    	String sourceFileName = null;
    	if(scriptFile.exists()) {
    		sourceFileName = DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath()+ "/" + pathPrefix + serviceName + ".java";;
    		// regular scriptfile, 
    	} else {
    		// pure java scriptfile
    		String temp =DispatcherFactory.getInstance().getNavajoConfig().getScriptPath()+ "/" + pathPrefix + serviceName + ".java";
    		File javaScriptfile = new File(temp);
    		if(javaScriptfile.exists()) {
       		sourceFileName = temp;    			    			
			}
		}
    	// set when either normal script or pure java, will skip if its neither (-> a jsr223 script)
		if (sourceFileName != null) {
			String classFileName = DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath() + "/" + pathPrefix
					+ serviceName + ".class";
			String className = (pathPrefix.equals("") ? serviceName : MappingUtils.createPackageName(pathPrefix) + "."
					+ serviceName);
			return new Object[] { pathPrefix, serviceName, scriptFile, sourceFileName, new File(sourceFileName), className,
					classFileName, new File(classFileName),true };
		}
    	
    	
   	System.err.println("Found JSR223 based script! (" + rpcName + ")" ); // Or a non existing script
		// jsr 223 script.
		File currentScriptDir = new File(DispatcherFactory.getInstance().getNavajoConfig().getScriptPath() + "/" + pathPrefix);
		final String servName = serviceName;
		File[] scripts = currentScriptDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(servName);
			}
		});
		if(scripts==null) {
			return null;
		}
		
		if (scripts.length > 1) {
			System.err.println("Warning, multiple candidates. Assuming the first: " + scripts[0].getName());
		}
		if(scripts.length==0) {
			System.err.println("Not found. dir: "+currentScriptDir.getAbsolutePath());
			return null;
		}
		String classFileName = null;
		String className = "com.dexels.navajo.server.scriptengine.GenericScriptEngine";    	
    	
    	return new Object[]{pathPrefix,serviceName,scriptFile,sourceFileName,scripts[0],className,classFileName,null,false};

    }
    
    /**
     * Check whether Navajo script file needs to be recompiled.
     * 
     * @param scriptFile
     * @param sourceFile
     * @return
     */
    private final static boolean checkScriptRecompile(File scriptFile, File sourceFile) {
    	if ( scriptFile == null ) {
    		return false;
    	}
    	boolean b = (!sourceFile.exists() || (scriptFile.lastModified() > sourceFile.lastModified()) || sourceFile.length() == 0);
    	return b;
    }
    
    /**
     * Check whether Java class file needs to be recompiled.
     * 
     * @param serviceName
     * @param sourceFile
     * @param targetFile
     * @return
     */
    private final static boolean checkJavaRecompile(File sourceFile, File targetFile) {
    	boolean b = !targetFile.exists() || (sourceFile.lastModified() > targetFile.lastModified());
    	return b;
    }
    
    private static final NavajoClassSupplier getScriptLoader(boolean isBetaUser, String className) {
    	NavajoClassSupplier newLoader = (NavajoClassLoader) loadedClasses.get(className);
         if (newLoader == null ) {
         	newLoader = new NavajoClassLoader(null, DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath(), 
         			( isBetaUser ? DispatcherFactory.getInstance().getNavajoConfig().getBetaClassLoader() : 
         				DispatcherFactory.getInstance().getNavajoConfig().getClassloader() ) );
         	// Use concurrent hashmap: if key exists, return existing classloader.
         	NavajoClassSupplier ncs = loadedClasses.putIfAbsent(className, newLoader);
         	if ( ncs != null ) {
         		return ncs;
         	}
         }
         return newLoader;
    }
    
    /**
     * Check whether web service Access needs recompile.
     * Can be used by interested parties, e.g. Dispatcher.
     * 
     * @param a
     * @return
     */
    public final static boolean needsRecompileForScript(Access a) {
    	Object [] all = getScriptPathServiceNameAndScriptFile(a.rpcName, a.betaUser);
 		if(all==null) {
 			return false;
 		}

    	File scriptFile = (File) all[2];
    	File sourceFile = (File) all[4];
    	String className = (String) all[5];
    	File targetFile = (File) all[7];
    	boolean isCompilable = (Boolean)all[8];

    	boolean nr = isCompilable && (checkScriptRecompile(scriptFile, sourceFile) || 
    	             checkJavaRecompile(sourceFile, targetFile) ||
    	             hasDirtyDepedencies(a, className));
    	//System.err.println(">>>>>>>>>>>>>>>>>>>>>>> needsRecompile()... " + nr);
    	return nr;
    }
    
    public boolean needsRecompile() {
    	return needsRecompileForScript(this.access);
    }
    
    public static CompiledScript compileScript(Access a, StringBuffer compilerErrors) throws Exception {
    	
    	NavajoConfigInterface properties = DispatcherFactory.getInstance().getNavajoConfig();
    	
    	String scriptPath = properties.getScriptPath();
    	
    		Object [] all = getScriptPathServiceNameAndScriptFile(a.rpcName, a.betaUser);
    		if(all==null) {
    			throw new FileNotFoundException("No script found for: "+a.rpcName);
    		}
    		String pathPrefix = (String) all[0];
    		String serviceName = (String) all[1];
    		File scriptFile = (File) all[2];
    		String sourceFileName = (String) all[3];
    		File sourceFile = (File) all[4];
    		String className = (String) all[5];
    		String classFileName = (String) all[6];
    		File targetFile = (File) all[7];

    		if (properties.isCompileScripts()) {

    			if ( scriptFile != null && scriptFile.exists()) { // We have a script that exists.

    				if ( checkScriptRecompile(scriptFile, sourceFile) || hasDirtyDepedencies(a, className) ) {

    					//System.err.println(">>>> RECOMPILING TSL..........");
    					synchronized (mutex1) { // Check for outdated compiled script Java source.

    						if ( checkScriptRecompile(scriptFile, sourceFile) || hasDirtyDepedencies(a, className) ) {

    							com.dexels.navajo.mapping.compiler.TslCompiler tslCompiler = new
    							com.dexels.navajo.mapping.compiler.TslCompiler(properties.getClassloader());

    							try {
    								tslCompiler.compileScript(serviceName, 
    										scriptPath,
    										properties.getCompiledScriptPath(),
    										pathPrefix,properties.getOutputWriter(properties.getCompiledScriptPath(), pathPrefix, serviceName, ".java"));
    							} catch (SystemException ex) {
    								sourceFile.delete();
    								AuditLog.log(AuditLog.AUDIT_MESSAGE_SCRIPTCOMPILER , ex.getMessage(), Level.SEVERE, a.accessID);
    								throw ex;
    							}
    						}
    					}
    				} // end of sync block.

    				// Java recompile.
    				compilerErrors.append(recompileJava(a, sourceFileName, sourceFile, className, targetFile,serviceName));

    			} else {

    				// Maybe the jave file exists in the script path.
    				if ( !sourceFile.exists() ) { // There is no java file present.
    					AuditLog.log(AuditLog.AUDIT_MESSAGE_SCRIPTCOMPILER, "SCRIPT FILE DOES NOT EXISTS, I WILL TRY TO LOAD THE CLASS FILE ANYWAY....", Level.WARNING, a.accessID);
    					//System.out.println("SCRIPT FILE DOES NOT EXISTS, I WILL TRY TO LOAD THE CLASS FILE ANYWAY....");
    				} else {
    					// Compile java file using normal java compiler.
    					//System.err.println("DOING PLAIN JAVA...");
    					if(sourceFile.getName().endsWith("java")) {
       					compilerErrors.append(recompileJava(a, sourceFileName, sourceFile, className, targetFile,serviceName));
    					} else {
    						// interpreted script. Nothing to do
    					}
    				}
    			}
    		}
    
    	    com.dexels.navajo.mapping.CompiledScript cso = getCompiledScript(a, className,sourceFile,serviceName);
    	    
    	    return cso;
    }
    
    /**
     * doService() is called by Dispatcher to perform web service.
     *
     * @return
     * @throws NavajoException
     * @throws UserException
     * @throws SystemException
     * @throws AuthorizationException
     */
    @SuppressWarnings("unchecked")
	public final Navajo doService() throws NavajoException, UserException, SystemException, AuthorizationException {

    	// Check whether break-was-set for access from 'the-outside'. If so, do NOT perform service and return
    	// current value of outputdoc.
    	
    	if ( access.isBreakWasSet() ) {
    		if ( access.getOutputDoc() == null ) {
    			Navajo outDoc = NavajoFactory.getInstance().createNavajo();
    			access.setOutputDoc(outDoc);
    		}
    		return access.getOutputDoc();
    	}
    	
        Navajo outDoc = null;
    	StringBuffer compilerErrors = new StringBuffer();
    	
        try {
            // Should method getCompiledNavaScript be fully synced???
        	CompiledScript cso = getOSGiService(access.rpcName);
        	if(cso==null) {
        		if(Version.osgiActive()) {
        			logger.warn("Script not found from OSGi registry while OSGi is active");
        		} else {
                	cso = compileScript(access, compilerErrors);
                	if(cso==null) {
                		logger.error("Can not find OSGi script for rpc: {} ",access.rpcName);
                	}
        		}
        	}
            outDoc = NavajoFactory.getInstance().createNavajo();
            access.setOutputDoc(outDoc);
            access.setCompiledScript(cso);
            cso.run(access);

            return access.getOutputDoc();
          } catch (Throwable e) {
            if (e instanceof com.dexels.navajo.mapping.BreakEvent) {
              // Create dummy header to set breakwasset attribute.
              Header h = NavajoFactory.getInstance().createHeader(outDoc, "", "", "", -1);
              outDoc.addHeader(h);
              outDoc.getHeader().setHeaderAttribute("breakwasset", "true");
              return outDoc;
            }
            else if (e instanceof com.dexels.navajo.server.ConditionErrorException) {
              return ( (com.dexels.navajo.server.ConditionErrorException) e).getNavajo();
            }
            else if (e instanceof AuthorizationException) {
              System.err.println("CAUGHT AUTHORIZATION ERROR IN GENERICHANDLER!!!!!!!!!!!!!!!!!!");
              throw (AuthorizationException) e;
            }
            else {
            	AuditLog.log(AuditLog.AUDIT_MESSAGE_SCRIPTCOMPILER, e.getMessage() + (!compilerErrors.toString().trim().equals("") ? (", java compile errors: " + compilerErrors) : ""), Level.SEVERE, access.accessID);
            	throw new SystemException( -1, e.getMessage() + (!compilerErrors.toString().trim().equals("") ? (", java compile errors: " + compilerErrors) : ""), e);
            }
          }
        }

	private CompiledScript getOSGiService(String scriptName) {
		final BundleContext bundleContext = Version.getDefaultBundleContext();
		if(bundleContext==null) {
			logger.debug("No OSGi context found");
			return null;
		}
		String rpcName = scriptName.replaceAll("/", ".");
		String filter = "(navajo.scriptName="+rpcName+")";
		ServiceReference<?>[] sr;
		try {
			sr = bundleContext.getServiceReferences(CompiledScriptFactory.class.getName(), filter);
		} catch (InvalidSyntaxException e) {
			logger.error("Filter syntax problem for: "+filter,e);
			return null;
		}
		if(sr==null || sr.length==0) {
			logger.error("No service reference found for "+filter);
			try {
				CompiledScript ss = loadOnDemand(bundleContext, rpcName, filter);
				return ss;
			} catch (Exception e) {
				logger.error("Service  "+filter,e);
			}
		}
		
		if(sr.length>1) {
			logger.warn("Multiple references found for "+filter);
		}
		
		 CompiledScriptFactory csf = (CompiledScriptFactory) bundleContext.getService(sr[0]);
		 if(csf==null ) {
			 logger.error("CompiledScriptFactory did not resolve properly for service: "+filter);
			 BundleCreator bc = DispatcherFactory.getInstance().getBundleCreator();
			 if(bc!=null) {
				 try {
					CompiledScript ss = bc.getOnDemandScriptService(rpcName);
					return ss;
				} catch (Exception e) {
					logger.error("on demand script resolution failed.",e);
				}
				 
			 }
			 return null;
		 }
		 CompiledScript ss;
		try {
			ss = csf.getCompiledScript();
			final CompiledScript ccs = ss;
			ss.setClassLoader(new NavajoClassSupplier() {
				
				@Override
				public File[] getJarFiles(String path, boolean beta) {
					return null;
				}
				
				@Override
				public Class<?> getCompiledNavaScript(String className)
						throws ClassNotFoundException {
					return null;
				}
				
				@Override
				public Class<?> getClass(String className) throws ClassNotFoundException {
					return Class.forName(className, true, ccs.getClass().getClassLoader()); 
//					return null;
				}
			});
		} catch (Exception e) {
			 logger.error("CompiledScriptFactory did not resolve properly for service: "+filter,e);
			 return null;
		}
		bundleContext.ungetService(sr[0]);
		 return ss;
	}

	private CompiledScript loadOnDemand(BundleContext bundleContext, String rpcName, String filter) throws Exception {
		BundleCreator bc = getBundleCreator();
		CompiledScript sc = bc.getOnDemandScriptService(rpcName);
		// wait for it..
		
//		Service
//		sr = bundleContext.getServiceReferences(CompiledScriptFactory.class.getName(), filter);
		return sc;
	}

	/**
	 * Bit of a hack, should be really DInjected
	 * @return
	 */
	private BundleCreator getBundleCreator() {
		return DispatcherFactory.getInstance().getBundleCreator();
	}

	private static String recompileJava(
			Access a,
			String sourceFileName,
			File sourceFile, String className, File targetFile,String scriptName) {
		
		String compilerErrors = "";
		
		
		if ( checkJavaRecompile(sourceFile, targetFile) ) { // Create class file

			synchronized(mutex2) {

				if ( checkJavaRecompile(sourceFile, targetFile) ) {

					//System.err.println(">>>> RECOMPILING JAVA..........");
					
					NavajoClassSupplier loader = null;
					if ( ( loader = loadedClasses.get(className) ) != null) {
						// Get previous version of CompiledScript.
						try {
							CompiledScript prev = getCompiledScript(a, className,sourceFile,scriptName);
							prev.releaseCompiledScript();
						} catch (Exception e) {
						}
						loadedClasses.remove(className);
						loader = null;
					}

					com.dexels.navajo.compiler.internal.NavajoCompiler compiler = new com.dexels.navajo.compiler.internal.NavajoCompiler();
					try {
						compiler.compile(a, DispatcherFactory.getInstance().getNavajoConfig(), sourceFileName);
						compilerErrors = compiler.errors;
						NavajoEventRegistry.getInstance().publishEvent(new NavajoCompileScriptEvent(a.rpcName));
					}
					catch (Throwable t) {
						AuditLog.log(AuditLog.AUDIT_MESSAGE_SCRIPTCOMPILER, "Could not run-time compile Java file: " + sourceFileName + " (" + t.getMessage() + "). It may be compiled already", Level.WARNING, a.accessID);
					}
				}
			}
		} // end of sync block.
		return compilerErrors;
	}
    
    /**
     * Return load script class count.
     * @return
     */
    public static int getLoadedClassesSize() {
    	if ( loadedClasses != null ) {
    		return loadedClasses.size();
    	} else {
    		return 0;
    	}
    }

}