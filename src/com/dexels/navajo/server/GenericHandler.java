package com.dexels.navajo.server;

import com.dexels.navajo.document.*;
import java.util.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.util.*;
import javax.naming.*;
/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class GenericHandler implements ServiceHandler {

  public GenericHandler() {
  }

  public Navajo doService(Navajo doc, Access access, Parameters parms, ResourceBundle properties)
        throws NavajoException, UserException, SystemException {
     //TODO: implement this com.dexels.navajo.server.NavajoServerServlet abstract method
    Navajo outDoc = null;

    String scriptPath = properties.getString("script_path");
    String classPath = properties.getString("class_path");
    Util.debugLog(this, "using script_path: " + scriptPath);
    Util.debugLog(this, "using script_path: " + classPath);
    Util.debugLog(this, "Access: " + access);
    Util.debugLog(this, "Parameters: " + parms);
    Util.debugLog(this, "Navajo: " + doc);

    XmlMapperInterpreter mi = null;
    Context context = null;
    try {
      context = new InitialContext();
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      mi = new XmlMapperInterpreter(scriptPath, access.rpcName, doc, parms, context, access, classPath);
    } catch (java.io.IOException ioe) {
      throw new SystemException(-1, ioe.getMessage());
    } catch (org.xml.sax.SAXException saxe) {
      throw new SystemException(-1, saxe.getMessage());
    }
    Util.debugLog(this, "Created MapperInterpreter version 10.0");
    try {
      Util.debugLog(this, "Before calling interpret() version 10.0");
      long start = System.currentTimeMillis();
      outDoc = mi.interpret(access.rpcName);
      long end = System.currentTimeMillis();
      Util.debugLog(this, "Finished interpret(). Interpretation took " + (end - start)/1000.0 + " secs.");
    } catch (MappingException me) {
      Util.debugLog("MappingException occured: " + me.getMessage());
      System.gc();
      throw new SystemException(-1, me.getMessage());
    } catch (MappableException mme) {
      Util.debugLog("MappableException occured: " + mme.getMessage());
      System.gc();
      throw new SystemException(-1, "Error in Mappable object: " + mme.getMessage());
    }
    return outDoc;
  }
}