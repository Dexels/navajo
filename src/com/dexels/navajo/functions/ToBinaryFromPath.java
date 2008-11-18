package com.dexels.navajo.functions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.types.Binary;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class ToBinaryFromPath extends FunctionInterface {
  public ToBinaryFromPath() {
  }

  public String remarks() {
    return "Load a binary from a URL";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
   Object o = getOperand(0);
   String s = (String)o;
 	try {
 		java.io.File u = new java.io.File(s);
		Binary b = new Binary(u);
		return b;
	   } catch (MalformedURLException e) {
		e.printStackTrace();
		throw new TMLExpressionException("Bad url in function ToBinaryFromPath: "+s);
	} catch (IOException e) {
		e.printStackTrace();
		throw new TMLExpressionException("Error opening url in function ToBinaryFromPath: "+s);
	}
  }

  public String usage() {
    return "ToBinaryFromPath(String): Binary";
  }


}
