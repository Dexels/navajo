package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiLabel extends TipiComponent {
  private JLabel myLabel = new JLabel();
  public TipiLabel() {
    setContainer(myLabel);
  }
  public void addTipiEvent(TipiEvent te) {
  }

  public void addComponent(TipiComponent c, TipiContext context, Map props) {
  }

  public void load(XMLElement e, XMLElement instance, TipiContext tc) {
    myLabel.setText((String)e.getAttribute("value"));
  }

  public void setText(String s) {
    myLabel.setText(s);
  }
}