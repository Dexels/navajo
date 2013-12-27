package com.dexels.navajo.functions;

import java.io.FileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.FunctionInterface;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class File extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory.getLogger(File.class);
	
  public File() {
  }

  @Override
public String remarks() {
   return "Reads a file";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

    String fileName = (String) getOperand(0);

    try {
      java.io.File f = new java.io.File(fileName);
      int length = (int) f.length();
      byte[] data = new byte[length];
      FileInputStream fis = new FileInputStream(f);
      int read = 0;
      int index = 0;
      while ( (read = fis.read()) > -1) {
        data[index++] = (byte) read;
      }
      fis.close();
      return new Binary(data);
    } catch (Exception e) {
    	logger.error("Error: ", e);
      return null;
    }
  }

  @Override
public String usage() {
     return "File(filename)";
  }

  public static void main(String [] args) throws Exception {

    String expression = "File('/home/arjen/teams.pdf')";
    Operand o = Expression.evaluate(expression, null);
    System.err.println("o = " + o + ", type = " + o.type);
    System.err.println("value = " + o.value);
  }
}
