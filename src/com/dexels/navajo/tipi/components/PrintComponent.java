package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;
import java.awt.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PrintComponent extends com.dexels.navajo.tipi.TipiComponent {
  private String myPath = null;
  private String myMode = null;
  public PrintComponent() {
  }
  public void addToContainer(Component c, Object constraints) {
    throw new java.lang.UnsupportedOperationException("Method createContainer() not yet implemented.");
  }
  public void registerEvents() {
    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent abstract method*/
  }
  public Container createContainer() {
    return null;
  }
  public void setComponentValue(String name, Object object) {
    if (name.equals("path")) {
    }
    if (name.equals("mode")) {
    }
    System.err.println("Ignored for now");
  }
  public Object getComponentValue(String name) {
    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
    return super.getComponentValue(name);
  }
  protected void performComponentMethod(String name, XMLElement invocation, TipiComponentMethod compMeth) {

    if (name.equals("print")) {
      System.err.println("INVOCATION: "+invocation.toString());
      TipiMethodParameter path = compMeth.getParameter("printpath");
      System.err.println(">>> "+path.getValue());
      System.err.println(">> "+path.getName());
//      Tipi t = myContext.getTipiByPath(myPath);
//      TipiValue path = invocation.getC getParameter("printpath");
      Message m = myContext.getMessageByPath(path.getValue());
      System.err.println("MESSAGE *********************8");
      System.err.println("Name: "+m.getName());
      System.err.println("END OF MESSAGE *********************8");
    }
  }

}