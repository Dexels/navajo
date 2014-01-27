package com.dexels.navajo.functions;

import java.io.OutputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.GenericHandler;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 * @deprecated But still used for the financial forms, I think
 */

public class ExecuteScript extends FunctionInterface {

  @Override
public String remarks() {
    return "ExecuteScript is used execute a Navajo script, the result is a Binary (xml) object";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    GenericHandler gh = new GenericHandler(DispatcherFactory.getInstance().getNavajoConfig());
    String script = (String) getOperand(0);
    Access access = new Access(1, 1, 1, "ANONYMOUS", script, "", "", "", false, null);
    access.setInDoc(inMessage);
    inMessage.getHeader().setRequestId("");
    gh.setInput(access);
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
    	throw new TMLExpressionException(this, "Error while trying to execute script: " + script);
    }
  }

  @Override
public String usage() {
    return "ExecuteScript(<script>)";
  }

  public static void main(String [] args) {

  }

}
