package com.dexels.navajo.server;

import java.util.*;
import javax.naming.*;
import java.io.File;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.document.*;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class GenericHandler extends ServiceHandler {

    private static String adapterPath = "";
    private static HashMap loadedClasses = null;

    private final static Logger logger = Logger.getLogger( GenericHandler.class );

    public GenericHandler() {
      if (loadedClasses == null)
        loadedClasses = new HashMap();
    }

//    public String getAdapterPath() {
//        return this.adapterPath;
//    }

    public Navajo doService()
            throws NavajoException, UserException, SystemException {
        // TODO: implement this com.dexels.navajo.server.NavajoServerServlet abstract method

        Navajo outDoc = null;
        String scriptPath = properties.getScriptPath();

        if (properties.compileScripts) {
          try {
            File scriptFile = new File(scriptPath + "/" + access.rpcName + ".xsl");
            System.out.println("SCRIPT FILE TIMESTAMP: " + scriptFile.lastModified());

            // Strip any paths definitions that are present in script name
            int strip = access.rpcName.lastIndexOf("/");
            String pathPrefix = "";
            String serviceName = access.rpcName;
            if (strip != -1) {
              serviceName = access.rpcName.substring(strip+1);
              pathPrefix = access.rpcName.substring(0, strip) + "/";
            }
            System.out.println("SERVICENAME =" + serviceName + ", PATHPREFIX = " + pathPrefix);

            String sourceFileName =properties.getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".java";
            File sourceFile = new File(sourceFileName);

            if (!sourceFile.exists() || (scriptFile.lastModified() > sourceFile.lastModified())) {
              System.out.println("CREATING JAVA FILE");
              com.dexels.navajo.mapping.compiler.TslCompiler tslCompiler = new com.dexels.navajo.mapping.compiler.TslCompiler(properties.getClassloader());
              tslCompiler.compileScript(serviceName, scriptPath+"/"+pathPrefix, properties.getCompiledScriptPath()+"/"+pathPrefix);
            }

            File targetFile = new File(properties.getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".class");

            NavajoClassLoader newLoader = (NavajoClassLoader) loadedClasses.get(serviceName);

            if (!targetFile.exists() || (sourceFile.lastModified() > targetFile.lastModified())) { // Create class file
              System.out.println("CLASS FILE DOES NOT EXIST, COMPILE JAVA...");
              if (newLoader != null) {
                  loadedClasses.remove(serviceName);
                  newLoader = null;
                  System.gc();
              }

              com.dexels.navajo.compiler.NavajoCompiler compiler = new com.dexels.navajo.compiler.NavajoCompiler();

              try {
                compiler.compile(access, properties, sourceFileName, pathPrefix);
              } catch (Throwable t) {
                t.printStackTrace();
              }
            }

            if (newLoader == null) {
              newLoader = new NavajoClassLoader(properties.getAdapterPath(), properties.getCompiledScriptPath()+"/"+pathPrefix);
              loadedClasses.put(serviceName, newLoader);
            }

            long start = System.currentTimeMillis();
            Class cs = newLoader.getCompiledNavaScript(serviceName);
            outDoc = NavajoFactory.getInstance().createNavajo();
            access.setOutputDoc(outDoc);
            com.dexels.navajo.mapping.CompiledScript cso = (com.dexels.navajo.mapping.CompiledScript) cs.newInstance();
            System.out.println("CREATE COMPILED SCRIPT OBJECT: " + cso);
            cso.setClassLoader(newLoader);
            cso.execute(parms, requestDocument, access, properties);
            System.out.println("AFTER EXECUTE() CALL, EXECUTION TIME: " + (System.currentTimeMillis() - start)/1000.0 + " secs.");
            return access.getOutputDoc();
          } catch (Exception e) {
            if (e instanceof com.dexels.navajo.mapping.BreakEvent) {
              return outDoc;
            } else
              e.printStackTrace();
              throw new SystemException(-1, e.getMessage());
          }
        } else {
          XmlMapperInterpreter mi = null;
          try {
              mi = new XmlMapperInterpreter(access.rpcName, requestDocument, parms, properties, access);
          } catch (java.io.IOException ioe) {
              logger.log(Priority.ERROR, "IO Exception", ioe);
              throw new SystemException(-1, ioe.getMessage());
          } catch (org.xml.sax.SAXException saxe) {
              logger.log(Priority.ERROR, "XML parse exception", saxe);
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
