package com.dexels.navajo.functions;

import java.io.IOException;
import java.net.MalformedURLException;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

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

  @Override
public String remarks() {
    return "Load a binary from a URL";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
   Object o = getOperand(0);
   String s = (String)o;
 	try {
 		java.io.File u = new java.io.File(s);
		Binary b = new Binary(u);
		return b;
	 } catch (MalformedURLException e) {
		throw new TMLExpressionException("Bad url in function ToBinaryFromPath: "+s);
	} catch (IOException e) {
		throw new TMLExpressionException("Error opening url in function ToBinaryFromPath: "+s);
	}
  }

  @Override
public String usage() {
    return "ToBinaryFromPath(String): Binary";
  }


}
