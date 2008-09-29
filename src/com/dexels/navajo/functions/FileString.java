package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.server.*;

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

public class FileString extends FunctionInterface {

  public FileString() {
  }

  public String remarks() {
   return "Reads a file into a string";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

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
    	e.printStackTrace();
    	return null;
    }
  }

  public String usage() {
     return "FileString(filename)";
  }


}
