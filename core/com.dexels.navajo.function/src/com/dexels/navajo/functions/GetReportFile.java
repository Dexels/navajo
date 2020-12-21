/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
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

	
	
	private final static Logger logger = LoggerFactory.getLogger(GetReportFile.class);

  public GetReportFile() {
  }

  @Override
public String remarks() {
   return "Reads a report file from the script folder to a binary";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
	 String script = DispatcherFactory.getInstance().getNavajoConfig().getScriptPath();
    String reportName = getStringOperand(0);
	 java.io.File scriptDir = new java.io.File(script); 
	 java.io.File report = new java.io.File(scriptDir, reportName+".rptdesign");
    try {
   	 Binary b = new Binary(report);
   	 return b;
    } catch (Exception e) {
    	logger.error("File issue: {}",report.getAbsolutePath());
    	logger.error("Error resolving report: "+reportName+" from script path: "+script, e);
    	throw new TMLExpressionException("Wrapping: "+script, e);
    }
  }

  @Override
public String usage() {
     return "GetReportFile(reportname)";
  }


}
