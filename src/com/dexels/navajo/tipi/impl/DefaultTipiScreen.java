package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiScreen extends TipiPanel implements TipiScreen{

  public DefaultTipiScreen() {
    //setBackground(Color.darkGray);
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    String elmName = elm.getName();
    if(!elmName.equals("screen")){
      throw new TipiException("Screen node not found!, found " + elmName + " instead.");
    }
  }

  public void addComponent(TipiComponent c, TipiContext context, Map td){
    this.add((JComponent)c, td);
  }

  public void addProperty(String name, TipiComponent comp, TipiContext context, Map td){
    // Not implemented
  }
  public void addTipi(Tipi t, TipiContext context, Map td) {
    addComponent(t, context, td);
  }
  public void addTipiContainer(TipiContainer t, TipiContext context, Map td) {
    throw new RuntimeException("SHIT!");
  }
}