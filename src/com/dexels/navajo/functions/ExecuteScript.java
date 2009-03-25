package com.dexels.navajo.functions;

import java.io.*;
import java.io.File;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class ExecuteScript extends FunctionInterface {

  public String remarks() {
    return "ExecuteScript is used execute a Navajo script, the result is a Binary (xml) object";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    GenericHandler gh = new GenericHandler();
    String script = (String) getOperand(0);
    Access access = new Access(1, 1, 1, "ANONYMOUS", script, "", "", "", false, null);
    access.setInDoc(inMessage);
    inMessage.getHeader().setRequestId("");
    gh.setInput(this.inMessage, access, null);
    Navajo result = null;
    try {
      result = gh.doService();

//      java.io.ByteArrayOutputStream byteArray = new java.io.ByteArrayOutputStream();
      Binary bbb = new Binary();
      OutputStream os = bbb.getOutputStream();
      result.write(os);
      os.flush();
      os.close();
//      // TODO: REMOVE AGAIN
//      File f = File.createTempFile("executeScriptDebug",".xml");
//      FileWriter fw = new FileWriter(f);
//      result.write(fw);
//      fw.flush();
//      fw.close();
      return bbb;
      }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new TMLExpressionException(this, "Error while trying to execute script: " + script);
    }
  }

  public String usage() {
    return "ExecuteScript(<script>)";
  }

  public static void main(String [] args) {

  }

}
