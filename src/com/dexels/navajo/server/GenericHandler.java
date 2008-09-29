package com.dexels.navajo.server;

import java.util.*;
import java.util.logging.Level;

import java.io.File;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.document.*;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoCompileScriptEvent;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001 - 2008
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

    @SuppressWarnings("unchecked")
	private static HashMap loadedClasses = null;

    private static Object mutex1 = new Object();
    private static Object mutex2 = new Object();
   
    public static final String applicationGroup = DispatcherFactory.getInstance().getNavajoConfig().getInstanceGroup();
    
    @SuppressWarnings("unchecked")
	public GenericHandler() {

    	boolean finishedSync = false;
    	
    	if (loadedClasses == null)
    		synchronized ( mutex1 ) {
    			if ( !finishedSync ) {
    				loadedClasses = new HashMap();
    				finishedSync = true;
    			}
    		}
    }

    @SuppressWarnings("unchecked")
	protected static void doClearCache() {
       loadedClasses = null;
       loadedClasses = new HashMap();
    }

    private static final Object[] getScriptPathServiceNameAndScriptFile(Access a) {
    	String scriptPath = DispatcherFactory.getInstance().getNavajoConfig().getScriptPath();
    	
    	int strip = a.rpcName.lastIndexOf("/");
        String pathPrefix = "";
        String serviceName = a.rpcName;
        if (strip != -1) {
          serviceName = a.rpcName.substring(strip+1);
          pathPrefix = a.rpcName.substring(0, strip) + "/";
        }
        
    	File scriptFile = new File(scriptPath + "/" + a.rpcName + "_" + applicationGroup + ".xml");
    	if (scriptFile.exists()) {
    		serviceName += "_" + applicationGroup;
    	} else {
    		scriptFile = new File(scriptPath + "/" + a.rpcName + ( a.betaUser ? "_beta" : "" ) + ".xml" );
    		if ( a.betaUser && !scriptFile.exists() ) {
    			// Try normal webservice.
    			scriptFile = new File(scriptPath + "/" + a.rpcName + ".xml" );
    		} else if ( a.betaUser ) {
    			serviceName += "_beta";
    		} 
    	}
    	String sourceFileName = DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".java";
    	String classFileName =  DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".class";
    	String className = (pathPrefix.equals("") ? serviceName : MappingUtils.createPackageName(pathPrefix) + "." + serviceName);
    	 
//    	System.err.println("in getScriptPathServiceNameAndScriptFile()");
//    	System.err.println("pathPrefix = " + pathPrefix);
//    	System.err.println("serviceName = " + serviceName);
//    	System.err.println("scriptFile = " + scriptFile.getName());
//    	System.err.println("sourceFileName = " + sourceFileName);
//    	System.err.println("classFileName = " + classFileName);
//    	System.err.println("className = " + className);
    	
    	return new Object[]{pathPrefix,serviceName,scriptFile,sourceFileName,new File(sourceFileName),className,classFileName,new File(classFileName)};
    }
    
    /**
     * Check whether Navajo script file needs to be recompiled.
     * 
     * @param scriptFile
     * @param sourceFile
     * @return
     */
    private final static boolean checkScriptRecompile(File scriptFile, File sourceFile) {
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
    
    /**
     * Check whether web service Access needs recompile.
     * Can be used by interested parties, e.g. Dispatcher.
     * 
     * @param a
     * @return
     */
    public final static boolean needsRecompile(Access a) {
    	Object [] all = getScriptPathServiceNameAndScriptFile(a);
    	File scriptFile = (File) all[2];
    	File sourceFile = (File) all[4];
    	File targetFile = (File) all[7];
    	return ( checkScriptRecompile(scriptFile, sourceFile) || checkJavaRecompile(sourceFile, targetFile) );
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

        Navajo outDoc = null;
        String scriptPath = properties.getScriptPath();
        String compilerErrors = "";
        
        NavajoClassSupplier newLoader = null;

          try {

        	Object [] all = getScriptPathServiceNameAndScriptFile(access);
        	String pathPrefix = (String) all[0];
        	String serviceName = (String) all[1];
        	File scriptFile = (File) all[2];
        	String sourceFileName = (String) all[3];
        	File sourceFile = (File) all[4];
        	String className = (String) all[5];
        	String classFileName = (String) all[6];
        	File targetFile = (File) all[7];
        	  
            newLoader = (NavajoClassLoader) loadedClasses.get(className);
         
            if (properties.isCompileScripts()) {
            	if (scriptFile.exists()) {

            		if ( checkScriptRecompile(scriptFile, sourceFile) ) {

            			synchronized (mutex1) { // Check for outdated compiled script Java source.

            				if ( checkScriptRecompile(scriptFile, sourceFile) ) {

            					com.dexels.navajo.mapping.compiler.TslCompiler tslCompiler = new
            					com.dexels.navajo.mapping.compiler.TslCompiler(properties.getClassloader());

            					try {
            						tslCompiler.compileScript(serviceName, 
            								scriptPath,
            								properties.getCompiledScriptPath(),
            								pathPrefix);
            					} catch (SystemException ex) {
            						sourceFile.delete();
            						AuditLog.log(AuditLog.AUDIT_MESSAGE_SCRIPTCOMPILER , ex.getMessage(), Level.SEVERE, access.accessID);
            						throw ex;
            					}
            				}
            			}
            		} // end of sync block.


            		if ( checkJavaRecompile(sourceFile, targetFile) ) { // Create class file

            			synchronized(mutex2) {

            				if ( checkJavaRecompile(sourceFile, targetFile) ) {

            					if (newLoader != null) {
            						loadedClasses.remove(className);
            						newLoader = null;
            					}

            					com.dexels.navajo.compiler.NavajoCompiler compiler = new com.dexels.navajo.compiler.NavajoCompiler();
            					try {
            						compiler.compile(access, properties, sourceFileName);
            						compilerErrors = compiler.errors;
            						NavajoEventRegistry.getInstance().publishEvent(new NavajoCompileScriptEvent(access.rpcName));
            					}
            					catch (Throwable t) {
            						AuditLog.log(AuditLog.AUDIT_MESSAGE_SCRIPTCOMPILER, "Could not run-time compile Java file: " + sourceFileName + " (" + t.getMessage() + "). It may be compiled already", Level.WARNING, access.accessID);
            					}
            				}
            			}
            		} // end of sync block.
            		//}

            	} else {
            		AuditLog.log(AuditLog.AUDIT_MESSAGE_SCRIPTCOMPILER, "SCRIPT FILE DOES NOT EXISTS, I WILL TRY TO LOAD THE CLASS FILE ANYWAY....", Level.WARNING, access.accessID);
            		//System.out.println("SCRIPT FILE DOES NOT EXISTS, I WILL TRY TO LOAD THE CLASS FILE ANYWAY....");
            	}
            }
            
            // Synchronized check for classloader of this script.
            newLoader = (NavajoClassLoader) loadedClasses.get(className);
            if (newLoader == null ) {
            	newLoader = new NavajoClassLoader(null, properties.getCompiledScriptPath(), 
            			( access.betaUser ? DispatcherFactory.getInstance().getNavajoConfig().getBetaClassLoader() : 
            				DispatcherFactory.getInstance().getNavajoConfig().getClassloader() ) );
            	loadedClasses.put(className, newLoader);
            }
            
            if ( newLoader == null ) {
            	throw new IllegalStateException("Could not create classloader for script: " + className);
            }
            
            // Should method getCompiledNavaScript be fully synced???
            Class cs = newLoader.getCompiledNavaScript(className);

            outDoc = NavajoFactory.getInstance().createNavajo();
            access.setOutputDoc(outDoc);
            com.dexels.navajo.mapping.CompiledScript cso = (com.dexels.navajo.mapping.CompiledScript) cs.newInstance();

            access.setCompiledScript(cso);
            cso.setClassLoader(newLoader);
            cso.run(access);

            return access.getOutputDoc();
          } catch (Throwable e) {
            if (e instanceof com.dexels.navajo.mapping.BreakEvent) {
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
            	AuditLog.log(AuditLog.AUDIT_MESSAGE_SCRIPTCOMPILER, e.getMessage() + (!compilerErrors.trim().equals("") ? (", java compile errors: " + compilerErrors) : ""), Level.SEVERE, access.accessID);
            	e.printStackTrace();
            	throw new SystemException( -1, e.getMessage() + (!compilerErrors.trim().equals("") ? (", java compile errors: " + compilerErrors) : ""), e);
            }
          }
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