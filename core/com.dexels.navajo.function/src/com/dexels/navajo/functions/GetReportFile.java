package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.server.DispatcherFactory;


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

  @Override
public String remarks() {
   return "Reads a report file from the script folder to a binary";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
	 String script = DispatcherFactory.getInstance().getNavajoConfig().getScriptPath();
    String reportName = (String) getOperand(0);
	 java.io.File scriptDir = new java.io.File(script); 
	 java.io.File report = new java.io.File(scriptDir, reportName+".rptdesign");
    try {
   	 Binary b = new Binary(report);
   	 return b;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
public String usage() {
     return "GetReportFile(reportname)";
  }


}
