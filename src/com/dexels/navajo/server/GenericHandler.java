package com.dexels.navajo.server;

import java.util.*;
import javax.naming.*;
import java.io.File;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.document.*;
import com.dexels.navajo.logger.*;
import java.io.FileInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import com.dexels.navajo.parser.Condition;
import java.io.*;

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
 */

public final class GenericHandler extends ServiceHandler {

    private static String adapterPath = "";
    private static HashMap loadedClasses = null;

    private static Object mutex1 = new Object();
    private static Object mutex2 = new Object();

    private final static NavajoLogger logger = NavajoConfig.getNavajoLogger(GenericHandler.class);

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
     * Check condition/validation rules inside the script.
     * @param f
     * @return
     * @throws Exception
     */
    private final ConditionData [] checkValidations(File f) throws Exception {
      Document d = null;
      try {
        FileInputStream fis = new FileInputStream(f);
        d = com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils.createDocument(fis, false);
        fis.close();
      }
      catch (Throwable ex) {
        throw new UserException(-1, "Invalid or non existing script when trying to read validations: " + access.rpcName);
      }

      NodeList list = d.getElementsByTagName("validations");
      boolean valid = true;
      ArrayList conditions = new ArrayList();
      for (int i = 0; i < list.getLength(); i++) {
        NodeList rules = list.item(i).getChildNodes();
        for (int j = 0; j < rules.getLength(); j++) {
          if (rules.item(j).getNodeName().equals("check")) {
            Element rule = (Element) rules.item(j);
            String code = rule.getAttribute("code");
            String value = rule.getAttribute("value");
            String condition = rule.getAttribute("condition");
            if (value.equals("")) {
              value = rule.getFirstChild().getNodeValue();
            }
            if (rule.equals("")) {
              throw new UserException(-1, "Validation syntax error: code attribute missing or empty");
            }
            if (value.equals("")) {
              throw new UserException(-1, "Validation syntax error: value attribute missing or empty");
            }
            // Check if condition evaluates to true, for evaluating validation ;)
            boolean check = (condition.equals("") ? true : Condition.evaluate(condition, requestDocument) );
            if (check) {
              ConditionData cd = new ConditionData();
              cd.id = Integer.parseInt(code);
              cd.condition = value;
              conditions.add(cd);
            }
          }
        }
      }
      if (conditions.size() > 0) {
        ConditionData [] cds = new ConditionData[conditions.size()];
        cds = (ConditionData []) conditions.toArray(cds);
        return cds;
      } else {
        return null;
      }
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

        //System.err.println("loadClasses size is " + loadedClasses.size());

        Navajo outDoc = null;
        String scriptPath = properties.getScriptPath();

        NavajoClassLoader newLoader = (properties.isHotCompileEnabled() ? null : properties.getClassloader());

        //System.err.println("IN DOSERVICE(), newLoader = " + newLoader);

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

            String className = (pathPrefix.equals("") ? serviceName : MappingUtils.createPackageName(pathPrefix) + "." + serviceName);

            File scriptFile = new File(scriptPath + "/" + access.rpcName + ".xml");

            // Check validations block (if present) and generate ConditionsError message if neccessary.
            try{
              ConditionData[] conditions = checkValidations(scriptFile);
              if (conditions != null) {
                Navajo outMessage = NavajoFactory.getInstance().createNavajo();
                Message[] failed = Dispatcher.checkConditions(conditions,
                    requestDocument, outMessage);
                if (failed != null) {
                  Message msg = NavajoFactory.getInstance().createMessage(
                      outMessage, "ConditionErrors");
                  outMessage.addMessage(msg);
                  msg.setType(Message.MSG_TYPE_ARRAY);
                  for (int i = 0; i < failed.length; i++) {
                    msg.addMessage( (Message) failed[i]);
                  }
                  return outMessage;
                }
              }
            }catch(UserException e){
              System.err.println(e.getMessage());
            }

            if (properties.isHotCompileEnabled()) {
              newLoader = (NavajoClassLoader) loadedClasses.get(className);
            }

            if (scriptFile.exists()) {

                String sourceFileName = properties.getCompiledScriptPath() + "/" + pathPrefix + serviceName + ".java";
                File sourceFile = null;

                synchronized (mutex1) { // Check for outdated compiled script Java source.
                  sourceFile = new File(sourceFileName);
                  if (!sourceFile.exists() ||
                      (scriptFile.lastModified() > sourceFile.lastModified())) {
                    com.dexels.navajo.mapping.compiler.TslCompiler tslCompiler = new
                        com.dexels.navajo.mapping.compiler.TslCompiler(properties.getClassloader());
                    tslCompiler.compileScript(serviceName, scriptPath,
                                              properties.getCompiledScriptPath(),
                                              pathPrefix);
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
                    }
                    catch (Throwable t) {
                      t.printStackTrace();
                    }
                  }
                }

            } else {
              //System.out.println("SCRIPT FILE DOES NOT EXISTS, I WILL TRY TO LOAD THE CLASS FILE ANYWAY....");
            }

