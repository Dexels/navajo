package com.dexels.navajo.tipi.components;

import nanoxml.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class BaseComponent extends TipiComponent {
  BorderLayout borderLayout1 = new BorderLayout();



  public BaseComponent() {
    setContainer(new TipiPanel());
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws TipiException {
    String type = (String)elm.getAttribute("type","label");
    if (type.equals("label")) {
      TipiLabel tl = new TipiLabel();
      tl.setText((String)elm.getAttribute("value",""));
      getContainer().add(tl.getContainer(),BorderLayout.CENTER);
    }
    if (type.equals("hidden")) {
      TipiLabel tl = new TipiLabel();
      tl.setText("");
      getContainer().add(tl.getContainer(),BorderLayout.CENTER);
    }
    if (type.equals("button")) {
      TipiButton tl = new TipiButton();
      tl.setText((String)elm.getAttribute("value",""));
      getContainer().add(tl.getContainer(),BorderLayout.CENTER);
    }
  }

  private void jbInit() throws Exception {
    getContainer().setLayout(borderLayout1);
  }

}