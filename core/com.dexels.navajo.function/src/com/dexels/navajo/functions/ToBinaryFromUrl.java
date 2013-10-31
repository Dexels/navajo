package com.dexels.navajo.functions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

public class ToBinaryFromUrl extends FunctionInterface {
  public ToBinaryFromUrl() {
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
		URL u = new URL(s);
		Binary b = new Binary(u.openStream());
		return b;
	   } catch (MalformedURLException e) {
		throw new TMLExpressionException("Bad url in function ToBinaryFromUrl: "+s);
	} catch (IOException e) {
		throw new TMLExpressionException("Error opening url in function ToBinaryFromUrl: "+s);
	}
  }

  @Override
public String usage() {
    return "ToBinaryFromUrl(String): Binary";
  }


}
