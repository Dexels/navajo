package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
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

public class File extends FunctionInterface {

  public File() {
  }

  public String remarks() {
   return "Reads a file";
  }

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
      return new Binary(data);
    } catch (Exception e) {
      e.printStackTrace();
      throw new TMLExpressionException("Could not read file: " + fileName);
    }
  }

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