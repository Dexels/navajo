package com.dexels.navajo.tipi;
import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TipiComponentMethod {

  private Map myArgs = new HashMap();

  public TipiComponentMethod() {
  }

  public void load(XMLElement x) {
    if (!x.getName().equals("method")) {
      throw new IllegalArgumentException("Method components are supposed to be called 'method'");
    }

    Vector v = x.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement child = (XMLElement)v.elementAt(i);
      String argName = child.getStringAttribute("name");
      TipiValue tv = new TipiValue();
      tv.load(child);
      myArgs.put(argName,tv);
    }

  }

  public void performMethod(TipiComponent source, TipiComponent current, XMLElement invocation) {
    System.err.println("Unimplemented method!");
  }

}