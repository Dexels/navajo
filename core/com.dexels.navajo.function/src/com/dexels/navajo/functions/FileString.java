/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.io.BufferedReader;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.server.DispatcherFactory;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class FileString extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(FileString.class);
	
  public FileString() {
  }

  @Override
public String remarks() {
   return "Reads a file into a string";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

    String fileName = (String) getOperand(0);

    try {
    	 //gh.setInput(this.inMessage, access, null, Dispatcher.getInstance().getNavajoConfig());
    String path = DispatcherFactory.getInstance().getNavajoConfig().getScriptPath();
      java.io.File f = new java.io.File(new java.io.File(path),fileName);
      BufferedReader fis = new BufferedReader(new FileReader(f));
      StringBuffer sb = new StringBuffer();
      String line;

      do {
    	  line = fis.readLine();
    	  if(line!=null) {
        	  sb.append(line);
    	  }
    	  sb.append("\n");
      } while(line!=null);
      fis.close();
      return sb.toString();
    } catch (Exception e) {
    	logger.error("Error: ", e);
    	return null;
    }
  }


}
