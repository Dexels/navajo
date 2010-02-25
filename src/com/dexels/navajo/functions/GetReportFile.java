package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.server.DispatcherFactory;

import java.io.*;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.Operand;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class GetReportFile extends FunctionInterface {

  public GetReportFile() {
  }

  public String remarks() {
   return "Reads a report file from the script folder to a binary";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
	 String script = DispatcherFactory.getInstance().getNavajoConfig().getScriptPath();
    String reportName = (String) getOperand(0);
	 java.io.File scriptDir = new java.io.File(script); 
	 java.io.File report = new java.io.File(scriptDir, reportName+".rptdesign");
    try {
   	 Binary b = new Binary(report);
   	 return b;
    } catch (Exception e) {
      //e.printStackTrace();
      //throw new TMLExpressionException("Could not read file: " + fileName);
      return null;
    }
  }

  public String usage() {
     return "GetReportFile(reportname)";
  }


}
