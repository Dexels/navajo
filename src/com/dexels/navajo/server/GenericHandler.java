package com.dexels.navajo.server;

import java.util.*;
import javax.naming.*;
import java.io.File;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.document.*;
import com.dexels.navajo.logger.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public class GenericHandler extends ServiceHandler {

    private static String adapterPath = "";
    private static HashMap loadedClasses = null;

    private final static NavajoLogger logger = NavajoConfig.getNavajoLogger(GenericHandler.class); //Logger.getLogger( GenericHandler.class );

    public GenericHandler() {
      if (loadedClasses == null)
        loadedClasses = new HashMap();
    }

//    public String getAdapterPath() {
//        return this.adapterPath;
//    }

    protected static void doClearCache() {
       loadedClasses = new HashMap();
    }

    public Navajo doService()
            throws NavajoException, UserException, SystemException {
        // TODO: implement this com.dexels.navajo.server.NavajoServerServlet abstract method

        Navajo outDoc = null;
        String scriptPath = properties.getScriptPath();

        NavajoClassLoader newLoader = (properties.isHotCompileEnabled() ? null : properties.getClassloader());

        if (properties.compileScripts) {
          try {

            // Strip any paths definitions that are present in script name
            int strip = access.rpcName.lastIndexOf("/");
            String pathPrefix = "";
            String serviceName = access.rpcName;
            if (strip != -1) {
              serviceName = access.rpcName.substring(strip+1);
              pathPrefix = access.rpcName.substring(0, strip) + "/";
            }
            //System.err.println("SERVICENAME =" + serviceName + ", PATHPREFIX = " + pathPrefix);

            String className = (pathPrefix.equals("") ? serviceName : MappingUtils.createPackageName(pathPrefix) + "." + serviceName);

            File scriptFile = new File(scriptPath + "/" + access.rpcName + ".xml");

            if (properties.isHotCompileEnabled())
              newLoader = (NavajoClassLoader) loadedClasses.get(className);

            if (scriptFile.exists()) {
                //System.out.println("SCRIPT FILE TIMESTAMP: " + scriptFile.lastModified());

                String sourceFileName = properties.getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".java";
                File sourceFile = new File(sourceFileName);

                if (!sourceFile.exists() || (scriptFile.lastModified() > sourceFile.lastModified())) {
                  //System.out.println("CREATING JAVA FILE");
                  com.dexels.navajo.mapping.compiler.TslCompiler tslCompiler = new com.dexels.navajo.mapping.compiler.TslCompiler(properties.getClassloader());
                  tslCompiler.compileScript(serviceName, scriptPath, properties.getCompiledScriptPath(), pathPrefix);
                }

                File targetFile = new File(properties.getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".class");

                if (!targetFile.exists() || (sourceFile.lastModified() > targetFile.lastModified())) { // Create class file
                  //System.out.println("CLASS FILE DOES NOT EXIST, COMPILE JAVA...");
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
                  } catch (Throwable t) {
                    t.printStackTrace();
                  }
                }

            } else {
              //System.out.println("SCRIPT FILE DOES NOT EXISTS, I WILL TRY TO LOAD THE CLASS FILE ANYWAY....");
            }

            if (newLoader == null &&  properties.isHotCompileEnabled()) {
                newLoader = new NavajoClassLoader(properties.getAdapterPath(), properties.getCompiledScriptPath());
                loadedClasses.put(className, newLoader);
            }

            long start = System.currentTimeMillis();
            Class cs = newLoader.getCompiledNavaScript(className);
            outDoc = NavajoFactory.getInstance().createNavajo();
            access.setOutputDoc(outDoc);
            com.dexels.navajo.mapping.CompiledScript cso = (com.dexels.navajo.mapping.CompiledScript) cs.newInstance();
            //System.err.println("CREATE COMPILED SCRIPT OBJECT: " + cso);
            cso.setClassLoader(newLoader);
            cso.execute(parms, requestDocument, access, properties);
            //System.err.println("AFTER EXECUTE() CALL, EXECUTION TIME: " + (System.currentTimeMillis() - start)/1000.0 + " secs.");
            return access.getOutputDoc();
          } catch (Exception e) {
            if (e instanceof com.dexels.navajo.mapping.BreakEvent) {
              return outDoc;
            }
            else if (e instanceof com.dexels.navajo.server.ConditionErrorException) {
              System.err.println("IN GENERICHANDLER, FOUND CONDITIONERROR!!!");
              return ( (com.dexels.navajo.server.ConditionErrorException) e).
                  getNavajo();
            }
            else {
              e.printStackTrace();
              throw new SystemException( -1, e.getMessage());
            }
          }
        } else {
          XmlMapperInterpreter mi = null;
          try {
              mi = new XmlMapperInterpreter(access.rpcName, requestDocument, parms, properties, access);
          } catch (java.io.IOException ioe) {
              logger.log(NavajoPriority.ERROR, "IO Exception", ioe);
              throw new SystemException(-1, ioe.getMessage());
          } catch (org.xml.sax.SAXException saxe) {
              logger.log(NavajoPriority.ERROR, "XML parse exception", saxe);
              throw new SystemException(-1, saxe.getMessage());
          }

          Util.debugLog(this, "Created MapperInterpreter version 10.0");
          try {
              Util.debugLog(this, "Before calling interpret() version 10.0");
              // long start = System.currentTimeMillis();
              outDoc = mi.interpret(access.rpcName);
              // long end = System.currentTimeMillis();
              // Util.debugLog(this, "Finished interpret(). Interpretation took " + (end - start)/1000.0 + " secs.");
          } catch (MappingException me) {
              //Util.debugLog("MappingException occured: " + me.getMessage());
              System.gc();
              throw new SystemException(-1, me.getMessage());
          } catch (MappableException mme) {
              //Util.debugLog("MappableException occured: " + mme.getMessage());
              System.gc();
              throw new SystemException(-1, "Error in Mappable object: " + mme.getMessage());
          }
          return outDoc;
        }
    }

}
