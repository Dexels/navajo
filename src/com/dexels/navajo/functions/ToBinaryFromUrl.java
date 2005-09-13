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

public class ToBinaryFromUrl extends FunctionInterface {
  public ToBinaryFromUrl() {
  }

  public String remarks() {
    return "Load a binary from a URL";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
   Object o = getOperand(0);
   String s = (String)o;
 	try {
		URL u = new URL(s);
		Binary b = new Binary(u.openStream());
		return b;
	   } catch (MalformedURLException e) {
		e.printStackTrace();
		throw new TMLExpressionException("Bad url in function ToBinaryFromUrl: "+s);
	} catch (IOException e) {
		e.printStackTrace();
		throw new TMLExpressionException("Error opening url in function ToBinaryFromUrl: "+s);
	}
  }

  public String usage() {
    return "ToBinaryFromUrl(String): Binary";
  }


}
