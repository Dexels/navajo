package com.dexels.navajo.server;

import java.util.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.document.*;
import com.dexels.navajo.logger.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
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

    private static HashMap loadedClasses = null;

    private static Object mutex1 = new Object();
    private static Object mutex2 = new Object();
    private static Object mutex3 = new Object();

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

    protected static void doClearCache() {
       loadedClasses = null;
       //System.gc();
       loadedClasses = new HashMap();
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
    public final Navajo doService() throws NavajoException, UserException, SystemException, AuthorizationException {

        Navajo outDoc = null;
        String scriptPath = properties.getScriptPath();
        String compilerErrors = "";
        
        NavajoClassSupplier newLoader = (properties.isHotCompileEnabled() ? null : properties.getClassloader());

          try {

            // Strip any paths definitions that are present in script name
            int strip = access.rpcName.lastIndexOf("/");
            String pathPrefix = "";
            String serviceName = access.rpcName;
            if (strip != -1) {
              serviceName = access.rpcName.substring(strip+1);
              pathPrefix = access.rpcName.substring(0, strip) + "/";
            }
      
            File scriptFile = new File(scriptPath + "/" + access.rpcName + ( access.betaUser ? "_beta" : "" ) + ".xml" );
            
            if ( access.betaUser && !scriptFile.exists() ) {
            	// Try normal webservice.
            	scriptFile = new File(scriptPath + "/" + access.rpcName + ".xml" );
            } else if ( access.betaUser ) {
            	serviceName += "_beta";
            } 
            
            String className = (pathPrefix.equals("") ? serviceName : MappingUtils.createPackageName(pathPrefix) + "." + serviceName);

            //System.err.println("scriptFile is " + scriptFile.getName());
            
            if (properties.isHotCompileEnabled()) {
              newLoader = (NavajoClassLoader) loadedClasses.get(className);
            }

            if (properties.compileScripts) {
            	if (scriptFile.exists()) {
            		
            		String sourceFileName = properties.getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".java";
            		File sourceFile = null;
            	
            		
            		// Use if-synchronized-if construction.
            		// 12/11/2006: If-sync-if does NOT work here for obvious reasons. The second
            		// if can evaluate to true even before the entire work done in the
            		// sync block is finished.
            		
            		//synchronized (mutex1) {
            			
            			sourceFile = new File(sourceFileName);
            			
            			if (!sourceFile.exists() || (scriptFile.lastModified() > sourceFile.lastModified()) || sourceFile.length() == 0 ) {

            				synchronized (mutex1) { // Check for outdated compiled script Java source.

            				if (!sourceFile.exists() || (scriptFile.lastModified() > sourceFile.lastModified()) || sourceFile.length() == 0  ) {
            					
            					com.dexels.navajo.mapping.compiler.TslCompiler tslCompiler = new
            					com.dexels.navajo.mapping.compiler.TslCompiler(properties.getClassloader());
            					
            					try {
            						if ( MapMetaData.isMetaScript(serviceName, scriptPath, pathPrefix) ) {
            							MapMetaData mmd = MapMetaData.getInstance();
            							String intermed = mmd.parse(scriptPath + "/" + pathPrefix + "/" + serviceName + ".xml");
            							tslCompiler.compileScript(new ByteArrayInputStream(intermed.getBytes()), pathPrefix, serviceName, 
            									scriptPath, properties.getCompiledScriptPath() );
            						} else {
            							tslCompiler.compileScript(serviceName, scriptPath,
            									properties.
            									getCompiledScriptPath(),
            									pathPrefix);
            						}
            					} catch (SystemException ex) {
            						sourceFile.delete();
            						throw ex;
            					}
            				}
            			}
            		} // end of sync block.
            		
            		String classFileName = properties.getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".class";
            		File targetFile = null;
            		
//            		// 12/11/2006: If-sync-if does NOT work here for obvious reasons. The second
            		// if can evaluate to true even before the entire work done in the
            		// sync block is finished.
            		//synchronized(mutex2) { // Check for outdated class file.
            			targetFile = new File(classFileName);
            			
            			if (!targetFile.exists() || (sourceFile.lastModified() > targetFile.lastModified())) { // Create class file

            				synchronized(mutex2) {
            					
            					if (!targetFile.exists() || (sourceFile.lastModified() > targetFile.lastModified())) {
            						if (properties.isHotCompileEnabled()) {
            							if (newLoader != null) {
            								loadedClasses.remove(className);
            								newLoader = null;
            								//System.gc();
            							}
            						}
            						com.dexels.navajo.compiler.NavajoCompiler compiler = new com.dexels.navajo.compiler.NavajoCompiler();
            						try {
            							compiler.compile(access, properties, sourceFileName);
            							compilerErrors = compiler.errors;
            						}
            						catch (Throwable t) {
            							t.printStackTrace();
            							throw new UserException(-1, "Could not compile Java file: " + sourceFileName + " (" + t.getMessage() + ")");
            						}
            					}
            				}
            			} // end of sync block.
            		//}
            		
            	} else {
            		//System.out.println("SCRIPT FILE DOES NOT EXISTS, I WILL TRY TO LOAD THE CLASS FILE ANYWAY....");
            	}
            }
            
            // Synchronized check for classloader of this script.
            //synchronized (mutex3) {
            newLoader = (NavajoClassLoader) loadedClasses.get(className);
            if (newLoader == null &&  properties.isHotCompileEnabled()) {
            	newLoader = new NavajoClassLoader(null, properties.getCompiledScriptPath(), 
            			( access.betaUser ? NavajoConfig.getInstance().getBetaClassLoader() : 
            				NavajoConfig.getInstance().adapterClassloader) );
            	loadedClasses.put(className, newLoader);
            }
            //}
            
            if ( newLoader == null ) {
            	return null;
            }
            
            // Should method getCompiledNavaScript be fully synced???
            Class cs = newLoader.getCompiledNavaScript(className);

            outDoc = NavajoFactory.getInstance().createNavajo();
            access.setOutputDoc(outDoc);
            com.dexels.navajo.mapping.CompiledScript cso = (com.dexels.navajo.mapping.CompiledScript) cs.newInstance();

            access.setCompiledScript(cso);
            cso.setClassLoader(newLoader);
            cso.run(parms, requestDocument, access, properties);

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