            if (newLoader == null &&  properties.isHotCompileEnabled()) {
                newLoader = new NavajoClassLoader(properties.getAdapterPath(), properties.getCompiledScriptPath());
                //System.err.println("CREATE NEW CLASSLOADER " + newLoader + " FOR CLASS " + className);
                loadedClasses.put(className, newLoader);
            }

            long start = System.currentTimeMillis();
            Class cs = newLoader.getCompiledNavaScript(className);
            //System.err.println("GOT COMPILED CLASS FROM GETCOMPILEDNAVASCRIPT()....");
            outDoc = NavajoFactory.getInstance().createNavajo();
            access.setOutputDoc(outDoc);
            com.dexels.navajo.mapping.CompiledScript cso = (com.dexels.navajo.mapping.CompiledScript) cs.newInstance();
            //System.err.println("CREATE COMPILED SCRIPT OBJECT: " + cso + ", USING CLASSLOADER: " + newLoader);
            cso.setClassLoader(newLoader);
            cso.execute(parms, requestDocument, access, properties);
            //System.err.println("AFTER EXECUTE() CALL, EXECUTION TIME: " + (System.currentTimeMillis() - start)/1000.0 + " secs.");
            return access.getOutputDoc();
          } catch (Exception e) {
            if (e instanceof com.dexels.navajo.mapping.BreakEvent) {
              return outDoc;
            }
            else if (e instanceof com.dexels.navajo.server.ConditionErrorException) {
              //System.err.println("IN GENERICHANDLER, FOUND CONDITIONERROR!!!");
              return ( (com.dexels.navajo.server.ConditionErrorException) e).getNavajo();
            }
            else if (e instanceof AuthorizationException) {
              System.err.println("CAUGHT AUTHORIZATION ERROR IN GENERICHANDLER!!!!!!!!!!!!!!!!!!");
              throw (AuthorizationException) e;
            }
            else {
              e.printStackTrace();
              throw new SystemException( -1, e.getMessage(), e);
            }
          }
        } else {
          XmlMapperInterpreter mi = null;
          try {
              mi = new XmlMapperInterpreter(access.rpcName, requestDocument, parms, properties, access);
          } catch (java.io.IOException ioe) {
              logger.log(NavajoPriority.ERROR, "IO Exception", ioe);
              throw new SystemException(-1, ioe.getMessage(), ioe);
          } catch (org.xml.sax.SAXException saxe) {
              logger.log(NavajoPriority.ERROR, "XML parse exception", saxe);
              throw new SystemException(-1, saxe.getMessage(), saxe);
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
              throw new SystemException(-1, me.getMessage(), me);
          } catch (MappableException mme) {
              //Util.debugLog("MappableException occured: " + mme.getMessage());
              System.gc();
              throw new SystemException(-1, "Error in Mappable object: " + mme.getMessage(), mme);
          }
          return outDoc;
        }
    }

}
