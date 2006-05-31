package com.dexels.navajo.server;

import java.util.*;

import java.io.File;

import com.dexels.navajo.mapping.*;
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

    public GenericHandler() {
      if (loadedClasses == null)
        loadedClasses = new HashMap();
    }

    protected static void doClearCache() {
       loadedClasses = null;
       System.gc();
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

            String className = (pathPrefix.equals("") ? serviceName : MappingUtils.createPackageName(pathPrefix) + "." + serviceName);

            File scriptFile = new File(scriptPath + "/" + access.rpcName + ".xml");

            if (properties.isHotCompileEnabled()) {
              newLoader = (NavajoClassLoader) loadedClasses.get(className);
            }

            if (properties.compileScripts) {
            	if (scriptFile.exists()) {
            		
            		String sourceFileName = properties.getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".java";
            		File sourceFile = null;
            		
            		synchronized (mutex1) { // Check for outdated compiled script Java source.
            			sourceFile = new File(sourceFileName);
            			if (!sourceFile.exists() ||
            					(scriptFile.lastModified() > sourceFile.lastModified())) {
            				com.dexels.navajo.mapping.compiler.TslCompiler tslCompiler = new
							com.dexels.navajo.mapping.compiler.TslCompiler(properties.getClassloader());
            				try {
            					tslCompiler.compileScript(serviceName, scriptPath,
            							properties.
										getCompiledScriptPath(),
										pathPrefix);
            				}
            				catch (SystemException ex) {
            					sourceFile.delete();
            					throw ex;
            				}
            			}
            		}
            		
            		String classFileName = properties.getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".class";
            		File targetFile = null;
            		
            		synchronized(mutex2) { // Check for outdated class file.
            			targetFile = new File(classFileName);
            			
            			if (!targetFile.exists() ||
            					(sourceFile.lastModified() > targetFile.lastModified())) { // Create class file
            				
            				if (properties.isHotCompileEnabled()) {
            					if (newLoader != null) {
            						loadedClasses.remove(className);
            						newLoader = null;
            						System.gc();
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
            		
            	} else {
            		//System.out.println("SCRIPT FILE DOES NOT EXISTS, I WILL TRY TO LOAD THE CLASS FILE ANYWAY....");
            	}
            }
            
            if (newLoader == null &&  properties.isHotCompileEnabled()) {
                newLoader = new NavajoClassLoader(properties.getAdapterPath(), properties.getCompiledScriptPath());
                loadedClasses.put(className, newLoader);
            }

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

}
