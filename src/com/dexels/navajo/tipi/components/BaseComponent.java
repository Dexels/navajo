package com.dexels.navajo.tipi.components;

import nanoxml.XMLElement;
import com.dexels.navajo.tipi.*;
import java.util.*;
import java.awt.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class BaseComponent extends TipiPanel implements TipiComponent {
  BorderLayout borderLayout1 = new BorderLayout();
  public BaseComponent() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void load(XMLElement elm, TipiContext context) throws TipiException {
    String type = (String)elm.getAttribute("type","label");
    if (type.equals("label")) {
      TipiLabel tl = new TipiLabel();
      tl.setText((String)elm.getAttribute("value",""));
      add(tl,BorderLayout.CENTER);
    }
    if (type.equals("hidden")) {
      TipiLabel tl = new TipiLabel();
      tl.setText("");
      add(tl,BorderLayout.CENTER);
    }
    if (type.equals("button")) {
      TipiButton tl = new TipiButton();
      tl.setText((String)elm.getAttribute("value",""));
      add(tl,BorderLayout.CENTER);
    }
  }
  public void addComponent(TipiComponent c, TipiContext context, Map m) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent method*/
    throw new java.lang.UnsupportedOperationException("Method addComponent() not yet implemented.");
  }
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
  }

}